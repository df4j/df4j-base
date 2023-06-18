package com.df4j.base.task;

import com.df4j.base.exception.BaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TaskCtxInterceptor<T, CFG extends TaskCfg, CTX extends TaskCtx> extends AbstractTaskInterceptor<T, CFG, CTX> {

    private TaskCtxFactory<CTX> taskCtxFactory;

    private Logger logger = LoggerFactory.getLogger(TaskCtxInterceptor.class);

    public void setTaskCtxFactory(TaskCtxFactory<CTX> taskCtxFactory) {
        this.taskCtxFactory = taskCtxFactory;
    }

    public TaskCtxFactory<CTX> getTaskCtxFactory() {
        return taskCtxFactory;
    }

    @Override
    public T execute0(CFG config, Task<T, CTX> task) {
        TaskCtx taskCtx = TaskCtxManager.getTaskCtx();
        boolean ctxReused = false;
        boolean beforeCtxReused = false;
        if (taskCtx != null && taskCtx.getException() == null && config.isReuseContext()) {
            ctxReused = true;
            beforeCtxReused = taskCtx.isReusable();
        } else {
            // todoTa
            taskCtx = this.getTaskCtxFactory().createTaskCtx();
        }
        taskCtx.setReusable(ctxReused);
        try {
            TaskCtxManager.setTaskCtx(taskCtx);
            return super.execute0(config, task);
        } catch (Exception e) {
            taskCtx.exception(e);
        } finally {
            try {
                if (!ctxReused) {
                    taskCtx.close();
                }
                taskCtx.setReusable(beforeCtxReused);
            } finally {
                TaskCtxManager.removeTaskCtx();
            }
        }
        if (ctxReused && taskCtx.getException() != null) {
            Throwable t = taskCtx.getException();
            taskCtx.resetException();
            if (t instanceof BaseException) {
                throw (BaseException) t;
            } else {
                throw new BaseException("exception during task execution", t);
            }
        }
        return null;
    }
}
