package cn.lucas.ad.sender.index;

import cn.lucas.ad.dump.table.AdCreativeTable;
import cn.lucas.ad.dump.table.AdPlanTable;
import cn.lucas.ad.handler.AdLevelDataHandler;
import cn.lucas.ad.index.DataLevel;
import cn.lucas.ad.mysql.constant.Constant;
import cn.lucas.ad.mysql.dto.MySqlRowData;
import cn.lucas.ad.sender.ISender;
import cn.lucas.ad.utils.CommonUtils;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 *
 * 增量数据的投递
 */
@Slf4j
@Component("indexSender")
public class IndexSender implements ISender{


    @Override
    public void sender(MySqlRowData rowData) {
        String level = rowData.getLevel();
        if (DataLevel.LEVEL2.getLevel().equals(level)) {
            level2RowData(rowData);
        } else if (DataLevel.LEVEL3.getLevel().equals(level)){

        } else if (DataLevel.LEVEL4.getLevel().equals(level)) {

        } else {
            log.error("MySqlRowData error :{}", JSON.toJSONString(rowData));
        }
    }


    // 增量数据的投递过程就是把 MySqlRowData 变成一个 table 类型  eg AdPlanTable
    private void level2RowData(MySqlRowData rowData) {
        if (rowData.getTableName().equals(
                Constant.AD_PALN_TABLE_INFO.TABLE_NAME)) {
            List<AdPlanTable> planTables = new ArrayList<>();
            for (Map<String, String> fieldValueMap : rowData.getFieldValueMap()) {
                AdPlanTable planTable = new AdPlanTable();
                fieldValueMap.forEach((k,v)->{
                    switch (k) {
                        case Constant.AD_PALN_TABLE_INFO.COLUMN_ID:
                            planTable.setId(Long.valueOf(v));
                            break;
                        case Constant.AD_PALN_TABLE_INFO.COLUMN_USER_ID:
                            planTable.setUserId(Long.valueOf(v));
                            break;
                        case Constant.AD_PALN_TABLE_INFO.COLUMN_STATUS:
                            planTable.setPlanStatus(Integer.valueOf(v));
                            break;
                        case Constant.AD_PALN_TABLE_INFO.COLUMN_START_DATE:
                            planTable.setStartDate(CommonUtils.parseStringDate(v));
                            break;
                        case Constant.AD_PALN_TABLE_INFO.COLUMN_END_DATE:
                            planTable.setEndDate(CommonUtils.parseStringDate(v));
                            break;
                    }
                });
                planTables.add(planTable);
            }
            planTables.forEach(p -> AdLevelDataHandler.handleLevel2(p, rowData.getOpType()));
        } else if (rowData.getTableName().equals(
                Constant.AD_CREATIVE_TABLE_INFO.TABLE_NAME
        )) {
            List<AdCreativeTable> creativeTables = new ArrayList<>();
            for (Map<String, String> fieldValueMap : rowData.getFieldValueMap()) {
                AdCreativeTable creativeTable = new AdCreativeTable();
                fieldValueMap.forEach((k,v)->{
                    switch (k) {
                        case Constant.AD_CREATIVE_TABLE_INFO.COLUMN_ID:
                            creativeTable.setAdId(Long.valueOf(v));
                            break;
                        case Constant.AD_CREATIVE_TABLE_INFO.COLUMN_TYPE:
                            creativeTable.setType(Integer.valueOf(v));
                            break;
                        case Constant.AD_CREATIVE_TABLE_INFO.COLUMN_METERIAL_TYPE:
                            creativeTable.setMaterialType(Integer.valueOf(v));
                            break;
                        case Constant.AD_CREATIVE_TABLE_INFO.COLUMN_HEIGHT:
                            creativeTable.setHeight(Integer.valueOf(v));
                            break;
                        case Constant.AD_CREATIVE_TABLE_INFO.COLUMN_WIDTH:
                            creativeTable.setWidth(Integer.valueOf(v));
                            break;
                        case Constant.AD_CREATIVE_TABLE_INFO.COLUMN_AUDIT_STATUS:
                            creativeTable.setAuditStatus(Integer.valueOf(v));
                            break;
                        case Constant.AD_CREATIVE_TABLE_INFO.COLUMN_URL:
                            creativeTable.setAdUrl(v);
                            break;
                    }
                });
                creativeTables.add(creativeTable);
            }
            creativeTables.forEach(c -> AdLevelDataHandler.handleLevel2(c, rowData.getOpType()));
        }
    }
}
