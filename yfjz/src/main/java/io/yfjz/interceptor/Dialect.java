package io.yfjz.interceptor;

import java.io.Serializable;
import java.util.List;

public class Dialect implements Serializable {
    public boolean supportsLimit()
    {
        return false;
    }

    public boolean supportsLimitOffset()
    {
        return supportsLimit();
    }

    public String getLimitString(String sql)
    {
        return getLimitString(sql);
    }

    /*public String getLimitString(String sql, int offset, String offsetPlaceholder, int limit, String limitPlaceholder)
    {
        throw new UnsupportedOperationException("paged queries not supported");
    }*/

    public String getCountString(String sql)
    {
        return "select count(1) from (" + sql + ") tmp_count";
    }

    public String getSortString(String sql, List<Order> orders)
    {
        if ((orders == null) || (orders.isEmpty())) {
            return sql;
        }
        StringBuffer buffer = new StringBuffer("select * from (").append(sql).append(") temp_order order by ");
        for (Order order : orders) {
            if (order != null) {
                buffer.append(order.toString()).append(", ");
            }
        }
        buffer.delete(buffer.length() - 2, buffer.length());
        return buffer.toString();
    }
}
