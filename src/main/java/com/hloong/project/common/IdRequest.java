package com.hloong.project.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class IdRequest implements Serializable {

    private static final long serialVersionUID = -402697744890330099L;

    private Long id;
}
