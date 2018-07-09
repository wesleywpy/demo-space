package com.wesley.study.transaction;

import com.orientechnologies.orient.core.db.ODatabase;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory;
import com.tinkerpop.blueprints.impls.orient.OrientGraphNoTx;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * @author Created by Wesley on 2018/5/15.
 */
public class OrientDBGraphFactory {
    private final OrientGraphFactory graphFactory;

    public OrientDBGraphFactory(String url, String user, String password) {
        graphFactory = new OrientGraphFactory(url, user, password);
    }

    public OrientGraph getCurrentTx() {
        TransactionHolder holder = (TransactionHolder) TransactionSynchronizationManager.getResource(ODatabase.class);
        if (holder == null) {
            throw new TransactionSystemException("当前线程,没有使用事物");
        }
        return holder.getOrientGraph();
    }

    public OrientGraph getTx() {
        return graphFactory.getTx();
    }

    public OrientGraphNoTx getNoTx() {
        return graphFactory.getNoTx();
    }

}
