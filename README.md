# shardSelectionsREST
This is the REST API that sent shard selection request to the shardselection plugin installed in data nodes and broker nodes. It's request format:

```json
{
"algorithm": "Hybrid",
"maxShards":20,
"totalShards":20,
"search_query" : {
 "query": {
    "match": {
     "content": "players coaches"
    }
  }
},
"indexName":"http",
"alpha":20

}
```
Here,
"algorithm" takes values of ["Redde", "Sushi", "RankS", "hybrid" ] <br/>
"maxShards" is the maximum number of shards to be considered for shard selection <br/>
"totalShards" is the total number of shard for the index to be queried <br/>
"search_query" is the query to be executed <br/>
"indexName" name of the index </br>
"alpha": a tunable numeric constant to determine how many documents are considered for the voting of their priority <br/>
"routingField": Fields to be considered for query routing(if any)

Dataset used for experimentation is Insider Threat Test Dataset(CERT V6.2) email.csv and http.csv from the site:

https://kilthub.cmu.edu/articles/dataset/Insider_Threat_Test_Dataset/12841247/1

