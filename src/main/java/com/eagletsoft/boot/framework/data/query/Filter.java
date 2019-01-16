package com.eagletsoft.boot.framework.data.query;

public class Filter {

    public static String OP_EQ = "=";
    public static String OP_NEQ = "<>";
    public static String OP_LE = "<=";
    public static String OP_GE = ">=";
    public static String OP_LT = "<";
    public static String OP_GT = ">";
    public static String OP_LIKE = "*";
    public static String OP_LEFT_LIKE = "%";
    public static String OP_IN = "IN";
    public static String OP_IS_NULL = "NULL";

    private String name;
    private String op;
    private Object value;

    public Filter() {}

    public Filter(String name, String op, Object value) {
        super();
        this.name = name;
        this.op = op;
        this.value = value;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getOp() {
        return op;
    }
    public void setOp(String op) {
        this.op = op;
    }
    public Object getValue() {
        return value;
    }
    public void setValue(Object value) {
        this.value = value;
    }
}
