package io.yfjz.entity.provinceplatform;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import javax.persistence.Transient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Oceans <oceans.woods@gmail.com>
 * @date 2018/6/2
 */
@XStreamAlias("children")
public class Children implements Serializable {
   /* @XStreamAlias("child")
    private Child child;*/
    @Transient
    /*@XStreamAlias("child")*/
    @XStreamImplicit(itemFieldName="child")
    private List<Child> childs = new ArrayList<>();

    public List<Child> getChilds() {
        return childs;
    }

    public void setChilds(List<Child> childs) {
        this.childs = childs;
    }
}
