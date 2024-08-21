package com.hloong.project.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.hloong.common.model.entity.UserInterfaceInfo;

/**
* @author lenovo
* @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Service
* @createDate 2024-08-16 08:28:23
*/
public interface UserInterfaceInfoService extends IService<UserInterfaceInfo> {

    void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, Boolean add);

    boolean invokeCount(long interfaceInfoId, long userId);
}
