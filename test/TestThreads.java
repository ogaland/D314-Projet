

import com.miage.device.ElectricMeter;
import com.miage.device.ElectricalPlug;
import com.miage.device.SimulateDevice;
import com.miage.sensors.ElectricMeterSensor;
import com.miage.sensors.ElectricalPlugSensor;
import com.miage.sensors.Sensor;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ko
 */
public class TestThreads {
    public static void main(String[] args) throws InterruptedException{
        
        //Préparation des Threads
        
        ElectricMeter device1 = new ElectricMeter();
        Sensor sensor1 = new ElectricMeterSensor("compteur", device1);
        Thread t1 = new Thread(new SimulateDevice(device1));
        Thread t1record = new Thread((Runnable) sensor1);
        
        
        ElectricalPlug device2 = new ElectricalPlug();
        Sensor sensor2 = new ElectricalPlugSensor("prise salon", device2);
        Thread t2 = new Thread(new SimulateDevice(device2));
        Thread t2record = new Thread((Runnable) sensor2);
        /*
        LightBulb device3 = new LightBulb();
        Sensor sensor3 = new LightBulbSensor("ampoule salon", device3);
        Thread t3 = new Thread(new SimulateDevice(device3));
        Thread t3record = new Thread((Runnable) sensor3);
        
        TemperatureDevice device4 = new TemperatureDevice();
        Sensor sensor4 = new TemperatureSensor("chauffage salon", device4);
        Thread t4 = new Thread(new SimulateDevice(device4));
        Thread t4record = new Thread((Runnable) sensor4);
        */
        
        //Lancement des Threads
        t1.start();
        t1record.start();
        
        t2.start();
        t2record.start();
        /*
        t3.start();
        t3record.start();
        
        t4.start();
        t4record.start();
        */
        boolean running = true;
        while(running){
            //Si on rentre 1 dans la console, l'enregistrement en base de donnée s'arrête.
            Scanner sc = new Scanner(System.in);
            int i = sc.nextInt();
            if(i == 0){
                
                System.exit(0);
            }
            //Si on tape 2, on récupère les dernières infos du capteur
            if(i==1){
                
                String[] infos2 = sensor2.getInformations();
                System.out.println("Etat courant du capteur : " + sensor2.getDevice().getState());
                System.out.println("Dernier enregistrement du capteur 1 : " + sensor2.getName());
                    for (int j = 0; j<infos2.length;j++) {
                        if(j==0){
                            System.out.println("Numéro de l'enregistrement : " + infos2[j]);
                        }
                        if(j==1){
                            System.out.println("Date : " + infos2[j]);
                        }
                        if(j==2){
                        System.out.println("Etat : " + infos2[j]);
                        }
                        if(j==3){
                            System.out.println("Consommation courante : " + infos2[j]);
                        }
                    }
            }
            if(i==2){              
                    sensor2.switchPower();
            }
            if(i==3){              
                    ArrayList<String> stats = sensor2.getStats("2016-09-29 07:00:00", "2016-12-29 21:52:00");
                    System.out.println("CAPTEUR 1 ");
                    
                    for(int j=0; j<stats.size(); j++){
                        System.out.println("Enregistrement " + j + " : " + stats.get(j));
                    }
                    
                    System.out.println("**********************************************");
                    System.out.println("CAPTEUR 2");
                    ArrayList<String> stats2 = sensor1.getStats("2016-09-29 07:00:00", "2016-12-29 21:52:00");
                    for(int j=0; j<stats2.size(); j++){                   
                        System.out.println("Enregistrement " + j + " : " + stats2.get(j));
                    }
            }
          
          
            /*
            if(i==2){
                String[] infos2 = sensor2.getInformations();
                System.out.println("Dernier enregistrement du capteur 2 " + sensor2.getName());
                for (int j = 0; j<infos2.length;j++) {
                    if(j==0){
                        System.out.println("Numéro de l'enregistrement : " + infos2[j]);
                    }
                    if(j==1){
                        System.out.println("Date : " + infos2[j]);
                    }
                    if(j==2){
                        System.out.println("Etat : " + infos2[j]);
                    }
                    if(j==3){
                        System.out.println("Consommation courante : " + infos2[j]);
                    }
                }       
            }
            if(i==3){
                String[] infos3 = sensor3.getInformations();
                System.out.println("Dernier enregistrement du capteur 3 " + sensor3.getName());
                for (int j = 0; j<infos3.length;j++) {
                    if(j==0){
                        System.out.println("Numéro de l'enregistrement : " + infos3[j]);
                    }
                    if(j==1){
                        System.out.println("Date : " + infos3[j]);
                    }
                    if(j==2){
                        System.out.println("Etat : " + infos3[j]);
                    }
                    if(j==3){
                        System.out.println("Couleur : " + infos3[j]);
                    }
                    if(j==4){
                        System.out.println("Luminosité : " + infos3[j]);
                    }
                    if(j==5){
                        System.out.println("Consommation courante : " + infos3[j]);
                    }
                }
            }
            if(i==4){
                String[] infos4 = sensor4.getInformations();
                System.out.println("Dernier enregistrement du capteur 4 " + sensor4.getName());
                for (int j = 0; j<infos4.length;j++) {
                    if(j==0){
                        System.out.println("Numéro de l'enregistrement : " + infos4[j]);
                    }
                    if(j==1){
                        System.out.println("Date : " + infos4[j]);
                    }
                    if(j==2){
                        System.out.println("Etat : " + infos4[j]);
                    }
                    if(j==3){
                        System.out.println("Temperature : " + infos4[j]);
                    }
                    if(j==4){
                        System.out.println("Consommation : " + infos4[j]);
                    }
                }*/
            }
            
        }
        
    }
    

