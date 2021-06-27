package com.trankimtung.kotlin.kreflect;

import kotlin.jvm.JvmClassMappingKt;
import kotlin.reflect.KClass;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class JavaClassTest extends BaseTest<JavaClassTest.TestClass> {

    @NotNull
    @Override
    public KClass<TestClass> getType() {
        return JvmClassMappingKt.getKotlinClass(TestClass.class);
    }

    @NotNull
    @Override
    public TestClass obj() {
        return new TestClass(new TestClass());
    }

    @Test
    void IsJavaClass() {
        Assertions.assertFalse(KReflects.isKotlinClass(getType()));
    }

    public static class TestClass {
        private String prop1 = "prop1";
        private Integer prop2 = null;
        private TestClass nested = null;

        public TestClass() {
        }

        public TestClass(TestClass nested) {
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

        public TestClass getNested() {
            return nested;
        }

        public void setNested(TestClass nested) {
            this.nested = nested;
        }
    }
}
