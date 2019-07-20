package com.wesley.study;

import com.orientechnologies.orient.core.command.OCommandContext;
import com.orientechnologies.orient.core.sql.OCommandSQL;
import com.tinkerpop.blueprints.GraphQuery;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import com.tinkerpop.blueprints.impls.orient.OrientGraphNoTx;
import com.wesley.study.transaction.OrientDBGraphFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Created by Wesley on 2017/8/24.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class QueryTest {

    @Autowired
    OrientDBGraphFactory orientGraphFactory;

    @Test
    public void find() {
        OrientGraphNoTx graphNoTx = orientGraphFactory.getNoTx();
        OCommandContext commandContext = graphNoTx.command(new OCommandSQL("SELECT EXPAND( OUT('Owns')) FROM Person WHERE name='Wesley'"))
                .getContext();
        System.out.println(commandContext);

        GraphQuery graphQuery = graphNoTx.query();
        System.out.println(graphQuery.getClass().getName());
    }

    @Test
    public void queryTest() {
        OrientGraph graph = orientGraphFactory.getTx();
        graph.query()
                .has("@class", "ModelRelation")
                .has("type", "BBR")
                .vertices().forEach(vertex -> {
            System.out.println(vertex.getId());
        });
    }

    /**
     * 全文索引查询
     */
    @Test
    public void fullIndex() {
//        String match_statment = "SELECT question,$score as score FROM Statment WHERE question LUCENE :que ORDER BY score DESC limit 1";
        String match_statment = "SELECT stat,$score FROM statement WHERE stat LUCENE :stat order by $score desc limit 3";
        OrientGraph graph = orientGraphFactory.getTx();
        Iterable<Vertex> result = graph.command(new OCommandSQL(match_statment)).execute("租房什么地方好");
        String que = result.iterator().next().getProperty("stat");
        System.out.println(que);
    }
}
