package com.example.demo;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;

@RestController
class HomeController {

    @GetMapping("/")
    public ModelAndView home(@AuthenticationPrincipal OidcUser user) {
        return new ModelAndView("home", Collections.singletonMap("claims", user.getClaims()));
    }
}