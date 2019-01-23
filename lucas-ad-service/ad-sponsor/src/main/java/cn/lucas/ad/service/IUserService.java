package cn.lucas.ad.service;

import cn.lucas.ad.exception.AdException;
import cn.lucas.ad.vo.CreateUserRequest;
import cn.lucas.ad.vo.CreateUserResponse;

public interface IUserService {


    /**
     * 创建 用户
     * @param request
     * @return
     * @throws AdException
     */
    CreateUserResponse createUser(CreateUserRequest request) throws AdException;
}
