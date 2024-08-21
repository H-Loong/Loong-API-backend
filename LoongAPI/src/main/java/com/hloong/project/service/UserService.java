package com.hloong.project.service;



import com.baomidou.mybatisplus.extension.service.IService;
import com.hloong.common.model.entity.User;

import javax.servlet.http.HttpServletRequest;

/**
* @author lenovo
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2024-08-08 14:39:08
*/
public interface UserService extends IService<User> {

    long userRegister(String userAccount, String userPassword, String checkPassword);

    User userLogin(String userAccount, String userPassword, HttpServletRequest request);

    User getLoginUser(HttpServletRequest request);

    boolean isAdmin(HttpServletRequest request);

    boolean userLogout(HttpServletRequest request);
}
