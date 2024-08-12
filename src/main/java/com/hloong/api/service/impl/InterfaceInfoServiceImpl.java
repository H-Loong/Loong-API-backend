package com.hloong.api.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hloong.api.common.ErrorCode;
import com.hloong.api.exception.BusinessException;
import com.hloong.api.model.InterfaceInfo;
import com.hloong.api.service.InterfaceInfoService;
import com.hloong.api.mapper.InterfaceInfoMapper;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;


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
        if(interfaceInfo == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String name = interfaceInfo.getName();
        String description = interfaceInfo.getDescription();
        String url = interfaceInfo.getUrl();
        String requestParams = interfaceInfo.getRequestParams();
        String requestHeader = interfaceInfo.getRequestHeader();
        String responseHeader = interfaceInfo.getResponseHeader();
        String method = interfaceInfo.getMethod();
        if (add){
            if (StringUtils.isAnyBlank(name,description,url,requestParams,requestHeader,responseHeader,method)){
                throw new BusinessException(ErrorCode.PARAMS_ERROR);
            }
        }
        if (StringUtils.isNotBlank(name) && name.length() > 64){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"名称过长");
        }
        if (StringUtils.isNotBlank(description) && description.length() > 512){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"描述过长");
        }
        if (StringUtils.isNotBlank(url) && url.length() > 512){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"url过长");
        }
        if (StringUtils.isNotBlank(method) &&
                !("GET".equalsIgnoreCase(method) || "POST".equalsIgnoreCase(method) ||
                "PUT".equalsIgnoreCase(method) || "DELETE".equalsIgnoreCase(method))){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"请求类型错误");
        }
    }

}




