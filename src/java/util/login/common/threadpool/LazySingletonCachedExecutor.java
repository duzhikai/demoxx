package util.login.common.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author xuliangliang.1995
 */
public class LazySingletonCachedExecutor {

    private LazySingletonCachedExecutor() {
    }

    public static ExecutorService instance() {
        return LazyExecutorHolder.executorService;
    }

    private static class LazyExecutorHolder {
        private static final ExecutorService executorService = Executors.newCachedThreadPool();
    }
}
