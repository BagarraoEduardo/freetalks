package eduardo.bagarrao.freetalks.message;

import java.util.Date;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONObject;

import eduardo.bagarrao.freetalks.util.DateParser;
import eduardo.bagarrao.freetalks.util.Encrypter;
import eduardo.bagarrao.freetalks.util.messageutil.MessageType;

/**
 * 
 * @author Eduardo
 *
 */
public abstract class Message extends MqttMessage {

	/**
	 * 
	 */
	public static final String TOPIC = "FreeTalks2017newChat";

	/**
	 * 
	 */
	protected static final String KEY_SENDER = "sender";
	
	/**
	 * 
	 */
	protected static final String KEY_MESSAGE = "message";
	
	/**
	 * 
	 */
	protected static final String KEY_DATE = "date";
	
	
	/**
	 *  
	 */
	 protected static final String KEY_TOPIC = "topic";
	 
	 /**
	  * 
	  */
	public static final String KEY_TYPE = "type";

	/**
	 * 
	 */
	private String sender;
	
	/**
	 * 
	 */
	private String message;
	
	/**
	 * 
	 */
	private Date date;
	
	/**
	 * 
	 */
	private String topic;
	
	/**
	 * 
	 */
	private MessageType type;

	/**
	 *  
	 * @param sender
	 * @param message
	 * @param date
	 * @param type
	 * @throws Exception
	 */
	protected Message(String sender, String message, Date date, MessageType type) throws Exception {
		this.sender = sender;
		this.message = message;
		this.date = date;
		this.type = type;
		setPayload(Encrypter.encrypt(toJSONObject().toString(), "ssshhhhhhhhhhh!!!!").getBytes());
	}

	/**
	 * 
	 * @param obj
	 * @param type
	 * @throws Exception
	 */
	protected Message(JSONObject obj, MessageType type) throws Exception {
		if (obj.has(KEY_SENDER) && obj.has(KEY_MESSAGE) && obj.has(KEY_DATE)) {
			setPayload((Encrypter.encrypt(obj.toString(), "ssshhhhhhhhhhh!!!!").getBytes()));
			this.sender = obj.getString(KEY_SENDER);
			this.message = obj.getString(KEY_MESSAGE);
			this.date = DateParser.parseDate(obj.getString(KEY_DATE));
			this.type = type;
		} else
			throw new IllegalArgumentException("[" + KEY_SENDER + "],[" + KEY_TYPE + "],[" + KEY_DATE + "] and ["
					+ KEY_MESSAGE + "] keys are inexistent in this JSONObject");
	}

	/**
	 * 
	 * @param topic
	 */
	protected Message(MessageType type) {
		this.date = null;
		this.message = "";
		this.sender = "";
		this.type = type;
	}

	/**
	 * 
	 * @return
	 */
	public String getSender() {
		return sender;
	}

	/**
	 * 
	 * @return
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * 
	 * @return
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * 
	 * @return
	 */
	protected MessageType getType() {
		return type;
	}

	/**
	 * 
	 * @param sender
	 */
	protected void setSender(String sender) {
		this.sender = sender;
	}

	/**
	 * 
	 * @param message
	 */
	protected void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 
	 * @param date
	 */
	protected void setDate(Date date) {
		this.date = date;
	}

	/**
	 * 
	 * @return
	 */
	public abstract JSONObject toJSONObject();
}