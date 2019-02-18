package cn.lucas.ad.mysql;

import cn.lucas.ad.mysql.constant.OpType;
import cn.lucas.ad.mysql.dto.ParseTemplate;
import cn.lucas.ad.mysql.dto.TableTemplate;
import cn.lucas.ad.mysql.dto.Template;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

/**
 * @Author mcg
 * @Date 2019/2/18 21:40
 **/

@Slf4j
@Component
public class TemplateHolder {

    private ParseTemplate template;
    private final JdbcTemplate jdbcTemplate;

    private String SQL_SCHEMA = "SELECT table_schema,table_name, column_name,ordinal_position from " +
            "information_schema.COLUMNS WHERE table_schema = ? and table_name = ?";

    @Autowired
    public TemplateHolder(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @PostConstruct
    private void init() {
        loadJson("template.json");
    }

    // 对外服务的 解析 binlog 【对表的解析】
    public TableTemplate getTable(String tableName) {
        return template.getTableTemplateMap().get(tableName);
    }

    private void loadJson(String  path) {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        InputStream is = cl.getResourceAsStream(path);
        try {

            Template template = JSON.parseObject(is, Charset.defaultCharset(), Template.class);
            this.template = ParseTemplate.pase(template);
            loadMeta();
        } catch (IOException ex) {
            log.error(ex.getMessage());
            throw new RuntimeException("fail to parse json file");
        }
    }

    private void loadMeta() {
        for (Map.Entry<String, TableTemplate> entry: template.getTableTemplateMap().entrySet()) {
            TableTemplate table = entry.getValue();

            List<String > updateFields = table.getOpTypeFieldSetMap().get(
                    OpType.UPDATE
            );
            List<String > insertFields = table.getOpTypeFieldSetMap().get(
                    OpType.ADD
            );
            List<String > deleteFields = table.getOpTypeFieldSetMap().get(
                    OpType.DELETE
            );

            jdbcTemplate.query(SQL_SCHEMA,new Object[]{
                    template.getDatabase(),table.getTableName()
            },(rs,i)->{
                int pos = rs.getInt("ORDINAL_POSITION");
                String colName = rs.getString("COLUMN_NAME");

                if ((null != updateFields && updateFields.contains(colName))
                        || (null!=insertFields && insertFields.contains(colName))
                        || (null!=deleteFields && deleteFields.contains(colName))) {

                    table.getPosMap().put(pos - 1,colName);
                }
                return null;
            });
        }
    }
}
