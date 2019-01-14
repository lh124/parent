package io.yfjz.interceptor;

import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import io.yfjz.entity.PersistentEntity;
import io.yfjz.utils.AESCodec;
import io.yfjz.utils.PropertiesUtils;
import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Intercepts({@org.apache.ibatis.plugin.Signature(type=Executor.class, method="query", args={MappedStatement.class, Object.class, RowBounds.class, org.apache.ibatis.session.ResultHandler.class})})
public class OffsetLimitInterceptor implements Interceptor {
    private static Logger logger = LoggerFactory.getLogger(OffsetLimitInterceptor.class);
    static int MAPPED_STATEMENT_INDEX = 0;
    static int PARAMETER_INDEX = 1;
    static int ROWBOUNDS_INDEX = 2;
    static int RESULT_HANDLER_INDEX = 3;
    private Properties properties;
    static ExecutorService Pool;
    Dialect dialect;
    boolean asyncTotalCount = false;
    private static String path = "AES.txt";
    private static String KEY_PATH = "";
    private static String[] aesCodes = new String[0];

    public Object intercept(final Invocation invocation)throws Throwable
    {
            if (aesCodes.length == 0) {
                String aesCodeStr = PropertiesUtils.getProperty("aesCode.properties", "AESCODE");
                if ((aesCodeStr != null) && (!"".equals(aesCodeStr))) {
                    aesCodes = aesCodeStr.split(";");
                }
                String localPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
                if (localPath.indexOf("%") >= 0) {
                    localPath = URLDecoder.decode(localPath, "utf-8");
                }
                KEY_PATH = localPath + path;
            }
            final Executor executor = (Executor) invocation.getTarget();

            Object[] queryArgs = invocation.getArgs();

            final MappedStatement ms = (MappedStatement) queryArgs[MAPPED_STATEMENT_INDEX];

            Object finalPar = queryArgs[PARAMETER_INDEX];
            BoundSql ss = ms.getBoundSql(finalPar);
            String oldSql = ss.getSql().trim();
            boolean noNeedAESDecode = false;
            if (finalPar != null) {
                if ((finalPar instanceof Map)) {
                    Map<Object, Object> val = (Map) finalPar;
                    if (!val.containsKey("noNeedAESDecode")) {
                        noNeedAESDecode = true;
                        for (String code : aesCodes) {
                            String[] aesCode = code.split(":");
                            String table = aesCode[0].substring(0, aesCode[0].indexOf("["));
                            String[] collName = aesCode[1].split(",");
                            if (oldSql.toLowerCase().contains(table.toLowerCase())) {
                                for (String name : collName) {
                                    if ((val.containsKey(name)) && (val.get(name) != null) && (!"".equals(val.get(name)))) {
                                        val.put(name, AESCodec.getAESEncode_new(val.get(name).toString(), KEY_PATH));
                                    }
                                }
                            }
                        }
                    }
                }else{
                    noNeedAESDecode = true;
                }
            } else {
                noNeedAESDecode = true;
            }
            final Object parameter = finalPar;

            RowBounds rowBounds = (RowBounds) queryArgs[ROWBOUNDS_INDEX];

            PageBounds pageBounds = new PageBounds(rowBounds);
            int offset = pageBounds.getOffset();
            final int limit = pageBounds.getPageSize();
            final int page = pageBounds.getPage();

            final BoundSql boundSql = ms.getBoundSql(parameter);

            final StringBuffer bufferSql = new StringBuffer(boundSql.getSql().trim());
            if (bufferSql.lastIndexOf(";") == bufferSql.length() - 1) {
                bufferSql.deleteCharAt(bufferSql.length() - 1);
            }
            String sql = bufferSql.toString();
            if ((pageBounds.getOrders() != null) && (!pageBounds.getOrders().isEmpty())) {
                sql = this.dialect.getSortString(sql, pageBounds.getOrders());
            }
            Object countTask = null;
            if ((this.dialect.supportsLimit()) && ((offset != 0) || (limit != Integer.MAX_VALUE))) {
                if (pageBounds.isContainsTotalCount()) {
                    countTask = new Callable() {
                        public Object call()
                                throws Exception {
                            Integer count = null;
                            Cache cache = ms.getCache();
                            if ((cache != null) && (ms.isUseCache())) {
                                CacheKey cacheKey = executor.createCacheKey(ms, parameter, new PageBounds(), OffsetLimitInterceptor.this.copyFromBoundSql(ms, boundSql, bufferSql.toString()));
                                count = (Integer) cache.getObject(cacheKey);
                                if (count == null) {
                                    count = Integer.valueOf(SQLHelp.getCount(bufferSql.toString(), ms, parameter, boundSql, OffsetLimitInterceptor.this.dialect));
                                    cache.putObject(cacheKey, count);
                                }
                            } else {
                                count = Integer.valueOf(SQLHelp.getCount(bufferSql.toString(), ms, parameter, boundSql, OffsetLimitInterceptor.this.dialect));
                            }
                            return new Paginator(page, limit, count.intValue());
                        }
                    };
                }
                if (this.dialect.supportsLimitOffset()) {
                    sql = this.dialect.getLimitString(sql);
                } else {
                    sql = this.dialect.getLimitString(sql);
                }
                queryArgs[ROWBOUNDS_INDEX] = new RowBounds(0, Integer.MAX_VALUE);
            }
            queryArgs[MAPPED_STATEMENT_INDEX] = copyFromNewSql(ms, boundSql, sql);

            Boolean async = Boolean.valueOf(pageBounds.getAsyncTotalCount() == null ? this.asyncTotalCount : pageBounds.getAsyncTotalCount().booleanValue());

            Future<List> listFuture = call(new Callable() {
                public List call()
                        throws Exception {
                    return (List) invocation.proceed();
                }
            }, async.booleanValue());
            if (countTask != null) {
                Future<Paginator> countFutrue = call((Callable) countTask, async.booleanValue());
                PageList list = new PageList((Collection) listFuture.get(), (Paginator) countFutrue.get());
                if (noNeedAESDecode) {
                    list = (PageList) getAESDecode_newObject(list, sql);
                }
                return list;
            }
            List fut = (List) listFuture.get();
            if (noNeedAESDecode) {
                fut = (List) getAESDecode_newObject(fut, sql);
            }
            return fut;
        }

        private <T> Future <T> call(Callable callable, boolean async)
        {
            if (async) {
                return Pool.submit(callable);
            }
            FutureTask<T> future = new FutureTask(callable);
            future.run();
            return future;
        }

        private MappedStatement copyFromNewSql (MappedStatement ms, BoundSql boundSql, String sql)
        {
            BoundSql newBoundSql = copyFromBoundSql(ms, boundSql, sql);
            return copyFromMappedStatement(ms, new BoundSqlSqlSource(newBoundSql));
        }

        private BoundSql copyFromBoundSql (MappedStatement ms, BoundSql boundSql, String sql)
        {
            BoundSql newBoundSql = new BoundSql(ms.getConfiguration(), sql, boundSql.getParameterMappings(), boundSql.getParameterObject());
            for (ParameterMapping mapping : boundSql.getParameterMappings()) {
                String prop = mapping.getProperty();
                if (boundSql.hasAdditionalParameter(prop)) {
                    newBoundSql.setAdditionalParameter(prop, boundSql.getAdditionalParameter(prop));
                }
            }
            return newBoundSql;
        }

        private MappedStatement copyFromMappedStatement (MappedStatement ms, SqlSource newSqlSource)
        {
            MappedStatement.Builder builder = new MappedStatement.Builder(ms.getConfiguration(), ms.getId(), newSqlSource, ms.getSqlCommandType());

            builder.resource(ms.getResource());
            builder.fetchSize(ms.getFetchSize());
            builder.statementType(ms.getStatementType());
            builder.keyGenerator(ms.getKeyGenerator());
            if ((ms.getKeyProperties() != null) && (ms.getKeyProperties().length != 0)) {
                StringBuffer keyProperties = new StringBuffer();
                for (String keyProperty : ms.getKeyProperties()) {
                    keyProperties.append(keyProperty).append(",");
                }
                keyProperties.delete(keyProperties.length() - 1, keyProperties.length());
                builder.keyProperty(keyProperties.toString());
            }
            builder.timeout(ms.getTimeout());

            builder.parameterMap(ms.getParameterMap());

            builder.resultMaps(ms.getResultMaps());
            builder.resultSetType(ms.getResultSetType());

            builder.cache(ms.getCache());
            builder.flushCacheRequired(ms.isFlushCacheRequired());
            builder.useCache(ms.isUseCache());

            return builder.build();
        }

        public Object plugin (Object target)
        {
            return Plugin.wrap(target, this);
        }

        public void setProperties (Properties properties)
        {
            PropertiesHelper propertiesHelper = new PropertiesHelper(properties);
            String dialectClass = propertiesHelper.getRequiredString("dialectClass");
            try {
                setDialect((Dialect) Class.forName(dialectClass).newInstance());
            } catch (Exception e) {
                throw new RuntimeException("cannot create dialect instance by dialectClass:" + dialectClass, e);
            }
            setAsyncTotalCount(propertiesHelper.getBoolean("asyncTotalCount", false));

            setPoolMaxSize(propertiesHelper.getInt("poolMaxSize", 0));
        }

        public static class BoundSqlSqlSource
                implements SqlSource {
            BoundSql boundSql;

            public BoundSqlSqlSource(BoundSql boundSql) {
                this.boundSql = boundSql;
            }

            public BoundSql getBoundSql(Object parameterObject) {
                return this.boundSql;
            }
        }

        public void setDialect (Dialect dialect)
        {
            logger.debug("dialectClass: {} ", dialect.getClass().getName());
            this.dialect = dialect;
        }

        public void setAsyncTotalCount ( boolean asyncTotalCount)
        {
            logger.debug("asyncTotalCount: {} ", Boolean.valueOf(asyncTotalCount));
            this.asyncTotalCount = asyncTotalCount;
        }

        public void setPoolMaxSize ( int poolMaxSize)
        {
            if (poolMaxSize > 0) {
                logger.debug("poolMaxSize: {} ", Integer.valueOf(poolMaxSize));
                Pool = Executors.newFixedThreadPool(poolMaxSize);
            } else {
                Pool = Executors.newCachedThreadPool();
            }
        }

    /**
     * 获取解密对象
     * @param object
     * @param sql
     * @return
     * @throws Throwable
     */
        private Object getAESDecode_newObject (List < Object > object, String sql)
          throws Throwable
        {
            for (Object ob : object) {
                int str1;
                String code;
                int str2;
                String name;
                if ((ob instanceof PersistentEntity)) {//判断要解密的对象是不是实现PersistentEntity接口
                    String[] arrayOfString1 = aesCodes;
                    int i = arrayOfString1.length;
                    for (str1 = 0; str1 < i; str1++) {
                        code = arrayOfString1[str1];
                        String[] aesCode = code.split(":");
                        String table = aesCode[0].substring(0, aesCode[0].indexOf("["));
                        String[] collName = aesCode[1].split(",");
                        if (sql.toLowerCase().contains(table.toLowerCase())) {//sql中是否含有要解密的字段的表
                            String[] arrayOfString3 = collName;
                            int j = arrayOfString3.length;
                            for (str2 = 0; str2 < j; str2++) {
                                name = arrayOfString3[str2];
                                String tableAlias = aesCode[0].substring(aesCode[0].indexOf("[") + 1, aesCode[0].indexOf("]"));//表的别名
                                Object obAlias = null;
                                if ((tableAlias.length() > 0) && (getField(ob, name) == null)) {
                                    obAlias = getFieldValue(ob, tableAlias);
                                } else {
                                    obAlias = ob;
                                }
                                if (obAlias != null) {
                                    Object obVal = getFieldValue(obAlias, name);
                                    if ((obVal != null) && (!"".equals(obVal))) {
                                        try {
                                            setFieldValue(obAlias, name, AESCodec.getAESDecode_new(obVal.toString(), KEY_PATH));
                                        } catch (Exception e) {
                                            logger.debug(name + "========>" + obVal.toString() + "��������");
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else if ((ob instanceof Map)) {
                    Object val = (Map) ob;
                    String[] arrayOfString2 = aesCodes;
                    str1 = arrayOfString2.length;
                    for (int coded = 0; coded < str1; coded++) {
                        String codes = arrayOfString2[coded];
                        String[] aesCode = codes.split(":");
                        String table = aesCode[0].substring(0, aesCode[0].indexOf("["));
                        String[] collName = aesCode[1].split(",");
                        if (sql.toLowerCase().contains(table.toLowerCase())) {
                            String[] arrayOfString4 = collName;
                            str2 = arrayOfString4.length;
                            for (int names = 0; names < str2; names++) {
                                 name = arrayOfString4[names];
                                if ((((Map) val).containsKey(name)) && (((Map) val).get(name) != null) && (!"".equals(((Map) val).get(name)))) {
                                    try {
                                        ((Map) val).put(name, AESCodec.getAESDecode_new(((Map) val).get(name).toString(), KEY_PATH));
                                    } catch (Exception e) {
                                        logger.debug(name + "========>" + ((Map) val).get(name).toString() + "��������");
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return object;
        }

        public static Object getFieldValue (Object obj, String fieldName)
        {
            Object result = null;
            Field field = getField(obj, fieldName);
            if (field != null) {
                field.setAccessible(true);
                try {
                    result = field.get(obj);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            return result;
        }

        private static Field getField (Object obj, String fieldName)
        {
            Field field = null;
            for (Class<?> clazz = obj.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
                try {
                    field = clazz.getDeclaredField(fieldName);
                } catch (NoSuchFieldException localNoSuchFieldException) {
                }
            }
            return field;
        }

        public static void setFieldValue (Object obj, String fieldName, String fieldValue)
        {
            Field field = getField(obj, fieldName);
            if (field != null) {
                try {
                    field.setAccessible(true);
                    field.set(obj, fieldValue);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

