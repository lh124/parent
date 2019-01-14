package io.yfjz.aop;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import io.yfjz.entity.sys.SysUserEntity;
import io.yfjz.utils.HttpContextUtils;
import io.yfjz.utils.ShiroUtils;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @class_name: AopSysLogAspect
 * @author 刘琪
 * @describe: AOP在spring中有两种配置方式，一是xml配置的方式，二是自动注解的模式。
 * 此类是自动注解的模式  -->  如日志、安全、事务、缓存等。
 *     AOP配置元素| 描述
 *  ----------- - | -------------
 *  `<aop:advisor>` | 定义AOP通知器
 *  `<aop:after>`  | 定义AOP后置通知（不管该方法是否执行成功）
 *  `<aop:after-returning>` | 在方法成功执行后调用通知
 *  `<aop:after-throwing>` | 在方法抛出异常后调用通知
 *  `<aop:around>` | 定义AOP环绕通知
 *  `<aop:aspect>` | 定义切面
 *  `<aop:aspect-autoproxy>` | 定义`@AspectJ`注解驱动的切面
 *  `<aop:before>` | 定义AOP前置通知
 *  `<aop:config>` | 顶层的AOP配置元素，大多数的<aop:*>包含在<aop:config>元素内
 *  `<aop:declare-parent>` | 为被通知的对象引入额外的接口，并透明的实现
 *  `<aop:pointcut>` | 定义切点
 * @QQ：1018628825@qq.com
 * @tel:15685413726
 * @date: 2017/12/19  16:43
 **/
@Aspect
@Service
public class AopSysLogAspect {
    private static Logger logger = Logger.getLogger(AopSysLogAspect.class);

   /* @Autowired
    MongoTemplate mongoTemplate;

    *//*
        执行过程打印。。。
        @Around             环绕通知开始 日志记录
        @Before             开始执行前置通知  日志记录: @Before
                ==>  Preparing: select count(*) from t_user
                ==> Parameters:
                <==      Total: 1
                ==>  Preparing: select * from t_user order by user_id desc limit ?, ?
                ==> Parameters: 0(Integer), 10(Integer)
                <==      Total: 10
        @Around             总共执行时长102 毫秒
        @Around             环绕通知结束 日志记录
        @After              开始执行后置通知 日志记录:
        @AfterReturning     方法成功执行后通知 日志记录:
    *//*
    // 配置切点 及要传的参数
    @Pointcut("execution(* io.yfjz.service.*.*.*(..))")
    public void pointCut() {

    }

    // 配置连接点 方法开始执行时通知
    @Before("pointCut()")
    public void beforeLog() {
//        logger.info("开始执行前置通知  日志记录:");
    }

    // 方法执行完后通知
    @After("pointCut()")
    public void afterLog() {
//        logger.info("开始执行后置通知 日志记录:");
    }

    // 执行成功后通知
    @AfterReturning("pointCut()")
    public void afterReturningLog() {
//        logger.info("方法成功执行后通知 日志记录:");
    }

    // 抛出异常后通知
    @AfterThrowing("pointCut()")
    public void afterThrowingLog() {
//        logger.info("方法抛出异常后执行通知 日志记录");
    }

    // 环绕通知
    @Around("pointCut()")
    public Object aroundLog(ProceedingJoinPoint joinpoint) throws Throwable {
        Object result = null;
        logger.info("环绕通知开始 日志记录");
        long start = System.currentTimeMillis();
        //有返回参数 则需返回值
        result = joinpoint.proceed();
        // 这里调用了proceed方法后，返回方法中返回的结果集，可以针对result来做后续的操作
        long end = System.currentTimeMillis();
        logger.info("总共执行时长" + (end - start) + " 毫秒");
        logger.info("环绕通知结束 日志记录");
        new Thread(new SaveSysLogs(joinpoint,(end - start),result)).start();
        return result;
    }


    *//**
     * @class_name: SaveSysLogs
     * @describe: 日志保存线程
     * @author: 刘琪
     * @QQ：1018628825@qq.com
     * @tel:15685413726
     * @date: 2018/8/3  6:37
     **//*
    class SaveSysLogs implements Runnable { // 实现了Runnable接口，jdk就知道这个类是一个线程
        private ProceedingJoinPoint joinPoint;
        private Long time;
        Object result;

        public SaveSysLogs() {
        }

        public SaveSysLogs(ProceedingJoinPoint joinPoint, Long time, Object result) {
            this.joinPoint = joinPoint;
            this.time = time;
            this.result = result;
        }

        @Override
        public void run() {
            DBObject saveDbObject = new BasicDBObject();
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();

            //请求的方法名
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = signature.getName();
            saveDbObject.put("method", className + "." + methodName + "()");

            //查询方法不存日志
            if (methodName.startsWith("get") || methodName.startsWith("query")
                    || methodName.startsWith("list") || methodName.startsWith("find")
                    || methodName.startsWith("select")) {
                logger.info("===={}查询方法不存入mongodb服务器");
                return;
            }

            //请求的参数
            Object[] args = joinPoint.getArgs();
            try {
                String params = new Gson().toJson(args[0]);
                saveDbObject.put("params", params);
                //获取request
                HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
                //设置IP地址
                saveDbObject.put("ip", HttpContextUtils.getIpAddr(request));
            } catch (Exception e) {
                logger.info("===={}无法获取HttpServletRequest");
            }
            SysUserEntity userEntity = ShiroUtils.getUserEntity();
            saveDbObject.put("userName", userEntity.getRealName());
            saveDbObject.put("userId", userEntity.getUserId());
            saveDbObject.put("time", time);
            saveDbObject.put("createTime", new Date());
            saveDbObject.put("result", result);
            //保存系统日志
            mongoTemplate.insert(saveDbObject, "sys_logs");
        }
    }*/
}