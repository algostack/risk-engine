package io.algostack.risk.engine;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public interface TaskExecutor {

     <T> Future<T> execute(Callable<T> task);


}
