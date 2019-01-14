//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package io.yfjz.entity.sys;

import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class SysMenuEntity implements Serializable,Cloneable  {
    private static final long serialVersionUID = 1L;
    private String menuId;
    private String parentId;
    private String parentName;
    private String name;
    private String url;
    private String perms;
    private Integer type;
    private String icon;
    private Integer orderNum;
    private Boolean open;
    private List<SysMenuEntity> list;
    private Integer status;
    private Integer deleteStatus;
    private Date createTime;
    private Date updateTime;
    /**
     * 是否拥有，0未用有，1拥有
     */
    private int hasStatus=0;
    @Override
    public Object clone() {
        SysMenuEntity o = null;
        try {
            o = (SysMenuEntity) super.clone();
        } catch (CloneNotSupportedException e) {
            System.out.println(e.toString());
        }
        return o;
    }
    public Object deepClone() throws IOException, OptionalDataException,
            ClassNotFoundException {
        // 将对象写到流里
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        ObjectOutputStream oo = new ObjectOutputStream(bo);
        oo.writeObject(this);
        // 从流里读出来
        ByteArrayInputStream bi = new ByteArrayInputStream(bo.toByteArray());
        ObjectInputStream oi = new ObjectInputStream(bi);
        return (oi.readObject());
    }
}
