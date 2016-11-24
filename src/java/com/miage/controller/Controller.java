/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miage.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.miage.device.Device;
import com.miage.device.ElectricMeter;
import com.miage.device.ElectricalPlug;
import com.miage.device.LightBulb;
import com.miage.device.SimulateDevice;
import com.miage.device.TemperatureDevice;
import com.miage.sensors.ElectricMeterSensor;
import com.miage.sensors.ElectricalPlugSensor;
import com.miage.sensors.LightBulbSensor;
import com.miage.sensors.Sensor;
import com.miage.sensors.TemperatureSensor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author olivier
 */
public class Controller {
    
    final int maxNbSensor = 20;
    private int currentNbSensor = 0;
    private final List<Sensor> sensors = new ArrayList(maxNbSensor);
    private final List<List<Thread>> th = new ArrayList(maxNbSensor);
    
    public Controller(){
        try{
            //ajout des capteurs à la liste des sensor
            addSensor("Prise salon","prise");
            addSensor("Compteur maison","compteur");
            addSensor("Ampoule","ampoule");
            addSensor("Capteur temperature","temperature");
            
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        
        
    }
    
    public List<Map<String,Object>> getListSensors() throws JsonProcessingException{
        //construit un string json de la liste des capteur pour envoi au client web
        List<Map<String,Object>> tmp = new ArrayList();
        for(int i =0; i < currentNbSensor; i++){
            tmp.add(i,new HashMap<String, Object>());
            tmp.get(i).put("Id",sensors.get(i).getId());
            tmp.get(i).put("Type",sensors.get(i).getType());
            tmp.get(i).put("Name",sensors.get(i).getName());
            tmp.get(i).put("Info",sensors.get(i).getInformations());
        }
        return tmp;
    }; 
    
    public String[] getInfoSensor(int id) throws JsonProcessingException, Exception{
        Sensor s = getSensor(id);
        return s.getInformations();
        
    }; 
    
    public String switchPower(int id) throws Exception{
        Sensor s = getSensor(id);
        s.switchPower();
        return s.getDevice().getState();
    };
    
    public String changeName(int id,String name) throws Exception{
        Sensor s = getSensor(id);
        s.setName(name);
        return s.getName();
    };
    
    public int addSensor(String nom, String type) throws Exception{
        try{
            Device device;
            /* ajoute un sensor avec pour name nom en fonction du type*/
            switch(type){
                case "prise" :
                    device = new ElectricalPlug();
                    sensors.add(currentNbSensor,new ElectricalPlugSensor(nom, (ElectricalPlug)device));
                break;
                case "compteur":
                    device = new ElectricMeter();
                    sensors.add(currentNbSensor,new ElectricMeterSensor(nom, (ElectricMeter)device));
                break;

                case "ampoule":
                    device = new LightBulb();
                    sensors.add(currentNbSensor,new LightBulbSensor(nom, (LightBulb)device));
                break;

                case "temperature":
                    device = new TemperatureDevice();
                    sensors.add(currentNbSensor,new TemperatureSensor(nom, (TemperatureDevice)device));
                break;
                default : 
                    throw new Exception("Vous devez indiquer un type de capteur");
            }
            
            List<Thread> threadSensor = new ArrayList();
            threadSensor.add(new Thread(new SimulateDevice(device)));
            threadSensor.add(new Thread((Runnable) sensors.get(currentNbSensor)));
            threadSensor.get(0).start();
            threadSensor.get(1).start();
            
            th.add(currentNbSensor,threadSensor);
            
            currentNbSensor++;

            /*Retourne l'indice du sensor nouvellement créé*/
            return (currentNbSensor - 1);
        }catch(Exception e){
            throw e;
        }
    }
    
   
    
    private Sensor getSensor(int id)throws Exception{
        
        for(int i =0; i < currentNbSensor; i++){
            if(sensors.get(i).getId() == id ){
                return sensors.get(i);
            }
        }
        throw new Exception("Impossible de trouver le sensor");
    };
    
    private String getJsonFromObject(Object o) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(o);
    }
    
}
