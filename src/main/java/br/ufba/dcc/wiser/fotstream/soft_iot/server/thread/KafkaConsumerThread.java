/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufba.dcc.wiser.fotstream.soft_iot.server.thread;

import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

/**
 *
 * @author Brenno Mello <brennodemello.bm at gmail.com>
 */
public class KafkaConsumerThread implements Runnable{
    
    private String threadName;  
    private KafkaConsumer<Long, String> consumer;
    private Map<String, List<String>> data;
    
    public KafkaConsumerThread(KafkaConsumer consumer, String name){
        this.threadName = name;
        this.consumer = consumer;
    }    
    
    public KafkaConsumerThread(KafkaConsumer consumer, String name, Map<String, List<String>> data){
        this.data = data;
        this.threadName = name;
        this.consumer = consumer;
    }    
    
    @Override
    public void run(){
       try {
                    System.out.println("Run: " +  this.threadName);
                    
                    while(true){
                            ConsumerRecords<Long, String> records = this.consumer.poll(Duration.ofSeconds(5));
                            //ConsumerRecords<Long, String> records = this.consumer.poll(5);
                            for (ConsumerRecord<Long, String> record : records){

                                System.out.println("topic = " + record.topic() + " partition = " + record.partition() + " country = " + record.offset());
                                System.out.println(" offset = " + record.offset() + " key = " + record.key() + " value = " + record.value());
                                
                                LinkedList<String> lista = new LinkedList<>();
                                lista.add(record.value());
                                this.getData().put("data", lista);
                                
                            }
                    }    
       
        }finally {
            this.consumer.close();
        }
                
     } 

    /**
     * @return the data
     */
    public Map<String, List<String>> getData() {
        return data;
    }
}
    
    

