package cn.lucas.ad.controller;

import cn.lucas.ad.exception.AdException;
import cn.lucas.ad.service.IUserService;
import cn.lucas.ad.vo.CreateUserRequest;
import cn.lucas.ad.vo.CreateUserResponse;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 */
@Slf4j
@RestController
public class UserOPController {

    private final IUserService userService;

    @Autowired
    public UserOPController(IUserService userService) {
        this.userService = userService;
    }


    /**
     * 创建用户
     * @param request
     * @return
     * @throws AdException
     */
    @PostMapping("create/user")
    public CreateUserResponse createUser(@RequestBody CreateUserRequest request) throws AdException {
        log.info("ad-sponsor create user ->{}", JSON.toJSONString(request));
        return userService.createUser(request);
    }
}
