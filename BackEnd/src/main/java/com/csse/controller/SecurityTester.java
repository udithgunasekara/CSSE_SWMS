package com.csse.Controller;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityTester {

    @GetMapping("/csrf-token")
    public CsrfToken getcsrfToken (HttpServletRequest request){
        return (CsrfToken) request.getAttribute("_csrf");
    }

}
