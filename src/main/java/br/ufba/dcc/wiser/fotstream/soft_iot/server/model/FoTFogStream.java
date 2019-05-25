/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufba.dcc.wiser.fotstream.soft_iot.server.model;

import br.ufba.dcc.wiser.fotstream.soft_iot.server.kafka.KafkaConsumerStreamAPI;
import br.ufba.dcc.wiser.fotstream.soft_iot.server.thread.KafkaConsumerThread;
import br.ufba.dcc.wiser.fotstream.soft_iot.server.util.UtilDebug;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.util.LinkedList;
import java.util.List;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
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
    
    public FoTFogStream(){
        //startStreamGatewayAnalysis();
        this.listRunThreadConsumer = new LinkedList<>();
        this.listKafkaConsumerThreads = new LinkedList<>();
        this.listKafkaStreams = new LinkedList<>();
        this.kafkaConsumerStreamAPI = new KafkaConsumerStreamAPI();
        //Thread.currentThread().setContextClassLoader(null);
        //this.builder = new StreamsBuilder();
    }
     
     //{"delayFog": 194, "LatencyWindow": "197", "WindowSize": 200, "deviceId": "sc01", 
     //"localDateTime": "2019-01-17T16:46:07.508", "sensorId": "dustSensor", 
     //"valueSensor": ["-45.215", "46.925", "0.855", "17.04", "19.115", "4.59", "16.625", "53.98", "16.21", "15.38"]}
     public void startStreamGatewayAnalysisKafkaStream(){
           for (FoTGatewayStream foTGatewayStream : listFoTGatewayStream) {
               try{
                    StreamsBuilder builder = foTGatewayStream.getBuilder();
                    KStream<Long, String> source = foTGatewayStream.getSource();

                    source.print(Printed.toSysOut());


                    KStream<Long, String> transformed = source.flatMap(
                         // Here, we generate two output records for each input record.
                         // We also change the key and value types.
                         // Example: (345L, "Hello") -> ("HELLO", 1000), ("hello", 9000)
                         (key, value) -> {
                             System.out.println("Value: " + value);
                             List<KeyValue<Long, String>> result = new LinkedList<>();
                             JsonParser parser = new JsonParser();
                             JsonElement element = parser.parse(value);
                             if(element.isJsonObject()){


                                 String sensor = element.getAsJsonObject().get("sensorId").getAsString();

                                 switch(sensor){
                                         case "dustSensor":
                                             System.out.println("Case: " + sensor);
                                             break;
                                         }


                                 result.add(KeyValue.pair(key, value));


                             }
                             return result;
                         }
                     );

                    transformed.print(Printed.toSysOut());
                    Thread.currentThread().setContextClassLoader(null);
                    KafkaStreams streams = new KafkaStreams(builder.build(), this.kafkaConsumerStreamAPI.getProps());
                    System.out.println("Start Kafka API: " + foTGatewayStream.getFoTGatewayiD());
                    this.listKafkaStreams.add(streams);
                    streams.start();
               }catch(Exception e){
                   UtilDebug.printDebugConsole("Error init FoTFogStream: " + e.getMessage());
                   UtilDebug.printError(e);
               }
           }
           
           
     }
    
    public void startStreamGatewayAnalysis(){      
       
            for (FoTGatewayStream foTGatewayStream : listFoTGatewayStream) {
                
                KafkaConsumerThread kafkaConsumerThread = new KafkaConsumerThread(foTGatewayStream.getConsumer(), this.fogID);
                Thread consumerThread = new Thread(kafkaConsumerThread);
                consumerThread.start();
                this.listRunThreadConsumer.add(consumerThread);
                this.listKafkaConsumerThreads.add(kafkaConsumerThread);
            
            }    
            
    
    }
    
    public void stopStream(){
         //streams.close();
    }
    
    public void stopThreads(){
        listRunThreadConsumer.forEach((thread) -> {
            System.out.println("Stop thread: "+thread.getName());
            thread.interrupt();
        });
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
