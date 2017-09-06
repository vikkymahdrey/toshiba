import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class test3  {
public static void main(String[] args){
	System.out.println("IN Main");
	 //String topic        = "MQTT";
	String topic="application/1/node/4786e6ed00490044/rx";
       int qos             = 1;
     String broker       = "tcp://139.59.84.50:1883";
     MemoryPersistence persistence = new MemoryPersistence();

     try {
                 
         MqttConnectOptions connOpts = new MqttConnectOptions();
         connOpts.setUserName("loragw");
         connOpts.setPassword("loragw".toCharArray());
         connOpts.setCleanSession(true);
         
         MqttClient sampleClient = new MqttClient(broker, MqttClient.generateClientId(), persistence);
            
         sampleClient.connect(connOpts);
         sampleClient.setCallback(new Sampletest());
                 
         sampleClient.subscribe(topic, qos);
         System.out.println("Message subscribed");
         
         //sampleClient.disconnect();
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


/*public void connectionLost(Throwable cause) {
	 // Called when the connection to the server has been lost.
    // An application may choose to implement reconnection
    // logic at this point. This sample simply exits.
	 System.out.println("Connection to MQTT broker lost!");
    System.exit(1);
	
}


public void messageArrived(String topic, MqttMessage message) throws Exception {
	// TODO Auto-generated method stub
	System.out.println("Message received:\n\t"+ new String(message.getPayload()) );
	
}


public void deliveryComplete(IMqttDeliveryToken token) {
	// TODO Auto-generated method stub
	
}*/

}
