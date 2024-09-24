package com.atlassian.shipit59.jsrefastdevloop.webapi.provider;

import com.atlassian.shipit59.jsrefastdevloop.webapi.data.User;

public interface UserProvider {
    /**
     * Get a user by email
     * @param email the email of the user
     * @return the user or null if not found
     */
    User getUser(String email);
}
