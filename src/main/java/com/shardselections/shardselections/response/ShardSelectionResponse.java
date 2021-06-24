package com.shardselections.shardselections.response;

import lombok.Data;

/**
 * @author yashasvi
 */

@Data
public class ShardSelectionResponse {

    Double documentScore;
    Long timeElapsed;
    Object response;
}
