package com.company.crm.controller;

import com.company.crm.service.UserService;
import com.company.crm.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zytwl
 */
@RestController()
@RequestMapping(value = "user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("login.do")
    public Result login(HttpServletRequest request){
        return userService.findUser(request.getParameter("loginAct"),
                request.getParameter("loginPwd") ,request.getRemoteAddr());
    }
}
