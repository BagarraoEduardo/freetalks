package eduardo.bagarrao.freetalks.message;

import org.json.JSONObject;

import eduardo.bagarrao.freetalks.util.messageutil.MessageType;

public class TextMessage extends Message{

	private static final MessageType TYPE = MessageType.TEXT_MESSAGE;
	
	protected TextMessage(JSONObject obj) throws Exception {
		super(obj);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public JSONObject toJSONObject() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}
