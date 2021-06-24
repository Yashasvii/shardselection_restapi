package com.shardselections.shardselections.services;

import abstractEntity.ResourceSelection;
import com.shardselections.shardselections.request.ShardSelectionRequest;
import com.shardselections.shardselections.response.ShardSelectionResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import shardSelectionAlgorithms.HybridShardSelectionAlgorithm;
import shardSelectionAlgorithms.RankS;
import shardSelectionAlgorithms.ReDDE;
import shardSelectionAlgorithms.Sushi;

/**
 * @author yashasvi
 */

@Service
@Log4j2
public class ShardSelectionService {

    @Autowired
    String clusterInfo;

    public ResponseEntity<Object> selectShardAndExecuteQuery(ShardSelectionRequest shardSelectionRequest) {


        try {

            log.info("algorithm = "+ shardSelectionRequest.getAlgorithm());

            long start = System.currentTimeMillis();

            ResourceSelection selection = getShardSelectionService(shardSelectionRequest.getAlgorithm());

            double documentScore = selection.getDocumentScore(getCSINumber(shardSelectionRequest.getSearch_query().toString()));

            log.info("documentScore = " + documentScore);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Object> clusterResponse =  restTemplate.postForEntity(clusterInfo + "/" + (shardSelectionRequest.getIndexName() == null?"" : shardSelectionRequest.getIndexName() + "/_search"), shardSelectionRequest.getSearch_query(), Object.class);

            long end = System.currentTimeMillis();
            long elapsedTime = end - start;
            log.info("elapsedTime = " + elapsedTime);

            ShardSelectionResponse shardSelectionResponse = new ShardSelectionResponse();
            shardSelectionResponse.setDocumentScore(documentScore);
            shardSelectionResponse.setTimeElapsed(elapsedTime);
            shardSelectionResponse.setResponse(clusterResponse);

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

    private int getCSINumber(String query) {

        int size = query.length();

        if(query.length() < 92) {
            return size *10;
        }

        else if(query.length() > 5000){
            return size/10;
        }
        return  size;
    }

}
