package cn.lucas.ad.service;

import cn.lucas.ad.entity.AdPlan;
import cn.lucas.ad.exception.AdException;
import cn.lucas.ad.vo.AdPlanGetRequest;
import cn.lucas.ad.vo.AdPlanRequest;
import cn.lucas.ad.vo.AdPlanResponse;

import java.util.List;

public interface IAdPlanService {

    /**
     * 创建 推广计划 AdPlan
     * @param request
     * @return
     * @throws AdException
     */
    AdPlanResponse createAdPlan(AdPlanRequest request) throws AdException;


    /**
     * 批量获取 AdPlan
     * @param request
     * @return
     * @throws AdException
     */
    List<AdPlan> getAdPlanByIds(AdPlanGetRequest request) throws AdException;


    /**
     * 更新推广计划
     * @param request
     * @return
     * @throws AdException
     */
    AdPlanResponse updateAdPlan(AdPlanRequest request) throws AdException;

    /**
     * 删除推广计划
     * @param request
     * @throws AdException
     */
    void deleteAdPlan(AdPlanRequest request) throws AdException;
}
