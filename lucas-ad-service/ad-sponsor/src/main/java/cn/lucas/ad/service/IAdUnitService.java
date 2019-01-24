package cn.lucas.ad.service;

import cn.lucas.ad.entity.Creative;
import cn.lucas.ad.exception.AdException;
import cn.lucas.ad.vo.*;

/**
 * @author Administrator
 */
public interface IAdUnitService {

    /**
     * 创建推广单元
     *
     * @param request
     * @return
     * @throws AdException
     */
    AdUnitResponse createUnit(AdUnitRequest request) throws AdException;

    AdUnitKeywordResponse createUnitKeyword(AdUnitKeywordRequest request) throws AdException;

    AdUnitItResponse createUnitIt(AdUnitItRequest request) throws AdException;

    AdUnitDistrictResponse createUnitDistrict(AdUnitDistrictRequest request) throws AdException;


    CreativeUnitResponse createCreativeUnit(CreativeUnitRequest request) throws AdException;

}
