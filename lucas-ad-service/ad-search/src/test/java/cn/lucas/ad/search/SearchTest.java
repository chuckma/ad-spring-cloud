package cn.lucas.ad.search;

import cn.lucas.ad.Application;
import cn.lucas.ad.search.vo.SearchRequest;
import cn.lucas.ad.search.vo.feature.DistrictFeature;
import cn.lucas.ad.search.vo.feature.FeatureRelation;
import cn.lucas.ad.search.vo.feature.ItFeature;
import cn.lucas.ad.search.vo.feature.KeywordFeature;
import cn.lucas.ad.search.vo.media.AdSlot;
import cn.lucas.ad.search.vo.media.App;
import cn.lucas.ad.search.vo.media.Device;
import cn.lucas.ad.search.vo.media.Geo;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author Administrator
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class}, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class SearchTest {

    @Autowired
    private ISearch iSearch;

    @Test
    public void testFetchAds() {
        SearchRequest request = new SearchRequest();
        request.setMediaId("imooc-ad");

        // 第一个测试条件
        request.setRequestInfo(new SearchRequest.RequestInfo(
                "aaaa", Collections.singletonList(
                new AdSlot(
                        "ad-x", 1, 1080, 720, Arrays.asList(1, 2),
                        1000
                )),
                buildExampleApp(),
                buildExampleGeo(),
                buildExampleDevice()
        ));

        request.setFeatureInfo(buildExampleFeatureInfo(
                Arrays.asList("宝马", "大众"),
                Collections.singletonList(
                        new DistrictFeature.ProvinceAndCity(
                                "安徽省", "合肥市"
                        )),
                Arrays.asList("台球", "游泳"),
                FeatureRelation.OR
        ));

        System.out.println(JSON.toJSONString(request));
        System.out.println(JSON.toJSONString(iSearch.fetchAds(request)));




        // 第二个测试条件
        request.setRequestInfo(new SearchRequest.RequestInfo(
                "aaaa", Collections.singletonList(
                new AdSlot(
                        "ad-y", 1, 1080, 720, Arrays.asList(1, 2),
                        1000
                )),
                buildExampleApp(),
                buildExampleGeo(),
                buildExampleDevice()
        ));

        request.setFeatureInfo(buildExampleFeatureInfo(
                Arrays.asList("宝马", "大众","标志"),
                Collections.singletonList(
                        new DistrictFeature.ProvinceAndCity(
                                "安徽省", "合肥市"
                        )),
                Arrays.asList("台球", "游泳"),
                FeatureRelation.AND
        ));

        System.out.println(JSON.toJSONString(request));
        System.out.println(JSON.toJSONString(iSearch.fetchAds(request)));

    }

    /**
     *
     */
    private App buildExampleApp() {
        return new App("imooc", "imooc", "com.imooc", "video");
    }

    private Geo buildExampleGeo() {
        return new Geo((float) 100.23, (float) 88.23, "北京市", "北京市");
    }

    private Device buildExampleDevice() {
        return new Device("iphone", "0xcfvxdf", "127.0.0.1", "x", "1920 1080",
                "1080,720", "1231223213");
    }

    private SearchRequest.FeatureInfo buildExampleFeatureInfo(
            List<String> keywords,
            List<DistrictFeature.ProvinceAndCity> provinceAndCities,
            List<String> its,
            FeatureRelation featureRelation
    ) {

        return new SearchRequest.FeatureInfo(
                new KeywordFeature(keywords),
                new DistrictFeature(provinceAndCities),
                new ItFeature(its),
                featureRelation
        );
    }

}
