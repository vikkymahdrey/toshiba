import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class Sampletest implements MqttCallback {
	public void connectionLost(Throwable cause) {
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
		
	}
}
