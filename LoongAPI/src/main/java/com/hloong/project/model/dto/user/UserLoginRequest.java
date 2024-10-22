package com.hloong.project.model.dto.user;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserLoginRequest implements Serializable {

    private static final long serialVersionUID = -7799321599952411274L;

    private String userAccount;

    private String userPassword;
}
