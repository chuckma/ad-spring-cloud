package cn.lucas.ad.mysql.listener;

import cn.lucas.ad.mysql.dto.BinlogRowData;

/**
 * @author Administrator
 */
public interface Ilistener {

    /**
     * 注册监听器
     */
    void register();


    void onEvent(BinlogRowData eventData);

}
