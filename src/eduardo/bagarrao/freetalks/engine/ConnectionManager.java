package eduardo.bagarrao.freetalks.engine;

import eduardo.bagarrao.freetalks.message.Message;
import eduardo.bagarrao.freetalks.message.TextMessage;
import eduardo.bagarrao.freetalks.util.messageutil.MessageHandler;

import java.lang.management.ManagementFactory;
import java.util.Vector;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONObject;

public class ConnectionManager {

	private static final ConnectionManager INSTANCE = new ConnectionManager();

	private boolean isIdSet;
	private String clientId;
	private MessageHandler handler;

	private ConnectionManager() {
		this.isIdSet = false;
	}

	public static ConnectionManager getInstance() {
		return (INSTANCE != null) ? INSTANCE : new ConnectionManager();
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
		setIdSet(true);
	}

	public void setIdSet(boolean isIdSet) {
		this.isIdSet = isIdSet;
	}

	public boolean isIdSet() {
		return isIdSet;
	}

	public void connect() throws MqttException {
		if (isIdSet()) {
			System.out.println("Here before init");
			handler = new MessageHandler(clientId);
			System.out.println("Here after init");
			handler.connect();
		} else
			System.out.println("Set clientId before connect to server");
	}

	public void disconnect() {
		handler.disconnect();
	}

	public Vector<TextMessage> getAllMessages() {
		Vector<TextMessage> vector = new Vector<TextMessage>();
		TextMessage msg;
		while ((msg = handler.getNextMessage()) != null) {
			vector.add(msg);
		}
		return vector;
	}

	public void publishMessage(String text) {
		handler.writeMessage(text);
	}
}
