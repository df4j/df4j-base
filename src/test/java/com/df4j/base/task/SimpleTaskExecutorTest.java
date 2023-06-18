package com.df4j.base.task;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

class SimpleTaskExecutorTest {

    private Logger logger = LoggerFactory.getLogger(SimpleTaskExecutorTest.class);


    @BeforeAll
    static void setUp() {

    }

    @Test
    void execute() {
        SimpleTaskExecutor<Boolean> executor = new SimpleTaskExecutor<>();

        List<TaskInterceptor<Boolean, TaskCfg, TaskCtx>> list = new ArrayList<>();

        TaskCtxFactory<TaskCtx> taskCtxFactory = new SimpleTaskCtxFactory();

        TaskInterceptor<Boolean, TaskCfg, TaskCtx> logTaskInterceptor = new LogInterceptor();
        list.add(logTaskInterceptor);

        TaskCtxInterceptor<Boolean, TaskCfg, TaskCtx> taskCtxInterceptor = new TaskCtxInterceptor<>();
        taskCtxInterceptor.setTaskCtxFactory(taskCtxFactory);
        list.add(taskCtxInterceptor);

        TaskInterceptor<Boolean, TaskCfg, TaskCtx> taskInterceptor = new AbstractTaskInterceptor<Boolean, TaskCfg, TaskCtx>() {
            @Override
            public Boolean execute0(TaskCfg config, Task<Boolean, TaskCtx> task) {
                return super.execute0(config, task);
            }
        };
        list.add(taskInterceptor);

        executor.setInterceptors(list);

        TaskCfg config = new TaskCfg();
        config.setReuseContext(false);
        executor.setDefaultConfig(config);
        Boolean flag = executor.execute(new Task<Boolean, TaskCtx>() {
            @Override
            public Boolean execute(TaskCtx context) {
                logger.debug("---------- test task execute. ------------------------------");
                return false;
            }
        });
        Assertions.assertEquals(config.isReuseContext(), flag);

        System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "debug");

        flag = executor.execute(new Task<Boolean, TaskCtx>() {
            @Override
            public Boolean execute(TaskCtx context) {
                logger.debug("---------- test task execute. ------------------------------");
                return false;
            }
        });
        Assertions.assertEquals(config.isReuseContext(), flag);
    }
}