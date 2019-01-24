package cn.lucas.ad.service.impl;

import cn.lucas.ad.dao.CreativeRepository;
import cn.lucas.ad.entity.Creative;
import cn.lucas.ad.service.ICreativeService;
import cn.lucas.ad.vo.CreativeResponse;
import cn.lucas.ad.vo.CreativeReuqest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 */
@Slf4j
@Service
public class CreativeService implements ICreativeService {


    private final CreativeRepository creativeRepository;

    @Autowired
    public CreativeService(CreativeRepository creativeRepository) {
        this.creativeRepository = creativeRepository;
    }

    @Override
    public CreativeResponse creatCreative(CreativeReuqest reuqest) {
        // TODO  略去校验

        Creative creative = creativeRepository.save(
                reuqest.convertToEntity()
        );

        return new CreativeResponse(creative.getId(),creative.getName());
    }
}
