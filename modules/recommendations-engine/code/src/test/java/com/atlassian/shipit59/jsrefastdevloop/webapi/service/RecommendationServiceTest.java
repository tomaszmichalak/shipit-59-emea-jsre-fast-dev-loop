package com.atlassian.shipit59.jsrefastdevloop.webapi.service;

import com.atlassian.shipit59.jsrefastdevloop.webapi.data.Recommendation;
import com.atlassian.shipit59.jsrefastdevloop.webapi.data.User;
import com.atlassian.shipit59.jsrefastdevloop.webapi.provider.RecommendationsProvider;
import com.atlassian.shipit59.jsrefastdevloop.webapi.provider.RecommendationsProviderImpl;
import com.atlassian.shipit59.jsrefastdevloop.webapi.provider.UserProvider;
import com.atlassian.shipit59.jsrefastdevloop.webapi.provider.UserProviderImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RecommendationServiceTest {


    @Test
    @DisplayName("If a user exists in the system, recommendations should be returned")
    void getRecommendationStatus() {
        // Given
        String email = "existingUser@atlassian.com";
        String userId = "user1";
        RecommendationsProvider recommendationsProvider = Mockito.mock(RecommendationsProvider.class);
        UserProvider userProvider = Mockito.mock(UserProvider.class);
        Mockito.when(recommendationsProvider.getRecommendations(userId)).thenReturn(
                List.of(new Recommendation("test1", "name1", "description1", "url1")));
        Mockito.when(userProvider.getUser(email)).thenReturn(
                new User(userId, "Bob", email));

//        RecommendationsProvider recommendationsProvider = new RecommendationsProviderImpl();
//        UserProvider userProvider = new UserProviderImpl();

        RecommendationService recommendationService = new RecommendationService(recommendationsProvider, userProvider);

        // When
        var status = recommendationService.getRecommendations(email);

        // Then
        assertNotNull(status);
        assertTrue(status.userExists());
        assertEquals("test1", status.recommendations().get(0).id());
    }

    @Test
    @DisplayName("If a user exists in the system and has no recommendations, no recommendations should be returned")
    void getRecommendationStatusNoRecommendations() {
        // Given
        String email = "existingUserNoRecommendations@atlassian.com";
        String userId = "user2";
        RecommendationsProvider recommendationsProvider = Mockito.mock(RecommendationsProvider.class);
        UserProvider userProvider = Mockito.mock(UserProvider.class);
        Mockito.when(recommendationsProvider.getRecommendations(userId)).thenReturn(List.of());
        Mockito.when(userProvider.getUser(email)).thenReturn(
                new User(userId, "John", email));

//        RecommendationsProvider recommendationsProvider = new RecommendationsProviderImpl();
//        UserProvider userProvider = new UserProviderImpl();

        RecommendationService recommendationService = new RecommendationService(recommendationsProvider, userProvider);

        // When
        var status = recommendationService.getRecommendations(email);

        // Then
        assertNotNull(status);
        assertTrue(status.userExists());
        assertEquals(0, status.recommendations().size());
    }

    @Test
    @DisplayName("If a user does not exist in the system, empty recommendations should be returned")
    void getRecommendationStatusNoUser() {
        // Given
        String email = "nonExistingUser@atlassian.com";
        String userId = "user3";
        RecommendationsProvider recommendationsProvider = Mockito.mock(RecommendationsProvider.class);
        UserProvider userProvider = Mockito.mock(UserProvider.class);
        Mockito.when(recommendationsProvider.getRecommendations(userId)).thenReturn(List.of());
        Mockito.when(userProvider.getUser(email)).thenReturn(null);

//        RecommendationsProvider recommendationsProvider = new RecommendationsProviderImpl();
//        UserProvider userProvider = new UserProviderImpl();

        RecommendationService recommendationService = new RecommendationService(recommendationsProvider, userProvider);

        // When
        var status = recommendationService.getRecommendations(email);

        // Then
        assertNotNull(status);
        assertFalse(status.userExists());
        assertEquals(0, status.recommendations().size());
    }
}
