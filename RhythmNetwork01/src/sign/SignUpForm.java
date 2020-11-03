package sign;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SignUpForm extends JFrame {
	
	DataTransferObject dto = new DataTransferObject(); // object dto
	
	private TextField memberName; // name
	private TextField memberAddress; // address
	private JPasswordField memberPassword; // password
	
	public SignUpForm() {
		
		setTitle("SignUp");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Panel p = new Panel();
		
		setLayout(new BorderLayout());
		
		Label name = new Label("name");
		Label password = new Label("password");
		Label address = new Label("address");
		
		// send button.
		Button send = new Button("send");
		send.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				Object obj = e.getSource();
				
				if((Button)obj == send) { // set name, password, address.
					dto.setName(memberName.getText());
					dto.setPassword(memberPassword.getText());
					dto.setAddress(memberAddress.getText());
				}
				
				try {
					DataAccessObject.create(dto);
					
					System.out.println("Data Insert Success...");
				} catch(Exception ex) {
					System.out.println("Data Insert Fail... " + ex.getMessage());
				}
			}
		});
		
		memberName = new TextField(20);
		memberPassword = new JPasswordField(20);
		memberAddress = new TextField(25);
		
		p.setLayout(new GridLayout(4, 2));
		p.add(name);
		p.add(memberName);
		p.add(password);
		p.add(memberPassword);
		p.add(address);
		p.add(memberAddress);
		
		add("Center", p);
		add("South", send);
		
		setSize(300, 300);
		setResizable(false);
		setVisible(true);
	}
}
