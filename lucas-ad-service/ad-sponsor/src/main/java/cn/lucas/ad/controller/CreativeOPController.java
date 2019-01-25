package cn.lucas.ad.controller;

import cn.lucas.ad.exception.AdException;
import cn.lucas.ad.service.ICreativeService;
import cn.lucas.ad.vo.CreativeResponse;
import cn.lucas.ad.vo.CreativeReuqest;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 */
@Slf4j
@RestController
public class CreativeOPController {

    private final ICreativeService creativeService;

    @Autowired
    public CreativeOPController(ICreativeService creativeService) {
        this.creativeService = creativeService;
    }

    @PostMapping("/create/creative")
    public CreativeResponse createCreative(@RequestBody CreativeReuqest reuqest) throws AdException {
        log.info("ad-sponsor: createCreative -> {}", JSON.toJSONString(reuqest));
        return creativeService.creatCreative(reuqest);
    }
}
