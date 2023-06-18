package com.df4j.base.task;

import java.util.Stack;

public class TaskCtxManager {

    protected static ThreadLocal<Stack<TaskCtx>> taskCtxThreadLocal = new ThreadLocal<>();


    public static TaskCtx getTaskCtx() {
        Stack<TaskCtx> stack = getTaskCtxStack(taskCtxThreadLocal);
        if (stack.isEmpty()) {
            return null;
        }
        return stack.peek();
    }

    public static void setTaskCtx(TaskCtx taskCtx) {
        Stack<TaskCtx> taskCtxStack = getTaskCtxStack(taskCtxThreadLocal);
        taskCtxStack.push(taskCtx);
    }

    public static void removeTaskCtx() {
        Stack<TaskCtx> taskCtxStack = getTaskCtxStack(taskCtxThreadLocal);
        taskCtxStack.pop();
    }

    protected static Stack<TaskCtx> getTaskCtxStack(ThreadLocal<Stack<TaskCtx>> taskCtxThreadLocal) {
        Stack<TaskCtx> stack = taskCtxThreadLocal.get();
        if (stack == null) {
            synchronized (TaskCtxManager.taskCtxThreadLocal) {
                if (stack == null) {
                    stack = new Stack<>();
                    taskCtxThreadLocal.set(stack);
                }
            }
        }
        return stack;
    }
}
