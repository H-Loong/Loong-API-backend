package com.hloong.common.service;

/**
 * 内部用户接口信息服务
 *
 * @author H-Loong
 */
public interface InnerUserInterfaceInfoService {

    /**
     * 调用接口统计
     * @param interfaceInfoId
     * @param userId
     * @return
     */
    boolean invokeCount(long interfaceInfoId, long userId);
}
