/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufba.dcc.wiser.fotstream.soft_iot.server.controller;

import br.ufba.dcc.wiser.fotstream.soft_iot.server.model.FoTGatewayStream;
import br.ufba.dcc.wiser.fotstream.soft_iot.server.util.UtilDebug;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Brenno Mello <brennodemello.bm at gmail.com>
 */
public class MainStreamController {
    
    /**
        * consumer is part of a consumer group, it will be assigned a subset of partitions
        from that topic.    
    */
    
    private String fotStreamGateway = "";
    private List<FoTGatewayStream> listFoTGatewayStream;
    
  
    public void init(){
        
        
        String topic = "";
    }

    public void readMessage(){
        
    }
    
     public void loadFoTStreamGateway(){
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(this.fotStreamGateway);
        JsonArray jarray = element.getAsJsonArray();
       
         UtilDebug.printDebugConsole("Tamanho do array: " + jarray.size());
        
        for (JsonElement jsonElement : jarray) {
            if(jsonElement.isJsonObject()){
                
                System.out.println("Loop 1");
                FoTGatewayStream fotGatewayStream = new FoTGatewayStream(this.topology, this.mqttConfig, bootstrapServers);
                JsonObject fotElement = jsonElement.getAsJsonObject();
                fotDeviceStream.setDeviceId(fotElement.get("id").getAsString());
                fotDeviceStream.setLatitude(fotElement.get("latitude").getAsFloat());
                fotDeviceStream.setLongitude(fotElement.get("latitude").getAsFloat());
                
                UtilDebug.printDebugConsole(fotDeviceStream.getDeviceId());
                
                
                JsonArray jsonArraySensors = fotElement.getAsJsonArray("sensors");
                List<FoTSensorStream> listFoTSensorStream = new ArrayList<FoTSensorStream>();
                
                        
               fotDeviceStream.setListFoTSensorStream(listFoTSensorStream);
               this.listFoTDeviceStream.add(fotDeviceStream);
            }
        }
    }
     }
}
