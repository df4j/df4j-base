package com.df4j.base.task;

public class SimpleTaskCtxFactory implements TaskCtxFactory<TaskCtx>{
    @Override
    public TaskCtx createTaskCtx() {
        return new TaskCtx();
    }
}
