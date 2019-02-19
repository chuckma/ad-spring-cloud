package cn.lucas.ad.sender;

import cn.lucas.ad.mysql.dto.MySqlRowData;

/**
 * @author Administrator
 */
public interface ISender {

    void sender(MySqlRowData rowData);
}
