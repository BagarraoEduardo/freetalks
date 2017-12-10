package eduardo.bagarrao.freetalks.message;

import java.util.Vector;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * @author Eduardo Bagarrao Class that handles all received and sent messages
 */
public class MessageHandler extends Thread implements MqttCallback {

	private static final String BROKER = "tcp://iot.eclipse.org:1883";
	private static final String TOPIC = "FreeTalks2017";

	private Vector<JSONObject> vector;

	private MqttClient client;
	private MqttConnectOptions options;
	private MemoryPersistence persistence;
	private String clientId;
	private boolean isConnected;

	/**
	 * Message handler constructor
	 * 
	 * @param clientId
	 *            session id inserted into login
	 * @throws MqttException
	 *             mqttexception
	 */
	public MessageHandler(String clientId) {
		this.vector = new Vector<JSONObject>();
		this.clientId = clientId;
		this.persistence = new MemoryPersistence();
		this.options = new MqttConnectOptions();
		try {
			this.client = new MqttClient(BROKER, clientId);
		} catch (MqttException e) {
			e.printStackTrace();
		}
		this.isConnected = false;
	}

	/**
	 * Connects to the Eclipse Paho Server
	 * 
	 * @throws MqttSecurityException
	 * @throws MqttException
	 */
	public void connect() {
		if (!isConnected()) {
			options.setCleanSession(true);
			try {
				client.connect();
				client.subscribe(TOPIC);
			} catch (MqttException e) {
				e.printStackTrace();
			}
			client.setCallback(this);
			setConnected(true);
		}
	}

	/**
	 * Disconnects from the Eclipse Paho server
	 * 
	 * @throws MqttException
	 */
	public void disconnect() {
		if (isConnected()) {
			try {
				client.unsubscribe(TOPIC);
				client.disconnect();
			} catch (MqttException e) {
				e.printStackTrace();
			}
			setConnected(false);
		}
	}

	/**
	 * Changes the {@link #isConnected()} attribute
	 * 
	 * @param isConnected
	 *            attribute to change
	 */
	private void setConnected(boolean isConnected) {
		this.isConnected = isConnected;
	}

	/**
	 * Checks whether this client is connected or not to the Eclipse Paho Server
	 * 
	 * @return {@link #isConnected} value
	 */
	public boolean isConnected() {
		return isConnected;
	}

	/**
	 * returns the oldest message from {@link #vector}
	 * 
	 * @return null value if the {@link #vector} size is zero, else returns the
	 *         message at the index 0 of the {@link #vector}
	 */
	public JSONObject getNextMessage() {
		return (hasNextMessage()) ? vector.remove(0) : null;
	}

	/**
	 * checks if the {@link #vector} has messages by handle
	 * 
	 * @return checks if the size of the {@link #vector} is not zero
	 */
	private boolean hasNextMessage() {
		return vector.size() != 0;
	}

	/**
	 * Sends a message to the Eclipse Paho server, who will be shown on all online
	 * FreeTalker chats
	 * 
	 * @param text
	 * @throws MqttPersistenceException
	 * @throws MqttException
	 */
	public void writeMessage(String text) {
		JSONObject obj = new JSONObject();
		obj.put("sender", clientId);
		obj.put("message", text);
		System.out.println(" clientID -->" + clientId);
		System.out.println(" text -->" + text);
		System.out.println(" jsonobject -->" + obj);
		if (isConnected()) {
			try {
				client.publish(TOPIC, new MqttMessage(obj.toString().getBytes()));
			} catch (MqttException e) {
				e.printStackTrace();
			}
			System.out.println("[Message Sent] --> " + text);
		}
	}

	@Override
	public void connectionLost(Throwable throwable) {
		throwable.printStackTrace();
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken arg0) {
	}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		System.out.println("Received Message --> " + message.toString());
		vector.add(new JSONObject(message.toString()));
	}

	@Override
	public void run() {
		while (true) {
			if (hasNextMessage())
				while (hasNextMessage()) {
					System.out.println("[Received Message] " + getNextMessage().toString());
				}
		}
	}

}
