package com.salesmanager.core.business.services.thread;


import java.util.concurrent.*;

public class ThreadPoolManager {


    private final ExecutorService executor;

    public ThreadPoolManager(int poolSize, int queueCapacity) {
        this.executor = new ThreadPoolExecutor(
                poolSize, // 核心线程数
                poolSize, // 最大线程数
                0L, TimeUnit.MILLISECONDS, // 空闲线程存活时间
                new LinkedBlockingQueue<>(queueCapacity), // 有界队列
                new RejectedExecutionHandler() {
                    @Override
                    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                        try {
                            // 队列满时，让提交任务的线程等待直到队列有空间
                            executor.getQueue().put(r);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
    }

    public ExecutorService getExecutor() {
        return executor;
    }

    public void shutdown() {
        executor.shutdown();
    }
}
