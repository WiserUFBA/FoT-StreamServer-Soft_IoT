/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufba.dcc.wiser.fotstream.soft_iot.server.model;

import java.util.Collections;
import java.util.List;
import org.apache.kafka.clients.consumer.KafkaConsumer;

/**
 *
 * @author Brenno Mello <brennodemello.bm at gmail.com>
 */
public class FoTGatewayStream {
    
    
    private String FoTGatewayiD;
    private String type;
    private float latitude;
    private float longitude;
    private KafkaConsumer<Long, String> consumer;
    
    public FoTGatewayStream(){
        startConsumer();
    }
    
    public void startConsumer(){
         
        String topic = "dev" + "." + this.FoTGatewayiD + ".*" ;
        this.consumer.subscribe(Collections.singletonList(topic));
        
    }
    
    /**
     * @return the FoTGatewayiD
     */
    public String getFoTGatewayiD() {
        return FoTGatewayiD;
    }

    /**
     * @param FoTGatewayiD the FoTGatewayiD to set
     */
    public void setFoTGatewayiD(String FoTGatewayiD) {
        this.FoTGatewayiD = FoTGatewayiD;
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
        this.setLatitude(latitude);
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
        this.setLongitude(longitude);
    }
    
     /**
     * @return the consumer
     */
    public KafkaConsumer<Long, String> getConsumer() {
        return consumer;
    }

    /**
     * @param consumer the consumer to set
     */
    public void setConsumer(KafkaConsumer<Long, String> consumer) {
        this.consumer = consumer;
    }
    
    
}
