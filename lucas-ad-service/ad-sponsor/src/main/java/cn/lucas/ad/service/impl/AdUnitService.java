package cn.lucas.ad.service.impl;

import cn.lucas.ad.constant.Constants;
import cn.lucas.ad.dao.AdPlanRepository;
import cn.lucas.ad.dao.AdUnitRepository;
import cn.lucas.ad.entity.AdPlan;
import cn.lucas.ad.entity.AdUnit;
import cn.lucas.ad.exception.AdException;
import cn.lucas.ad.service.IAdUnitService;
import cn.lucas.ad.vo.AdUnitRequest;
import cn.lucas.ad.vo.AdUnitResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Administrator
 */
@Slf4j
@Service
public class AdUnitService implements IAdUnitService {

    private final AdUnitRepository unitRepository;
    private final AdPlanRepository planRepository;

    @Autowired
    public AdUnitService(AdUnitRepository unitRepository, AdPlanRepository planRepository) {
        this.unitRepository = unitRepository;
        this.planRepository = planRepository;
    }

    @Override
    public AdUnitResponse createUnit(AdUnitRequest request) throws AdException {
        if (!request.createValidate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        Optional<AdPlan> adPlan = planRepository.findById(request.getPlanId());
        if (!adPlan.isPresent()) {
            throw new AdException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
        }

        AdUnit oldUnit = unitRepository.findByPlanIdaAndUnitName(
                request.getPlanId(), request.getUnitName()
        );

        if (oldUnit != null) {
            throw new AdException(Constants.ErrorMsg.SAME_NAME_UNIT_ERROR);
        }

        AdUnit newUnit = unitRepository.save(new AdUnit(
                request.getPlanId(), request.getUnitName(),
                request.getPositionType(), request.getBudget()));


        return new AdUnitResponse(newUnit.getId(), newUnit.getUnitName());
    }
}
