package com.hloong.project.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.hloong.common.model.entity.InterfaceInfo;

/**
* @author lenovo
* @description 针对表【interface_info(接口信息)】的数据库操作Service
* @createDate 2024-08-07 16:26:12
*/
public interface InterfaceInfoService extends IService<InterfaceInfo> {

    void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add);
}
