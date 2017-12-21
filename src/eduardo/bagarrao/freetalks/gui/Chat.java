package eduardo.bagarrao.freetalks.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.border.Border;

import eduardo.bagarrao.freetalks.engine.ConnectionManager;
import eduardo.bagarrao.freetalks.message.TextMessage;
import eduardo.bagarrao.freetalks.util.DateParser;

/**
 * 
 * Chat UI where user send and receive messages.
 * 
 * @author Eduardo
 *
 */
public class Chat extends JFrame {

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
	 * panel that contains both {@link #sendPanel} and {@link #writeTextArea}.
	 */
	private JPanel sendPanel;

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
	 * Chat Constructor.
	 * 
	 */
	public Chat() {
		initGUI();
		setupGUI();
		setListeners();
		awaitMessages();
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
		this.mainPanel = new JPanel(new BorderLayout());
		this.areaScrollPane = new JScrollPane(area, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);
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
		sendPanel.add(writeTextArea);
		sendPanel.add(sendButton, BorderLayout.EAST);
		mainPanel.add(area);
		mainPanel.add(sendPanel, BorderLayout.SOUTH);
		add(areaScrollPane);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainPanel.setBorder(padding);
		sendPanel.setBorder(padding);
		setContentPane(mainPanel);
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
	public void awaitMessages() {
		new Thread(() -> {
			while (true) {
				try {
					while (true) {
						Vector<TextMessage> vector = cm.getAllMessages();
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
	 * creates the {@link #sendButton} ActionListener and this JFrame WindowListener
	 * 
	 */
	public void setListeners() {
		sendButton.addActionListener(e -> {
			cm.publishMessage(writeTextArea.getText());
			writeTextArea.setText("");
		});

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				cm.disconnect();
			}
		});
	}
}
