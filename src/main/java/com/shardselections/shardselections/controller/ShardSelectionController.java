package com.shardselections.shardselections.controller;

import com.shardselections.shardselections.constants.PathConstants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shardSelectionAlgorithms.HybridShardSelectionAlgorithm;

/**
 * @author yashasvi
 */

@RestController
@RequestMapping(PathConstants.SHARD_SELECTION)
public class ShardSelectionController {

    @GetMapping(path = "")
    public ResponseEntity<Object> executeShardSelection() {

        HybridShardSelectionAlgorithm hybridShardSelectionAlgorithm = new HybridShardSelectionAlgorithm();
        hybridShardSelectionAlgorithm.test();
        return null;

    }
}
