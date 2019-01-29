package cn.lucas.ad.client;

import cn.lucas.ad.client.vo.AdPlan;
import cn.lucas.ad.client.vo.AdPlanGetRequest;
import cn.lucas.ad.vo.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @author Administrator
 *  一旦 ad-sponsor 不可用，就会在这里进行降级操作 SponsorClientHystrix.class
 */
@FeignClient(value = "eureka-client-ad-sponsor",fallback = SponsorClientHystrix.class)
public interface SponsorClient {

    @RequestMapping(value = "/ad-sponsor/get/adPlan", method = RequestMethod.POST)
    CommonResponse<List<AdPlan>> getAdPlans(@RequestBody AdPlanGetRequest request);

}
