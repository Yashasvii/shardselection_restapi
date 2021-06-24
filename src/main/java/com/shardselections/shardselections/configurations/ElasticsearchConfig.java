package com.shardselections.shardselections.configurations;


import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yashasvi
 */
@Configuration
public class ElasticsearchConfig {
//
    @Value("${elasticsearch.host}")
    public String host;

    @Value("${elasticsearch.port}")
    public int port;

    @Value("${elasticsearch.username}")
    public String username;

    @Value("${elasticsearch.password}")
    public String password;


    static final AWSCredentialsProvider credentialsProvider = new DefaultAWSCredentialsProviderChain();
    private static final String serviceName = "es";
    private static final String region = "ap-south-1";
    private static final String aesEndpoint = "https://search-estest-qn7gye4v5ocpbu3fgdpotgxlq.ap-south-1.es.amazonaws.com";

//    @Bean
//    public RestHighLevelClient elasticsearchClient() {
//
//        AWS4Signer signer = new AWS4Signer();
//        signer.setServiceName(serviceName);
//        signer.setRegionName(region);
//        HttpRequestInterceptor interceptor = new AWSRequestSigningApacheInterceptor(serviceName, signer, credentialsProvider);
//        return new RestHighLevelClient(RestClient.builder(HttpHost.create(aesEndpoint)).setHttpClientConfigCallback(hacb -> hacb.addInterceptorLast(interceptor)));
//    }

//    @Bean
//    public String clusterInfo() {
//        return aesEndpoint;
//    }


    @Bean
    public RestHighLevelClient elasticsearchClient() {

        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));

        RestClientBuilder builder = RestClient.builder(new HttpHost(host, port, "http"))
                .setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider));

        return new RestHighLevelClient(builder);
    }

    @Bean
    public String clusterInfo() {
        return "http://"+ host + ":"+port;
    }

}
