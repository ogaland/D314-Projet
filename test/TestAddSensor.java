
import com.miage.device.ElectricMeter;
import com.miage.device.ElectricalPlug;
import com.miage.device.LightBulb;
import com.miage.device.TemperatureDevice;
import com.miage.sensors.ElectricMeterSensor;
import com.miage.sensors.ElectricalPlugSensor;
import com.miage.sensors.LightBulbSensor;
import com.miage.sensors.Sensor;
import com.miage.sensors.TemperatureSensor;


/**
 *
 * @author ko
 */
public class TestAddSensor {
    public static void main(String[] args){
        
        ElectricalPlug prise1 = new ElectricalPlug();
        ElectricMeter compteur = new ElectricMeter();
        LightBulb ampoule1 = new LightBulb();
        TemperatureDevice temperatureDevice1 = new TemperatureDevice();
        
        
        Sensor s1 = new ElectricalPlugSensor("Prise salon", prise1);
        Sensor s2 = new ElectricMeterSensor("Compteur maison", compteur);
        Sensor s3 = new LightBulbSensor("Ampoule salon", ampoule1);
        Sensor s4 = new TemperatureSensor("chauffage salon", temperatureDevice1);
        for(int i=0;i<3;i++){
            s1.recordBehavior();
            s2.recordBehavior();
            s3.recordBehavior();
            s4.recordBehavior();           
        }
        
        String[] infos2 = s2.getInformations();
        System.out.println("Dernier enregistrement du capteur 2 : " + s2.getName());
        for (int i = 0; i<infos2.length;i++) {
            if(i==0){
                System.out.println("Numéro de l'enregistrement : " + infos2[i]);
            }
            if(i==1){
                System.out.println("Date : " + infos2[i]);
            }
            if(i==2){
                System.out.println("Consommation courante : " + infos2[i]);
            }
        }
        
        System.out.println("*****************************************");
        String[] infos1 = s1.getInformations();
        System.out.println("Dernier enregistrement du capteur 1 " + s1.getName());
        for (int i = 0; i<infos1.length;i++) {
            if(i==0){
                System.out.println("Numéro de l'enregistrement : " + infos1[i]);
            }
            if(i==1){
                System.out.println("Date : " + infos1[i]);
            }
            if(i==2){
                System.out.println("Etat : " + infos1[i]);
            }
            if(i==3){
                System.out.println("Consommation courante : " + infos1[i]);
            }
        }
        
        System.out.println("*****************************************");
        String[] infos3 = s3.getInformations();
        System.out.println("Dernier enregistrement du capteur 3 " + s3.getName());
        for (int i = 0; i<infos3.length;i++) {
            if(i==0){
                System.out.println("Numéro de l'enregistrement : " + infos3[i]);
            }
            if(i==1){
                System.out.println("Date : " + infos3[i]);
            }
            if(i==2){
                System.out.println("Etat : " + infos3[i]);
            }
            if(i==3){
                System.out.println("Couleur : " + infos3[i]);
            }
            if(i==4){
                System.out.println("Luminosité : " + infos3[i]);
            }
            if(i==5){
                System.out.println("Consommation courante : " + infos3[i]);
            }
        }
        
        
        System.out.println("*****************************************");
        String[] infos4 = s4.getInformations();
        System.out.println("Dernier enregistrement du capteur 4 " + s4.getName());
        for (int i = 0; i<infos4.length;i++) {
            if(i==0){
                System.out.println("Numéro de l'enregistrement : " + infos4[i]);
            }
            if(i==1){
                System.out.println("Date : " + infos4[i]);
            }
            if(i==2){
                System.out.println("Etat : " + infos4[i]);
            }
            if(i==3){
                System.out.println("Temperature : " + infos4[i]);
            }
            if(i==4){
                System.out.println("Consommation : " + infos4[i]);
            }
        }
        
    }
}
