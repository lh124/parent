package io.yfjz.entity.mongodb;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author 刘琪
 * @class_name: MongoUser
 * @Description:
 * @QQ: 1018628825@qq.com
 * @tel: 15685413726
 * @date: 2018-08-15 21:17
 */
@Setter
@Getter
@ToString
public class MongoUser {

    private String _id;
    private String method;
    private String params;
    private String userName;
    private String userId;
    private Long time;
    private Date createTime;
    private String result;
}
