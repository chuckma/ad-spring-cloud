package cn.lucas.ad.controller;

import cn.lucas.ad.annotation.IgnoreResponseAdvice;
import cn.lucas.ad.client.SponsorClient;
import cn.lucas.ad.client.vo.AdPlan;
import cn.lucas.ad.client.vo.AdPlanGetRequest;
import cn.lucas.ad.search.ISearch;
import cn.lucas.ad.search.vo.SearchRequest;
import cn.lucas.ad.search.vo.SearchResponse;
import cn.lucas.ad.vo.CommonResponse;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author Administrator
 */
@Slf4j
@RestController
public class SearchController {

    private final ISearch iSearch;
    private final SponsorClient sponsorClient;
    private final RestTemplate restTemplate;

    @Autowired
    public SearchController(RestTemplate restTemplate, SponsorClient sponsorClient, ISearch iSearch) {
        this.restTemplate = restTemplate;
        this.sponsorClient = sponsorClient;
        this.iSearch = iSearch;
    }

    /**
     * 广告请求外部调用接口
     * @param request
     * @return
     */
    @PostMapping("/fetchAds")
    public SearchResponse fetchAds(@RequestBody SearchRequest request) {
        log.info("ad-search: fetchAds ->{}", JSON.toJSONString(request));
        return iSearch.fetchAds(request);
    }

    @IgnoreResponseAdvice
    @PostMapping("/getAdPlans")
    public CommonResponse<List<AdPlan>> getAdPlans(@RequestBody AdPlanGetRequest request) {
        log.info("ad-search: getAdPlans-{}", JSON.toJSONString(request));
        return sponsorClient.getAdPlans(request);
    }

    @SuppressWarnings("all")
    @IgnoreResponseAdvice
    @PostMapping("/getAdPlansByRebbon")
    public CommonResponse<List<AdPlan>> getAdPlansByRebbon(@RequestBody AdPlanGetRequest request) {
        log.info("ad-search: getAdPlansByRebbon -> {}", JSON.toJSONString(request));
        return restTemplate.postForEntity(
                "http://eureka-client-ad-sponsor/ad-sponsor/get/adPlan",
                request,
                CommonResponse.class
        ).getBody();
    }


}
