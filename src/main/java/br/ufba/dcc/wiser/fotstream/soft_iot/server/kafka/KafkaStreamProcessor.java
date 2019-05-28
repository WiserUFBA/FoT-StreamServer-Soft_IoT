/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufba.dcc.wiser.fotstream.soft_iot.server.kafka;

import org.apache.kafka.streams.processor.Processor;
import org.apache.kafka.streams.processor.ProcessorContext;

/**
 *
 * @author Brenno Mello <brennodemello.bm at gmail.com>
 */
public class KafkaStreamProcessor implements Processor<Long, String>{
  
  private ProcessorContext context;

  public KafkaStreamProcessor() {
   
  }

  @Override
  public void init(ProcessorContext context) {
    this.context = context;

    // Here you would perform any additional initializations such as setting up an email client.
  }

  @Override
  public void process(Long key,String message) {
    // Here you would format and send the alert email.
    //
    // In this specific example, you would be able to include information about the page's ID and its view count
    // (because the class implements `Processor<PageId, Long>`).
    
    System.out.println(message);
  }

  @Override
  public void close() {
    // Any code for clean up would go here.  This processor instance will not be used again after this call.
  }
  
}
