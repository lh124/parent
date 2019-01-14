package io.yfjz.entity.tree;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @describe: easyui tree 实体
 * class_name: TreeNode
 * @author 刘琪
 * @QQ：1018628825@qq.com
 * @tel:15685413726
 * @date: 2017/12/25  21:09
 **/

@Getter
@Setter
public class TreeNode {
    private String code;
    private String name;
    private String pid;
    private Boolean open;
    private List<TreeNode> children = new ArrayList<>();//必须这样处理，否则子节点无法加载出来

}
