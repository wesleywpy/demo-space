package com.wesley.study;

import com.orientechnologies.orient.console.OConsoleDatabaseApp;
import com.orientechnologies.orient.core.command.OCommandContext;
import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.db.tool.ODatabaseImport;
import com.orientechnologies.orient.core.sql.OCommandSQL;
import com.tinkerpop.blueprints.GraphQuery;
import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory;
import com.tinkerpop.blueprints.impls.orient.OrientGraphNoTx;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.io.IOException;

/**
 * @author Created by Wesley on 2017/4/5.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class BaseTest {

    /**
     * 创建数据库
     * @throws IOException
     */
    @Test
    public void createDB() throws IOException {
        OConsoleDatabaseApp databaseApp = new OConsoleDatabaseApp(new String[]{});
        databaseApp.createDatabase("remote:192.168.2.168/ea-lfwjw", "root", "root", "plocal", "graph", null);
        databaseApp.close();

    }

    /**
     * 导入数据库
     * @throws IOException
     */
    @Test
    public void importDB() throws IOException {
        ODatabaseDocumentTx documentTx = new ODatabaseDocumentTx("remote:192.168.2.168/ea-lfwjw");
        documentTx.open("root", "root");
        ODatabaseImport dbImport = new ODatabaseImport(documentTx, "C:\\Users\\Administrator\\Downloads\\ea-lfwjw.gz", null);
        dbImport.importDatabase();
        dbImport.close();
        documentTx.commit();
        documentTx.close();
    }
}
