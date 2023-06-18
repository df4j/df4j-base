package com.df4j.base.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractTaskInterceptor<T, CFG extends TaskCfg, CTX extends TaskCtx> implements TaskInterceptor<T, CFG, CTX> {
    private TaskInterceptor<T, CFG, CTX> next;

    private Logger logger = LoggerFactory.getLogger(AbstractTaskInterceptor.class);

    @Override
    public void setNext(TaskInterceptor<T, CFG, CTX> next) {
        this.next = next;
    }

    @Override
    public TaskInterceptor<T, CFG, CTX> getNext() {
        return next;
    }

    @Override
    public boolean match(CFG config, Task<T, CTX> task) {
        return true;
    }

    @Override
    public T execute(CFG config, Task<T, CTX> task) {
        boolean matched = this.match(config, task);
        if (matched) {
            logger.debug("---------- {} matched ------------------------------", this.getClass().getName());
            return this.execute0(config, task);
        } else {
            logger.debug("---------- {} not matched ------------------------------", this.getClass().getName());
            return this.executeNextOrCommand(config, task);
        }
    }

    @Override
    public T execute0(CFG config, Task<T, CTX> task) {
        return this.executeNextOrCommand(config, task);
    }

    @Override
    public T executeNextOrCommand(CFG config, Task<T, CTX> task) {
        TaskInterceptor<T, CFG, CTX> next = this.getNext();
        if (next != null) {
            logger.debug("---------- {} execute next ------------------------------", this.getClass().getName());
            return next.execute(config, task);
        } else {
            CTX context = (CTX)TaskCtxManager.getTaskCtx();
            logger.debug("---------- {} execute command ------------------------------", this.getClass().getName());
            return task.execute(context);
        }
    }
}
