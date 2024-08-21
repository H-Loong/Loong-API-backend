package com.hloong.project.controller;

import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.hloong.client_sdk.model.BaseRequest;
import com.hloong.common.model.entity.InterfaceInfo;
import com.hloong.common.model.entity.User;
import com.hloong.common.model.enums.InterfaceInfoStatusEnum;
import com.hloong.project.annotation.AuthCheck;
import com.hloong.project.exception.BaseResponse;
import com.hloong.project.common.IdRequest;
import com.hloong.common.exception.ErrorCode;
import com.hloong.project.common.ResultUtils;
import com.hloong.project.constant.CommonConstant;
import com.hloong.project.constant.UserConstant;
import com.hloong.common.exception.BusinessException;
import com.hloong.project.exception.ThrowUtils;
import com.hloong.project.model.dto.interfaceInfo.InterfaceInfoInvokeRequest;
import com.hloong.project.model.dto.interfaceInfo.InterfaceInfoQueryRequest;
import com.hloong.project.model.dto.interfaceInfo.InterfaceInfoUpdateRequest;

import com.hloong.project.model.dto.interfaceInfo.InterfaceInfoAddRequest;

import com.hloong.project.service.InterfaceInfoService;
import com.hloong.project.service.UserService;
import com.hloong.client_sdk.client.LoongAPIClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * 接口管理
 *
 * @author H-Loong
 */
@RestController
@RequestMapping("/interfaceInfo")
@Slf4j
public class InterfaceInfoController {

    @Resource
    private InterfaceInfoService interfaceInfoService;

    @Resource
    private UserService userService;

    @Resource
    private LoongAPIClient loongAPIClient;

    /**
     * 添加接口信息
     */
    @PostMapping("/add")
    public BaseResponse<Long> addInterfaceInfo(@RequestBody InterfaceInfoAddRequest interfaceInfoAddRequest, HttpServletRequest request) {
        if (interfaceInfoAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        BeanUtils.copyProperties(interfaceInfoAddRequest, interfaceInfo);
        interfaceInfoService.validInterfaceInfo(interfaceInfo, true);
        User loginUser = userService.getLoginUser(request);
        interfaceInfo.setUserId(loginUser.getId());
        boolean result = interfaceInfoService.save(interfaceInfo);
        if (!result) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        Long interfaceInfoId = interfaceInfo.getId();
        return ResultUtils.success(interfaceInfoId);
    }

    /**
     * 删除接口
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteInterfaceInfo(@RequestBody IdRequest idRequest, HttpServletRequest request) {
        if (idRequest == null || idRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        Long id = idRequest.getId();
        InterfaceInfo interfaceInfo = interfaceInfoService.getById(id);
        if (interfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        //仅管理员或本人可以删除
        if (!interfaceInfo.getUserId().equals(loginUser.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean result = interfaceInfoService.removeById(id);
        return ResultUtils.success(result);
    }

    /**
     * 更新接口信息
     */
    @PostMapping("/update")
    public BaseResponse<Boolean> updateInterfaceInfo(@RequestBody InterfaceInfoUpdateRequest updateRequest, HttpServletRequest request) {
        if (updateRequest == null || updateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        BeanUtils.copyProperties(updateRequest, interfaceInfo);
        //校验参数
        interfaceInfoService.validInterfaceInfo(interfaceInfo, false);
        User loginUser = userService.getLoginUser(request);
        Long id = updateRequest.getId();
        InterfaceInfo oldInterfaceInfo = interfaceInfoService.getById(id);
        if (oldInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if (!oldInterfaceInfo.getUserId().equals(loginUser.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean result = interfaceInfoService.updateById(interfaceInfo);
        return ResultUtils.success(result);
    }

    /**
     * 根据id查询
     */
    @GetMapping("/get")
    public BaseResponse<InterfaceInfo> getInterfaceInfoById(long id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo interfaceInfo = interfaceInfoService.getById(id);
        return ResultUtils.success(interfaceInfo);
    }

    /**
     * 获取列表（仅管理员可用）
     */
    @AuthCheck(mustRole = "admin")
    @GetMapping("/list")
    public BaseResponse<List<InterfaceInfo>> listInterfaceInfo(InterfaceInfoQueryRequest queryRequest) {
        if (queryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        InterfaceInfo interfaceInfoQuery = new InterfaceInfo();
        BeanUtils.copyProperties(queryRequest, interfaceInfoQuery);
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>(interfaceInfoQuery);
        List<InterfaceInfo> interfaceInfoList = interfaceInfoService.list(queryWrapper);
        return ResultUtils.success(interfaceInfoList);
    }

    /**
     * 分页获取列表
     */
    @GetMapping("/list/page")
    public BaseResponse<Page<InterfaceInfo>> listInterfaceInfoByPage(InterfaceInfoQueryRequest queryRequest) {
        if (queryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }
        InterfaceInfo interfaceInfoQuery = new InterfaceInfo();
        BeanUtils.copyProperties(queryRequest, interfaceInfoQuery);
        long current = queryRequest.getCurrent();
        long size = queryRequest.getPageSize();
        String sortField = queryRequest.getSortField();
        String sortOrder = queryRequest.getSortOrder();
        String description = queryRequest.getDescription();
        // 限制爬虫
        if (size > 50) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "每页大小不能超过50");
        }
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>(interfaceInfoQuery);
        if (StringUtils.isNotBlank(description)) {
            queryWrapper.like("description", description);
        }
        // 校验排序字段和排序顺序
        if (StringUtils.isNotBlank(sortField) &&
                (CommonConstant.SORT_ORDER_ASC.equals(sortOrder) || CommonConstant.SORT_ORDER_DESC.equals(sortOrder))) {
            queryWrapper.orderBy(true, CommonConstant.SORT_ORDER_ASC.equals(sortOrder), sortField);
        }
        Page<InterfaceInfo> interfaceInfoPage = interfaceInfoService.page(new Page<>(current, size), queryWrapper);
        return ResultUtils.success(interfaceInfoPage);
    }

    /**
     * 发布（仅管理员）
     *
     * @param idRequest id
     * @return
     */
    @PostMapping("/online")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> onlineInterfaceInfo(@RequestBody IdRequest idRequest) throws BusinessException {
        // 参数校验
        Long id = idRequest.getId();
        if (idRequest == null || id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 校验接口是否存在
        InterfaceInfo oldInterfaceInfo = interfaceInfoService.getById(id);
        ThrowUtils.throwIf(oldInterfaceInfo == null, ErrorCode.NOT_FOUND_ERROR);
        // 校验接口是否可以调用
        com.hloong.client_sdk.model.entity.User user = new com.hloong.client_sdk.model.entity.User();
        user.setName("H-Loong");
        // todo 这个地方传的是固定值，后续需要改为从接口信息中获取
        URL url = null;
        try {
            url = new URL(oldInterfaceInfo.getUrl());
        } catch (MalformedURLException e) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "无效的地址转换");
        }
        String path = url.getPath();
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setPath(path);
        baseRequest.setMethod(oldInterfaceInfo.getMethod());
        baseRequest.setRequestParams(null);
        Object clientResult = loongAPIClient.parseAddressAndCallInterface(baseRequest);
        if (clientResult == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "接口验证失败");
        }
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        interfaceInfo.setId(id);
        interfaceInfo.setStatus(InterfaceInfoStatusEnum.ONLINE.getValue());
        boolean result = interfaceInfoService.updateById(interfaceInfo);
        return ResultUtils.success(result);
    }

    /**
     * 下线（仅管理员）
     *
     * @param idRequest
     * @return
     */
    @PostMapping("/offline")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> offlineInterfaceInfo(@RequestBody IdRequest idRequest) {
        // 参数校验
        Long id = idRequest.getId();
        if (idRequest == null || id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 校验接口是否存在
        InterfaceInfo oldInterfaceInfo = interfaceInfoService.getById(id);
        ThrowUtils.throwIf(oldInterfaceInfo == null, ErrorCode.NOT_FOUND_ERROR);
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        interfaceInfo.setId(id);
        interfaceInfo.setStatus(InterfaceInfoStatusEnum.OFFLINE.getValue());
        boolean result = interfaceInfoService.updateById(interfaceInfo);
        return ResultUtils.success(result);
    }
    /**
     * 测试调用
     */
    @PostMapping("/invoke")
    public BaseResponse<Object> invokeInterfaceInfo(@RequestBody InterfaceInfoInvokeRequest interfaceInfoInvokeRequest, HttpServletRequest request) {
        // 参数校验
        Long id = interfaceInfoInvokeRequest.getId();
        if (interfaceInfoInvokeRequest == null || id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 校验接口是否存在
        InterfaceInfo oldInterfaceInfo = interfaceInfoService.getById(id);
        ThrowUtils.throwIf(oldInterfaceInfo == null, ErrorCode.NOT_FOUND_ERROR, "接口不存在");

        // 校验接口是否上线
        if(!oldInterfaceInfo.getStatus().equals(InterfaceInfoStatusEnum.ONLINE.getValue())){
            throw new BusinessException(ErrorCode.FORBIDDEN_ERROR, "接口未上线");
        }

        // 获取api密钥
        User loginUser = userService.getLoginUser(request);
        String accessKey = loginUser.getAccessKey();
        String secretKey = loginUser.getSecretKey();
        log.info("获取当前登录用户API密钥 accessKey:{}  secretKey:{}", accessKey, secretKey);
        // 创建一个client发送请求
        LoongAPIClient client = new LoongAPIClient(accessKey, secretKey);
        // 调用sdk自动判断调用接口
        // 调用sdk
        URL url ;
        try {
            url = new URL(oldInterfaceInfo.getUrl());
        } catch (MalformedURLException e) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "无效的地址转换");
        }
        String path = url.getPath();
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setPath(path);
        baseRequest.setMethod(oldInterfaceInfo.getMethod());
        baseRequest.setRequestParams(interfaceInfoInvokeRequest.getRequestParams());
        baseRequest.setUserRequest(request);
        Object result = null;
        try {
            // 调用sdk解析地址方法
            result = client.parseAddressAndCallInterface(baseRequest);
        } catch (BusinessException e) {
            throw new BusinessException(e.getCode(), e.getMessage());
        }
        if (ObjUtil.isEmpty(request)) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "请求SDK失败");
        }
        log.info("调用api接口返回结果：" + result);
        return ResultUtils.success(result);
    }
}
