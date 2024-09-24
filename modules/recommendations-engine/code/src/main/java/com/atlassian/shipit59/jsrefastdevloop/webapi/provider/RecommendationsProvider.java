package com.atlassian.shipit59.jsrefastdevloop.webapi.provider;

import com.atlassian.shipit59.jsrefastdevloop.webapi.data.Recommendation;

import java.util.List;

public interface RecommendationsProvider {
    /**
     * Get recommendations for a user
     * @param id the id of the user
     * @return the recommendations
     */
    List<Recommendation> getRecommendations(String id);
}
