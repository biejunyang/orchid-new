package com.orchid.core.util.tree;

/**
 * 树性结构操作异常
 */
public class TreeOperationException extends RuntimeException {

    public TreeOperationException() {
        super("tree operation exception");
    }

    public TreeOperationException(String message) {
        super(message);
    }
}
