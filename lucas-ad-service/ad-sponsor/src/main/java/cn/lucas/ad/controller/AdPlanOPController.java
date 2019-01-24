package cn.lucas.ad.controller;

import cn.lucas.ad.entity.AdPlan;
import cn.lucas.ad.exception.AdException;
import cn.lucas.ad.service.IAdPlanService;
import cn.lucas.ad.vo.AdPlanGetRequest;
import cn.lucas.ad.vo.AdPlanRequest;
import cn.lucas.ad.vo.AdPlanResponse;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Administrator
 */
@Slf4j
@RestController
public class AdPlanOPController {

    private final IAdPlanService planService;

    @Autowired
    public AdPlanOPController(IAdPlanService planService) {
        this.planService = planService;
    }

    @PostMapping("/create/adPlan")
    public AdPlanResponse createAdPlan(@RequestBody AdPlanRequest request) throws AdException{
        log.info("ad-sponsor: createAdPlan ->{}", JSON.toJSONString(request));
        return planService.createAdPlan(request);
    }

    @PostMapping("/get/adPlan")
    public List<AdPlan> getAdPlanByIds(@RequestBody AdPlanGetRequest request) throws AdException{
        log.info("ad-sponsor: getAdPlanByIds ->{}", JSON.toJSONString(request));
        return planService.getAdPlanByIds(request);
    }


    @PutMapping("/update/adPlan")
    public AdPlanResponse updateAdPlan(@RequestBody AdPlanRequest request) throws AdException{
        log.info("ad-sponsor: updateAdPlan ->{}", JSON.toJSONString(request));
        return planService.updateAdPlan(request);
    }

    @DeleteMapping("/delete/adPlan")
    public void deleteAdPlan(@RequestBody AdPlanRequest request) throws AdException{
        log.info("ad-sponsor: deleteAdPlan ->{}", JSON.toJSONString(request));
        planService.deleteAdPlan(request);
    }
}
