package org.example.api.common.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class QueryParam {
    private String key;
    private Object value;
}
