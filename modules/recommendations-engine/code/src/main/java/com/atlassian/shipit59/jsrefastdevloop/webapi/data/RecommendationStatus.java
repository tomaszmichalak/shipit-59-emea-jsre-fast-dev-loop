package com.atlassian.shipit59.jsrefastdevloop.webapi.data;

import java.util.List;

public record RecommendationStatus(boolean userExists, List<Recommendation> recommendations) {
}
