package com.wesley.study.transaction;

import com.tinkerpop.blueprints.impls.orient.OrientGraph;

public class OrientTransaction {
    private OrientGraph graph;

    public OrientTransaction(OrientGraph graph) {
        this.graph = graph;
    }

    public OrientGraph getGraph() {
        return graph;
    }

    public void setGraph(OrientGraph graph) {
        this.graph = graph;
    }
}
