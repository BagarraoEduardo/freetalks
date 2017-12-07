package eduardo.bagarrao.freetalks.gui.login;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.eclipse.paho.client.mqttv3.MqttException;

import eduardo.bagarrao.freetalks.engine.ConnectionManager;

public class Login extends JFrame{

	private static final long serialVersionUID = 1L;

	private static final String APP_NAME = "FreeTalks";
	private static final String PHASE = "Alpha";
	private static final String VERSION = "0.0.1";
	
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
		setLocationRelativeTo(null);
		
		revalidate();
		repaint();
		pack();
		
		loginButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(isValidUsername(usernameTextField.getText())) {
						cm.setClientId(usernameTextField.getText());
						cm.connect();
					//TODO: closes Login gui and opens chat lobby
				}
			}
		});
	}

	public void go() {
		setVisible(true);
	}
	
	private boolean isValidUsername(String username) {
		return (!username.equals(""));
	}
}
