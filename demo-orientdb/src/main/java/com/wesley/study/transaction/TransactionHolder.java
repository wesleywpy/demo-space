package com.wesley.study.transaction;

import com.tinkerpop.blueprints.impls.orient.OrientGraph;

/**
 *
 */
public class TransactionHolder {

    private OrientGraph orientGraph;

    public TransactionHolder(OrientGraph orientGraph) {
        this.orientGraph = orientGraph;
    }

    public OrientGraph getOrientGraph() {
        return orientGraph;
    }

    public void setOrientGraph(OrientGraph orientGraph) {
        this.orientGraph = orientGraph;
    }
}
