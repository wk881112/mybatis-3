/**
 * Copyright 2009-2018 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.ibatis.autoconstructor;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.Reader;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AutoConstructorTest {
    private static SqlSessionFactory sqlSessionFactory;
    private static Log log;

    @BeforeAll
    public static void setUp() throws Exception {

        // use log4j
//        LogFactory.useLog4JLogging();
        log = LogFactory.getLog(AutoConstructorTest.class);


        // create a SqlSessionFactory
        try (Reader reader = Resources.
                getResourceAsReader("org/apache/ibatis/autoconstructor/mybatis-config.xml")) {

            SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();

            sqlSessionFactory = sqlSessionFactoryBuilder.build(reader);

        }


        // populate in-memory database
//    BaseDataTest.runScript(sqlSessionFactory.getConfiguration().getEnvironment().getDataSource(),
//        "org/apache/ibatis/autoconstructor/CreateDB.sql");
    }

    @Test
    public void testInit() {
        log.debug("test body: do nothing");
    }

    @Test
    public void fullyPopulatedSubject() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            final AutoConstructorMapper mapper = sqlSession.getMapper(AutoConstructorMapper.class);
            final Object subject = mapper.getSubject(1);
            assertNotNull(subject);
        }
    }

    @Test
    public void primitiveSubjects() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            final AutoConstructorMapper mapper = sqlSession.getMapper(AutoConstructorMapper.class);
            assertThrows(PersistenceException.class, () -> {
                mapper.getSubjects();
            });
        }
    }

    @Test
    public void annotatedSubject() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            final AutoConstructorMapper mapper = sqlSession.getMapper(AutoConstructorMapper.class);
            verifySubjects(mapper.getAnnotatedSubjects());
        }
    }

    @Test
    public void badSubject() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            final AutoConstructorMapper mapper = sqlSession.getMapper(AutoConstructorMapper.class);
            assertThrows(PersistenceException.class, () -> {
                mapper.getBadSubjects();
            });
        }
    }

    @Test
    public void extensiveSubject() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            final AutoConstructorMapper mapper = sqlSession.getMapper(AutoConstructorMapper.class);
            verifySubjects(mapper.getExtensiveSubject());
        }
    }

    private void verifySubjects(final List<?> subjects) {
        assertNotNull(subjects);
        Assertions.assertThat(subjects.size()).isEqualTo(3);
    }
}
