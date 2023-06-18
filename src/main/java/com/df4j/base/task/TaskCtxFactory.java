package com.df4j.base.task;

public interface TaskCtxFactory<T extends TaskCtx> {
    T createTaskCtx();
}
