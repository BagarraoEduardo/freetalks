package eduardo.bagarrao.freetalks.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.eclipse.paho.client.mqttv3.MqttMessage;

import eduardo.bagarrao.freetalks.engine.ConnectionManager;

public class Chat extends JFrame {

	private static final long serialVersionUID = 1L;

	private ConnectionManager cm = ConnectionManager.getInstance();

	private InputChecker checker;
	private JTextArea area;
	private JTextArea writeTextArea;
	private JButton sendButton;
	private JPanel sendPanel;

	public Chat() {
		setTitle("[" + cm.getClientId() + "]" + Login.APP_NAME + " " + Login.PHASE + " v" + Login.VERSION);
		this.checker = new InputChecker();
		this.area = new JTextArea();
		this.writeTextArea = new JTextArea();
		this.sendPanel = new JPanel(new BorderLayout());
		this.sendButton = new JButton("Send!");
		
		setLayout(new BorderLayout());
		setSize(new Dimension(600, 600));
	
		sendPanel.add(writeTextArea);
		sendPanel.add(sendButton, BorderLayout.EAST);
		add(area);
		add(sendPanel,BorderLayout.SOUTH);
		
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		sendButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				cm.publishMessage(writeTextArea.getText());
				writeTextArea.setText("");
			}
		});
		
		 addWindowListener(new WindowAdapter()
	        {
	            @Override
	            public void windowClosing(WindowEvent e)
	            {
	                cm.disconnect();
	            }
	        });
		checker.start();
		
	}

	private class InputChecker extends Thread{
		@Override
		public void run() {
			super.run();
			while(true) {
				try {
					while(true) {
						Vector<MqttMessage> vector = cm.getAllMessages();
						for(MqttMessage message : vector){
							area.setText(area.getText() + message.toString() + "\n");
						}
						Thread.sleep(1000);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void init() {
		setVisible(true);
	}
}
	