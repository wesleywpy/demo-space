package com.wesley.study;

import com.orientechnologies.orient.core.record.impl.ODocument;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory;
import com.tinkerpop.blueprints.impls.orient.OrientGraphNoTx;
import com.tinkerpop.blueprints.impls.orient.OrientVertex;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.StringUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;

/**
 * @author Created by Wesley on 2017/4/5.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class ChatRoomTest {

    @Autowired
    OrientGraphFactory orientGraphFactory;

    public void initClasses(){
    }

    @Test
    public void saveMsg(){
        OrientGraphNoTx graphNoTx = orientGraphFactory.getNoTx();
        OrientVertex vertex = graphNoTx.getVertex("#28:0");
        addMessage("ItalianRestaurant",  "Have you ever been at Ponza island?", vertex);
    }

    /**
     * 聊天室创建一条消息
     */
    private ODocument addMessage(String chatRoom, String message, OrientVertex vertex) {
        ODocument msg = new ODocument(chatRoom);
        msg.field("date", new Date());
        msg.field("text", message);
        msg.field("user", vertex);
        msg.save();
        return msg;
    }

    @Test
    public void importDataFromText() throws IOException {
        FileReader fileReader = new FileReader(new File("D:\\Nestvision\\fhir\\zs.txt"));
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        OrientGraph graph = orientGraphFactory.getTx();
        OrientVertex sparqlVertex = graph.getVertex("#25:0");
        String line = bufferedReader.readLine();
        while(Objects.nonNull(line)){
            System.out.println(line);
            OrientVertex statmentVertex = graph.addVertex("class:Statment");
            statmentVertex.setProperty("question", line);
            statmentVertex.addEdge("matching", sparqlVertex);
            line = bufferedReader.readLine();
        }
        graph.shutdown();
        bufferedReader.close();
        fileReader.close();
    }
}
