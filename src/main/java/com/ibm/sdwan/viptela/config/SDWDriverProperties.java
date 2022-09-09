package com.ibm.sdwan.viptela.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * Properties specific to the SDWAN Driver.
 *
 * <p>
 * Properties are configured in the application.yml file.
 * </p>
 */
@Component
@ConfigurationProperties(prefix = "rcdriver")
public class SDWDriverProperties {
    private final Async async = new Async();
    private final Topics topics = new Topics();
    private final Logging logging = new Logging();
    private Duration executionResponseDelay = Duration.ofSeconds(5);
    private Duration lcmOpOccPollingDelay = Duration.ofSeconds(10);
    private Duration restConnectTimeout = Duration.ofSeconds(5);
    private Duration restReadTimeout = Duration.ofSeconds(60);
    private Duration vManageApiDelay = Duration.ofSeconds(5);

    public Duration getvManageApiDelay() {
        return vManageApiDelay;
    }

    public void setvManageApiDelay(Duration vManageApiDelay) {
        this.vManageApiDelay = vManageApiDelay;
    }

    public Async getAsync() {
        return async;
    }

    public Topics getTopics() {
        return topics;
    }
    public Logging getLogging() {
        return logging;
    }


    public Duration getRestConnectTimeout() {
        return restConnectTimeout;
    }

    public Duration getRestReadTimeout() {
        return restReadTimeout;
    }
    public Duration getExecutionResponseDelay() {
        return executionResponseDelay;
    }

    public void setExecutionResponseDelay(Duration executionResponseDelay) {
        this.executionResponseDelay = executionResponseDelay;
    }
    public Duration getLcmOpOccPollingDelay() {
        return lcmOpOccPollingDelay;
    }

    public void setLcmOpOccPollingDelay(Duration lcmOpOccPollingDelay) {
        this.lcmOpOccPollingDelay = lcmOpOccPollingDelay;
    }

    public static class Async {
        private int corePoolSize = 4;
        private int maxPoolSize = 32;
        private int queueCapacity = 10000;

        public int getCorePoolSize() {
            return corePoolSize;
        }

        public void setCorePoolSize(int corePoolSize) {
            this.corePoolSize = corePoolSize;
        }

        public int getMaxPoolSize() {
            return maxPoolSize;
        }

        public void setMaxPoolSize(int maxPoolSize) {
            this.maxPoolSize = maxPoolSize;
        }

        public int getQueueCapacity() {
            return queueCapacity;
        }

        public void setQueueCapacity(int queueCapacity) {
            this.queueCapacity = queueCapacity;
        }
    }

    public static class Topics {
        private String lifecycleResponsesTopic;
        private String vManageApiRetryTopic;

        public String getLifecycleResponsesTopic() {
            return lifecycleResponsesTopic;
        }

        public void setLifecycleResponsesTopic(String lifecycleResponsesTopic) {
            this.lifecycleResponsesTopic = lifecycleResponsesTopic;
        }

        public String getVManageApiRetryTopic() {
            return vManageApiRetryTopic;
        }

        public void setVManageApiRetryTopic(String vManageApiRetryTopic) {
            this.vManageApiRetryTopic = vManageApiRetryTopic;
        }
    }
    public static class Logging {
        private int loggingRequestInterceptMaxBodySize = 10000000;

        public int getLoggingRequestInterceptMaxBodySize() {
            return loggingRequestInterceptMaxBodySize;
        }

        public void setLoggingRequestInterceptMaxBodySize(int loggingRequestInterceptMaxBodySize) {
            this.loggingRequestInterceptMaxBodySize = loggingRequestInterceptMaxBodySize;
        }
    }

}