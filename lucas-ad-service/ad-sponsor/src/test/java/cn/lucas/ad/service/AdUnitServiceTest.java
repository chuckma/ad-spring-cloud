package cn.lucas.ad.service;

import cn.lucas.ad.Application;
import cn.lucas.ad.exception.AdException;
import cn.lucas.ad.vo.AdUnitKeywordRequest;
import cn.lucas.ad.vo.AdUnitRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.constraints.Max;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Administrator
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class}, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class AdUnitServiceTest {

    @Autowired
    private IAdUnitService unitService;


    /**
     *  insert 依然有 乱码问题
     * @throws AdException
     */
    @Test
    public void testCreateUnit() throws AdException{

        System.out.println(unitService.createUnit(
                new AdUnitRequest(
                        10L,
                        "第四个推广单元",
                        1,
                        10000L

                )
        ));
    }


    @Test
    public void testCreateUnitKeyword() throws AdException {

    }

}
