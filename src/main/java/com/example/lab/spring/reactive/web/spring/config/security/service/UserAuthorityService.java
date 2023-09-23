package com.example.lab.spring.reactive.web.spring.config.security.service;

import com.example.lab.spring.reactive.web.spring.config.security.dto.AuthenticatedUser;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserAuthorityService {

    public List<SimpleGrantedAuthority> getAuthorities(AuthenticatedUser user) {
        // TODO: get authorities of the given user when needed in the future
        return new ArrayList<>();
    }
}
