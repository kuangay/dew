package com.tairanchina.csp.dew.core.cluster;

import com.tairanchina.csp.dew.core.cluster.ha.ClusterHA;
import com.tairanchina.csp.dew.core.cluster.ha.H2ClusterHA;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 集群服务
 */
public class Cluster {
    private static final Logger logger = LoggerFactory.getLogger(Cluster.class);

    public static final String CLASS_LOAD_UNIQUE_FLAG = UUID.randomUUID().toString();


    private static Function<String, Map<String, Object>> _mqGetHeader;
    private static Consumer<Object[]> _mqSetHeader;
    private static ClusterHA clusterHA = null;

    public static void initMQHeader(Function<String, Map<String, Object>> mqGetHeader, Consumer<Object[]> mqSetHeader) {
        _mqGetHeader = mqGetHeader;
        _mqSetHeader = mqSetHeader;
    }

    public static void ha() {
        clusterHA = new H2ClusterHA();
        try {
            clusterHA.init(new HashMap<String, String>() {{
                put("url", "jdbc:h2:./.cluster/ha;DB_CLOSE_ON_EXIT=FALSE");
            }});
            logger.info("HA initialized");
        } catch (SQLException e) {
            logger.error("HA init error", e);
        }
    }

    public static boolean haEnabled() {
        return clusterHA != null;
    }

    public static ClusterHA getClusterHA(){
        return clusterHA;
    }

    public static Map<String, Object> getMQHeader(String name) {
        if (_mqGetHeader != null) {
            return _mqGetHeader.apply(name);
        } else {
            return null;
        }
    }

    public static void setMQHeader(String name, Map<String, Object> header) {
        if (_mqSetHeader != null) {
            _mqSetHeader.accept(new Object[]{name, header});
        }
    }

    /**
     * MQ服务
     */
    public ClusterMQ mq;

    /**
     * 常用分布式服务，锁与Map
     */
    public ClusterDist dist;

    /**
     * 缓存服务
     */
    public ClusterCache cache;

    /**
     * 领导者选举服务
     */
    public ClusterElection election;

}
