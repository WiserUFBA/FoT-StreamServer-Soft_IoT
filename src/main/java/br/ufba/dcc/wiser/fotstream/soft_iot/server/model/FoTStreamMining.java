/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufba.dcc.wiser.fotstream.soft_iot.server.model;

import br.ufba.dcc.wiser.fotstream.soft_iot.server.conceptDrift.CusumDM;
import java.util.List;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.Updater;
import org.deeplearning4j.nn.conf.layers.GravesLSTM;
import org.deeplearning4j.nn.conf.layers.RnnOutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.lossfunctions.LossFunctions;



/**
 *
 * @author Brenno Mello <brennodemello.bm at gmail.com>
 */
public class FoTStreamMining {

      private List<SensorData> listData;
      private CusumDM detectorConcepDrift;
      private boolean changeDetector;
      private MultiLayerNetwork network;
      
      public FoTStreamMining(){
          this.detectorConcepDrift = new CusumDM();
          this.detectorConcepDrift.setlambdaOption(50);
          runModelDeeplearning4j();
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
    
      public void trainLSTMDeeplearning4j(List<SensorData> data){
        //INDArray inputArray = Nd4j.zeros(1, 10, data.size());
        //INDArray inputLabels = Nd4j.zeros(1, 30, data.size());
        double [] inputArray = new double[data.size()];
        double [] labelsArray = new double[data.size()];
        
        for (int i = 0; i < data.size()-1; i++) {
            inputArray[i] = data.get(i).getValue();
            labelsArray[i] = data.get(i+1).getValue();
        }
        
        INDArray features = Nd4j.create(inputArray);
	INDArray labels = Nd4j.create(labelsArray);
	
        System.out.print("features: " + features.toString());
        System.out.print("labels: " + labels.toString());
        
        DataSet dataSet = new DataSet(features, labels);
        
        for(int z=0; z<10 ; z++) {
            this.network.fit(dataSet);

            INDArray testInputArray = Nd4j.zeros(10);
            testInputArray.putScalar(0, 1);

            this.network.rnnClearPreviousState();
           
            INDArray outputArray = network.rnnTimeStep(testInputArray);
            System.out.println(outputArray.toString());
        }
        
      }
          
      
      
     public void runModelDeeplearning4j(){
        GravesLSTM.Builder lstmBuilder = new GravesLSTM.Builder();
        lstmBuilder.activation(Activation.TANH);
        lstmBuilder.nIn(10);
        lstmBuilder.nOut(30); // Hidden
        GravesLSTM inputLayer = lstmBuilder.build();
        
        RnnOutputLayer.Builder outputBuilder = new RnnOutputLayer.Builder();
        outputBuilder.lossFunction(LossFunctions.LossFunction.MSE);
        outputBuilder.activation(Activation.SOFTMAX);
        outputBuilder.nIn(30); // Hidden
        outputBuilder.nOut(10);
        RnnOutputLayer outputLayer = outputBuilder.build();
        
        NeuralNetConfiguration.Builder nnBuilder = new NeuralNetConfiguration.Builder();
        nnBuilder.optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT);
        nnBuilder.updater(Updater.ADAM);
        nnBuilder.weightInit(WeightInit.XAVIER);
        nnBuilder.learningRate(0.01);
        nnBuilder.miniBatch(true);
        
        //Thread.currentThread().setContextClassLoader(null);
        this.network = new MultiLayerNetwork(
            nnBuilder.list().layer(0, inputLayer)
                    .layer(1, outputLayer)
                    .backprop(true).pretrain(false)
                    .build());

        network.init();
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

