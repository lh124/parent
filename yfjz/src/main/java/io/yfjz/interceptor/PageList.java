package io.yfjz.interceptor;

import java.util.ArrayList;
import java.util.Collection;

public class PageList<E> extends ArrayList<E> {
    private static final long serialVersionUID = -3267390730341394727L;
    private Paginator paginator;

    public PageList() {}

    public PageList(Collection<? extends E> c)
    {
        super(c);
    }

    public PageList(Paginator p)
    {
        this.paginator = p;
    }

    public PageList(Collection<? extends E> c, Paginator p)
    {
        super(c);
        this.paginator = p;
    }

    public Paginator getPaginator()
    {
        if (this.paginator == null) {
            this.paginator = new Paginator(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);
        }
        return this.paginator;
    }

    public void setPaginator(Paginator paginator)
    {
        this.paginator = paginator;
    }
}
