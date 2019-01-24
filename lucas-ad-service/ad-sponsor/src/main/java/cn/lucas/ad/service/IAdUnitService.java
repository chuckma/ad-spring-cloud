package cn.lucas.ad.service;

import cn.lucas.ad.exception.AdException;
import cn.lucas.ad.vo.AdUnitRequest;
import cn.lucas.ad.vo.AdUnitResponse;

/**
 * @author Administrator
 */
public interface IAdUnitService {

    /**
     * 创建推广单元
     * @param request
     * @return
     * @throws AdException
     */
    AdUnitResponse createUnit(AdUnitRequest request) throws AdException;
}
