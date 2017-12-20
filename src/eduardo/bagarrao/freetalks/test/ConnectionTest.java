package eduardo.bagarrao.freetalks.test;

import static org.junit.Assert.assertEquals;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import eduardo.bagarrao.freetalks.util.messageutil.MessageHandler;


public class ConnectionTest {

	private static final String CLIENT_ID = "JUnitTester";
	private MessageHandler handler;

	@Before
	public void prepare() throws MqttException {
		this.handler = new MessageHandler(CLIENT_ID);
	}

	@After
	public void end() throws MqttException {
		handler.disconnect();
	}

	@Test
	public void checkConnectionBeforeConnectionTest() {
		assertEquals(handler.isConnected(), false);
	}

	@Test
	public void checkConnectionAfterConnectionTest() throws MqttSecurityException, MqttException {
		handler.connect();
		assertEquals(handler.isConnected(), false);
	}
}
