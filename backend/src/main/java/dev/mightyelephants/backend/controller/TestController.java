package dev.mightyelephants.backend.controller;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @RequestMapping("/test")
    public String test(@AuthenticationPrincipal Jwt jwt) {
        return "Hello, " + jwt.getClaimAsString("name") + "!\n";
    }
}
