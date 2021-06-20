package com.trankimtung.kotlin.kreflect.test;

@SuppressWarnings("unused")
public class JavaTestClass {

    private String prop1 = "prop1";
    private Integer prop2 = null;
    private JavaTestClass nested = null;

    public JavaTestClass() {
    }

    public JavaTestClass(JavaTestClass nested) {
        this.nested = nested;
    }

    public String getProp1() {
        return prop1;
    }

    public void setProp1(String prop1) {
        this.prop1 = prop1;
    }

    public Integer getProp2() {
        return prop2;
    }

    public void setProp2(Integer prop2) {
        this.prop2 = prop2;
    }

    public JavaTestClass getNested() {
        return nested;
    }

    public void setNested(JavaTestClass nested) {
        this.nested = nested;
    }
}
