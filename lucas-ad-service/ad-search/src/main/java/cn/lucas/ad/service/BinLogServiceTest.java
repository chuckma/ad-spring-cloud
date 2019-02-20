package cn.lucas.ad.service;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.DeleteRowsEventData;
import com.github.shyiko.mysql.binlog.event.EventData;
import com.github.shyiko.mysql.binlog.event.UpdateRowsEventData;
import com.github.shyiko.mysql.binlog.event.WriteRowsEventData;

import java.io.IOException;

/**
 * @Author mcg
 * @Date 2019/2/17 19:48
 **/

public class BinLogServiceTest {

//    write ---------
//    WriteRowsEventData{tableId=224, includedColumns={0, 1, 2}, rows=[
//    [16, 10, 迈巴赫]
//]}
//    update --------
//    UpdateRowsEventData{tableId=224, includedColumnsBeforeUpdate={0, 1, 2}, includedColumns={0, 1, 2}, rows=[
//        {before=[14, 10, 宝来], after=[14, 10, 保时捷]}
//]}
//    delete -------
//    DeleteRowsEventData{tableId=224, includedColumns={0, 1, 2}, rows=[
//    [15, 10, 马自达]
//]}

//
//    write ---------
//    WriteRowsEventData{tableId=110, includedColumns={0, 1, 2, 3, 4, 5, 6, 7}, rows=[
//    [11, 10, plan, 1, Tue Jan 01 08:00:00 CST 2019, Tue Jan 01 08:00:00 CST 2019, Tue Jan 01 08:00:00 CST 2019, Tue Jan 01 08:00:00 CST 2019]
//    Tue Jan 01 08:00:00 CST 2019
//]}

    public static void main(String[] args) throws IOException {
        BinaryLogClient client = new BinaryLogClient(
            "127.0.0.1",
            3306,
            "root",
            "123456"
        );

//        client.setBinlogFilename();


        client.registerEventListener(event -> {
            EventData data = event.getData();
            if (data instanceof UpdateRowsEventData) {
                System.out.println("update -------- ");
                System.out.println(data.toString());
            } else if (data instanceof WriteRowsEventData) {
                System.out.println("write --------- ");
                System.out.println(data.toString());
            } else if (data instanceof DeleteRowsEventData) {
                System.out.println("delete ------- ");
                System.out.println(data.toString());
            }
        });

        client.connect();
    }
}
