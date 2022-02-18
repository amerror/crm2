package com.company.crm.service;

import com.company.crm.vo.Result;

/**
 * @author zytwl
 */
public interface UserService {
    /**
     * 登录
     * @param loginAct 账号
     * @param loginPwd 密码
     * @return
     * @author wanglin
     */
    Result findUser(String loginAct,String loginPwd,String ip);
}
