package cn.lucas.ad.client;

import cn.lucas.ad.client.vo.AdPlan;
import cn.lucas.ad.client.vo.AdPlanGetRequest;
import cn.lucas.ad.vo.CommonResponse;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Administrator
 *
 * 断路器
 */
@Component
public class SponsorClientHystrix implements SponsorClient{
    @Override
    public CommonResponse<List<AdPlan>> getAdPlans(AdPlanGetRequest request) {
        return new CommonResponse<>(-1,"eureka-client-ad-sponsor error");
    }
}
