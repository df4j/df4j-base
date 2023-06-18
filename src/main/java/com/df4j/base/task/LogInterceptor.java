package com.df4j.base.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogInterceptor<T, CFG extends TaskCfg, CTX extends TaskCtx> extends AbstractTaskInterceptor<T, CFG, CTX> {

    private final Logger logger = LoggerFactory.getLogger(LogInterceptor.class);


    @Override
    public boolean match(CFG config, Task<T, CTX> task) {
        return this.logger.isDebugEnabled();
    }

    @Override
    public T execute0(CFG config, Task<T, CTX> task) {
        logger.info("---------- starting {} ------------------------------", this.getClass().getName());
        try {
            return this.executeNextOrCommand(config, task);
        } finally {
            logger.info("---------- {} finished ------------------------------", this.getClass().getName());
        }
    }
}
