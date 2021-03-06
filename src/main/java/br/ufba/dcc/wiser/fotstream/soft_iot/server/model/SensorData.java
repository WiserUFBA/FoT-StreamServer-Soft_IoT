/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufba.dcc.wiser.fotstream.soft_iot.server.model;

import java.time.LocalDateTime;


/**
 *
 * @author brenno
 */
public class SensorData {
    private double value;
    //private FoTDeviceStream device;
    //private FoTSensorStream sensor;
    private LocalDateTime localDateTime;
    private long delay;
    private String gatewayID;
    private String topic;

    
      
    public SensorData(double value){
        this.value = value;
        
    }
    
     public SensorData(double data, String topic){
        this.topic = topic;
        this.value = data;
    }
     
    public long getDelay() {
        return delay;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }
    
    
    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    /**
     * @return the gatewayID
     */
    public String getGatewayID() {
        return gatewayID;
    }

    /**
     * @param gatewayID the gatewayID to set
     */
    public void setGatewayID(String gatewayID) {
        this.gatewayID = gatewayID;
    }

    /**
     * @return the topic
     */
    public String getTopic() {
        return topic;
    }

    /**
     * @param topic the topic to set
     */
    public void setTopic(String topic) {
        this.topic = topic;
    }
    
    	
}
