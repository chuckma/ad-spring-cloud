package cn.lucas.ad.service;

import cn.lucas.ad.constant.CommonStatus;
import cn.lucas.ad.dao.AdPlanRepository;
import cn.lucas.ad.dao.AdUnitRepository;
import cn.lucas.ad.dao.CreativeRepository;
import cn.lucas.ad.dao.unit_condition.AdUnitDistrictRepository;
import cn.lucas.ad.dao.unit_condition.AdUnitItRepository;
import cn.lucas.ad.dao.unit_condition.AdUnitKeywordRepository;
import cn.lucas.ad.dao.unit_condition.CreativeUnitRepository;
import cn.lucas.ad.dump.DConstant;
import cn.lucas.ad.dump.table.*;
import cn.lucas.ad.entity.AdPlan;
import cn.lucas.ad.entity.AdUnit;
import cn.lucas.ad.entity.Creative;
import cn.lucas.ad.entity.unit_condition.AdUnitDistrict;
import cn.lucas.ad.entity.unit_condition.AdUnitIt;
import cn.lucas.ad.entity.unit_condition.AdUnitKeyword;
import cn.lucas.ad.entity.unit_condition.CreativeUnit;
import com.alibaba.fastjson.JSON;
import cn.lucas.ad.Application;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author mcg
 * @Date 2019/2/14 21:55
 **/
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class}, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class DumpDataService {

    @Autowired
    private AdPlanRepository adPlanRepository;

    @Autowired
    private AdUnitRepository adUnitRepository;

    @Autowired
    private CreativeRepository creativeRepository;

    @Autowired
    private CreativeUnitRepository creativeUnitRepository;

    @Autowired
    private AdUnitDistrictRepository districtRepository;

    @Autowired
    private AdUnitItRepository itRepository;

    @Autowired
    private AdUnitKeywordRepository keywordRepository;

    @Test
    public void dumpAdTableData() {
        dumpAdPlanTable(
                String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.AD_PLAN)
        );
        dumpAdUnitTable(
                String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.AD_UNIT)
        );
        dumpAdCreativeTable(
                String.format("%s%s",DConstant.DATA_ROOT_DIR, DConstant.AD_CREATIVE)
        );
        dumpAdCreativeUnitTable(
                String.format("%s%s",DConstant.DATA_ROOT_DIR, DConstant.AD_CREATIVE_UNIT)
        );
        dumpAdUnitDistrictTable(
                String.format("%s%s",DConstant.DATA_ROOT_DIR, DConstant.AD_UNIT_DISTRICT)
        );
        dumpAdUnitTable(
                String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.AD_UNIT_IT)
        );
        dumpAdUnitKeywordTable(
                String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.AD_UNIT_KEYWORD)
        );
    }

    // 将表的各个数据字段导出为 json 结构
    private void dumpAdPlanTable(String fileName) {
        List<AdPlan> adPlans = adPlanRepository.findAllByPlanStatus(
                CommonStatus.VALID.getStatus()
        );

        if (CollectionUtils.isEmpty(adPlans)) {
            return;
        }
        List<AdPlanTable> planTables = new ArrayList<>();
        adPlans.forEach(p -> planTables.add(
                new AdPlanTable(p.getId(),
                        p.getUserId(),
                        p.getPlanStatus(),
                        p.getStartDate(),
                        p.getEndDate())
        ));

        // 获取文件路劲

        Path path = Paths.get(fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (AdPlanTable planTable : planTables) {
                writer.write(JSON.toJSONString(planTable));
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            log.error("dumpAdPlanTable error");
        }
    }


    private void dumpAdUnitTable(String fileName) {
        List<AdUnit> adUnits = adUnitRepository.findAllByUnitStatus(
                CommonStatus.VALID.getStatus()
        );

        if (CollectionUtils.isEmpty(adUnits)) {
            return;
        }

        List<AdUnitTable> unitTables = new ArrayList<>();

        adUnits.forEach(u-> unitTables.add(
                new AdUnitTable(u.getId(),
                        u.getUnitStatus(),
                        u.getPositionType(),
                        u.getPlanId())
        ));


        Path path = Paths.get(fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (AdUnitTable unitTable : unitTables) {
                writer.write(JSON.toJSONString(unitTable));
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            log.error("dumpAdUnitTable error");
        }
    }

    private void dumpAdCreativeTable(String fileName) {
        List<Creative> creatives = creativeRepository.findAll();
        if (CollectionUtils.isEmpty(creatives)) {
            return;
        }

        List<AdCreativeTable> creativeTables = new ArrayList<>();
        creatives.forEach(c -> creativeTables.add(
                new AdCreativeTable(c.getId(),
                        c.getName(),
                        c.getType(),
                        c.getMaterialType(),
                        c.getHeight(),
                        c.getWidth(),
                        c.getAuditStatus(),
                        c.getUrl())
        ));
        Path path = Paths.get(fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (AdCreativeTable creativeTable : creativeTables) {
                writer.write(JSON.toJSONString(creativeTable));
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            log.error("dumpAdCreativeTable error");
        }
    }


    private void dumpAdCreativeUnitTable(String fileName) {
        List<CreativeUnit> creativeUnits = creativeUnitRepository.findAll();
        if (CollectionUtils.isEmpty(creativeUnits)) {
            return;
        }
        List<AdCreativeUnitTable> creativeUnitTables = new ArrayList<>();
        creativeUnits.forEach(c->creativeUnitTables.add(
                new AdCreativeUnitTable(
                        c.getCreativeId(),
                        c.getUnitId()
                )
        ));

        Path path = Paths.get(fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (AdCreativeUnitTable creativeUnitTable : creativeUnitTables) {
                writer.write(JSON.toJSONString(creativeUnitTable));
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            log.error("dumpAdCreativeUnitTable error");
        }
    }

    private void dumpAdUnitDistrictTable(String fileName) {
        List<AdUnitDistrict> unitDistricts = districtRepository.findAll();
        if (CollectionUtils.isEmpty(unitDistricts)) {
            return;
        }

        List<AdUnitDistrictTable> unitDistrictTables = new ArrayList<>();
        unitDistricts.forEach(d -> unitDistrictTables.add(
                new AdUnitDistrictTable(
                        d.getUnitId(),
                        d.getProvince(),
                        d.getCity()
                )
        ));

        Path path = Paths.get(fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (AdUnitDistrictTable unitDistrictTable : unitDistrictTables) {
                writer.write(JSON.toJSONString(unitDistrictTable));
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            log.error("dumpAdUnitDistrictTable error");
        }
    }


    private void dumpAdUnitItTable(String fileName) {
        List<AdUnitIt> unitIts = itRepository.findAll();
        if (CollectionUtils.isEmpty(unitIts)) {
            return;
        }

        List<AdUnitItTable> unitItTables = new ArrayList<>();
        unitIts.forEach(i -> unitItTables.add(
                new AdUnitItTable(
                        i.getUnitId(),
                        i.getItTag()
                )
        ));

        Path path = Paths.get(fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (AdUnitItTable unitItTable : unitItTables) {
                writer.write(JSON.toJSONString(unitItTable));
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            log.error("dumpAdUnitItTable error");
        }
    }


    private void dumpAdUnitKeywordTable(String fileName) {
        List<AdUnitKeyword> unitKeywords = keywordRepository.findAll();
        if (CollectionUtils.isEmpty(unitKeywords)) {
            return;
        }

        List<AdUnitKeywordTable> unitKeywordTables = new ArrayList<>();
        unitKeywords.forEach(k -> unitKeywordTables.add(
                new AdUnitKeywordTable(
                        k.getUnitId(),
                        k.getKeyword()
                )
        ));

        Path path = Paths.get(fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (AdUnitKeywordTable unitKeywordTable : unitKeywordTables) {
                writer.write(JSON.toJSONString(unitKeywordTable));
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            log.error("dumpAdUnitKeywordTable error");
        }
    }
}
