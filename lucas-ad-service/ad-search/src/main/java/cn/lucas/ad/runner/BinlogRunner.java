package cn.lucas.ad.runner;

import cn.lucas.ad.mysql.BinlogClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 */
@Slf4j
@Component
public class BinlogRunner implements CommandLineRunner {

    private final BinlogClient client;

    @Autowired
    public BinlogRunner(BinlogClient client) {
        this.client = client;
    }

    /**
     * spring 程序启动的时候会自动运行
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
        log.info("connecting in BinlogRunner...");
        client.connect();
    }
}
