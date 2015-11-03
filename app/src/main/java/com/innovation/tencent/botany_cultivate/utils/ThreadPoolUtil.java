package com.innovation.tencent.botany_cultivate.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Mr.Jadyn on 15/10/14.
 */
public class ThreadPoolUtil {
    public static final String DEFAULT_SINGLE_POOL_NAME = "DEFAULT_SINGLE_POOL_NAME";

    private static ThreadPoolProxy mLongPool = null;
    private static final Object mLongLock = new Object();

    private static ThreadPoolProxy mShortPool = null;
    private static final Object mShortLock = new Object();

    private static ThreadPoolProxy mDownloadPool = null;
    private static final Object mDownloadLock = new Object();

    private static Map<String, ThreadPoolProxy> mMap = new HashMap<String, ThreadPoolProxy>();
    private static final Object mSingleLock = new Object();

    /** 获取下载线程 */
    public static ThreadPoolProxy getDownloadPool() {
        synchronized (mDownloadLock) {
            if (mDownloadPool == null) {
                mDownloadPool = new ThreadPoolProxy(3, 3, 5L);
            }
            return mDownloadPool;
        }
    }

    /** 获取一个用于执行长耗时任务的线程池，避免和短耗时任务处在同一个队列而阻塞了重要的短耗时任务，通常用来联网操作 */
    public static ThreadPoolProxy getLongPool() {
        synchronized (mLongLock) {
            if (mLongPool == null) {
                mLongPool = new ThreadPoolProxy(5, 5, 5L);
            }
            return mLongPool;
        }
    }

    /** 获取一个用于执行短耗时任务的线程池，避免因为和耗时长的任务处在同一个队列而长时间得不到执行，通常用来执行本地的IO/SQL */
    public static ThreadPoolProxy getShortPool() {
        synchronized (mShortLock) {
            if (mShortPool == null) {
                mShortPool = new ThreadPoolProxy(2, 2, 5L);
            }
            return mShortPool;
        }
    }

//    /** 获取一个单线程池，所有任务将会被按照加入的顺序执行，免除了同步开销的问题 */
//    public static ThreadPoolProxy getSinglePool() {
//        return getSinglePool(DEFAULT_SINGLE_POOL_NAME);
//    }
//
//    /** 获取一个单线程池，所有任务将会被按照加入的顺序执行，免除了同步开销的问题 */
//    public static ThreadPoolProxy getSinglePool(String name) {
//        synchronized (mSingleLock) {
//            ThreadPoolProxy singlePool = mMap.get(name);
//            if (singlePool == null) {
//                singlePool = new ThreadPoolProxy(1, 1, 5L);
//                mMap.put(name, singlePool);
//            }
//            return singlePool;
//        }
//    }
    public static class ThreadPoolProxy {
        private ThreadPoolExecutor mPool;
        private int corePoolSize;//线程池中维护线程的最小数量
        private int maximumPoolSize;//线程池中维护线程的最大数量
        private long keepAliveTime;//线程池维护线程所允许的空闲时间

        private ThreadPoolProxy(int corePoolSize, int maximumPoolSize, long keepAliveTime) {
            this.corePoolSize = corePoolSize;
            this.maximumPoolSize = maximumPoolSize;
            this.keepAliveTime = keepAliveTime;
        }

        /**
         * 执行任务，当线程池处于关闭，将会重新创建新的线程池
         *
         * @param run
         */
        public synchronized void execute(Runnable run) {
            if (run == null) {
                return;
            }
            if (mPool == null || mPool.isShutdown()) {
                mPool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<Runnable>(), Executors.defaultThreadFactory(),new ThreadPoolExecutor.AbortPolicy());
            }
            mPool.execute(run);
        }
        /** 取消线程池中某个还未执行的任务 */
        public synchronized void cancel(Runnable run) {
            if (mPool != null && (!mPool.isShutdown() || mPool.isTerminating())) {
                mPool.getQueue().remove(run);
            }
        }

        /** 取消线程池中某个还未执行的任务 */
        public synchronized boolean contains(Runnable run) {
            return mPool != null && (!mPool.isShutdown() || mPool.isTerminating()) && mPool.getQueue().contains(run);
        }

        /** 立刻关闭线程池，并且正在执行的任务也将会被中断 */
        public void stop() {
            if (mPool != null && (!mPool.isShutdown() || mPool.isTerminating())) {
                mPool.shutdownNow();
            }
        }

        /** 平缓关闭单任务线程池，但是会确保所有已经加入的任务都将会被执行完毕才关闭 */
        public synchronized void shutdown() {
            if (mPool != null && (!mPool.isShutdown() || mPool.isTerminating())) {
                mPool.shutdownNow();
            }
        }

    }
}
