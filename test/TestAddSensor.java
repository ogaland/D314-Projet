import com.miage.device.Device;
import com.miage.sensors.ElectricalPlugSensor;
import com.miage.sensors.Sensor;

/**
 *
 * @author ko
 */
public class TestAddSensor {
    public static void main(String[] args){
        Device d = new Device();
        Sensor s = new ElectricalPlugSensor("PriseSalon","prise",d);
        System.out.println(s.toString());
    } 
}
