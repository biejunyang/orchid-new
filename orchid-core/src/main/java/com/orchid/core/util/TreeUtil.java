package com.orchid.core.util;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.orchid.core.model.TreeNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


public class TreeUtil {


    /**
     * 通过集合构建树
     * @param nodes
     * @return
     */
    public static <T extends TreeNode> List<T> buildTree(List<T> nodes){

        List<T> roots=new ArrayList<>();
        for(T node: nodes){
            List<TreeNode> children= CollectionUtil.newArrayList();
            for(T child: nodes){
                if(child.getId().equals(node.getId())){
                    continue;
                }
                if(node.getId().equals(child.getPid())){
                    children.add(child);
                }

            }
            node.setChildren(children);

            if(ObjectUtil.isEmpty(node.getPid())){
                roots.add(node);
            }
        }
        return roots;
    }



//
//    /**
//     * 广度优先搜索算法（英语：Breadth-First-Search，缩写为BFS）
//     * @return
//     */
//    public List<T> breadthFirstTraversal(){
//        List<T> breadthFirstTraversalList=new ArrayList<>();
//
//        LinkedList<com.orchid.core.util.tree.TreeNode> queue=new LinkedList<>();
//        queue.add(root);
//
//        com.orchid.core.util.tree.TreeNode<T> node=null;
//        while ((node=queue.pollFirst()) !=null){
//            breadthFirstTraversalList.add(node.getValue());
//            if(node.getChildren()!=null && node.getChildren().size()>0){
//                node.getChildren().forEach(child -> {
//                    queue.add(child);
//                });
//            }
//        }
//
//        return breadthFirstTraversalList;
//    }
//
//    /**
//     * 深度优先搜索算法（英语：Depth-First-Search，DFS）
//     * @return
//     */
//    public List<T> depthFirstTraversal(){
//        List<T> depthFirstTraversalList=new ArrayList<>();
//
//        LinkedList<com.orchid.core.util.tree.TreeNode> stack=new LinkedList<>();
//
//        stack.push(root);
//
//        com.orchid.core.util.tree.TreeNode<T> node=null;
//        while ((node=stack.pop()) !=null){
//            depthFirstTraversalList.add(node.getValue());
//
//            if(node.getChildren()!=null && node.getChildren().size()>0){
//                Collections.reverse(node.getChildren());
//                node.getChildren().forEach(child -> {
//                    stack.push(child);
//                });
//            }
//        }
//
//        return depthFirstTraversalList;
//    }
}
