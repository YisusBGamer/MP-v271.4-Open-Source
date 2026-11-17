package net.swordie.ms.handlers.executors;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Sjonnie
 * Created on 7/27/2018.
 *
 * Class that wraps every callable/runnable inside a try/catch block to ensure that Exceptions are caught.
 * http://code.nomad-labs.com/2011/12/09/mother-fk-the-scheduledexecutorservice/
 */
public class CatchingScheduledThreadPoolExecutor extends ScheduledThreadPoolExecutor {
    private static final Logger log = LogManager.getLogger();
    private static final int STACKFRAME_FOR_DEBUG = 5;

    public CatchingScheduledThreadPoolExecutor(int corePoolSize) {
        super(corePoolSize);
        setThreadFactory(new ThreadFactoryBuilder().setNameFormat("scheduled-executor-%d").build());
        setRemoveOnCancelPolicy(true);
    }

    @Override
    public <V> ScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit timeUnit) {
        callable = new DebuggableCallable<>(wrapCallable(callable), STACKFRAME_FOR_DEBUG);
        return super.schedule(callable, delay, timeUnit);
    }

    @Override
    public ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit) {
        command = new DebuggableRunnable(wrapRunnable(command), STACKFRAME_FOR_DEBUG);
        return super.schedule(command, delay, unit);
    }

    @Override
    public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
        command = new RepeatingRunnable(new DebuggableRunnable(command, STACKFRAME_FOR_DEBUG));
        return super.scheduleAtFixedRate(command, initialDelay, period, unit);
    }

    @Override
    public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
        command = new RepeatingRunnable(new DebuggableRunnable(command, STACKFRAME_FOR_DEBUG));
        return super.scheduleWithFixedDelay(command, initialDelay, delay, unit);
    }

    /** Failures in a row after which a repeating task is given up (it is broken, not unlucky). */
    private static final int MAX_FAILURES_IN_A_ROW = 20;

    /**
     * A repeating task survives a failing run (an exception would otherwise cancel it for good, e.g. a boss timer
     * hitting a mob that just died); only one that keeps failing is stopped.
     */
    private static class RepeatingRunnable implements Runnable {
        private final Runnable runnable;
        private int failuresInARow;

        RepeatingRunnable(Runnable runnable) {
            this.runnable = runnable;
        }

        @Override
        public void run() {
            try {
                runnable.run();
                failuresInARow = 0;
            } catch (Exception e) {
                failuresInARow++;
                if (failuresInARow == 1) {
                    log.warn(String.format("error in repeating task %s; it keeps running", runnable), e);
                }
                if (failuresInARow >= MAX_FAILURES_IN_A_ROW) {
                    log.warn(String.format("repeating task %s failed %d times in a row. It will no longer be run!",
                            runnable, failuresInARow), e);
                    throw new RuntimeException(e);
                }
            }
        }

        @Override
        public String toString() {
            return runnable.toString();
        }
    }


    private Runnable wrapRunnable(Runnable command) {
        return new LogOnExceptionRunnable(command);
    }

    private <V> Callable<V> wrapCallable(Callable<V> command) {
        return new LogOnExceptionCallable<>(command);
    }

    private class LogOnExceptionRunnable implements Runnable {
        private Runnable runnable;

        public LogOnExceptionRunnable(Runnable runnable) {
            super();
            this.runnable = runnable;
        }

        @Override
        public void run() {
            try {
                runnable.run();
            } catch (Exception e) {
                log.warn(String.format("error in executing: %s. It will no longer be run!", runnable));
                log.error("CatchingScheduledThreadPoolExecutor.run", e);

                // and re throw it so that the Executor also gets this error so that it can do what it would
                // usually do
                throw new RuntimeException(e);
            }
        }
    }

    private class LogOnExceptionCallable<V> implements Callable<V> {
        private Callable<V> callable;

        public LogOnExceptionCallable(Callable<V> callable) {
            super();
            this.callable = callable;
        }

        @Override
        public V call() throws Exception {
            try {
                return callable.call();
            } catch (Exception e) {
                log.warn(String.format("error in executing: %s. It will no longer be run!", callable));
                log.error("CatchingScheduledThreadPoolExecutor.call", e);

                // and re throw it so that the Executor also gets this error so that it can do what it would
                // usually do
                throw new RuntimeException(e);
            }
        }
    }
}
