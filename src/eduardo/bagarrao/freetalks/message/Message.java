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
public class Message extends MqttMessage{

	private static final String KEY_SENDER = "sender";
	private static final String KEY_MESSAGE = "message";
	private static final String KEY_DATE = "date";
	private static final String KEY_IMAGE = "image";
	
	private String sender;
	private String message;
	private Date date;
	private BufferedImage image;
	
	
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
		this.image = null;
		setPayload(Encrypter.encrypt(toJSONObject().toString(),"ssshhhhhhhhhhh!!!!").getBytes());
	}
	
	/**
	 * 
	 * @param sender
	 * @param message
	 * @param date
	 * @param image
	 * @throws Exception
	 */
	public Message(String sender, String message, Date date, BufferedImage image) throws Exception{
		this.sender = sender;
		this.message = message;
		this.date = date;
		this.image = image;
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
			this.sender = obj.getString(KEY_SENDER);
			this.message = obj.getString(KEY_MESSAGE);
			this.date = DateParser.parseDate(obj.getString(KEY_DATE));
			if(obj.has(KEY_IMAGE)) {
				//TODO: handle image here
			}
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
	
	public Image getImage() {
		return image;
	}
	
	public void setImage(BufferedImage image) {
		this.image = image;
	}
	
	//TODO: metodo de converter dados de uma imagem para uma string
	
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
			obj.put(KEY_DATE, "00-00-0000 00:00");
			e.printStackTrace();
		}
		return obj;
	}

	@Override
	public String toString() {
		return toJSONObject().toString();
	}
	
}
