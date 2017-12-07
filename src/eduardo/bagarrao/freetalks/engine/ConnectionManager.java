package eduardo.bagarrao.freetalks.engine;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;

import eduardo.bagarrao.freetalks.message.MessageHandler;

public class ConnectionManager {

	private static final ConnectionManager INSTANCE = new ConnectionManager();
	
	private boolean isIdSet;
	private String clientId;
	private MessageHandler handler;
	
	
	private ConnectionManager() {
		this.isIdSet = false;
	}
	
	public static ConnectionManager getInstance() {
		return (INSTANCE != null)? INSTANCE : new ConnectionManager();
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
	
	public void connect(){
		if(isIdSet()) {
			System.out.println("Here before init");
			handler = new MessageHandler(clientId);
			System.out.println("Here after init");
			handler.connect();
		}else
			System.out.println("Set clientId before connect to server");
	}
	
	public void disconnect(){
		handler.disconnect();
	}
}
