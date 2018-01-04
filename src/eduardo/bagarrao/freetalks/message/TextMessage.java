package eduardo.bagarrao.freetalks.message;

import java.text.ParseException;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import eduardo.bagarrao.freetalks.util.DateParser;
import eduardo.bagarrao.freetalks.util.Encrypter;
import eduardo.bagarrao.freetalks.util.messageutil.MessageType;

/**
 * 
 * TextMessage class.
 * 
 * @author Eduardo
 *
 */
public class TextMessage extends Message {

	/**
	 * Type of the message.
	 */
	private static final MessageType TYPE = MessageType.TEXT_MESSAGE;

	/**
	 * Text Message constructor.
	 * @param sender
	 * @param message
	 * @param date
	 * @throws Exception
	 */
	public TextMessage(String sender, String message, Date date) throws Exception {
		super(sender, message, date, TYPE);
		setPayload(Encrypter.encrypt(toJSONObject().toString(), "ssshhhhhhhhhhh!!!!").getBytes());
	}

	/**
	 * Constructor that receives a JSONObject.
	 * @param obj
	 * @throws Exception
	 */
	public TextMessage(JSONObject obj) throws Exception {
		super(obj, TYPE);
		if (obj.has(KEY_SENDER) && obj.has(KEY_MESSAGE) && obj.has(KEY_DATE)) {
			setPayload((Encrypter.encrypt(obj.toString(), "ssshhhhhhhhhhh!!!!").getBytes()));
			setSender(obj.getString(KEY_SENDER));
			setMessage(obj.getString(KEY_MESSAGE));
			setDate(DateParser.parseDate(obj.getString(KEY_DATE)));
		} else
			throw new IllegalArgumentException("[" + KEY_SENDER + "],[" + KEY_DATE + "] and [" + KEY_MESSAGE
					+ "] keys are inexistent in this JSONObject");
	}

	@Override
	public JSONObject toJSONObject() {
		JSONObject obj = new JSONObject();
		obj.put(KEY_SENDER, getSender());
		obj.put(KEY_MESSAGE, getMessage());
		obj.put(KEY_TYPE, getType());
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

	@Override
	public String toString() {
		return toJSONObject().toString();
	}
}
