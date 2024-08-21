package com.hloong.client_sdk.model.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户
 * @author H-Loong
 */
@Data
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
}
