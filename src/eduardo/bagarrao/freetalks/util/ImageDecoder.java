package eduardo.bagarrao.freetalks.util;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class ImageDecoder {

//	private static final String KEY_LENGTH_X = "xCoordinate";
//	private static final String KEY_LENGTH_Y = "yCoordinate";
//	private static final String IMAGE_WIDTH_KEY = "width";
//	private static final String IMAGE_HEIGHT_KEY = "height";
//	private static final String IMAGE_COLOR_KEY = "height";
//	
//	public static JSONArray parseJSONArray(BufferedImage image) {
//		JSONArray array = new JSONArray();
//		JSONObject obj = new JSONObject();
//		obj.put(IMAGE_WIDTH_KEY,image.getWidth());
//		obj.put(IMAGE_HEIGHT_KEY,image.getHeight());
//		
//		List<JSONObject> list = new ArrayList<JSONObject>();
//		for(int i = 0;i < image.getWidth();i++) {
//			for(int j = 0;j < image.getHeight();j++) {
//				list.add(getPixelInfo(image,i,j));
//			}
//		}
//		array.put(list);
//		return array;
//	}
//	
//	public static BufferedImage parseBufferedImage(JSONArray array) {
//		//TODO:
//		return null;
//	}
//
//	// parte do principio que width e height estao dentro da imagem
//	private static JSONObject getPixelInfo(BufferedImage image, int x, int y) {
//		JSONObject obj = new JSONObject();
//		obj.put(KEY_LENGTH_X, x);
//		obj.put(KEY_LENGTH_X, y);
//		obj.put(IMAGE_COLOR_KEY, new Color(image.getRGB(x, y)));
//		return obj;
//	}
//
//	private JSONObject getJSONObject(JSONArray array, String key) {
//		
//		JSONObject obj = null;
//		for(Object arrayObj : array) {
//			if(arrayObj instanceof JSONObject) {
//				obj = (JSONObject) arrayObj;
//				if((obj.has(KEY_LENGTH_X) && obj.has(KEY_LENGTH_Y)))
//					obj = null;
//			}
//		}
//		return obj;
//	}
//	
//	private class Pixel {
//
//		private Color color;
//		private int x;
//		private int y;
//
//		public Pixel(int x, int y, Color color) {
//			this.x = x;
//			this.y = y;
//			this.color = color;
//		}
//
//		public Pixel(int x, int y, int rgb) {
//			this.x = x;
//			this.y = y;
//			this.color = new Color(rgb);
//		}
//	}
//	
//
//	public static void main(String[] args) {
//		// TODO: testar conversao para jsonobject e conversao para imagem aqui
//	}
}
