package com.hloong.api.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hloong.api.model.InterfaceInfo;
import com.hloong.api.service.InterfaceInfoService;
import com.hloong.api.mapper.InterfaceInfoMapper;
import org.springframework.stereotype.Service;

/**
* @author lenovo
* @description 针对表【interface_info(接口信息)】的数据库操作Service实现
* @createDate 2024-08-07 16:26:12
*/
@Service
public class InterfaceInfoServiceImpl extends ServiceImpl<InterfaceInfoMapper, InterfaceInfo>
    implements InterfaceInfoService {

    @Override
    public void validInterfaceInfo(InterfaceInfo interfaceInfo,boolean add){

    }

}




