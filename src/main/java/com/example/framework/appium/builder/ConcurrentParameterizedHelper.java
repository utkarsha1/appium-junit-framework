package com.example.framework.appium.builder;

import com.example.framework.appium.exception.AppiumException;
import com.google.common.collect.Lists;
import com.example.framework.appium.domain.PlatformConfig;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

public class ConcurrentParameterizedHelper {

    public List<PlatformConfig[]> getPlatformConfiguration(List<PlatformConfig> platformConfigs) {
        List<PlatformConfig[]> testConfigs = Lists.newArrayList();
        if(CollectionUtils.isEmpty(platformConfigs)) {
            throw new AppiumException("Platform Config list is null or empty");
        }
        for (PlatformConfig platformConfig : platformConfigs) {
            testConfigs.add(new PlatformConfig[]{platformConfig});
        }
        return testConfigs;
    }
}
