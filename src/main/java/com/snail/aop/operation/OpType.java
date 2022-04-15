package com.snail.aop.operation;

public enum OpType {
    ADD("新增"),
    DEL("删除"),
    MODITY("修改"),
    UPDATE("更新");

    private String opKind;

    OpType(String opKind) {
        this.opKind = opKind;
    }

    public String getOpKind() {
        return opKind;
    }
}
