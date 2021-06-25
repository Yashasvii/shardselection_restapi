package com.shardselections.shardselections.services;

import abstractEntity.ResourceSelection;
import com.shardselections.shardselections.request.ShardSelectionRequest;
import com.shardselections.shardselections.response.ShardSelectionResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import shardSelectionAlgorithms.HybridShardSelectionAlgorithm;
import shardSelectionAlgorithms.RankS;
import shardSelectionAlgorithms.ReDDE;
import shardSelectionAlgorithms.Sushi;

import java.util.Map;

/**
 * @author yashasvi
 */

@Service
@Log4j2
public class ShardSelectionService {

    public ResponseEntity<Object> selectShardAndExecuteQuery(ShardSelectionRequest shardSelectionRequest) {


        try {

            log.info("algorithm = "+ shardSelectionRequest.getAlgorithm());

            ResourceSelection selection = getShardSelectionService(shardSelectionRequest.getAlgorithm());

            Map<String, Object> documentInfos = selection.getDocumentResponseScoreAndTime(shardSelectionRequest.getIndexName(),
                    shardSelectionRequest.getSearch_query(), true);

            ShardSelectionResponse shardSelectionResponse = new ShardSelectionResponse();
            shardSelectionResponse.setDocumentScore((Double) documentInfos.get("documentScore"));
            shardSelectionResponse.setTimeElapsed((Long) documentInfos.get("elapsedTime"));

            shardSelectionResponse.setResponse(documentInfos.get("response"));

            log.info("Document Score = " + documentInfos.get("documentScore"));
            log.info("Elapsed Time = " + documentInfos.get("elapsedTime"));
            log.info("----------------------------------------------------------------------");

            return new ResponseEntity<>(shardSelectionResponse, HttpStatus.ACCEPTED);

        } catch (Exception e) {
            log.error("Exception", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }



    }

    private ResourceSelection getShardSelectionService(String algorithm) {
        if(algorithm.equalsIgnoreCase("Redde")){
            return new ReDDE();
        }
        else if(algorithm.equalsIgnoreCase("Sushi")) {
            return new Sushi();
        }
        else if(algorithm.equalsIgnoreCase("RankS")) {
            return new RankS();
        }
        else if(algorithm.equalsIgnoreCase("hybrid")) {
            return new HybridShardSelectionAlgorithm();
        }
        return null;
    }
}
