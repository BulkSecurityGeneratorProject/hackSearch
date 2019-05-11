package com.hacksearch.config;

import org.springframework.context.annotation.Configuration;

/**
 * Properties specific to Hack Search.
 * <p>
 * Properties are configured in the application.yml file.
 * See {@link io.github.jhipster.config.JHipsterProperties} for a good example.
 */
@Configuration
public class HackProperties {

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
