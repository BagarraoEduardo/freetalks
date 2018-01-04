package eduardo.bagarrao.freetalks.message;

import java.util.Date;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONObject;

import eduardo.bagarrao.freetalks.util.DateParser;
import eduardo.bagarrao.freetalks.util.Encrypter;
import eduardo.bagarrao.freetalks.util.messageutil.MessageType;

/**
 * 
 * Message super class.
 * 
 * @author Eduardo
 *
 */
public abstract class Message extends MqttMessage {

	/**
	 * topic of the messages.
	 */
	public static final String TOPIC = "FreeTalks2017newChat";

	/**
	 * key of {@link #sender}.
	 */
	protected static final String KEY_SENDER = "sender";
	
	/**
	 * Key of the {@link #message}.
	 */
	protected static final String KEY_MESSAGE = "message";
	
	/**
	 * Key of the {@link #date}.
	 */
	protected static final String KEY_DATE = "date";
	
	
	/**
	 *  Topic of the message.
	 */
	 protected static final String KEY_TOPIC = "topic";
	 
	 /**
	  * Key of the Message type.
	  */
	public static final String KEY_TYPE = "type";

	/**
	 * message sender.
	 */
	private String sender;
	
	/**
	 * text that message contains.
	 */
	private String message;
	
	/**
	 * when the message was sent.
	 */
	private Date date;
	
	/**
	 * topic of the message.
	 */
	private String topic;
	
	/**
	 * Type of the message.
	 */
	private MessageType type;

	/**
	 * Message constructor.
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
	}

	/**
	 * Message constructor that receives a JSONObject and a message type.
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
	 * Message constructor.
	 * @param type
	 */
	protected Message(MessageType type) {
		this.date = null;
		this.message = "";
		this.sender = "";
		this.type = type;
	}

	/**
	 * getter for {@link #sender}.
	 * @return
	 */
	public String getSender() {
		return sender;
	}

	/**
	 * getter for {@link #message}.
	 * @return
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * getter for {@link #date}.
	 * @return
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * getter for {@link #type}.
	 * @return
	 */
	protected MessageType getType() {
		return type;
	}

	/**
	 * setter for {@link #sender}.
	 * @param sender
	 */
	protected void setSender(String sender) {
		this.sender = sender;
	}

	/**
	 * setter for {@link #message}.
	 * @param message
	 */
	protected void setMessage(String message) {
		this.message = message;
	}

	/**
	 * setter for {@link #date}.
	 * @param date
	 */
	protected void setDate(Date date) {
		this.date = date;
	}

	/**
	 * Converts the message to a JSONObject.
	 * @return
	 */
	public abstract JSONObject toJSONObject();
}