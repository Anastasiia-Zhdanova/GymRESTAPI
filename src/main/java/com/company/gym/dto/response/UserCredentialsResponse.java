package com.company.gym.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response DTO containing generated user credentials upon successful registration.")
public class UserCredentialsResponse {

    @Schema(description = "Generated unique username.")
    private String username;

    @Schema(description = "Generated temporary password.")
    private String password;

    public UserCredentialsResponse(String username, String password) {
        this.username = username;
        this.password = password;
    }

}