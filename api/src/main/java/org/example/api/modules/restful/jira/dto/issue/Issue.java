package org.example.api.modules.restful.jira.dto.issue;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Issue {
    private String expand;
    private String id;
    private String self;
    private String key;

    @JsonProperty("fields")
    private IssueField fields;
}
