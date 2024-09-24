package com.atlassian.shipit59.jsrefastdevloop.webapi.service;

import com.atlassian.shipit59.jsrefastdevloop.webapi.data.Recommendation;
import com.atlassian.shipit59.jsrefastdevloop.webapi.data.RecommendationStatus;
import com.atlassian.shipit59.jsrefastdevloop.webapi.data.User;
import com.atlassian.shipit59.jsrefastdevloop.webapi.provider.RecommendationsProvider;
import com.atlassian.shipit59.jsrefastdevloop.webapi.provider.UserProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RecommendationService {

    private final RecommendationsProvider recommendationProvider;
    private final UserProvider userProvider;

    @Autowired
    public RecommendationService(RecommendationsProvider recommendationProvider, UserProvider userProvider) {
        this.recommendationProvider = recommendationProvider;
        this.userProvider = userProvider;
    }

    public RecommendationStatus getRecommendations(String email) {
        User user = userProvider.getUser(email);
        List<Recommendation> recommendations;
        if (user != null) {
            recommendations = recommendationProvider.getRecommendations(user.id());
        } else {
            recommendations = List.of();
        }
        return new RecommendationStatus(!(user == null), recommendations);
    }
}
