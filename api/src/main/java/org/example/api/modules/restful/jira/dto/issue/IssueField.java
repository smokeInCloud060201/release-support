package org.example.api.modules.restful.jira.dto.issue;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class IssueField {
    @JsonProperty("statuscategorychangedate")
    private String statusCategoryChangeDate;
    @JsonProperty("fixVersions")
    private List<FixVersion> fixVersions;
    private String resolution;
    private String lastViewed;
    private Priority priority;
    private List<IssueLink> issuelinks;
    private Assignee assignee;
    private Status status;
    private String description;
    private String summary;
    private List<Subtask> subtasks;

    @Getter
    @Setter
    public static class FixVersion {
        private String self;
        private String id;
        private String description;
        private String name;
        private boolean archived;
        private boolean released;
    }

    @Getter
    @Setter
    public static class Priority {
        private String self;
        private String iconUrl;
        private String name;
        private String id;

    }

    @Getter
    @Setter
    public static class IssueLink {
        private String id;
        private String self;
        private Type type;
        private OutwardIssue outwardIssue;
        private InwardIssue inwardIssue;


        @Getter
        @Setter
        public static class Type {
            private String id;
            private String name;
            private String inward;
            private String outward;
            private String self;
        }

        @Getter
        @Setter
        public static class OutwardIssue {
            private String id;
            private String key;
            private String self;
        }

        @Getter
        @Setter
        public static class InwardIssue {
            private String id;
            private String key;
            private String self;
        }
    }

    @Getter
    @Setter
    public static class Assignee {
        private String self;
        private String accountId;
        private String emailAddress;
        private Map<String, String> avatarUrls;
        private String displayName;
        private boolean active;
        private String timeZone;
        private String accountType;

    }

    @Getter
    @Setter
    public static class Status {
        private String self;
        private String description;
        private String iconUrl;
        private String name;
        private String id;
        private StatusCategory statusCategory;


        @Getter
        @Setter
        public static class StatusCategory {
            private String self;
            private int id;
            private String key;
            private String colorName;
            private String name;

        }
    }

    @Getter
    @Setter
    public static class Subtask {
        private String id;
        private String key;
        private String self;
        private SubtaskFields fields;


        @Getter
        @Setter
        public static class SubtaskFields {
            private String summary;
            private Status status;
            private Priority priority;
            private IssueType issuetype;

            @Getter
            @Setter
            public static class IssueType {
                private String self;
                private String id;
                private String description;
                private String iconUrl;
                private String name;
                private boolean subtask;
                private int avatarId;
                private int hierarchyLevel;
            }
        }
    }

    @Getter
    @Setter
    public static class Creator {
        private String self;
        private String accountId;
        private String emailAddress;
        private Map<String, String> avatarUrls;
        private String displayName;
        private boolean active;
        private String timeZone;
        private String accountType;
    }
}
