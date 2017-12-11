package eduardo.bagarrao.freetalks.message;

import java.text.ParseException;
import java.util.Date;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONException;
import org.json.JSONObject;

import eduardo.bagarrao.freetalks.util.DateParser;

/**
 * 
 * @author Eduardo
 *
 */
public class Message extends MqttMessage{

	private static final String KEY_SENDER = "sender";
	private static final String KEY_MESSAGE = "message";
	private static final String KEY_DATE = "date";
	
	private String sender;
	private String message;
	private Date date;
	
	/**
	 * 
	 * @param sender
	 * @param message
	 * @throws Exception 
	 */
	public Message(String sender, String message, Date date) throws Exception{
		this.sender = sender;
		this.message = message;
		this.date = date;
		setPayload(Encrypter.encrypt(toJSONObject().toString(),"ssshhhhhhhhhhh!!!!").getBytes());
	}
	
	/**
	 * 
	 * @param obj
	 * @throws Exception 
	 */
	public Message(JSONObject obj) throws Exception{
		if(obj.has(KEY_SENDER) && obj.has(KEY_MESSAGE) && obj.has(KEY_DATE)) {
			setPayload((Encrypter.encrypt(obj.toString(),"ssshhhhhhhhhhh!!!!").getBytes()));
			this.sender = obj.get(KEY_SENDER).toString();
			this.message = obj.get(KEY_MESSAGE).toString();
			this.date = DateParser.parseDate(obj.get(KEY_DATE).toString());
		}else
			throw new IllegalArgumentException("[" + KEY_SENDER + "],[" + KEY_DATE + 
					"] and [" + KEY_MESSAGE + "] keys are inexistent in this JSONObject");
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
	
	/**
	 * 
	 * @return
	 */
	public JSONObject toJSONObject() {
		JSONObject obj = new JSONObject();
		obj.put(KEY_SENDER, this.sender);
		obj.put(KEY_MESSAGE, this.message);try {
			obj.put(KEY_DATE, DateParser.parseString(this.date));
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			if(obj.has(KEY_DATE))
				obj.remove(KEY_DATE);
			obj.put(KEY_DATE, "00-00-0000");
			e.printStackTrace();
		}
		return obj;
	}

	@Override
	public String toString() {
		return toJSONObject().toString();
	}
	
}
