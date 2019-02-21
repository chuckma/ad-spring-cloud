package cn.lucas.ad.sender.kafka;

import cn.lucas.ad.mysql.dto.MySqlRowData;
import cn.lucas.ad.sender.ISender;
import com.alibaba.fastjson.JSON;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author Administrator
 */
@Component("kafkaSender")
public class KafkaSender implements ISender {

    @Value("${adconf.kafka.topic}")
    private String topic;

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public KafkaSender(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void sender(MySqlRowData rowData) {
        kafkaTemplate.send(
                topic,
                JSON.toJSONString(rowData)
        );
    }


    /**
     * kafka 接收方法  测试
     */
    @KafkaListener(topics = {"ad-search-mysql-data"},groupId = "ad-search")
    public void processMySQLRowData(ConsumerRecord<?, ?> record) {
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            Object message = kafkaMessage.get();
            MySqlRowData rowData = JSON.parseObject(message.toString(), MySqlRowData.class);
            System.out.println("kafka processMySqlRowData :" + JSON.toJSONString(rowData));
        }
    }
}
