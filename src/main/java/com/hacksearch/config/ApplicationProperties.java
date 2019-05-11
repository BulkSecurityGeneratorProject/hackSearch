package com.hacksearch.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Hack Search.
 * <p>
 * Properties are configured in the application.yml file.
 * See {@link io.github.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    private final Amazon amazon = new Amazon();

    public Amazon getAmazon(){
        return amazon;
    }

    public static class Amazon {
        private String host = "localhost";

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }
    }


}
