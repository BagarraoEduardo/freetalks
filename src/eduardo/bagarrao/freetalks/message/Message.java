package eduardo.bagarrao.freetalks.message;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.text.ParseException;
import java.util.Date;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONException;
import org.json.JSONObject;

import eduardo.bagarrao.freetalks.util.DateParser;
import eduardo.bagarrao.freetalks.util.Encrypter;
import eduardo.bagarrao.freetalks.util.ImageDecoder;

/**
 * 
 * @author Eduardo
 *
 */
public abstract class Message extends MqttMessage{

	protected static final String KEY_SENDER = "sender";
	protected static final String KEY_MESSAGE = "message";
	protected static final String KEY_DATE = "date";
	protected static final String KEY_TOPIC = "topic";
	
	private String sender;
	private String message;
	private Date date;
	private String topic;
	
	/**
	 * 
	 * @param sender
	 * @param message
	 * @throws Exception 
	 */
	protected Message(String sender, String message, Date date, String topic) throws Exception{
		this.sender = sender;
		this.message = message;
		this.date = date;
		this.topic = topic;
		setPayload(Encrypter.encrypt(toJSONObject().toString(),"ssshhhhhhhhhhh!!!!").getBytes());
	}
	
	
	/**
	 * 
	 * @param obj
	 * @throws Exception 
	 */
	protected Message(JSONObject obj) throws Exception{
		if(obj.has(KEY_SENDER) && obj.has(KEY_MESSAGE) && obj.has(KEY_DATE)) {
			setPayload((Encrypter.encrypt(obj.toString(),"ssshhhhhhhhhhh!!!!").getBytes()));
			this.sender = obj.getString(KEY_SENDER);
			this.message = obj.getString(KEY_MESSAGE);
			this.date = DateParser.parseDate(obj.getString(KEY_DATE));
		}else
			throw new IllegalArgumentException("[" + KEY_SENDER + "],[" + KEY_DATE + 
					"] and [" + KEY_MESSAGE + "] keys are inexistent in this JSONObject");
	}
	
	/**
	 * 
	 * @param topic
	 */
	protected Message(String topic) {
		this.topic = topic;
		this.date = null;
		this.message = "";
		this.sender = "";
	}

	

	public String getSender() {
		return sender;
	}
	
	public String getMessage() {
		return message;
	}
	
	public Date getDate() {
		return date;
	}

	public String getTopic() {
		return topic;
	}
	
	protected void setSender(String sender) {
		this.sender = sender;
	}
	
	protected void setMessage(String message) {
		this.message = message;
	}
	
	protected void setDate(Date date) {
		this.date = date;
	}
	
	protected void setTopic(String topic) {
		this.topic = topic;
	}
	
	
	
	//TODO: metodo de converter dados de uma imagem para uma string
	
//	/**
//	 * 
//	 * @return
//	 */
//	public JSONObject toJSONObject() {
//		JSONObject obj = new JSONObject();
//		obj.put(KEY_SENDER, this.sender);
//		obj.put(KEY_MESSAGE, this.message);try {
//			obj.put(KEY_DATE, DateParser.parseString(this.date));
//		} catch (JSONException e) {
//			e.printStackTrace();
//		} catch (ParseException e) {
//			if(obj.has(KEY_DATE))
//				obj.remove(KEY_DATE);
//			obj.put(KEY_DATE, "00-00-0000 00:00");
//			e.printStackTrace();
//		}
//		return obj;
//	}

	public abstract JSONObject toJSONObject();
	
	@Override
	public String toString() {
		return toJSONObject().toString();
	}
	
}
