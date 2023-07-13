package com.neil.pay.wx.auth;

import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author nihao
 * @date 2023/7/12
 */
public class SafeSingleScheduleExecutor extends ScheduledThreadPoolExecutor {

    private static final int MAX_QUEUE_CAPACITY = 1;
    private static final int MAXIMUM_POOL_SIZE = 1;
    private static final int CORE_POOL_SIZE = 1;

    public SafeSingleScheduleExecutor() {
        super(CORE_POOL_SIZE);
        super.setMaximumPoolSize(MAXIMUM_POOL_SIZE);
    }


    @Override
    public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period,
                                                  TimeUnit unit) {
        if (getQueue().size() < MAX_QUEUE_CAPACITY) {
            return super.scheduleAtFixedRate(command, initialDelay, period, unit);
        } else {
            throw new RejectedExecutionException("当前任务数量超过最大队列最大数量");
        }
    }
}
