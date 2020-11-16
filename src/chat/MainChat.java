package chat;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

public class MainChat extends JFrame implements ActionListener, Runnable {

	JList<String> roomInfo, roomUser, waitInfo;
	JScrollPane spRoomInfo, spRoomPerson, spWaitInfo;
	JButton btChange, btEnter, btExit;
	JPanel p;

	private Client client;

	private BufferedReader in;
	private OutputStream out;

	private String selectedRoom;

	public MainChat() {
		setTitle("WaitingRoom");

		client = new Client();
		roomInfo = new JList<String>();
		roomInfo.setBorder(new TitledBorder("������"));

		roomInfo.addMouseListener(new MouseAdapter() { // mouse event
			@Override
			public void mouseClicked(MouseEvent e) {
				String str = roomInfo.getSelectedValue();

				if (str == null) {
					return;
				}

				System.out.println("STR = " + str);
				selectedRoom = str.substring(0, str.indexOf("-"));
				sendMessage("170|" + selectedRoom);
			}
		});

		roomUser = new JList<String>();
		roomUser.setBorder(new TitledBorder("User Information"));

		waitInfo = new JList<String>();
		waitInfo.setBorder(new TitledBorder("Room Information"));

		spRoomInfo = new JScrollPane(roomInfo);
		spRoomPerson = new JScrollPane(roomUser);
		spWaitInfo = new JScrollPane(waitInfo);

		btChange = new JButton("Create");
		btEnter = new JButton("Enter");
		btExit = new JButton("Exit");

		p = new JPanel();

		spRoomInfo.setBounds(10, 10, 300, 300);
		spRoomPerson.setBounds(320, 10, 150, 300);
		spWaitInfo.setBounds(10, 320, 300, 130);

		btChange.setBounds(320, 330, 150, 30);
		btEnter.setBounds(320, 370, 150, 30);
		btExit.setBounds(320, 410, 150, 30);

		p.setLayout(null);
		p.setBackground(Color.orange);
		p.add(spRoomInfo);
		p.add(spRoomPerson);
		p.add(spWaitInfo);
		p.add(btChange);
		p.add(btEnter);
		p.add(btExit);

		add(p);
		setBounds(300, 200, 500, 500);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		connect();// server Connect

		new Thread(this).start();// waiting for server message

		sendMessage("100|");// alarm
		String id = Sign.SignInForm.dto.getName();
		// id = JOptionPane.showInputDialog(this, "Title:");
		sendMessage("150|" + id);
		eventListener();

	}

	private void eventListener() { // event
		// waiting room
		btChange.addActionListener(this);
		btEnter.addActionListener(this);
		btExit.addActionListener(this);

		// client room
		client.tfSend.addActionListener(this);
		client.btChange.addActionListener(this);
		client.btExit.addActionListener(this);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object ob = e.getSource();

		if (ob == btChange) {// request create room
			String title = JOptionPane.showInputDialog(this, "RoomTitle:");

			// send room title to server
			sendMessage("160|" + title);
			client.setTitle("CharRoom-[" + title + "]");
			sendMessage("175|");
			setVisible(false);
			client.setVisible(true);
		} else if (ob == btEnter) { // request enter room
			if (selectedRoom == null) {
				JOptionPane.showMessageDialog(this, "Choice room!");
				return;
			}

			sendMessage("200|" + selectedRoom);
			sendMessage("175|");

			setVisible(false);
			client.setVisible(true);
		} else if (ob == client.btExit) { // request exit room
			sendMessage("400|");
			client.setVisible(false);
			setVisible(true);
		} else if (ob == client.tfSend) {// request send message
			String msg = client.tfSend.getText();

			if (msg.length() > 0) {
				sendMessage("300|" + msg);
				client.tfSend.setText("");
			}
		} else if (ob == btExit) { // exit -> end
			dispose();
		}
	}

	public void connect() { // request server for connection
		try {
			Socket socket = new Socket("localhost", 1234);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = socket.getOutputStream();
		} catch (UnknownHostException e) {
			System.out.println("Client connection error... " + e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendMessage(String message) { // send to server
		try {
			out.write((message + "\n").getBytes());
		} catch (IOException e) {
			System.out.println("Message send error... " + e.getMessage());
		}
	}

	@Override
	public void run() {
		try {
			while (true) {
				String message = in.readLine();// message -> server message
				String msgs[] = message.split("\\|");
				String protocol = msgs[0];

				switch (protocol) {
				case "300":
					client.textArea.append(msgs[1] + "\n");
					client.textArea.setCaretPosition(client.textArea.getText().length());
					break;

				case "160": // create room
					if (msgs.length > 1) {
						String roomNames[] = msgs[1].split(",");
						roomInfo.setListData(roomNames);
					}
					break;

				case "170": // in roby user info
					String roomInwons[] = msgs[1].split(",");
					roomUser.setListData(roomInwons);
					break;

				case "175": // in room user info
					String myRoomInwons[] = msgs[1].split(",");
					client.liPerson.setListData(myRoomInwons);
					break;

				case "180": // roby user info
					String waitNames[] = msgs[1].split(",");
					waitInfo.setListData(waitNames);
					break;

				case "200": // enter room
					client.textArea.append("[" + msgs[1] + "] entered.\n");
					client.textArea.setCaretPosition(client.textArea.getText().length());
					break;

				case "400": // room exit
					client.textArea.append("[" + msgs[1] + "] exit.\n");
					client.textArea.setCaretPosition(client.textArea.getText().length());
					break;

				case "202": // get room title
					client.setTitle("CharRoom-[" + msgs[1] + "]");
					break;
				}
			}
		} catch (IOException e) {
			System.out.println("Run error... " + e.getMessage());
		}
	}
}