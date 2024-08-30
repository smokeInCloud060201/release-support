package org.example.api.common.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app.config.jira")
@Getter
@Setter
public class JiraProperty {

    private User user;
    private Project project;


    @Getter
    @Setter
    public static class User {
        private String token;
        private String email;
    }

    @Getter
    @Setter
    public static class Project {
        private String domain;
        private String key;
    }
}
