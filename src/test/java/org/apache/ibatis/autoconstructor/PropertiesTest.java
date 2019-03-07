package org.apache.ibatis.autoconstructor;

import org.junit.jupiter.api.Test;

import java.util.Properties;

public class PropertiesTest {

    @Test
    public void testSameNameKey() {
        Properties propertiesA = new Properties();
        propertiesA.put("abc","abc");


        Properties propertiesB = new Properties();
        propertiesB.put("abc","cbc");


        propertiesA.putAll(propertiesB);

        System.out.println(propertiesA);
    }
}
