/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miage.device;

import static java.lang.Thread.sleep;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ko
 */
public class SimulateDevice implements Runnable {
    
    Device device;
    
    public SimulateDevice(Device device){
        this.device = device;
    }
    
    public Device getDevice(){
        return this.device;
    }
    
    @Override
    public void run() {
        Random r = new Random();
        while(device.getState()=="on"){          
            try {
                device.setCurrentConsumption(1 + r.nextInt(1000 - 1));
                sleep(5000);
            } catch (InterruptedException ex) {
                Logger.getLogger(SimulateDevice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(device.getState()=="off"){
            System.out.println("Device stopped");
        }
    }
    
}
