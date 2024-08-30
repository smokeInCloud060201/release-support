package org.example.api.common.dto.request;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.util.List;

@Data
@Builder
public class URLComponent {
    @Builder.Default
    private String scheme = "https";
    private String host;
    private String path;
    @Singular("queryParam")
    private List<QueryParam> queryParams;
}
