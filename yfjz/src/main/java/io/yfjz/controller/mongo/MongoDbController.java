/*
package io.yfjz.controller.mongo;

import com.mongodb.BasicDBObject;
import io.yfjz.utils.R;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

*/
/**
 * @author 刘琪
 * @class_name: MongoDbController
 * @Description: mongodb控制器
 * @QQ: 1018628825@qq.com
 * @tel: 15685413726
 * @date: 2018-08-03 10:24
 *//*

@Controller
@RequestMapping("/mongo")
public class MongoDbController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    MongoTemplate mongoTemplate;


    @RequestMapping("/mongolist")
    @ResponseBody
    public R mongolist(){
//        Query query = new Query();
//        query.addCriteria(Criteria.where("userName").is("admin"));
//        //排序
//        query.with(new Sort(Sort.Direction.ASC, "time"));
//        List<MongoUser> users = mongoTemplate.find(query, MongoUser.class,"sys_logs");
//        //分页
//        final Pageable pageableRequest = new PageRequest(0, 2);
//        query.with(pageableRequest);
//        System.out.println(users.toString());


        //多条件查询
//        Criteria criteria = new Criteria(); criteria.orOperator(Criteria.where("classId").is("2"),Criteria.where("teacher").is("Mr.wang"));
//        criteria.andOperator(Criteria.where("classId").is("2"));
//        query.addCriteria(criteria);


//        Aggregation  agg = Aggregation.newAggregation(
//                project("sys_logs")
//                ,group("userName").count().as("totalNum")
//                ,match(Criteria.where("totalNum").gte(4))
//                ,sort(Sort.Direction.DESC, "totalNum")
//        );

//        AggregationResults<BasicDBObject> result = mongoTemplate.aggregate(agg,"sys_logs", BasicDBObject.class);
//        System.out.println(agg.toString());
//        //执行语句差不多
//        //{ "aggregate" : "__collection__" , "pipeline" : [ { "$project" : { "userName" : 1}} , { "$group" : { "_id" : "$userName" , "totalNum" : { "$sum" : 1}}} , { "$match" : { "totalNum" : { "$gte" : 4}}} , { "$sort" : { "totalNum" : -1}}]}
//        System.out.println(result.getMappedResults());
//        //查询结果简洁明了
//        //[{ "_id" : 0 , "totalNum" : 10047}, { "_id" : 1 , "totalNum" : 9955}]
//
//        //使用此方法，如果封装好了某一个类，类里面的属性和结果集的属性一一对应，那么，Spring是可以直接把结果集给封装进去的
//        //就是AggregationResults<BasicDBObject> result = mongoTemplate.aggregate(agg, BasicDBObject);中的BasicDBObject改为自己封装的类
//        //但是感觉这样做有点不灵活，其实吧，应该是自己现在火候还不到，还看不到他的灵活性，好处在哪里；等火候旺了再说呗
//        //所以，就用这个万能的BasicDBObject类来封装返回结果
//        List<BasicDBObject> resultList = result.getMappedResults();
//
//
//        for (BasicDBObject object : resultList){
//            System.out.println(object.toString());
//        }

        Aggregation agg = newAggregation(
//                project("method","params","createTime","userName","userId","result","time"),//挑选所需的字段
                match(
                        Criteria.where("time").gt(560)
                ),//筛选符合条件的记录
//                unwind("frags"),//如果有MASTER-ITEM关系的表，需同时JOIN这两张表的，展开子项LIST，且是内链接，即如果父和子的关联ID没有的就不会输出
                group("method")//设置分组字段
                        .count().as("mCount")//增加COUNT为分组后输出的字段
//                project("method","params","createTime","userName","userId","result","time")//重新挑选字段
//                        .and("method").previousOperation()//为前一操作所产生的ID FIELD建立别名

        );

        AggregationResults<BasicDBObject> results = mongoTemplate.aggregate(agg, "sys_logs", BasicDBObject.class);
        List<BasicDBObject> cat1UpdateCountList = results.getMappedResults();

        return R.ok().put("users",cat1UpdateCountList);
    }
}

@Setter
@Getter
@ToString
class MongoUser{
    private String _id;
    private String method;
    private String params;
    private String userName;
    private String userId;
    private Long time;
    private Date createTime;
    private String result;

}
*/
