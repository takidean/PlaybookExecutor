package com.activeviam.creator.service;

public interface SecurityService {
    String findLoggedInUsername();

    void autoLogin(String username, String password);
}
