package eduardo.bagarrao.freetalks.gui;

import java.awt.BorderLayout;
import java.awt.Container;
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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.border.Border;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import eduardo.bagarrao.freetalks.engine.ConnectionManager;
import eduardo.bagarrao.freetalks.message.ImageMessage;
import eduardo.bagarrao.freetalks.message.TextMessage;
import eduardo.bagarrao.freetalks.util.DateParser;
import eduardo.bagarrao.freetalks.util.ImageDecoder;

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
	 * panel that contains both {@link #sendPanel2} and {@link #writeTextArea}.
	 */
	private JPanel sendPanel;

	/**
	 * Opens the filechooser dialog in order to add a image to message.
	 */
	private JFileChooser fileChooser;

	/**
	 * Main panel, needed to use the {@link #areaScrollPane}.
	 */
	private JPanel mainPanel;

	/**
	 * Contains {@link #addImageButton} and {@link #sendButton}.
	 */
	private JPanel sendPanel2;
	
	/**
	 * allows {@link #area} to have a scrollbar.
	 */
	private JScrollPane areaScrollPane;

	/**
	 * Image opened to send.
	 */
	private BufferedImage image;

	/**
	 * Document where the images and text will be added.
	 */
	private StyledDocument document;

	/**
	 * Chat Constructor.
	 */
	public Chat() {
		initGUI();
		setupGUI();
		awaitTextMessages();
		awaitImageMessages();
	}

	/**
	 * Inits all the JFrame content.
	 */
	private void initGUI() {
		this.area = new JTextPane();
		this.document = (StyledDocument) area.getDocument();
		this.writeTextArea = new JTextArea();
		this.sendPanel = new JPanel(new BorderLayout());
		this.sendPanel2 = new JPanel(new GridLayout(1, 2));
		this.sendButton = new JButton("Send!");
		this.addImageButton = new JButton("Include Image");
		this.mainPanel = new JPanel(new BorderLayout());
		this.areaScrollPane = new JScrollPane(area, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		this.fileChooser = new JFileChooser();
		this.padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);
		this.image = null;
	}

	/**
	 * Sets all the JFrame content and also the own JFrame.
	 */
	private void setupGUI() {
		setTitle("[" + cm.getClientId() + "]" + Login.APP_NAME + " " + Login.PHASE + " v" + Login.VERSION);
		setSize(new Dimension(600, 600));
		area.setEditable(false);
		addImageButton.addActionListener(this);
		sendButton.addActionListener(this);
		sendPanel.add(writeTextArea);
		sendPanel2.add(addImageButton);
		sendPanel2.add(sendButton);
		sendPanel.add(sendPanel2, BorderLayout.EAST);
		mainPanel.add(area);
		mainPanel.add(sendPanel, BorderLayout.SOUTH);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainPanel.setBorder(padding);
		sendPanel.setBorder(padding);
		setContentPane(mainPanel);
		areaScrollPane.setPreferredSize(new Dimension(600, 600));
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		FileFilter filter = new FileNameExtensionFilter("Image files", "png");
		fileChooser.setFileFilter(filter);
		revalidate();
		repaint();
	}

	/**
	 * sets the JFrame visibility flag to true.
	 */
	public void init() {
		setVisible(true);
	}

	/**
	 * Creates a thread that at real time checks if there is received  text messages and
	 * writes them in the {@link #area}.
	 */
	public void awaitTextMessages() {
		new Thread(() -> {
			while (true) {
				try {
					while (true) {
						Vector<TextMessage> vector = cm.getAllTexMessages();
						for (TextMessage msg : vector) {
							writeReceivedMessage(msg);
						}
						Thread.sleep(1000);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ParseException e) {
					e.printStackTrace();
				} catch (BadLocationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}

	/**
	 * Creates a thread that at real time checks if there is received image messages and
	 * writes them in the {@link #area}.
	 */
	public void awaitImageMessages() {
		new Thread(() -> {
			while (true) {
				try {
					while (true) {
						Vector<ImageMessage> vector = cm.getAllImageMessages();
						for (ImageMessage msg : vector) {
							writeReceivedMessage(msg);
						}
						Thread.sleep(1000);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ParseException e) {
					e.printStackTrace();
				} catch (BadLocationException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	/**
	 * Writes a received image message in the {@link #document}.
	 * @param msg
	 * @throws ParseException
	 * @throws BadLocationException
	 */
	public void writeReceivedMessage(ImageMessage msg) throws ParseException, BadLocationException {
		synchronized (area) {
			String text = "[" + ((msg.getSender().equals(cm.getClientId()) ? "You" : msg.getSender())) + "] " + " ["
					+ DateParser.parseString(msg.getDate()) + "] --> " + msg.getMessage().toString() + "\n";
			BufferedImage image = msg.getImage();
			Style style = document.addStyle("StyleName", null);
			StyleConstants.setIcon(style, new ImageIcon(image));
			document.insertString(document.getLength(), "ignored length ", style);
			document.insertString(document.getLength(), "\n" + text, null);
			revalidate();
			repaint();
		}
	}

	/**
	 * Writes a received text message in the {@link #document}.
	 * @param msg message to write
	 * @throws ParseException
	 * @throws BadLocationException
	 */
	public void writeReceivedMessage(TextMessage msg) throws ParseException, BadLocationException {
		synchronized (area) {
			String text = "[" + ((msg.getSender().equals(cm.getClientId()) ? "You" : msg.getSender())) + "] " + " ["
					+ DateParser.parseString(msg.getDate()) + "] --> " + msg.getMessage().toString() + "\n";
			document.insertString(document.getLength(), text, null);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == sendButton) {
			if (image != null) {
				cm.publishMessage(writeTextArea.getText(), image);
				image = null;
			} else
				cm.publishMessage(writeTextArea.getText());
			writeTextArea.setText("");
		} else if (e.getSource() == addImageButton) {
			System.out.println("Estou aqui");
			int returnValue = fileChooser.showOpenDialog(this);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				System.out.println(file.toString());
				try {
					this.image = ImageIO.read(file);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
}
