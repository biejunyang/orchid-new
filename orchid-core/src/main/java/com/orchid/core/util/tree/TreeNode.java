package com.orchid.core.util.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * 树形结构节点
 * @param <T>
 */
public class TreeNode<T extends TreeElement<T>> {

    private T value;

    private TreeNode<T> father;

    private List<TreeNode<T>> children=new ArrayList<>();


    public TreeNode(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public TreeNode<T> getFather() {
        return father;
    }

    public void setFather(TreeNode<T> father) {
        this.father = father;
    }

    public List<TreeNode<T>> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode<T>> children) {
        this.children = children;
    }
}
