package com.atlassian.shipit59.jsrefastdevloop.webapi.provider;

import com.atlassian.shipit59.jsrefastdevloop.webapi.data.Recommendation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.List;

@Component
public class RecommendationsProviderImpl implements RecommendationsProvider {

    private final static Logger LOGGER = LoggerFactory.getLogger(RecommendationsProviderImpl.class);

    @Value("${services.recommendations.url}")
    private String baseUrl;

    private final RestClient client;

    public RecommendationsProviderImpl() {
        this.client = RestClient.create();
    }

    @Override
    public List<Recommendation> getRecommendations(String id) {
        Recommendation[] recommendations = client.method(HttpMethod.GET)
                .uri(baseUrl + "/recommendations/" + id)
                .retrieve()
                .body(Recommendation[].class);

        if (recommendations == null) {
            recommendations = new Recommendation[0];
        }

        LOGGER.debug("Recommendations length {}", recommendations.length);

        return Arrays.asList(recommendations);
    }
}
