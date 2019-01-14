package io.yfjz.interceptor;

import org.apache.ibatis.session.RowBounds;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PageBounds extends RowBounds implements Serializable {
    private static final long serialVersionUID = 4018518870259558581L;
    public static final int NO_PAGE = 1;
    protected int page = 1;
    protected int pageSize = Integer.MAX_VALUE;
    protected List<Order> orders = new ArrayList();
    protected boolean containsTotalCount = true;
    protected Boolean asyncTotalCount;

    public PageBounds() {}

    public PageBounds(RowBounds rowBounds)
    {
        if ((rowBounds instanceof PageBounds))
        {
            PageBounds pageBounds = (PageBounds)rowBounds;
            this.page = pageBounds.page;
            this.pageSize = pageBounds.pageSize;
            this.orders = pageBounds.orders;
            this.containsTotalCount = pageBounds.containsTotalCount;
            this.asyncTotalCount = pageBounds.asyncTotalCount;
        }
        else
        {
            this.page = (rowBounds.getOffset() / rowBounds.getLimit() + 1);
            this.pageSize = rowBounds.getLimit();
        }
    }

    public PageBounds(int pageSize)
    {
        this.pageSize = pageSize;
        this.containsTotalCount = false;
    }

    public PageBounds(int page, int pageSize)
    {
        this(page, pageSize, new ArrayList(), true);
    }

    public PageBounds(List<Order> orders)
    {
        this(1, Integer.MAX_VALUE, orders, false);
    }

    public PageBounds(Order... order)
    {
        this(1, Integer.MAX_VALUE, order);
        this.containsTotalCount = false;
    }

    public PageBounds(int page, int pageSize, Order... order)
    {
        this(page, pageSize, Arrays.asList(order), true);
    }

    public PageBounds(int page, int pageSize, List<Order> orders)
    {
        this(page, pageSize, orders, true);
    }

    public PageBounds(int page, int pageSize, List<Order> orders, boolean containsTotalCount)
    {
        this.page = page;
        this.pageSize = pageSize;
        this.orders = orders;
        this.containsTotalCount = containsTotalCount;
    }

    public int getPage()
    {
        return this.page;
    }

    public void setPage(int page)
    {
        this.page = page;
    }

    public int getPageSize()
    {
        return this.pageSize;
    }

    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
    }

    public boolean isContainsTotalCount()
    {
        return this.containsTotalCount;
    }

    public void setContainsTotalCount(boolean containsTotalCount)
    {
        this.containsTotalCount = containsTotalCount;
    }

    public List<Order> getOrders()
    {
        return this.orders;
    }

    public void setOrders(List<Order> orders)
    {
        this.orders = orders;
    }

    public Boolean getAsyncTotalCount()
    {
        return this.asyncTotalCount;
    }

    public void setAsyncTotalCount(Boolean asyncTotalCount)
    {
        this.asyncTotalCount = asyncTotalCount;
    }

    public int getOffset()
    {
        if (this.page >= 1) {
            return (this.page - 1) * this.pageSize;
        }
        return 0;
    }

    public String toString()
    {
        StringBuilder sb = new StringBuilder("PageBounds{");
        sb.append("page=").append(this.page);
        sb.append(", limit=").append(this.pageSize);
        sb.append(", orders=").append(this.orders);
        sb.append(", containsTotalCount=").append(this.containsTotalCount);
        sb.append(", asyncTotalCount=").append(this.asyncTotalCount);
        sb.append('}');
        return sb.toString();
    }
}
