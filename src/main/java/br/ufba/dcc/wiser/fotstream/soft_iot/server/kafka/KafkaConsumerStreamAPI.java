/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufba.dcc.wiser.fotstream.soft_iot.server.kafka;

import java.util.Properties;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;

/**
 *
 * @author Brenno Mello <brennodemello.bm at gmail.com>
 */
public class KafkaConsumerStreamAPI {
    
    private String KAFKA_BROKERS="18.218.147.104:9092";
    private String CLIENT_ID="client1";
    
    public KafkaConsumerStreamAPI(){
        
    }
    
    
    public void init(){
         System.out.println("Init kafka consumer");
    }
    
    public void disconnect(){
        
    }
    
    
    public Properties getProps(){
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, CLIENT_ID);
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_BROKERS);
        props.put(StreamsConfig.CACHE_MAX_BYTES_BUFFERING_CONFIG, 0);
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.Float().getClass().getName());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        //props.put(StreamsConfig., props)
        return props;
    }
}
