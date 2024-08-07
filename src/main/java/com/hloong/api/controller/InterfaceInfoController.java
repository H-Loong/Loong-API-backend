package com.hloong.api.controller;

import com.hloong.api.common.BaseResponse;
import com.hloong.api.common.ErrorCode;
import com.hloong.api.exception.BusinessException;
import com.hloong.api.mapper.InterfaceInfoMapper;
import com.hloong.api.model.InterfaceInfo;
import com.hloong.api.model.dto.InterfaceInfoAddRequest;
import com.hloong.api.service.InterfaceInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/interfaceInfo")
@Slf4j
public class InterfaceInfoController {

    @Resource
    private InterfaceInfoService interfaceInfoService;

    /**
     * 添加接口信息
     */
    @PostMapping("/add")
    public BaseResponse<Long> addInterfaceInfo(@RequestBody InterfaceInfoAddRequest interfaceInfoAddRequest, HttpServletRequest request){
        if (interfaceInfoAddRequest == null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"请求参数为空");
        }
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        BeanUtils.copyProperties(interfaceInfoAddRequest,interfaceInfo);

    }
}
