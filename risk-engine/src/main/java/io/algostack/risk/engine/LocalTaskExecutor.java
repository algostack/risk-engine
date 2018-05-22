package io.algostack.risk.engine;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class LocalTaskExecutor implements TaskExecutor {

    final ExecutorService executor = Executors.newCachedThreadPool();

    public LocalTaskExecutor() {
    }

    @Override
    public <T> Future<T> execute(Callable<T> task) {
        return executor.submit(task);
    }
}
