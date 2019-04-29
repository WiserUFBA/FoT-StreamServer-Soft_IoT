/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufba.dcc.wiser.fotstream.soft_iot.server.controller;

import br.ufba.dcc.wiser.fotstream.soft_iot.server.kafka.KafkaConsumerConfig;
import br.ufba.dcc.wiser.fotstream.soft_iot.server.model.FoTFogStream;
import br.ufba.dcc.wiser.fotstream.soft_iot.server.model.FoTGatewayStream;
import br.ufba.dcc.wiser.fotstream.soft_iot.server.util.UtilDebug;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import java.util.List;
import org.apache.kafka.clients.consumer.KafkaConsumer;


/**
 *
 * @author Brenno Mello <brennodemello.bm at gmail.com>
 */
public class MainStreamController {
    
    /**
        * consumer is part of a consumer group, it will be assigned a subset of partitions
        from that topic.    
    */
    
    private String fotStreamGateways;
    private List<FoTGatewayStream> listFoTGatewayStream;
    private KafkaConsumerConfig kafkaConsumerConfig;
  
    public void init(){
        
        try{
        
            UtilDebug.printDebugConsole("Init FoT-StreamServer Controller");
            UtilDebug.printDebugConsole(this.fotStreamGateways);


            loadFoTStreamGateway();
            initKafkaConsumer();
            
            
        
        }catch(Exception e){
            UtilDebug.printDebugConsole("Error init StreamController: " + e.getMessage());
        }
    }

    public void readMessage(){
        
    }
    
    public void initKafkaConsumer(){
        String topic = "";
        KafkaConsumer<Long, String> consumer = kafkaConsumerConfig.createConsumer();
        
        ConsumerRecords<String, String> records = consumer.poll(long value);
        for (TopicPartition partition : records.partitions()) {
            List<ConsumerRecord<String, String>> partitionRecords = records.records(partition);
            for (ConsumerRecord<String, String> record : partitionRecords) {
                System.out.println(record.offset() + ": " + record.value());
            }
        long lastOffset = partitionRecords.get(partitionRecords.size() - 1).offset();
        consumer.commitSync(Collections.singletonMap(partition, new OffsetAndMetadata(lastOffset + 1)));
    }
        
    }
    
    public void loadFoTStreamGateway(){
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(this.fotStreamGateways);
        JsonArray jarray = element.getAsJsonArray();
       
         UtilDebug.printDebugConsole("Tamanho do array: " + jarray.size());
        
        for (JsonElement jsonElement : jarray) {
            if(jsonElement.isJsonObject()){
                
                System.out.println("Loop 1");
                FoTFogStream fotFogStream = new FoTFogStream();
                JsonObject fotElement = jsonElement.getAsJsonObject();
                fotFogStream.setFogID(fotElement.get("id").getAsString());
                fotFogStream.setLatitude(fotElement.get("type").getAsFloat());
                fotFogStream.setLongitude(fotElement.get("latitude").getAsFloat());
                
                UtilDebug.printDebugConsole(fotFogStream.getFogID());
                  
                JsonArray jsonArraySensors = fotElement.getAsJsonArray("gateways");
                List<FoTGatewayStream> listFoTGatewayStream = new ArrayList<FoTGatewayStream>();
                
               
                
                for (JsonElement jsonElementSensor : jsonArraySensors) {
                    if(jsonElementSensor.isJsonObject()){
                        JsonObject fotGateway = jsonElementSensor.getAsJsonObject();
                        String sensorID = fotGateway.get("id").getAsString();
                        
                        FoTGatewayStream fotGatewayStream = new FoTSensorStream(this.topology, this.mqttConfig, 
                                sensorID, fotDeviceStream, this.pathLog);
                        
                        fotSensorStream.setType(fotSensor.get("type").getAsString());
                        fotSensorStream.setCollectionTime(fotSensor.get("collection_time").getAsInt());
                        fotSensorStream.setPublishingTime(fotSensor.get("publishing_time").getAsInt());
                        
                        //fotSensorStream.sendTatuFlow();
                        
                        System.out.println("Loop 2");
                        
                        UtilDebug.printDebugConsole(fotSensorStream.getSensorid());
                        UtilDebug.printDebugConsole(String.valueOf(fotSensorStream.getCollectionTime()));
                        UtilDebug.printDebugConsole(String.valueOf(fotSensorStream.getPublishingTime()));
                                
                        listFoTSensorStream.add(fotSensorStream);
                    }   
                }
                
                
                
                this.listFoTGatewayStream = new ArrayList<FoTGatewayStream>();
                
                this.listFoTGatewayStream.add(fotGatewayStream);
            }
        }
    }
   
}
