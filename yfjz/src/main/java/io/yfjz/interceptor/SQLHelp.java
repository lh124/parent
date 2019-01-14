package io.yfjz.interceptor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class SQLHelp {
    private static Logger logger = LoggerFactory.getLogger(SQLHelp.class);

    public static int getCount(String sql, MappedStatement mappedStatement, Object parameterObject, BoundSql boundSql, Dialect dialect)
            throws SQLException
    {
        String count_sql = dialect.getCountString(sql);
        logger.debug("Total count SQL [{}] ", count_sql);
        logger.debug("Total count Parameters: {} ", parameterObject);

        Connection connection = null;
        PreparedStatement countStmt = null;
        ResultSet rs = null;
        try
        {
            connection = mappedStatement.getConfiguration().getEnvironment().getDataSource().getConnection();
            countStmt = connection.prepareStatement(count_sql);
            BoundSql countBS = new BoundSql(mappedStatement.getConfiguration(), count_sql, boundSql.getParameterMappings(), parameterObject);

            DefaultParameterHandler handler = new DefaultParameterHandler(mappedStatement, parameterObject, countBS);
            handler.setParameters(countStmt);

            rs = countStmt.executeQuery();
            int count = 0;
            if (rs.next()) {
                count = rs.getInt(1);
            }
            logger.debug("Total count: {}", Integer.valueOf(count));
            return count;
        }
        finally
        {
            try
            {
                if (rs != null) {
                    rs.close();
                }
            }
            finally
            {
                try
                {
                    if (countStmt != null) {
                        countStmt.close();
                    }
                }
                finally
                {
                    if ((connection != null) && (!connection.isClosed())) {
                        connection.close();
                    }
                }
            }
        }
    }
}
