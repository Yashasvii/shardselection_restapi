package com.shardselections.shardselections.request;

import lombok.Data;

import java.util.Map;

/**
 * @author yashasvi
 */

@Data
public class ShardSelectionRequest {

    private String algorithm;
    private Map search_query;
    private String indexName;
}
