package cn.lucas.ad.service.impl;

import cn.lucas.ad.constant.Constants;
import cn.lucas.ad.dao.AdPlanRepository;
import cn.lucas.ad.dao.AdUnitRepository;
import cn.lucas.ad.dao.unit_condition.AdUnitDistrictRepository;
import cn.lucas.ad.dao.unit_condition.AdUnitItRepository;
import cn.lucas.ad.dao.unit_condition.AdUnitKeywordRepository;
import cn.lucas.ad.entity.AdPlan;
import cn.lucas.ad.entity.AdUnit;
import cn.lucas.ad.entity.unit_condition.AdUnitDistrict;
import cn.lucas.ad.entity.unit_condition.AdUnitIt;
import cn.lucas.ad.entity.unit_condition.AdUnitKeyword;
import cn.lucas.ad.exception.AdException;
import cn.lucas.ad.service.IAdUnitService;
import cn.lucas.ad.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Administrator
 */
@Slf4j
@Service
public class AdUnitService implements IAdUnitService {

    private final AdUnitRepository unitRepository;
    private final AdPlanRepository planRepository;

    private final AdUnitKeywordRepository unitKeywordRepository;
    private final AdUnitItRepository unitItRepository;
    private final AdUnitDistrictRepository unitDistrictRepository;

    @Autowired
    public AdUnitService(AdUnitRepository unitRepository,
                         AdPlanRepository planRepository,
                         AdUnitDistrictRepository unitDistrictRepository,
                         AdUnitItRepository unitItRepository,
                         AdUnitKeywordRepository unitKeywordRepository) {
        this.unitRepository = unitRepository;
        this.planRepository = planRepository;
        this.unitDistrictRepository = unitDistrictRepository;
        this.unitItRepository = unitItRepository;
        this.unitKeywordRepository = unitKeywordRepository;
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


    @Override
    public AdUnitKeywordResponse createUnitKeyword(AdUnitKeywordRequest request) throws AdException {
        List<Long> unitIds = request.getUnitKeywords().stream()
                .map(AdUnitKeywordRequest.UnitKeyword::getUnitId)
                .collect(Collectors.toList());

        if (!isRelatedUnitExist(unitIds)) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        // unit 主键
        List<Long> ids = Collections.emptyList();

        List<AdUnitKeyword> unitKeywords = new ArrayList<>();
        if (!CollectionUtils.isEmpty(request.getUnitKeywords())) {
            request.getUnitKeywords().forEach(i -> unitKeywords.add(
                    new AdUnitKeyword(i.getUnitId(), i.getKeyword())));
            ids = unitKeywordRepository.saveAll(unitKeywords).stream()
                    .map(AdUnitKeyword::getUnitId).collect(Collectors.toList());
        }

        return new AdUnitKeywordResponse(ids);
    }

    @Override
    public AdUnitItResponse createUnitIt(AdUnitItRequest request) throws AdException {
        List<Long> unitIds = request.getUnitIts().stream()
                .map(AdUnitItRequest.UnitIt::getUnitId).collect(Collectors.toList());

        if (!isRelatedUnitExist(unitIds)) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        List<AdUnitIt> unitIts = new ArrayList<>();
        request.getUnitIts().forEach(i->unitIts.add(new AdUnitIt(i.getUnitId(),i.getItTag())));

        List<Long> ids = unitItRepository.saveAll(unitIts).stream()
                .map(AdUnitIt::getId)
                .collect(Collectors.toList());
        return new AdUnitItResponse(ids);
    }

    @Override
    public AdUnitDistrictResponse createUnitDistrict(AdUnitDistrictRequest request) throws AdException {
        List<Long> unitIds = request.getUnitDistricts().stream()
                .map(AdUnitDistrictRequest.UnitDistrict::getUnitId)
                .collect(Collectors.toList());
        if (!isRelatedUnitExist(unitIds)) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        List<AdUnitDistrict> unitDistricts = new ArrayList<>();
        request.getUnitDistricts().forEach(i -> unitDistricts.add(
                new AdUnitDistrict(i.getUnitId(), i.getProvince(), i.getCity())
        ));
        List<Long> ids = unitDistrictRepository.saveAll(unitDistricts).stream()
                .map(AdUnitDistrict::getId)
                .collect(Collectors.toList());

        return new AdUnitDistrictResponse(ids);
    }

    /**
     * 相关推广单元是否存在
     * @param unitIds 推广单元 id
     * @return
     */
    private boolean isRelatedUnitExist(List<Long> unitIds) {
        if (CollectionUtils.isEmpty(unitIds)) {
            return false;
        }

        // unitid 可能重复 所以用 hashset 来判断
        // 通过 unitIds 查询的推广单元集合大小 是否和 去重的推广单元id 集合大小相等

        return unitRepository.findAllById(unitIds).size() == new HashSet<>(unitIds).size();
    }
}
