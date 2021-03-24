package com.orchid.core.model;

import java.util.List;

/**
 * 树节点
 */
public interface TreeNode {
    Object getId();

    Object getPid();

    void setChildren(List<TreeNode> children);
}
