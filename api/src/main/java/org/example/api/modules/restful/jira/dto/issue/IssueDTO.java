package org.example.api.modules.restful.jira.dto.issue;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class IssueDTO {
    private String expand;
    private int startAt;
    private int maxResults;
    private int total;

    @JsonProperty("issues")
    private List<Issue> issueFields;
}
