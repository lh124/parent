//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package io.yfjz.utils;


import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author 刘琪
 * @describe: 分页工具
 * @class_name: PropertyUtils
 * @QQ：1018628825@qq.com
 * @tel:15685413726
 * @date: 2017-12-14  14:20
 **/
public class PageUtils<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private int totalCount;                             //总的条数
    private int pageSize;                               //每页显示的条数
    private int totalPage;                              //总页数
    private int currPage;                               //当前页
    private List<T> list;                               //查询到的集合

    /**
     * 不带分页功能,仅返回list,页面调用是data.page.list
     * @param 
     * @作者 田应平
     * @QQ 444141300
     * @创建时间 2018/12/23 14:34
    */
    public PageUtils(List<T> list){
        this.list = list;
    }
    
    /**
     * @method_name: PageUtils
     * @describe: 将service层查询到的数据通过构造函数返回到前端界面解析
     * @param [list：查询到的offset,limit之间的数据, totalCount：所有查询到的数据, pageSize：没有显示的条数, currPage：当前的页数]
     * @return
     * @author 刘琪
     * @QQ：1018628825@qq.com
     * @tel:15685413726
     * @date:
     **/
    public PageUtils(List<T> list, int totalCount, Map paramMap) {
        this.list = list;
        this.totalCount = totalCount;
        this.pageSize = Integer.parseInt(paramMap.get("limit").toString());
        this.currPage = Integer.parseInt(paramMap.get("page").toString());
        //查询到页数：如果查询到的总数除以每页显示总数，没有除尽自动加1
        this.totalPage = this.totalCount % this.pageSize > 0 ? (this.totalCount / this.pageSize + 1) : (this.totalCount / this.pageSize);
    }
    public PageUtils(List<T> list, int totalCount, int pageSize, int currPage) {
        this.list = list;
        this.totalCount = totalCount;
        this.pageSize = pageSize;
        this.currPage = currPage;
        //查询到页数：如果查询到的总数除以每页显示总数，没有除尽自动加1
        this.totalPage = this.totalCount % this.pageSize > 0 ? (this.totalCount / this.pageSize + 1) : (this.totalCount / this.pageSize);
    }


    public int getTotalCount() {
        return this.totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPage() {
        return this.totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getCurrPage() {
        return this.currPage;
    }

    public void setCurrPage(int currPage) {
        this.currPage = currPage;
    }

    public List<T> getList() {
        return this.list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
