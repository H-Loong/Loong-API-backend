package com.hloong.project.model.dto.interfaceInfo;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * 接口调用请求
 */
@Data
public class InterfaceInfoInvokeRequest implements Serializable {

    private static final long serialVersionUID = -1567479606932782773L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 用户请求参数
     */
    private Map<String, Object> requestParams;
}
