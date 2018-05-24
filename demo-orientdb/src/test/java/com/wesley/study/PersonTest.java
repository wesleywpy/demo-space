package com.wesley.study;

import com.tinkerpop.blueprints.impls.orient.OrientBaseGraph;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory;
import com.tinkerpop.blueprints.impls.orient.OrientVertex;
import com.tinkerpop.blueprints.impls.orient.OrientVertexType;
import com.wesley.study.conf.OrientDBConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Created by Wesley on 2017/4/1.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class PersonTest {

    @Autowired
    OrientDBConfig orientDBConfig;

    @Autowired
    OrientGraphFactory orientGraphFactory;

    private static final String PERSON = "Person";
    private static final String RESTAURANT = "Restaurant";

    /**
     * 为Vertex指定存储的Cluster
     */
    @Test
    public void createVertexInSpecificClusters(){
        OrientGraph orientGraph = orientGraphFactory.getTx();
        orientGraph.addVertex("class:Person,cluster:Person_usa");
        orientGraph.shutdown();
        Assert.assertEquals(null, orientGraph.getEdgeType("aaa"));
    }


    /**
     * 增加Vertex Classes 和 Records
     */
    @Test
    public void add(){
        OrientGraph graph = orientGraphFactory.getTx();
        try{
            addVertexAndEgees(graph);
            graph.commit();
        }finally {
            graph.shutdown();
        }
    }

    private void addVertexAndEgees(OrientGraph graph){
        OrientVertexType personType = graph.createVertexType(PERSON, "V");
        OrientVertexType restaurantType = graph.createVertexType(RESTAURANT, "V");

        OrientVertex wesleyVertex = graph.addVertex(OrientBaseGraph.CLASS_PREFIX + PERSON, "name", "Wesley");
        OrientVertex billVertex = graph.addVertex(OrientBaseGraph.CLASS_PREFIX + PERSON, "name", "Bill");

        Map<String, Object> restaurantPro = new HashMap<>();
        restaurantPro.put("name", "Dante");
        restaurantPro.put("type", "Pizza");
        OrientVertex pizzaVertex = graph.addVertex(OrientBaseGraph.CLASS_PREFIX + RESTAURANT, restaurantPro);
        OrientVertex chineseRestaurantVertex = graph.addVertex(OrientBaseGraph.CLASS_PREFIX + RESTAURANT, "name", "Charlie", "type", "Chinese");

        graph.addEdge(OrientBaseGraph.CLASS_PREFIX + "Eat", chineseRestaurantVertex, wesleyVertex, "Eat");
        graph.addEdge(OrientBaseGraph.CLASS_PREFIX + "Eat", pizzaVertex, billVertex, "Eat");
    }

    @Test
    public void update(){
        OrientGraph graph = orientGraphFactory.getTx();
        try{
            graph.getVerticesOfClass(PERSON).forEach(vertex -> {
                vertex.setProperty("age", 20);
                vertex.setProperty("gender", "man");
            });
            graph.commit();
        }finally {
            graph.shutdown();
        }

    }

    @Test
    public void deleteVerticesAndEdges(){
        OrientGraph graph = orientGraphFactory.getTx();
        try{
            delteVertices(graph);
            delteEdges(graph);
            graph.commit();
        }finally {
            graph.shutdown();
        }

    }

    public void delteVertices(OrientGraph graph){
        /**
         * 删除Person所有记录和VertexClasses
         */
        OrientVertexType vertexType = graph.getVertexType(PERSON);
        if(Objects.nonNull(vertexType)){
            graph.getVerticesOfClass(PERSON).forEach(vertex -> {
                System.out.println(vertex.getId());
                graph.removeVertex(vertex);
            });
            graph.dropVertexType(PERSON);
        }

        /**
         * 删除Restaurant所有记录和VertexClasses
         */
        vertexType = graph.getVertexType(RESTAURANT);
        if(Objects.nonNull(vertexType)){
            graph.getVerticesOfClass(RESTAURANT).forEach(vertex -> {
                System.out.println(vertex.getClass());
                graph.removeVertex(vertex);
            });
            graph.dropVertexType(RESTAURANT);
        }
    }

    /**
     * 删除Eat和Friend Edge和所有记录
     */
    public void delteEdges(OrientGraph graph){
        graph.getEdgesOfClass("Eat").forEach(edge -> {
            System.out.println(edge.getId());
            graph.removeEdge(edge);
        });
        graph.dropEdgeType("Eat");

        graph.getEdgesOfClass("Friend").forEach(edge -> {
            System.out.println(edge.getId());
            graph.removeEdge(edge);
        });
        graph.dropEdgeType("Friend");
    }

}
