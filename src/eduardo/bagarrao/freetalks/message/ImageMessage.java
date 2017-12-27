package eduardo.bagarrao.freetalks.message;

import java.awt.image.BufferedImage;
import java.text.ParseException;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import eduardo.bagarrao.freetalks.util.DateParser;
import eduardo.bagarrao.freetalks.util.Encrypter;
import eduardo.bagarrao.freetalks.util.ImageDecoder;
import eduardo.bagarrao.freetalks.util.messageutil.MessageType;

public class ImageMessage extends Message {

	/**
	 * 
	 */
	private static final MessageType TYPE = MessageType.IMAGE_MESSAGE;

	/**
	 * 
	 */
	public static final String KEY_IMAGE = "image";

	/**
	 * 
	 */
	private BufferedImage image;

	/**
	 * 
	 * @param sender
	 * @param message
	 * @param image
	 * @param date
	 * @throws Exception
	 */
	public ImageMessage(String sender, String message, BufferedImage image, Date date) throws Exception {
		super(sender, message, date, TYPE);
		this.image = image;
		setPayload(Encrypter.encrypt(toJSONObject().toString(), "ssshhhhhhhhhhh!!!!").getBytes()); // TODO:
	}

	/**
	 * 
	 * @param obj
	 * @throws Exception
	 */
	public ImageMessage(JSONObject obj) throws Exception {
		super(TYPE);
		if (obj.has(KEY_SENDER) && obj.has(KEY_MESSAGE) && obj.has(KEY_DATE) && obj.has(KEY_IMAGE)) {
			setPayload((Encrypter.encrypt(obj.toString(), "ssshhhhhhhhhhh!!!!").getBytes()));
			setSender(obj.getString(KEY_SENDER));
			setMessage(obj.getString(KEY_MESSAGE));
			setDate(DateParser.parseDate(obj.getString(KEY_DATE)));
			this.image = ImageDecoder.parseBufferedImage(obj.getJSONArray(KEY_IMAGE));
		} else
			throw new IllegalArgumentException("[" + KEY_SENDER + "],[" + KEY_DATE + "],[" + KEY_IMAGE + "] and ["
					+ KEY_MESSAGE + "] keys are inexistent in this JSONObject");
	}

	@Override
	public JSONObject toJSONObject() {
		JSONObject obj = new JSONObject();
		obj.put(KEY_SENDER, getSender());
		obj.put(KEY_MESSAGE, getMessage());
		obj.put(KEY_TYPE, getType());
		obj.put(KEY_IMAGE, ImageDecoder.parseJSONArray(image));
		try {
			obj.put(KEY_DATE, DateParser.parseString(getDate()));
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			if (obj.has(KEY_DATE))
				obj.remove(KEY_DATE);
			obj.put(KEY_DATE, "00-00-0000 00:00");
			e.printStackTrace();
		}
		return obj;
	}
}
