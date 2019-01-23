package cn.lucas.ad.service.impl;

import cn.lucas.ad.constant.CommonStatus;
import cn.lucas.ad.constant.Constants;
import cn.lucas.ad.dao.AdPlanRepository;
import cn.lucas.ad.dao.AdUserRepository;
import cn.lucas.ad.entity.AdPlan;
import cn.lucas.ad.entity.AdUser;
import cn.lucas.ad.exception.AdException;
import cn.lucas.ad.service.IAdPlanService;
import cn.lucas.ad.utils.CommonUtils;
import cn.lucas.ad.vo.AdPlanGetRequest;
import cn.lucas.ad.vo.AdPlanRequest;
import cn.lucas.ad.vo.AdPlanResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class AdPlanService implements IAdPlanService {

    private final AdUserRepository userRepository;

    private final AdPlanRepository planRepository;

    @Autowired
    public AdPlanService(AdUserRepository userRepository, AdPlanRepository planRepository) {
        this.userRepository = userRepository;
        this.planRepository = planRepository;
    }

    @Override
    public AdPlanResponse createAdPlan(AdPlanRequest request) throws AdException {
        if (!request.createValidate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        // 确保关联的 user 是存在的
        Optional<AdUser> adUser = userRepository.findById(request.getUserId());
        if (!adUser.isPresent()) {
            throw new AdException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
        }

        AdPlan oldPlan = planRepository.findByUserIdAndPlanName(request.getUserId(), request.getPlanName());
        if (oldPlan != null) {
            throw new AdException(Constants.ErrorMsg.SAME_NAME_PLAN_ERROR);
        }

        AdPlan newPlan = planRepository.save(
                new AdPlan(
                request.getUserId(),
                request.getPlanName(),
                CommonUtils.parseStringDate(request.getStartDate()),
                CommonUtils.parseStringDate(request.getEndDate())
                )
        );
        return new AdPlanResponse(newPlan.getId(), newPlan.getPlanName());
    }

    @Override
    public List<AdPlan> getAdPlanByIds(AdPlanGetRequest request) throws AdException {
        if (!request.validate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        return planRepository.findAllByIdInAndUserId(request.getIds(), request.getUserId());
    }

    @Override
    @Transactional
    public AdPlanResponse updateAdPlan(AdPlanRequest request) throws AdException {
        if (!request.updateValidate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        AdPlan plan = planRepository.findByIdAndUserId(request.getId(), request.getUserId());
        if (plan == null) {
            throw new AdException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
        }

        if (request.getPlanName() != null) {
            plan.setPlanName(request.getPlanName());
        }

        if (request.getStartDate() != null) {
            plan.setStartDate(CommonUtils.parseStringDate(request.getStartDate()));
        }

        if (request.getEndDate() != null) {
            plan.setEndDate(CommonUtils.parseStringDate(request.getEndDate()));
        }

        plan.setUpdateTime(new Date());

        plan = planRepository.save(plan);
        return new AdPlanResponse(plan.getId(),plan.getPlanName());
    }

    @Override
    @Transactional
    public void deleteAdPlan(AdPlanRequest request) throws AdException {
        if (!request.deleteValidate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        AdPlan plan = planRepository.findByIdAndUserId(request.getId(), request.getUserId());
        if (plan == null) {
            throw new AdException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
        }
        plan.setPlanStatus(CommonStatus.INVALID.getStatus());
        plan.setUpdateTime(new Date());
        planRepository.save(plan);
    }
}
