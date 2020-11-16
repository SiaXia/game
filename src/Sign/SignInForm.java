package Sign;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import RhythmGame.Multiplay;

public class SignInForm extends JFrame {
	public static DataTransferObject dto = new DataTransferObject(); // object dto

	private TextField memberName; // name
	private JPasswordField memberPassword; // password

	public SignInForm() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		// 종료버튼 누를 시
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				new Multiplay();
			}
		});
		setTitle("SignIn");
		setLocationRelativeTo(null);
		Panel p = new Panel();
		setLayout(new BorderLayout());

		Label name = new Label("name");
		Label password = new Label("password");

		// login button.
		Button login = new Button("Login");
		login.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				Object obj = e.getSource();

				if ((Button) obj == login) { // set name, password.
					dto.setName(memberName.getText());
					dto.setPassword(memberPassword.getText());
				}

				try {
					switch (DataAccessObject.loginCheck(dto)) {
					case 1:
						System.out.println("Login Success...");
						dispose();
						RhythmGame.DynamicBeat.setMultiButtonNull(true);
						break;

					case 0:
						System.out.println("Password error...");
						break;

					case -1:
						System.out.println("None ID...");
						break;
					}
				} catch (Exception ex) {
					System.out.println("Login Failed... " + ex.getMessage());
				}
			}
		});

		memberName = new TextField(20);
		memberPassword = new JPasswordField(20);

		p.setLayout(new GridLayout(2, 2));
		p.add(name);
		p.add(memberName);
		p.add(password);
		p.add(memberPassword);

		add("Center", p);
		add("South", login);

		setSize(300, 300);
		setResizable(false);
		setVisible(true);
	}
}
