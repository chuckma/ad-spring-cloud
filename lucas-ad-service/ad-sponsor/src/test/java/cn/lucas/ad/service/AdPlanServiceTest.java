package cn.lucas.ad.service;

import cn.lucas.ad.Application;
import cn.lucas.ad.exception.AdException;
import cn.lucas.ad.vo.AdPlanGetRequest;
import cn.lucas.ad.vo.AdPlanRequest;
import net.bytebuddy.asm.Advice;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

/**
 * @author Administrator
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class},webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class AdPlanServiceTest {


    /**
     * 提问 14-2 企业级开发中 接口的测试覆盖率如何检测的 手动？
     */

    @Autowired
    private IAdPlanService planService;

    /**
     * 测试 推广计划
     * @throws AdException
     */
    @Test
    public void testGetAdPlan() throws AdException {
        System.out.println(planService.getAdPlanByIds(
                new AdPlanGetRequest(
                        15L,
                        Collections.singletonList(10L)
                )
        ));
    }

    /**
     * 创建推广计划  存在问题 入库中文乱码，参数对象id
     * @throws AdException
     */
    @Test
    public void testAddAdPlan() throws AdException {
        System.out.println(planService.createAdPlan(
                new AdPlanRequest(
                        null,
                        15L,
                        "推广计划1",
                        "2019-02-22",
                        "2019-03-22"

                )
        ));
    }


    /**
     * 更新 推广计划测试
     * @throws AdException
     */
    @Test
    public void testUpdateAdPlan() throws AdException {
        System.out.println(planService.updateAdPlan(
                new AdPlanRequest(
                        11L,
                        15L,
                        "1111test",
                        "2019-02-22",
                        "2019-05-22"
                )
        ));
    }

    @Test
    public void testDeleteAdPlan() throws AdException {
        planService.deleteAdPlan(
                new AdPlanRequest(
                        11L,
                        15L,
                        null,
                        null,null
                )
        );
    }

}
