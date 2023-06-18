package com.df4j.base.task;


public interface Task<T, CTX extends TaskCtx> {
    T execute(CTX context);
}
