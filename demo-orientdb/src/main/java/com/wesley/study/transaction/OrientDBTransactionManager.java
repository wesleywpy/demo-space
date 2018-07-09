package com.wesley.study.transaction;

import com.orientechnologies.orient.core.db.ODatabase;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory;
import javax.annotation.Resource;
import org.springframework.transaction.NoTransactionException;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionStatus;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * OrientDB事物管理
 */
public class OrientDBTransactionManager implements PlatformTransactionManager {

    @Resource
    OrientGraphFactory orientGraphFactory;

    @Override
    public TransactionStatus getTransaction(TransactionDefinition definition) {
        TransactionHolder holder = (TransactionHolder) TransactionSynchronizationManager.getResource(ODatabase.class);
        boolean newTransaction = holder == null;
        DefaultTransactionStatus status;
        switch (definition.getPropagationBehavior()) {
        // 支持当前事务。如果没有事务则开启一个新的事务
        case TransactionDefinition.PROPAGATION_REQUIRED:
            if (newTransaction) {
                holder = getHolder();
            }
            status = new DefaultTransactionStatus(newTransaction ? holder.getOrientGraph() : null, newTransaction, true, false, false, null);
            break;
        // 支持当前事务，如果当前没有事务，就以非事务方式执行。
        case TransactionDefinition.PROPAGATION_SUPPORTS:
            status = new DefaultTransactionStatus(newTransaction ? null : holder.getOrientGraph(), newTransaction, true, false, false, null);
            break;
        // 支持当前的事务，如果当前没有事务，就抛出异常。
        case TransactionDefinition.PROPAGATION_MANDATORY:
            if (newTransaction) {
                throw new NoTransactionException("当前没有事务");
            }
            status = new DefaultTransactionStatus(holder.getOrientGraph(), false, true, false, false, null);
            break;
        // 新建事务，如果当前存在事务，把当前事务挂起。
        case TransactionDefinition.PROPAGATION_REQUIRES_NEW:
            holder = new TransactionHolder(orientGraphFactory.getTx());
            status = new DefaultTransactionStatus(holder.getOrientGraph(), true, true, false, false, newTransaction ? null : holder.getOrientGraph());
            begin(holder);
            break;
        //Do not support a current transaction; rather always execute non-transactionally.
        case TransactionDefinition.PROPAGATION_NOT_SUPPORTED:
            status = new DefaultTransactionStatus(null, false, true, false, false, null);
            break;
        //Do not support a current transaction; throw an exception if a current transaction
        case TransactionDefinition.PROPAGATION_NEVER:
            if (!newTransaction) {
                throw new NoTransactionException("不支持事务");
            }
            status = new DefaultTransactionStatus(null, false, true, false, false, null);
            break;
        //Execute within a nested transaction if a current transaction exists
        case TransactionDefinition.PROPAGATION_NESTED:
            if (newTransaction) {
                throw new NoTransactionException("没有事务");
            }
            status = new DefaultTransactionStatus(holder.getOrientGraph(), false, true, false, false, null);
            break;
        default:
            throw new NoTransactionException("当前事务的传播不支持");
        }

        return status;
    }

    private TransactionHolder getHolder() {
        TransactionHolder holder = new TransactionHolder(orientGraphFactory.getTx());
        begin(holder);
        return holder;
    }

    private void begin(TransactionHolder holder) {
        TransactionSynchronizationManager.bindResource(ODatabase.class, holder);
        holder.getOrientGraph().setAutoStartTx(false);
        holder.getOrientGraph().begin();
    }

    @Override
    public void commit(TransactionStatus status) {
        DefaultTransactionStatus defaultStatus = (DefaultTransactionStatus) status;
        if (defaultStatus.hasTransaction()) {
            OrientGraph graph = (OrientGraph) defaultStatus.getTransaction();
            graph.commit();
            releaseConnection(graph);
        }
    }

    @Override
    public void rollback(TransactionStatus status) {
        DefaultTransactionStatus defaultStatus = (DefaultTransactionStatus) status;
        if (defaultStatus.hasTransaction()) {
            OrientGraph graph = (OrientGraph) defaultStatus.getTransaction();
            graph.rollback();
            releaseConnection(graph);
        }
    }

    /**
     * 释放连接
     *
     * @param orientGraph Spring事务托管的对象
     */
    protected void releaseConnection(OrientGraph orientGraph) {
        orientGraph.shutdown();
        TransactionSynchronizationManager.unbindResource(ODatabase.class);
    }
}
