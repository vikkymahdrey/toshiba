import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class test3 implements MqttCallback {

MqttClient client;

public test3() {
}

public static void main(String[] args) {
    new test3().doDemo();
}

public void doDemo() {
    try {
    	MqttConnectOptions connOpts = new MqttConnectOptions();
        connOpts.setUserName("loragw");
        connOpts.setPassword("loragw".toCharArray());
        connOpts.setCleanSession(true);
        client = new MqttClient("tcp://139.59.84.50:1883", MqttClient.generateClientId());
        
        client.connect(connOpts);
        client.setCallback(this);
        client.subscribe("application/1/node/4786e6ed00490044/rx");
        MqttMessage message = new MqttMessage();
        message.setPayload("A single message from my computer fff"
                .getBytes());
        client.publish("application/1/node/4786e6ed00490044/tx", message);
        //System.exit(0);
    } catch (MqttException e) {
        e.printStackTrace();
    }
}

@Override
public void connectionLost(Throwable cause) {
    // TODO Auto-generated method stub

}

@Override
public void messageArrived(String topic, MqttMessage message)
        throws Exception {
 System.out.println("hiiiiii"+message);   
}

@Override
public void deliveryComplete(IMqttDeliveryToken token) {
    // TODO Auto-generated method stub

}

}