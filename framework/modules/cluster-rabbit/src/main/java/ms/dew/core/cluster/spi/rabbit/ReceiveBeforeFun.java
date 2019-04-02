/*
 * Copyright 2019. the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ms.dew.core.cluster.spi.rabbit;

import com.rabbitmq.client.AMQP;

/**
 * The interface Receive before fun.
 *
 * @author gudaoxuri
 */
@FunctionalInterface
public interface ReceiveBeforeFun {

    /**
     * Invoke object.
     *
     * @param exchange          the exchange
     * @param routingKey        the routing key
     * @param queueName         the queue name
     * @param messageProperties the message properties
     * @return the object
     */
    Object invoke(String exchange, String routingKey,String queueName,AMQP.BasicProperties messageProperties);

}