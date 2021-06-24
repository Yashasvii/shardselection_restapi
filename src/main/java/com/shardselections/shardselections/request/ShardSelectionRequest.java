package com.shardselections.shardselections.request;

import lombok.Data;

/**
 * @author yashasvi
 */

@Data
public class ShardSelectionRequest {

    private String algorithm;
    private Object search_query;
    private String indexName;
}
