package io.yfjz.entity.queue;

import lombok.Getter;
import lombok.Setter;
import org.apache.activemq.command.ActiveMQTopic;

import java.util.Map;

/**
 * @author Woods
 * @email oceans.woods@gmail.com
 * @date 2018-08-22 23:59:55
 */

@Getter
@Setter
public class QueueConfig {
    private Map<String,ActiveMQTopic> topicMap;
}
