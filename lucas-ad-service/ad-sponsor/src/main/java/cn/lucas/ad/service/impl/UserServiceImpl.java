package cn.lucas.ad.service.impl;

import cn.lucas.ad.constant.Constants;
import cn.lucas.ad.dao.AdUserRepository;
import cn.lucas.ad.entity.AdUser;
import cn.lucas.ad.exception.AdException;
import cn.lucas.ad.service.IUserService;
import cn.lucas.ad.utils.CommonUtils;
import cn.lucas.ad.vo.CreateUserRequest;
import cn.lucas.ad.vo.CreateUserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class UserServiceImpl implements IUserService {

    private final AdUserRepository adUserRepository;

    @Autowired
    public UserServiceImpl(AdUserRepository adUserRepository) {
        this.adUserRepository = adUserRepository;
    }

    @Override
    @Transactional
    public CreateUserResponse createUser(CreateUserRequest request) throws AdException {

        if (!request.validate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        AdUser oldUser = adUserRepository.findByUsername(request.getUsername());
        if (oldUser != null) {
            throw new AdException(Constants.ErrorMsg.SAME_NAME_ERROR);
        }

        AdUser newUser = adUserRepository.save(new AdUser(request.getUsername(),
                CommonUtils.md5(request.getUsername())));

        return new CreateUserResponse(newUser.getId(),
                newUser.getUsername(),newUser.getToken(),
                newUser.getCreateTime(),newUser.getUpdateTime());
    }
}
