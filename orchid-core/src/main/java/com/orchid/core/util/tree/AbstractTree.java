package com.orchid.core.util.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * 一棵树结构
 * @param <T>
 */
public class AbstractTree<T extends TreeElement<T>>{

    //根节点,
    private TreeNode<T> root;

    //节点个数
    private int size;


    /**
     * 添加元素
     * @param value
     */
    public void add(T value){
        if(root==null){
            root=new TreeNode(value);
        }else{
            LinkedList<TreeNode> queue=new LinkedList<>();
            queue.add(root);

            TreeNode<T> parentNode=null;
            while ((parentNode=queue.pollFirst()) !=null){
                if(parentNode.getValue().equals(value.parent())){
                    break;
                }
            }

            if(parentNode!=null){
                TreeNode node=new TreeNode(value);
                node.setFather(parentNode);
                parentNode.getChildren().add(node);
            }else{
                throw new TreeOperationException("can't find father node!!!!");
            }
        }
        size++;
    }





    /**
     * 广度优先搜索算法（英语：Breadth-First-Search，缩写为BFS）
     * @return
     */
    public List<T> breadthFirstTraversal(){
        List<T> breadthFirstTraversalList=new ArrayList<>();

        LinkedList<TreeNode> queue=new LinkedList<>();
        queue.add(root);

        TreeNode<T> node=null;
        while ((node=queue.pollFirst()) !=null){
            breadthFirstTraversalList.add(node.getValue());
            if(node.getChildren()!=null && node.getChildren().size()>0){
                node.getChildren().forEach(child -> {
                    queue.add(child);
                });
            }
        }

        return breadthFirstTraversalList;
    }

    /**
     * 深度优先搜索算法（英语：Depth-First-Search，DFS）
     * @return
     */
    public List<T> depthFirstTraversal(){
        List<T> depthFirstTraversalList=new ArrayList<>();

        LinkedList<TreeNode> stack=new LinkedList<>();

        stack.push(root);

        TreeNode<T> node=null;
        while ((node=stack.pop()) !=null){
            depthFirstTraversalList.add(node.getValue());

            if(node.getChildren()!=null && node.getChildren().size()>0){
                Collections.reverse(node.getChildren());
                node.getChildren().forEach(child -> {
                    stack.push(child);
                });
            }
        }

        return depthFirstTraversalList;
    }

}
