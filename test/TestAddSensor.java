import com.miage.device.Device;
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
        
        
        Sensor s1 = new ElectricalPlugSensor("Prise salon","prise", prise1);
        Sensor s2 = new ElectricMeterSensor("Compteur maison","compteur", compteur);
        Sensor s3 = new LightBulbSensor("Prise salon","ampoule", ampoule1);
        Sensor s4 = new TemperatureSensor("Prise salon","temperature", temperatureDevice1);
        for(int i=0;i<3;i++){
            s1.recordBehavior();
            s2.recordBehavior();
            s3.recordBehavior();
            s4.recordBehavior();
            
        }
        String[] infos1 = s1.getInformations();
        String[] infos2 = s2.getInformations();
        String[] infos3 = s3.getInformations();
        String[] infos4 = s4.getInformations();
        
        System.out.println("Informations du capteur n°" + s1.getId()+ " : " + s1.getName());
        for (String info : infos1) {
            System.out.println(info);
        }
        
        System.out.println("************************************");
        
        System.out.println("Informations du capteur n°" + s2.getId()+ " : " + s2.getName());
        for (String info : infos2) {
            System.out.println(info);
        }
        
        System.out.println("************************************");
        
        System.out.println("Informations du capteur n°" + s3.getId()+ " : " + s3.getName());
        for (String info : infos3) {
            System.out.println(info);
        }
        
        System.out.println("************************************");
        
        System.out.println("Informations du capteur n°" + s4.getId()+ " : " + s4.getName());
        for (String info : infos4) {
            System.out.println(info);
        }
        
        System.out.println("************************************");
        
        
    } 
}
