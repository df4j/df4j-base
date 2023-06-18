package com.df4j.base.task;

import java.util.List;

public class SimpleTaskExecutor<T> implements TaskExecutor<T, TaskCfg, TaskCtx> {

    private TaskCfg defaultConfig;

    private TaskInterceptor<T, TaskCfg, TaskCtx> head;

    public void setDefaultConfig(TaskCfg defaultConfig) {
        this.defaultConfig = defaultConfig;
    }

    @Override
    public void setInterceptors(List<? extends TaskInterceptor<T, TaskCfg, TaskCtx>> taskInterceptors) {
        if (taskInterceptors == null || taskInterceptors.isEmpty()) {
            return;
        }
        int size = taskInterceptors.size();
        if (size > 1) {
            for (int i = 0; i < size - 1; i++) {
                taskInterceptors.get(i).setNext(taskInterceptors.get(i + 1));
            }
        }
        this.head = taskInterceptors.get(0);
    }

    @Override
    public T execute(Task<T, TaskCtx> task) {
        return this.execute(defaultConfig, task);
    }

    @Override
    public T execute(TaskCfg config, Task<T, TaskCtx> task) {
        if (head != null) {
            return head.execute(config, task);
        } else {
            return task.execute(null);
        }
    }
}
