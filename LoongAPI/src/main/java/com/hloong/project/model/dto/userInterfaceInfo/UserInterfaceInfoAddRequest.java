package com.hloong.project.model.dto.userInterfaceInfo;


import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class UserInterfaceInfoAddRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -5285204334336838487L;
    /**
     * 调用用户 id
     */
    private Long userId;

    /**
     * 接口 id
     */
    private Long interfaceInfoId;

    /**
     * 总调用次数
     */
    private Integer totalNum;

    /**
     * 剩余调用次数
     */
    private Integer leftNum;

}
