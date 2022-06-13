package com.webmvctest.webmvctest.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Getter
@Setter

public final class UserResource {

    @NotNull
    private  String name;

    @NotNull
    private  String email;

    public UserResource(String name,  String email) {
        this.name = name;
        this.email = email;
    }

    public UserResource() {
    }
}