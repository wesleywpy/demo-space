package com.wesley.study;

import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory;
import com.tinkerpop.gremlin.java.GremlinPipeline;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Created by Wesley on 2017/8/25.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class GremlinTest {

    @Autowired
    OrientGraphFactory orientGraphFactory;

    @Test
    public void pipeLineTest(){
        int page = 1;
        int size = 8;
        new GremlinPipeline<>(orientGraphFactory.getTx())
                .V()
                .has("@class", "object_class")
                .order()
                .forEach(vertex -> {
                    String name = vertex.getProperty("name");
                    System.out.println(vertex.getId() +": "+ name);
                });
    }

}
