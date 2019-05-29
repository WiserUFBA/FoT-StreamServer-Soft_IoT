/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufba.dcc.wiser.fotstream.soft_iot.server.model;

import br.ufba.dcc.wiser.fotstream.soft_iot.server.kafka.KafkaConsumerStreamAPI;
import br.ufba.dcc.wiser.fotstream.soft_iot.server.kafka.KafkaStreamProcessor;
import br.ufba.dcc.wiser.fotstream.soft_iot.server.thread.KafkaConsumerThread;
import br.ufba.dcc.wiser.fotstream.soft_iot.server.util.UtilDebug;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.record.Record;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Printed;



/**
 *
 * @author Brenno Mello <brennodemello.bm at gmail.com>
 */
public class FoTFogStream {
    
    private String fogID;
    private String type;
    private float latitude;
    private float longitude;
    private List<FoTGatewayStream> listFoTGatewayStream;
    private List<Thread> listRunThreadConsumer;
    private List<KafkaConsumerThread> listKafkaConsumerThreads;
    private KafkaStreams gatewayStreams;
    private KafkaConsumerStreamAPI kafkaConsumerStreamAPI;
    private List<KafkaStreams> listKafkaStreams;
    private StreamsBuilder builder;
    private KafkaStreams streams;
    private List<SensorData> listSensorData;
    private JsonParser parser;
    private Map<String, List<String>> mapData;
    
    public FoTFogStream(){
        //startStreamGatewayAnalysis();
        this.listRunThreadConsumer = new LinkedList<>();
        this.listKafkaConsumerThreads = new LinkedList<>();
        this.listKafkaStreams = new LinkedList<>();
        this.kafkaConsumerStreamAPI = new KafkaConsumerStreamAPI();
        this.listSensorData = new LinkedList<>();
        this.parser = new JsonParser();
        this.mapData = new HashMap<String, List<String>> ();
        
    }
    
    public FoTFogStream(StreamsBuilder builder){
        //startStreamGatewayAnalysis();
        this.listRunThreadConsumer = new LinkedList<>();
        this.listKafkaConsumerThreads = new LinkedList<>();
        this.listKafkaStreams = new LinkedList<>();
        this.kafkaConsumerStreamAPI = new KafkaConsumerStreamAPI();
        this.builder = builder;
        
    }
     
     
     public void startStreamGatewayAnalysisKafkaStream(){
            //StreamsBuilder builderLocal = new StreamsBuilder();
            //KStream<Long, String> source = builderLocal.stream(Pattern.compile("dev.Gateway02.*"));
            //KStream<Long, String> source2 = builderLocal.stream(Pattern.compile("dev.Gateway01.*"));
            //source.print(Printed.toSysOut());
            //source2.print(Printed.toSysOut());
            //Topology topology = builderLocal.build();
            
            //KStream<Long, String> source = null;
            
            List<KStream<Long, String>> listSources = new LinkedList<>();
            for (FoTGatewayStream foTGatewayStream : listFoTGatewayStream) {
                    Thread.currentThread().setContextClassLoader(null);
                    Thread thread = new Thread() {
					public void run() {
                                                
                                                KStream<Long, String> source = null;
                                                StreamsBuilder builderLocal = new StreamsBuilder();
                                                String topic = "dev" + "." + foTGatewayStream.getFoTGatewayiD() + ".*";
                                                System.out.println("topic FoTFog: "+ Pattern.compile(topic).toString());
                                                source = builderLocal.stream(Pattern.compile(topic));

                                                source.print(Printed.toSysOut());
                                                //topology.addSource(foTGatewayStream.getFoTGatewayiD(), Pattern.compile(topic));
                                                //topology.addProcessor("Id" + foTGatewayStream.getFoTGatewayiD(), KafkaStreamProcessor::new, foTGatewayStream.getFoTGatewayiD());
                                                //listSources.add(source);


                        //                        KStream<Long, String> transformed = source.flatMap(
                        //                               // Here, we generate two output records for each input record.
                        //                              // We also change the key and value types.
                        //                             // Example: (345L, "Hello") -> ("HELLO", 1000), ("hello", 9000)
                        //                             (key, value) -> {
                        //                                 //System.out.println(topic + " " + "Value: " + value);
                        //                                 List<KeyValue<Long, String>> result = new LinkedList<>();
                        //                                 JsonParser parser = new JsonParser();
                        //                                 JsonElement element = parser.parse(value);
                        //                                 if(element.isJsonObject()){
                        //
                        //
                        //                                     String sensor = element.getAsJsonObject().get("sensorId").getAsString();
                        //
                        //                                     switch(sensor){
                        //                                             case "dustSensor":
                        //                                                 System.out.println("Case: " + sensor);
                        //                                                 break;
                        //                                             }
                        //
                        //
                        //                                     result.add(KeyValue.pair(key, value));
                        //
                        //
                        //                                 }
                        //                                 return result;
                        //                             }
                        //                         );
                        //
                        //                        transformed.print(Printed.toFile("/home/brenno/Documentos/log.kafka.txt"));                     
                                                  try{  
                                                    Thread.sleep(5000);
                                                  }catch(Exception e){
                                                    e.printStackTrace();
                                                  }

                                                  
                                                  KafkaStreams streamLocal = new KafkaStreams(builderLocal.build(), kafkaConsumerStreamAPI.getProps());
                                                  streamLocal.cleanUp();
                                                  streamLocal.start();
                                                  System.out.println("Start Kafka API");
                                                  Thread.currentThread().setContextClassLoader(null);  
                                                }
                                        };      
                        thread.start();
                        this.listRunThreadConsumer.add(thread);	
		}
		
//                          KafkaStreams streamLocal = new KafkaStreams(builderLocal.build(), this.kafkaConsumerStreamAPI.getProps());
//                          streamLocal.cleanUp();
//                          streamLocal.start();
//                          System.out.println("Start Kafka API");

                            
                            //Thread.currentThread().setContextClassLoader(null);
                            //this.streams = new KafkaStreams(topology, this.kafkaConsumerStreamAPI.getProps());
                            //System.out.println("Start Kafka API");
                            //this.listKafkaStreams.add(streams);
                            //this.streams.cleanUp();
                            //this.streams.start();
               
            }
    
    //{"delayFog": 194, "LatencyWindow": "197", "WindowSize": 200, "deviceId": "sc01", 
    //"localDateTime": "2019-01-17T16:46:07.508", "sensorId": "dustSensor", 
    //"valueSensor": ["-45.215", "46.925", "0.855", "17.04", "19.115", "4.59", "16.625", "53.98", "16.21", "15.38"]}
     
     //"dev" + "." + this.fotDeviceStream.getGatewayID() + "." + this.fotDeviceStream.getDeviceId() + "." + this.Sensorid;
    public synchronized void inputData(ConsumerRecord<Long, String> record){
        /*
        list.stream().forEach((t) -> {
           this.listSensorData.add(t);
        });
        
        this.listSensorData.forEach((t) -> {
           System.out.println("id: " + t.getGatewayID() + " value: " + t.getValue());
        });
        System.out.println();
        */  
        try{
            String topicSplit [] = record.topic().split(".");
            JsonElement element = this.parser.parse(record.value());
            if(element.isJsonObject()){
                JsonArray jarray = element.getAsJsonArray();
                String sensor = topicSplit[4];

                //this.mapData.put(sensor, value);
            }else{
                System.out.println(record.value());
            }
        }catch(Exception e){
            UtilDebug.printDebugConsole("Error init FoTFogStream: " + e.getMessage());
            UtilDebug.printError(e);
        }
       
    }
           
     
    
    public void startStreamGatewayAnalysis(){      
            
            for (FoTGatewayStream foTGatewayStream : listFoTGatewayStream) {
                //Thread.currentThread().setContextClassLoader(null);
                KafkaConsumerThread kafkaConsumerThread = new KafkaConsumerThread(foTGatewayStream.getConsumer(), foTGatewayStream.getFoTGatewayiD(), this);
                Thread consumerThread = new Thread(kafkaConsumerThread);
                consumerThread.start();
                this.listRunThreadConsumer.add(consumerThread);
                //this.listKafkaConsumerThreads.add(kafkaConsumerThread);
            
            }
            
            
//            while(true){
//                for (KafkaConsumerThread kafka : listKafkaConsumerThreads) {
//                    try{  
//                        Thread.sleep(5000);
//                    }catch(Exception e){
//                        e.printStackTrace();
//                    }
//                    System.out.println("Data: "+ kafka.getData());
//                }
//            }
    }
    
    public void stopStream(){
         //streams.close();
    }
    
    public void stopThreads(){
        listRunThreadConsumer.forEach((thread) -> {
            System.out.println("Stop thread: "+thread.getName());
            thread.stop();
        });
    }
    
    public void stopKafkaStream(){
        this.streams.close();
    }
    
    /**
     * @return the fogID
     */
    public String getFogID() {
        return fogID;
    }

    /**
     * @param fogID the fogID to set
     */
    public void setFogID(String fogID) {
        this.fogID = fogID;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the latitude
     */
    public float getLatitude() {
        return latitude;
    }

    /**
     * @param latitude the latitude to set
     */
    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    /**
     * @return the longitude
     */
    public float getLongitude() {
        return longitude;
    }

    /**
     * @param longitude the longitude to set
     */
    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    /**
     * @return the listFoTGatewayStream
     */
    public List<FoTGatewayStream> getListFoTGatewayStream() {
        return listFoTGatewayStream;
    }

    /**
     * @param listFoTGatewayStream the listFoTGatewayStream to set
     */
    public void setListFoTGatewayStream(List<FoTGatewayStream> listFoTGatewayStream) {
        this.listFoTGatewayStream = listFoTGatewayStream;
    }
    
    
    
}
