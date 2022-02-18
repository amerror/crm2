package com.company.crm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.company.crm.dao.mapper.UserDao;
import com.company.crm.dao.pojo.User;
import com.company.crm.service.UserService;
import com.company.crm.utils.DateTimeUtil;
import com.company.crm.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zytwl
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Override
    public Result findUser(String loginAct, String loginPwd,String ip) {
        System.out.println("loginAct:" + loginAct);
        System.out.println("loginPwd:" + loginPwd);
        if (StringUtils.isBlank(loginAct) || StringUtils.isBlank(loginPwd)) {
            return Result.fail(400, "账号或者密码为空");
        }
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getLoginAct,loginAct);
        wrapper.eq(User::getLoginPwd,loginPwd);
        wrapper.last("limit 1");
        User user = userDao.selectOne(wrapper);
        if (user == null) {
            return Result.fail(400, "账号未找到");
        }
        String expireTime = user.getExpireTime();
        String currentTime = DateTimeUtil.getSysTime();
        if (expireTime.compareTo(currentTime) < 0) {
            return Result.fail(400, "账号已失效");
        }
        if ("0".equals(user.getLockState())) {
            return Result.fail(400, "账号已锁定");
        }
        if (!user.getAllowIps().contains(ip)) {
            return Result.fail(400, "该ip不允许访问");
        }
        return Result.success(user);
    }
}
