
package eduardo.bagarrao.freetalks.gui;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

public class Login extends JFrame {

	private static final long serialVersionUID = 1L;

	public static final String APP_NAME = "FreeTalks";
	public static final String PHASE = "Alpha";
	public static final String VERSION = "0.0.3";

	private ConnectionManager cm = ConnectionManager.getInstance();

	private JTextField usernameTextField;
	private JLabel usernameLabel;
	private JPanel usernamePanel;
	private JPanel loginPanel;
	private JButton loginButton;

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

	public void go() {
		setVisible(true);
	}

	private void close() {
		setVisible(false);
	}

	private boolean isValidUsername(String username) {
		return (!username.equals(""));
	}
}
