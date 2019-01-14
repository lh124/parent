package io.yfjz.utils;
import java.util.List;


/**
 * @describe: 表实体
 * class_name: TableEntity
 * @author 刘琪
 * @QQ：1018628825@qq.com
 * @tel:15685413726
 * @date: 2017/12/25  21:39
 **/
public class TableEntity {
    private String tableName;//表名
    private String comments;//表注释
    private ColumnEntity pk;//主键
    private List<ColumnEntity> columns;//字段
    private String className;//类名
    private String classname;//类名

    public TableEntity() {
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getComments() {
        return this.comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public ColumnEntity getPk() {
        return this.pk;
    }

    public void setPk(ColumnEntity pk) {
        this.pk = pk;
    }

    public List<ColumnEntity> getColumns() {
        return this.columns;
    }

    public void setColumns(List<ColumnEntity> columns) {
        this.columns = columns;
    }

    public String getClassName() {
        return this.className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassname() {
        return this.classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }
}
