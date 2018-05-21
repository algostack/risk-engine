package io.algostack.risk.engine;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IExecutorService;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public class HazelcastDistributedExecutor implements DistributedExecutor {

    private final HazelcastInstance instance;
    private final IExecutorService executor;

    public HazelcastDistributedExecutor() {
        this.instance = Hazelcast.newHazelcastInstance();
        this.executor = instance.getExecutorService("executor");
    }

    @Override
    public <T> Future<T> execute(Callable<T> task) {
        return executor.submit(task);
    }
}
