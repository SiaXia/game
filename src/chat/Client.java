package chat;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Client extends JFrame { // client UI

	JTextField tfSend;
	JLabel laMessage;
	
	JTextArea textArea;
	JScrollPane spTextArea, spList;
	
	JList<String> liPerson;
	JButton btChange, btExit;
	
	JPanel p;
	
	public Client() {
		setTitle("ChatRoom");
		tfSend = new JTextField(15);
		laMessage = new JLabel("Message");
		
		textArea = new JTextArea();
		textArea.setLineWrap(true); // auto CR, LF
		liPerson = new JList<String>();
		
		spTextArea = new JScrollPane(textArea);
		spList = new JScrollPane(liPerson);
		
		btChange = new JButton("ChangeTitle");
		btExit = new JButton("Exit");

		p = new JPanel();
		spTextArea.setBounds(10, 300, 380, 100);
		laMessage.setBounds(10, 410, 60, 30);
		tfSend.setBounds(70, 410, 320, 30);
		spList.setBounds(400, 10, 120, 350);
		btChange.setBounds(400, 370, 120, 30);
		btExit.setBounds(400, 410, 120, 30);
		p.setLayout(null);
		p.setBackground(Color.PINK);
		p.add(spTextArea);
		p.add(laMessage);
		p.add(tfSend);
		p.add(spList);
		p.add(btChange);
		p.add(btExit);

		add(p);
		setBounds(300, 200, 550, 500);
		// setVisible(true);
		tfSend.requestFocus();	 
	}
}
