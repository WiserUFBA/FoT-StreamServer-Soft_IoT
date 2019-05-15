/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufba.dcc.wiser.fotstream.soft_iot.server.model;

import br.ufba.dcc.wiser.fotstream.soft_iot.server.util.UtilDebug;
import java.time.Duration;
import java.util.List;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;



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
    
    
    public FoTFogStream(){
        //startStreamGatewayAnalysis();
    }
    
    public void startStreamGatewayAnalysis(){      
       while(true){
            for (FoTGatewayStream foTGatewayStream : listFoTGatewayStream) {
                try {
 
                    ConsumerRecords<Long, String> records = foTGatewayStream.getConsumer().poll(Duration.ofMinutes(100));
                    for (ConsumerRecord<Long, String> record : records){
                        
                        System.out.println("topic = " + record.topic() + "partition = " + record.partition() + "country = " + record.offset());
                        System.out.println("offset = " + record.offset() + "key = " + record.key() + "value = " + record.value());
                            
                        
                       
                    }
                    }finally {
                        foTGatewayStream.getConsumer().close();
                    }
            
            }
        }
    
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
