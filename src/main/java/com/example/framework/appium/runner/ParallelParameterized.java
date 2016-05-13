package com.example.framework.appium.runner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.runners.Parameterized;
import org.junit.runners.model.RunnerScheduler;

/**
 * Class adapted from the following source:
 * http://jankesterblog.blogspot.com/2011/10
 * /junit4-running-parallel-junit-classes.html
 *
 * @author Jan Kester
 *
 */
public class ParallelParameterized extends Parameterized {

    private static class ThreadPoolScheduler implements RunnerScheduler {
        private static final int TIME_OUT = 5;
        public static final String DEFAULT_PARALLEL_THREADS = "8";
        private ExecutorService executor;

        public ThreadPoolScheduler() {
            String threads = System.getProperty("junit.parallel.threads", DEFAULT_PARALLEL_THREADS);
            int numThreads = Integer.parseInt(threads);
            executor = Executors.newFixedThreadPool(numThreads);
        }

        public void finished() {
            executor.shutdown();
            try {
                executor.awaitTermination(TIME_OUT, TimeUnit.MINUTES);
            } catch (InterruptedException exc) {
                throw new RuntimeException(exc);
            }
        }

        public void schedule(Runnable childStatement) {
            executor.submit(childStatement);
        }
    }

    /**
     * Instantiates a new parallelized.
     *
     * @param klass
     *            the klass
     * @throws Throwable
     *             the throwable
     */
    public ParallelParameterized(Class<?> klass) throws Throwable {
        super(klass);
        setScheduler(new ThreadPoolScheduler());
    }
}
