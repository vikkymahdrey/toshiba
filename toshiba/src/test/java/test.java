import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class test {
public static void main(String[] args){
	System.out.println("IN Main");
	 String topic        = "MQTT";
     String content      = "Hi Vikky";
     int qos             = 2;
     String broker       = "tcp://139.59.84.50:1883";
     //String clientId     = "262311";
     MemoryPersistence persistence = new MemoryPersistence();

     try {
                 
         MqttConnectOptions connOpts = new MqttConnectOptions();
         connOpts.setUserName("loragw");
         connOpts.setPassword("loragw".toCharArray());
         connOpts.setCleanSession(true);
         
         MqttClient sampleClient = new MqttClient(broker, MqttClient.generateClientId(), persistence);
            
         sampleClient.connect(connOpts);
         sampleClient.setCallback(new Sampletest());
         
         System.out.println("Connected");
         System.out.println("Publishing message: "+content);
         
         MqttMessage message = new MqttMessage(content.getBytes());
         message.setQos(qos);
         
         sampleClient.publish(topic, message);
         System.out.println("Message published");
         
         sampleClient.disconnect();
         System.out.println("Disconnected");
         
         System.exit(0);
     } catch(MqttException me) {
         System.out.println("reason "+me.getReasonCode());
         System.out.println("msg "+me.getMessage());
         System.out.println("loc "+me.getLocalizedMessage());
         System.out.println("cause "+me.getCause());
         System.out.println("excep "+me);
         me.printStackTrace();
     }
 }

}
