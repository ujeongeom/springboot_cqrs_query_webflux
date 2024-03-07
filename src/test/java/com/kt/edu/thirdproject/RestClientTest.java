package com.kt.edu.thirdproject;

import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.util.Map;

public class RestClientTest {

    @Test
    public void FirstClient() {
        // https://open.er-api.com/v6/latest
        RestTemplate rt = new RestTemplate();
        String res = rt.getForObject("https://open.er-api.com/v6/latest", String.class);
        System.out.println(res);

        // KRW 꺼내서 달러 대비 비교
        Map<String, Object> res1 = rt.getForObject("https://open.er-api.com/v6/latest", Map.class);
        System.out.println(res1.get("rates"));

        Map<String, Map<String, Double>> res2 = rt.getForObject("https://open.er-api.com/v6/latest", Map.class);
        System.out.println(res2.get("rates").get("KRW"));
    }

    @Test
    public void SecondClient() {
        WebClient wc = WebClient.create("https://open.er-api.com");
        Map<String, Map<String, Double>> wc1 = wc.get().uri("/v6/latest").retrieve().bodyToMono(Map.class).block();

        System.out.println(wc1.get("rates").get("KRW"));
    }

    @Test
    public void ThirdClient() {
        WebClient client = WebClient.create("https://open.er-api.com");

        HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory
                .builder(WebClientAdapter.forClient(client))
                .build();

        ErApi erApi = httpServiceProxyFactory.createClient(ErApi.class);
        Map<String, Map<String, Double>> http1 = erApi.getLatest();

        System.out.println(http1.get("rates").get("KRW"));
    }

    interface ErApi {
        @GetExchange("/v6/latest")
        Map getLatest();
    }
}
