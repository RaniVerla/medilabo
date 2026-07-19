package com.medilabo.gateway;

import lombok.Data;

@Data
public class LoginRequest {

    private String username;
    private String password;
}
