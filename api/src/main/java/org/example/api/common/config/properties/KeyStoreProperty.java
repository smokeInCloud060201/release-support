package org.example.api.common.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app.config.keystore")
@Getter
@Setter
public class KeyStoreProperty {
    private String pk;
    private String pvk;
}
