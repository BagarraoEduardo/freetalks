
package eduardo.bagarrao.freetalks.gui;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.eclipse.paho.client.mqttv3.MqttException;

import eduardo.bagarrao.freetalks.engine.ConnectionManager;

/**
 * 
 * class that is used to login on the MQTT server.
 * 
 * @author Eduardo
 *
 */
public class Login extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * name of the app.
	 */
	public static final String APP_NAME = "FreeTalks";
	
	/**
	 * Phase of the app.
	 */
	public static final String PHASE = "Alpha";
	
	/**
	 * Version of the app.
	 */
	public static final String VERSION = "0.0.3";

	/**
	 * Object that handles the connection.
	 */
	private ConnectionManager cm = ConnectionManager.getInstance();

	/**
	 * JTextfield used for insert the username.
	 */
	private JTextField usernameTextField;
	
	/**
	 * label that is at left of the username JTextfield.
	 */
	private JLabel usernameLabel;
	
	/**
	 * JPanel that contains {@link #usernameLabel} and {@link #usernameTextField}.
	 */
	private JPanel usernamePanel;
	
	/**
	 * JPanel that contains {@link #loginButton}.
	 */
	private JPanel loginPanel;
	
	/**
	 * button for login action.
	 */
	private JButton loginButton;

	/**
	 * Login construtor.
	 */
	public Login() {
		
		setTitle(APP_NAME + " " + PHASE + " v" + VERSION);

		this.usernameLabel = new JLabel("Username:");
		this.usernameTextField = new JTextField(20);
		this.usernamePanel = new JPanel(new GridLayout(1, 2));
		this.loginPanel = new JPanel(new FlowLayout());
		this.loginButton = new JButton("login");

		setLayout(new GridLayout(2, 1));
		usernamePanel.add(usernameLabel);
		usernamePanel.add(usernameTextField);
		loginPanel.add(loginButton);
		add(usernamePanel);
		add(loginPanel);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		revalidate();
		repaint();
		pack();
		setLocationRelativeTo(null);
		
		addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent arg0) {
				if(arg0.getKeyCode() == KeyEvent.VK_ENTER)
					loginButton.doClick();
			}
			
			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		loginButton.addActionListener(e -> {
			boolean isConnected = false;
			while (!isConnected) {
				if (isValidUsername(usernameTextField.getText())) {
					cm.setClientId(usernameTextField.getText());
					try {
						cm.connect();
						isConnected = true;
					} catch (MqttException e2) {
						e2.printStackTrace();
						JOptionPane.showMessageDialog(this,
								"Check your connection! If you are connected, please sign in with another username");
						usernameTextField.setText("");
						loginButton.setEnabled(true);
					}

				}
			}
			new Chat().init();
			close();
		});
		getRootPane().setDefaultButton(loginButton);
	}

	/**
	 * shows the open frame.
	 */
	public void go() {
		setVisible(true);
	}

	/**
	 * closes the login frame.
	 */
	private void close() {
		setVisible(false);
	}

	/**
	 * Checks if the username is valid to login.
	 * @param username username to insert.
	 * @return validation of the username.
	 */
	private boolean isValidUsername(String username) {
		return (!username.equals(""));
	}
}
