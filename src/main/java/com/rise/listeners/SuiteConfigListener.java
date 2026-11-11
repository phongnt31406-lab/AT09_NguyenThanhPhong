package com.rise.listeners;

import com.rise.config.ConfigReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.IAlterSuiteListener;
import org.testng.xml.XmlSuite;

import java.util.List;

/**
 * Áp chế độ parallel + số thread từ config.properties (hoặc -Dthread.count) cho mọi suite trước khi chạy.
 */
public class SuiteConfigListener implements IAlterSuiteListener {

    private static final Logger LOG = LogManager.getLogger(SuiteConfigListener.class);

    @Override
    public void alter(List<XmlSuite> suites) {
        XmlSuite.ParallelMode mode = XmlSuite.ParallelMode.getValidParallel(ConfigReader.get("parallel.mode", "methods"));
        int threads = ConfigReader.getInt("thread.count");
        int dataProviderThreads = ConfigReader.getInt("dataprovider.thread.count");
        for (XmlSuite suite : suites) {
            suite.setParallel(mode);
            suite.setThreadCount(threads);
            suite.setDataProviderThreadCount(dataProviderThreads);
            LOG.info("Suite '{}': parallel={}, thread.count={}, dataprovider.thread.count={}", suite.getName(), mode, threads, dataProviderThreads);
        }
    }
}
