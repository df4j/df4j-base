package com.df4j.base.task;

import java.util.List;

public interface TaskExecutor<T, CFG extends TaskCfg, CTX extends TaskCtx> {

    void setInterceptors(List<? extends TaskInterceptor<T, CFG, CTX>> interceptors);

    T execute(Task<T, CTX> task);

    T execute(CFG config, Task<T, CTX> task);
}
