package com.atlassian.shipit59.jsrefastdevloop.webapi;

import com.atlassian.shipit59.jsrefastdevloop.webapi.data.Recommendation;
import com.atlassian.shipit59.jsrefastdevloop.webapi.data.RecommendationStatus;
import com.atlassian.shipit59.jsrefastdevloop.webapi.service.RecommendationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {

    private final static Logger LOG = LoggerFactory.getLogger(TestController.class);

    private final RecommendationService service;

    @Autowired
    TestController(RecommendationService service) {
        this.service = service;
    }

    @GetMapping("/recommendations/{email}")
    public List<Recommendation> recommendations(@PathVariable String email) {
        RecommendationStatus status = service.getRecommendations(email);
        LOG.info("Recommendations status: {}", status);
        return status.recommendations();
    }

    @GetMapping("/test-e2e")
    public String testE2E() {
        return "Hello World!";
    }
}
