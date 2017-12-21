package eduardo.bagarrao.freetalks.engine;

import eduardo.bagarrao.freetalks.message.TextMessage;
import eduardo.bagarrao.freetalks.util.messageutil.MessageHandler;

import java.util.Vector;

import org.eclipse.paho.client.mqttv3.MqttException;

/**
 * 
 * Singleton that connects({@link #connect()}) or
 * disconnects({@link #disconnect()}) to the client and gets the
 * messages({@link #getAllMessages()}) from the MessageHandler class.
 * 
 * @author Eduardo
 *
 */
public class ConnectionManager {

	/**
	 * Only instance of {@link #ConnectionManager()} class.
	 */
	private static final ConnectionManager INSTANCE = new ConnectionManager();

	/**
	 * boolean that checks that {@link #clientId} is not empty.
	 */
	private boolean isIdSet;

	/**
	 * clientId that is used to {@link #connect()}
	 */
	private String clientId;

	/**
	 * Handler that allow message receiving and sending.
	 */
	private MessageHandler handler;

	/**
	 * {@link #ConnectionManager()} private and unique constructor
	 */
	private ConnectionManager() {
		this.isIdSet = false;
	}

	/**
	 * getter for {@link #INSTANCE}
	 * 
	 * @return {@link #INSTANCE}
	 */
	public static ConnectionManager getInstance() {
		return (INSTANCE != null) ? INSTANCE : new ConnectionManager();
	}

	/**
	 * getter for {@link #clientId}
	 * 
	 * @return {@link #clientId}
	 */
	public String getClientId() {
		return clientId;
	}

	/**
	 * setter for {@link #clientId}
	 * 
	 * @param clientId
	 *            String to be set as {@link #clientId}
	 */
	public void setClientId(String clientId) {
		this.clientId = clientId;
		setIdSet(true);
	}

	/**
	 * 
	 * setter for {@link #isIdSet}
	 * 
	 * @param isIdSet
	 */
	private void setIdSet(boolean isIdSet) {
		this.isIdSet = isIdSet;
	}

	/**
	 * return the value of the {@link #isIdSet}.
	 * 
	 * @return {@link #isIdSet}
	 */
	public boolean isIdSet() {
		return isIdSet;
	}

	/**
	 * 
	 * inits the {@link #handler}, connecting to the MQTT client (only if
	 * {@link #isIdSet} is true)
	 * 
	 * @throws MqttException
	 */
	public void connect() throws MqttException {
		if (isIdSet()) {
			System.out.println("Here before init");
			handler = new MessageHandler(clientId);
			System.out.println("Here after init");
			handler.connect();
		} else
			System.out.println("Set clientId before connect to server");
	}

	/**
	 * 
	 * Disconnects the MQTT client that is in the {@link #handler} class.
	 * 
	 */
	public void disconnect() {
		handler.disconnect();
	}

	/**
	 * 
	 * Returns all messages that {@link #handler} has on his Vector
	 * 
	 * @return Vector with all textMessages that {@link #handler} has saved.
	 */
	public Vector<TextMessage> getAllMessages() {
		Vector<TextMessage> vector = new Vector<TextMessage>();
		TextMessage msg;
		while ((msg = handler.getNextMessage()) != null) {
			vector.add(msg);
		}
		return vector;
	}

	/**
	 * 
	 * Sends a String to send a textMessage to MQTT client
	 * 
	 * @param text
	 *            data to send to {@link #handler} to publish
	 */
	public void publishMessage(String text) {
		handler.writeMessage(text);
	}
}
