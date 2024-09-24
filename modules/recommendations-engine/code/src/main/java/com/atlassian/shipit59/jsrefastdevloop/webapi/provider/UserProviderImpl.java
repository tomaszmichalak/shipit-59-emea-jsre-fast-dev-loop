package com.atlassian.shipit59.jsrefastdevloop.webapi.provider;

import com.atlassian.shipit59.jsrefastdevloop.webapi.data.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

@Component
public class UserProviderImpl implements UserProvider {

    private final static Logger LOGGER = LoggerFactory.getLogger(UserProviderImpl.class);

    @Value("${services.users.url}")
    private String baseUrl;

    private final RestClient client;

    public UserProviderImpl() {
        this.client = RestClient.create();
    }

    @Override
    public User getUser(String email) {
        User response = client.method(HttpMethod.GET).uri(baseUrl + "/user/" + email)
                .retrieve()
                .body(User.class);

        LOGGER.debug("User data received: {}", response);

        return response;
    }
}
