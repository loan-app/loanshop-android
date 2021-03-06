package com.ailiangbao.provider.support.bridge.testable.rx.scheduler;


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 4/15/16.
 */
public final class SchedulerBridge {
    private SchedulerBridge() {
    }

    private static final int CORE_POOL_SIZE = 4;
    private static final int MAXIMUM_POOL_SIZE = 128;
    private static final int KEEP_ALIVE = 1;
    private static final BlockingQueue<Runnable> POOL_WORK_QUEUE_NET = new LinkedBlockingQueue<Runnable>(20);
    private static final BlockingQueue<Runnable> POOL_WORK_QUEUE_DB = new LinkedBlockingQueue<Runnable>(20);
    private static final ThreadFactory THREAD_FACTORY_NET = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        public Thread newThread(Runnable r) {
            return new Thread(r, "RxThread NET #" + mCount.getAndIncrement());
        }
    };

    private static final ThreadFactory THREAD_FACTORY_DB = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        public Thread newThread(Runnable r) {
            return new Thread(r, "RxThread DB #" + mCount.getAndIncrement());
        }
    };

    private static final Executor THREAD_POOL_EXECUTOR_NETWORK
            = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE,
            TimeUnit.SECONDS, POOL_WORK_QUEUE_NET, THREAD_FACTORY_NET, new ThreadPoolExecutor.DiscardOldestPolicy());

    private static final Executor THREAD_POOL_EXECUTOR_DATABASE
            = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE,
            TimeUnit.SECONDS, POOL_WORK_QUEUE_DB, THREAD_FACTORY_DB, new ThreadPoolExecutor.DiscardOldestPolicy());

    public static void initialize() {
        SchedulerSelector schedulerSelector = SchedulerSelector.get();

        schedulerSelector.putScheduler(SchedulerType.MAIN, new SchedulerSelector.SchedulerCreation<Scheduler>() {
            @Override
            public Scheduler create() {
                return AndroidSchedulers.mainThread();
            }
        });

        schedulerSelector.putScheduler(SchedulerType.NET, new SchedulerSelector.SchedulerCreation<Scheduler>() {
            @Override
            public Scheduler create() {
                return Schedulers.from(THREAD_POOL_EXECUTOR_NETWORK);
            }
        });

        schedulerSelector.putScheduler(SchedulerType.DB, new SchedulerSelector.SchedulerCreation<Scheduler>() {
            @Override
            public Scheduler create() {
                return Schedulers.from(THREAD_POOL_EXECUTOR_DATABASE);
            }
        });

    }
}
