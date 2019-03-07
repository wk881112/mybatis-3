package org.apache.ibatis.reflection;

import org.apache.ibatis.reflection.property.PropertyNamer;
import org.junit.jupiter.api.Test;

public class PropertyNamerTest {

    @Test
    public void testMethodToProperty() {
        boolean getter = PropertyNamer.isGetter("getAbc");
        boolean getter2 = PropertyNamer.isGetter("isbbbbc");

        System.out.println(getter);
        System.out.println(getter2);

        String getAbc = PropertyNamer.methodToProperty("getAbc");
        String getbbbbb = PropertyNamer.methodToProperty("getbbbbb");
        System.out.println(getAbc);
        System.out.println(getbbbbb);

        String name = "Abc";

        if (name.length() == 1 || (name.length() > 1 && !Character.isUpperCase(name.charAt(1)))) {
            System.out.println(!Character.isUpperCase(name.charAt(1)));
        }
    }
}
