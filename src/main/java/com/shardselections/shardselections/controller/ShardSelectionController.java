package com.shardselections.shardselections.controller;

import com.shardselections.shardselections.constants.PathConstants;
import com.shardselections.shardselections.request.ShardSelectionRequest;
import com.shardselections.shardselections.services.ShardSelectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yashasvi
 */

@RestController
@RequestMapping(PathConstants.SHARD_SELECTION)
public class ShardSelectionController {

    @Autowired
    ShardSelectionService shardSelectionService;

    @PostMapping(path = "")
    public ResponseEntity<Object> executeShardSelection(@RequestBody ShardSelectionRequest shardSelectionRequest) {

        return shardSelectionService.selectShardAndExecuteQuery(shardSelectionRequest);

    }
}
