package com.pradeep.controller;

import com.pradeep.entity.JWTRequest;
import com.pradeep.entity.JWTResponse;
import com.pradeep.service.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class JWTController {

    @Autowired
    private JWTService jwtService;

    @PostMapping("/authenticate")
    private JWTResponse createJWTToken(@RequestBody JWTRequest jwtRequest) throws Exception {
        return jwtService.createJWTToken(jwtRequest);
    }
}
