package io.yfjz.activemq;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Woods
 * @email oceans.woods@gmail.com
 * @date 2018-08-22 23:59:55
 */

@Getter
@Setter
public class QueueMessage {
    //发送者
    private String sender;
    //动作
    private String action;
    //号码
    private String number;

}
