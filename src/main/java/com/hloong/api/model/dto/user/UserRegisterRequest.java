package com.hloong.api.model.dto.user;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserRegisterRequest implements Serializable {

    private static final long serialVersionUID = -8307882973567972410L;

    private String userAccount;

    private String userPassword;

    private String checkPassword;

}
