/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufba.dcc.wiser.fotstream.soft_iot.server.kafka;

import br.ufba.dcc.wiser.fotstream.soft_iot.server.util.UtilDebug;
import java.util.Properties;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;



/**
 *
 * @author Brenno Mello <brennodemello.bm at gmail.com>
 */
public class KafkaConsumerConfig {
    
    //Settings kafka
    private String KAFKA_BROKERS="18.218.147.104:9092";
    private Integer MESSAGE_COUNT=1000;
    private String CLIENT_ID="client1";
    private String TOPIC_NAME="demo";
    private String GROUP_ID_CONFIG="consumerGroup1";
    private Integer MAX_NO_MESSAGE_FOUND_COUNT=100;
    private String OFFSET_RESET_LATEST="latest";
    private String OFFSET_RESET_EARLIER="earliest";
    private Integer MAX_POLL_RECORDS=1;
    
    
    public KafkaConsumerConfig(){
        
    }
    
    
    
    public void init(){
         System.out.println("Init kafka consumer");
    }
    
    public void disconnect(){
        
    }
    
    public KafkaConsumer<Long, String> createConsumer() {
        Properties props = new Properties();
        try{
        
            
            System.out.println(this.KAFKA_BROKERS);
            System.out.println(this.CLIENT_ID);
            props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, this.KAFKA_BROKERS);
            //props.put(ProducerConfig.CLIENT_ID_CONFIG, this.CLIENT_ID);
            props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,  Class.forName("org.apache.kafka.common.serialization.LongDeserializer"));
            props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, Class.forName("org.apache.kafka.common.serialization.StringDeserializer"));
            props.put("key.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
            props.put("group.id", "Demo_Group");
            props.put("enable.auto.commit", "true");
            props.put("auto.commit.internal.ms", "1000");
            props.put("session.timeout.ms", "30000");
            //props.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, CustomPartitioner.class.getName());
            
            
        }catch(Exception e){
            UtilDebug.printDebugConsole("Error init KafkaConsumerConfig: " + e.getMessage());
            UtilDebug.printError(e);
        }
        
        Thread.currentThread().setContextClassLoader(null);
        return new KafkaConsumer<Long, String>(props);
    }

    /**
     * @return the KAFKA_BROKERS
     */
    public String getKAFKA_BROKERS() {
        return KAFKA_BROKERS;
    }

    /**
     * @param KAFKA_BROKERS the KAFKA_BROKERS to set
     */
    public void setKAFKA_BROKERS(String KAFKA_BROKERS) {
        this.KAFKA_BROKERS = KAFKA_BROKERS;
    }

    /**
     * @return the MESSAGE_COUNT
     */
    public Integer getMESSAGE_COUNT() {
        return MESSAGE_COUNT;
    }

    /**
     * @param MESSAGE_COUNT the MESSAGE_COUNT to set
     */
    public void setMESSAGE_COUNT(Integer MESSAGE_COUNT) {
        this.MESSAGE_COUNT = MESSAGE_COUNT;
    }

    /**
     * @return the CLIENT_ID
     */
    public String getCLIENT_ID() {
        return CLIENT_ID;
    }

    /**
     * @param CLIENT_ID the CLIENT_ID to set
     */
    public void setCLIENT_ID(String CLIENT_ID) {
        this.CLIENT_ID = CLIENT_ID;
    }

    /**
     * @return the TOPIC_NAME
     */
    public String getTOPIC_NAME() {
        return TOPIC_NAME;
    }

    /**
     * @param TOPIC_NAME the TOPIC_NAME to set
     */
    public void setTOPIC_NAME(String TOPIC_NAME) {
        this.TOPIC_NAME = TOPIC_NAME;
    }

    /**
     * @return the GROUP_ID_CONFIG
     */
    public String getGROUP_ID_CONFIG() {
        return GROUP_ID_CONFIG;
    }

    /**
     * @param GROUP_ID_CONFIG the GROUP_ID_CONFIG to set
     */
    public void setGROUP_ID_CONFIG(String GROUP_ID_CONFIG) {
        this.GROUP_ID_CONFIG = GROUP_ID_CONFIG;
    }

    /**
     * @return the MAX_NO_MESSAGE_FOUND_COUNT
     */
    public Integer getMAX_NO_MESSAGE_FOUND_COUNT() {
        return MAX_NO_MESSAGE_FOUND_COUNT;
    }

    /**
     * @param MAX_NO_MESSAGE_FOUND_COUNT the MAX_NO_MESSAGE_FOUND_COUNT to set
     */
    public void setMAX_NO_MESSAGE_FOUND_COUNT(Integer MAX_NO_MESSAGE_FOUND_COUNT) {
        this.MAX_NO_MESSAGE_FOUND_COUNT = MAX_NO_MESSAGE_FOUND_COUNT;
    }

    /**
     * @return the OFFSET_RESET_LATEST
     */
    public String getOFFSET_RESET_LATEST() {
        return OFFSET_RESET_LATEST;
    }

    /**
     * @param OFFSET_RESET_LATEST the OFFSET_RESET_LATEST to set
     */
    public void setOFFSET_RESET_LATEST(String OFFSET_RESET_LATEST) {
        this.OFFSET_RESET_LATEST = OFFSET_RESET_LATEST;
    }

    /**
     * @return the OFFSET_RESET_EARLIER
     */
    public String getOFFSET_RESET_EARLIER() {
        return OFFSET_RESET_EARLIER;
    }

    /**
     * @param OFFSET_RESET_EARLIER the OFFSET_RESET_EARLIER to set
     */
    public void setOFFSET_RESET_EARLIER(String OFFSET_RESET_EARLIER) {
        this.OFFSET_RESET_EARLIER = OFFSET_RESET_EARLIER;
    }

    /**
     * @return the MAX_POLL_RECORDS
     */
    public Integer getMAX_POLL_RECORDS() {
        return MAX_POLL_RECORDS;
    }

    /**
     * @param MAX_POLL_RECORDS the MAX_POLL_RECORDS to set
     */
    public void setMAX_POLL_RECORDS(Integer MAX_POLL_RECORDS) {
        this.MAX_POLL_RECORDS = MAX_POLL_RECORDS;
    }
}

