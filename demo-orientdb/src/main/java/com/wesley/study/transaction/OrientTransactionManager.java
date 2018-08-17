package com.wesley.study.transaction;

import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;
import org.springframework.transaction.support.DefaultTransactionStatus;
import org.springframework.transaction.support.ResourceTransactionManager;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import java.util.Objects;

/**
 * {@link org.springframework.transaction.PlatformTransactionManager} implementation
 * for OrientDB.
 *
 * @author Dzmitry_Naskou
 */
public class OrientTransactionManager extends AbstractPlatformTransactionManager implements ResourceTransactionManager {
    private static final long serialVersionUID = 1L;

    private OrientGraphFactory factory;

    public OrientTransactionManager(OrientGraphFactory factory) {
        super();
        this.factory = factory;
    }

    @Override
    protected Object doGetTransaction() throws TransactionException {
        return new OrientTransaction((OrientGraph) TransactionSynchronizationManager.getResource(getResourceFactory()));
    }

    @Override
    protected boolean isExistingTransaction(Object transaction) throws TransactionException {
        OrientTransaction tx = (OrientTransaction) transaction;
        return tx.getGraph() != null;
    }

    @Override
    protected void doBegin(Object transaction, TransactionDefinition definition) throws TransactionException {
        OrientTransaction tx = (OrientTransaction) transaction;
        OrientGraph graph = tx.getGraph();
        if (Objects.isNull(graph)) {
            graph = factory.getTx();
            graph.setAutoStartTx(false);
            tx.setGraph(graph);
//            log.debug("beginning transaction, db.hashCode() = {}", graph.hashCode());
            TransactionSynchronizationManager.bindResource(factory, graph);
        }
        graph.begin();
    }

    @Override
    protected void doCommit(DefaultTransactionStatus status) throws TransactionException {
        OrientTransaction tx = (OrientTransaction) status.getTransaction();
        OrientGraph graph = tx.getGraph();
//        log.debug("committing transaction, db.hashCode() = {}", graph.hashCode());
        graph.commit();
    }

    @Override
    protected void doRollback(DefaultTransactionStatus status) throws TransactionException {
        OrientTransaction tx = (OrientTransaction) status.getTransaction();
        OrientGraph graph = tx.getGraph();
//        log.debug("rolling back transaction, db.hashCode() = {}", graph.hashCode());
        graph.rollback();
    }

    @Override
    protected void doSetRollbackOnly(DefaultTransactionStatus status) throws TransactionException {
        status.setRollbackOnly();
    }

    @Override
    protected void doCleanupAfterCompletion(Object transaction) {
        OrientTransaction tx = (OrientTransaction) transaction;
        if (!tx.getGraph().isClosed()) {
//            log.debug("closing transaction, db.hashCode() = {}", tx.getGraph().hashCode());
            tx.getGraph().shutdown();
        }

        TransactionSynchronizationManager.unbindResourceIfPossible(factory);
    }

    @Override
    protected Object doSuspend(Object transaction) throws TransactionException {
        OrientTransaction tx = (OrientTransaction) transaction;
        return tx.getGraph();
    }

    @Override
    protected void doResume(Object transaction, Object suspendedResources) throws TransactionException {
        OrientTransaction tx = (OrientTransaction) transaction;
        OrientGraph graph = tx.getGraph();
        if (!graph.isClosed()) {
            graph.shutdown();
        }
        OrientGraph oldDb = (OrientGraph) suspendedResources;
        TransactionSynchronizationManager.bindResource(factory, oldDb);
    }

    @Override
    public Object getResourceFactory() {
        return factory;
    }

}
