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
import java.util.LinkedList;
import java.util.List;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.streams.StreamsBuilder;


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
    private List<FoTFogStream> listFoTFogStream;
    private KafkaConsumerConfig kafkaConsumerConfig;
    private String pathLog;
    private StreamsBuilder builder;
    
    public MainStreamController(){
        
    }
    
    public void init(){
        
        try{
        
            UtilDebug.printDebugConsole("Init FoT-StreamServer Controller");
            UtilDebug.printDebugConsole(this.getFotStreamGateways());
            this.kafkaConsumerConfig = new KafkaConsumerConfig();
            this.listFoTFogStream = new LinkedList<>();
            this.builder = new StreamsBuilder();
            loadFoTStreamGateway();
            //initKafkaConsumer();
            
            
        
        }catch(Exception e){
            UtilDebug.printDebugConsole("Error init StreamController: " + e.getMessage());
            UtilDebug.printError(e);
        }
    }
    
     public void disconnect(){
       System.out.println("Disconnect MainStreamController FoT-StreamServer");
       listFoTFogStream.forEach((t) -> {
               t.stopThreads();
       });
       
       //Thread.currentThread().getThreadGroup().interrupt();
       //Thread.currentThread().getThreadGroup().destroy();
       //Thread.currentThread().destroy();
    }

    public void readMessage(){
        
    }
    
    public void initKafkaConsumer(){
        
        /*
        String topic = "";
       
        for (FoTFogStream foTFogStream : listFoTFogStream) {
            KafkaConsumer<Long, String> consumer = kafkaConsumerConfig.createConsumer();
            String topic = foTFogStream.getListFoTGatewayStream().get(0);
        }
        consumer.subscribe(topics);
        
        
        ConsumerRecords<long, String> records = consumer.poll(long value);
        for (TopicPartition partition : records.partitions()) {
            List<ConsumerRecord<String, String>> partitionRecords = records.records(partition);
            for (ConsumerRecord<String, String> record : partitionRecords) {
                System.out.println(record.offset() + ": " + record.value());
            }
        long lastOffset = partitionRecords.get(partitionRecords.size() - 1).offset();
        consumer.commitSync(Collections.singletonMap(partition, new OffsetAndMetadata(lastOffset + 1)));
        }   
        */
        
    }
    
    public void loadFoTStreamGateway(){
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(this.getFotStreamGateways());
        JsonArray jarray = element.getAsJsonArray();
       
        UtilDebug.printDebugConsole("Tamanho do array: " + jarray.size());
        
        try{

            for (JsonElement jsonElement : jarray) {
                if(jsonElement.isJsonObject()){

                    System.out.println("Loop 1");
                    FoTFogStream fotFogStream = new FoTFogStream();
                    JsonObject fotElement = jsonElement.getAsJsonObject();
                    fotFogStream.setFogID(fotElement.get("id").getAsString());
                    fotFogStream.setType(fotElement.get("type").getAsString());
                    fotFogStream.setLatitude(fotElement.get("latitude").getAsFloat());
                    fotFogStream.setLongitude(fotElement.get("longitude").getAsFloat());

                    UtilDebug.printDebugConsole(fotFogStream.getFogID());

                    JsonArray jsonArrayGateways = fotElement.getAsJsonArray("gateways");
                    List<FoTGatewayStream> listFoTGatewayStream = new ArrayList<FoTGatewayStream>();


                    for (JsonElement jsonElementSensor : jsonArrayGateways) {
                        if(jsonElementSensor.isJsonObject()){
                            JsonObject fotGateway = jsonElementSensor.getAsJsonObject();
                            

                            KafkaConsumer<Long, String> consumer = getKafkaConsumerConfig().createConsumer();
                            
                            FoTGatewayStream fotGatewayStream = new FoTGatewayStream(this.builder);
                            
                            String ID = fotGateway.get("id").getAsString();
                            String type = fotGateway.get("type").getAsString();
                            float latitude = fotGateway.get("latitude").getAsFloat();
                            float longitude = fotGateway.get("longitude").getAsFloat();
                            
                            System.out.println("Type: "+type);
                            System.out.println(latitude);
                            System.out.println(longitude);
                            System.out.println(ID);

                            fotGatewayStream.setFoTGatewayiD(ID);
                            fotGatewayStream.setType(type);
                            fotGatewayStream.setLatitude(latitude);
                            fotGatewayStream.setLongitude(longitude);
                            //fotGatewayStream.startConsumer();
                            fotGatewayStream.startConsumerKafkaStream();
                                    
                            //fotGatewayStream.setConsumer(consumer);

                            //fotSensorStream.sendTatuFlow();

                            System.out.println("Loop 2");

                            UtilDebug.printDebugConsole(fotGatewayStream.getFoTGatewayiD());
                            UtilDebug.printDebugConsole(String.valueOf(fotGatewayStream.getType()));
                            //UtilDebug.printDebugConsole(String.valueOf(fotGatewayStream.getPublishingTime()));



                            listFoTGatewayStream.add(fotGatewayStream);
                        }   
                    }


                    fotFogStream.setListFoTGatewayStream(listFoTGatewayStream);
                    //fotFogStream.startStreamGatewayAnalysis();
                    fotFogStream.startStreamGatewayAnalysisKafkaStream();
                    this.getListFoTFogStream().add(fotFogStream);
                }
            }
        
        }catch(Exception e){
            UtilDebug.printDebugConsole("Error init loadFoTStreamGateway: " + e.getMessage());
            UtilDebug.printError(e);
        }
    }
   
    /**
     * @return the fotStreamGateways
     */
    public String getFotStreamGateways() {
        return fotStreamGateways;
    }

    /**
     * @param fotStreamGateways the fotStreamGateways to set
     */
    public void setFotStreamGateways(String fotStreamGateways) {
        this.fotStreamGateways = fotStreamGateways;
    }

    /**
     * @return the listFoTFogStream
     */
    public List<FoTFogStream> getListFoTFogStream() {
        return listFoTFogStream;
    }

    /**
     * @param listFoTFogStream the listFoTFogStream to set
     */
    public void setListFoTFogStream(List<FoTFogStream> listFoTFogStream) {
        this.listFoTFogStream = listFoTFogStream;
    }

    /**
     * @return the kafkaConsumerConfig
     */
    public KafkaConsumerConfig getKafkaConsumerConfig() {
        return kafkaConsumerConfig;
    }

    /**
     * @param kafkaConsumerConfig the kafkaConsumerConfig to set
     */
    public void setKafkaConsumerConfig(KafkaConsumerConfig kafkaConsumerConfig) {
        this.kafkaConsumerConfig = kafkaConsumerConfig;
    }

    /**
     * @return the pathLog
     */
    public String getPathLog() {
        return pathLog;
    }

    /**
     * @param pathLog the pathLog to set
     */
    public void setPathLog(String pathLog) {
        this.pathLog = pathLog;
    }
    
}
