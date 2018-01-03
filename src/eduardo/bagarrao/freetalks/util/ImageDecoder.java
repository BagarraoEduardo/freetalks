package eduardo.bagarrao.freetalks.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.json.JSONArray;
import org.json.JSONObject;

public class ImageDecoder {

	private static final int WIDTH_CODE = 0;
	private static final int HEIGHT_CODE = 1;
	private static final int DATA_CODE = 2;

	/**
	 * 
	 * @param image
	 * @return
	 */
	public static JSONArray parseJSONArray(BufferedImage image) {
		JSONArray array = new JSONArray();
		array.put(image.getWidth());
		array.put(image.getHeight());
		JSONArray dataArray = new JSONArray();
		for (int i = 0; i < image.getHeight(); i++) {
			for (int j = 0; j < image.getWidth(); j++) {
				JSONObject obj = new JSONObject();
				obj.put("xCoordinate", j);
				obj.put("yCoordinate", i);
				obj.put("rgb", image.getRGB(j, i));
				dataArray.put(obj);
			}
		}
		array.put(dataArray);
		return array;
	}

	/**
	 * 
	 * @param array
	 * @return
	 * @throws IOException
	 */
	public static BufferedImage parseBufferedImage(JSONArray array) throws IOException {
		int width = array.getInt(WIDTH_CODE);
		int height = array.getInt(HEIGHT_CODE);
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		BufferedImage newImage;
		JSONArray dataArray = (JSONArray) array.get(DATA_CODE);
		for (Object obj : dataArray) {
			int x = ((JSONObject) obj).getInt("xCoordinate");
			int y = ((JSONObject) obj).getInt("yCoordinate");
			int rgb = ((JSONObject) obj).getInt("rgb");
			image.setRGB(x, y, rgb);
		}
		String dir = "C:\\Users\\eduar\\Desktop\\";
		int iterator = 0;
		String fileName = "file";
		String extension = ".png";
		File file;
		while (true) {
			file = new File(dir + fileName + iterator + extension);
			if (file.exists()) {
				++iterator;
				continue;
			} else {
				file.createNewFile();
				break;
			}
		}
		ImageIO.write(image, "png", file);
		newImage = ImageIO.read(file);
		file.delete();
		return newImage;
	}

	public static void main(String[] args) throws IOException {
		BufferedImage image = ImageIO.read(new File("C:\\chara.png"));
		JSONArray array = parseJSONArray(image);
		BufferedImage newImage = parseBufferedImage(array);
		File file = new File("C:\\Users\\eduar\\Desktop\\newChara.png");
		if (!file.exists())
			file.createNewFile();
		ImageIO.write(newImage, "png", file);

	}
}
