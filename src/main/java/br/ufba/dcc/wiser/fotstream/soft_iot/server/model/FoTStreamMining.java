/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufba.dcc.wiser.fotstream.soft_iot.server.model;

import br.ufba.dcc.wiser.fotstream.soft_iot.server.conceptDrift.CusumDM;
import java.util.List;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.weights.WeightInit;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.learning.config.Sgd;


/**
 *
 * @author Brenno Mello <brennodemello.bm at gmail.com>
 */
public class FoTStreamMining {

      private List<SensorData> listData;
      private CusumDM detectorConcepDrift;
      private boolean changeDetector;
      
      public FoTStreamMining(){
          this.detectorConcepDrift = new CusumDM();
          this.detectorConcepDrift.setlambdaOption(50);
      }
      
      public void input(double data){
          this.detectorConcepDrift.input(data);
          if(this.detectorConcepDrift.getChange()){
              this.setChangeDetector(true);
          }
      }
      
      public void input(List<SensorData> data){
          
      }
      
      public void runModelTensorFlow(){
        
      }
    
     public void runModelDeeplearning4j(){
        /* 
        MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
        .weightInit(WeightInit.XAVIER)
        .activation(Activation.RELU)
        .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
        .updater(new Sgd(0.05))
        // ... other hyperparameters
        .list()
        .backprop(true)
        .build();
        */
    }
        
    /**
     * @return the changeDetector
     */
    public boolean isChangeDetector() {
        return changeDetector;
    }

    /**
     * @param changeDetector the changeDetector to set
     */
    public void setChangeDetector(boolean changeDetector) {
        this.changeDetector = changeDetector;
    }
      
}
