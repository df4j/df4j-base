package com.df4j.base.task;

public interface TaskInterceptor<T, CFG extends TaskCfg, CTX extends TaskCtx> {

    TaskInterceptor<T, CFG, CTX> getNext();

    void setNext(TaskInterceptor<T, CFG, CTX> next);

    boolean match(CFG config, Task<T, CTX> task);

    T execute(CFG config, Task<T, CTX> task);

    T execute0(CFG config, Task<T, CTX> task);

    T executeNextOrCommand(CFG config, Task<T, CTX> task);
}
