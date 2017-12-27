package eduardo.bagarrao.freetalks.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.border.Border;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import eduardo.bagarrao.freetalks.engine.ConnectionManager;
import eduardo.bagarrao.freetalks.message.ImageMessage;
import eduardo.bagarrao.freetalks.message.TextMessage;
import eduardo.bagarrao.freetalks.util.DateParser;

/**
 * 
 * Chat UI where user send and receive messages.
 * 
 * @author Eduardo
 *
 */
public class Chat extends JFrame implements ActionListener {

	/**
	 * SerialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * {@link #cm} ConnectionManager instance.
	 */
	private ConnectionManager cm = ConnectionManager.getInstance();

	/**
	 * padding for JFrame.
	 */
	private Border padding;

	/**
	 * Area that show's both sent messages and received messages.
	 */
	private JTextPane area;

	/**
	 * Area where you write your messages to send.
	 */
	private JTextArea writeTextArea;

	/**
	 * Button that sends the message when clicked.
	 */
	private JButton sendButton;

	/**
	 * Button that adds a BufferedImage to the message.
	 */
	private JButton addImageButton;

	/**
	 * panel that contains both {@link #sendPanel} and {@link #writeTextArea}.
	 */
	private JPanel sendPanel;

	/**
	 * Opens the filechooser dialog in order to add a image to message.
	 */
	private JFileChooser fileChooser;

	/**
	 * 
	 * Main panel, needed to use the {@link #areaScrollPane}.
	 * 
	 */
	private JPanel mainPanel;

	/**
	 *
	 * allows {@link #area} to have a scrollbar.
	 * 
	 */
	private JScrollPane areaScrollPane;

	/**
	 * 
	 */
	private BufferedImage image;
	
	/**
	 * 
	 * Chat Constructor.
	 * 
	 */
	public Chat() {
		initGUI();
		setupGUI();
		awaitTextMessages();
		awaitImageMessages();
	}

	/**
	 * 
	 * Inits all the JFrame content.
	 * 
	 */
	private void initGUI() {
		this.area = new JTextPane();
		this.writeTextArea = new JTextArea();
		this.sendPanel = new JPanel(new BorderLayout());
		this.sendButton = new JButton("Send!");
		this.addImageButton = new JButton("Include Image");
		this.mainPanel = new JPanel(new BorderLayout());
		this.areaScrollPane = new JScrollPane(area, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.fileChooser = new JFileChooser();
		this.padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);
		this.image = null;
	}

	/**
	 * 
	 * Sets all the JFrame content and also the own JFrame.
	 * 
	 */
	private void setupGUI() {
		setTitle("[" + cm.getClientId() + "]" + Login.APP_NAME + " " + Login.PHASE + " v" + Login.VERSION);
		setSize(new Dimension(600, 600));
		area.setEditable(false);
		addImageButton.addActionListener(this);
		sendButton.addActionListener(this);
		sendPanel.add(writeTextArea);
		JPanel sendPanel2 = new JPanel(new GridLayout(1, 2));
		sendPanel2.add(addImageButton);
		sendPanel2.add(sendButton);
		sendPanel.add(sendPanel2, BorderLayout.EAST);
		mainPanel.add(area);
		mainPanel.add(sendPanel, BorderLayout.SOUTH);
		add(areaScrollPane);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainPanel.setBorder(padding);
		sendPanel.setBorder(padding);
		setContentPane(mainPanel);
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		FileFilter filter = new FileNameExtensionFilter("Image files", "png");
		fileChooser.setFileFilter(filter);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				cm.disconnect();
			}
		});
	}

	/**
	 * 
	 * sets the JFrame visibility flag to true.
	 * 
	 */
	public void init() {
		setVisible(true);
	}

	/**
	 * 
	 * Creates a thread that at real time checks if there is received messages and
	 * writes them in the {@link #area}.
	 * 
	 */
	public void awaitTextMessages() {
		new Thread(() -> {
			while (true) {
				try {
					while (true) {
						Vector<TextMessage> vector = cm.getAllTexMessages();
						for (TextMessage msg : vector) {
							area.setText(area.getText() + "["
									+ ((msg.getSender().equals(cm.getClientId()) ? "You" : msg.getSender())) + "] "
									+ " [" + DateParser.parseString(msg.getDate()) + "] --> "
									+ msg.getMessage().toString() + "\n");
						}
						Thread.sleep(1000);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	/**
	 * 
	 * Creates a thread that at real time checks if there is received messages and
	 * writes them in the {@link #area}.
	 * 
	 */
	public void awaitImageMessages() {
		new Thread(() -> {
			while (true) {
				try {
					while (true) {
						Vector<ImageMessage> vector = cm.getAllImageMessages();
						for (ImageMessage msg : vector) {

							//TODO:
						}
						Thread.sleep(1000);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				} 
			}
		}).start();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == sendButton) {
			if(image != null)
				cm.publishMessage(writeTextArea.getText(),image); 
			else
				cm.publishMessage(writeTextArea.getText());
			writeTextArea.setText("");
		} else if (e.getSource() == addImageButton) {
			System.out.println("Estou aqui");
			int returnValue = fileChooser.showOpenDialog(this);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				//TODO: filechooser select file extensions
				File file = fileChooser.getSelectedFile();
				try {
					this.image = ImageIO.read(file);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			} else {
				// TODO: return file canceled
			} 
		}
	}
}
