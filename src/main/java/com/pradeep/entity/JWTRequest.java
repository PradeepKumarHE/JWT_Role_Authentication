package com.pradeep.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JWTRequest {
    private String username;
    private String userpassword;
}
