package bai1_2;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Client {
	static String serverAddress;
	static int serverPort;
	static Socket socket;
	static DataInputStream inStream;
	static DataOutputStream outStream;
	static JFrame splashFrame, frame;

	JPanel outputPanel, inputPanel;
	JTextField output, input;

	public Client() {
		initGUI();
		addListeners();
	}

	private void initGUI() {
		frame = new JFrame("Client - Hoàng Bùi Ngọc Quý - " + serverAddress + ":" + serverPort);
		frame.setLayout(new GridLayout(2, 1));
		frame.setBounds(200, 200, 500, 200);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		inputPanel = new JPanel();
		inputPanel.setLayout(new GridLayout());
		outputPanel = new JPanel();
		outputPanel.setLayout(new GridLayout());
		input = new JTextField();
		input.setFont(new Font("Consolas", Font.BOLD, 30));
		input.setHorizontalAlignment(JTextField.RIGHT);
		output = new JTextField();
		output.setFont(new Font("Consolas", Font.BOLD, 30));
		output.setHorizontalAlignment(JTextField.RIGHT);
		output.setEditable(false);
		inputPanel.add(input);
		outputPanel.add(output);
		frame.add(output);
		frame.add(input);
		frame.setVisible(true);
	}

	private void addListeners() {
		input.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String expresion = input.getText();
				try {
					socket = new Socket(serverAddress, serverPort);
					socket.setSoTimeout(1000);
					inStream = new DataInputStream(socket.getInputStream());
					outStream = new DataOutputStream(socket.getOutputStream());
					outStream.writeUTF(expresion);
					String result = inStream.readUTF();
					output.setText(result);
					inStream.close();
					outStream.close();
					socket.close();
				} catch (UnknownHostException e) {
					JOptionPane.showMessageDialog(null, "Not Find to Server.");
					e.printStackTrace();
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "Lỗi kết nối vào ra khi truyền dữ liệu.");
					e.printStackTrace();
				}
			}
		});
		frame.addWindowListener(new WindowListener() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				splashFrame.setVisible(false);
			}

			@Override
			public void windowIconified(WindowEvent arg0) {
			}

			@Override
			public void windowDeiconified(WindowEvent arg0) {
			}

			@Override
			public void windowDeactivated(WindowEvent arg0) {
			}

			@Override
			public void windowClosing(WindowEvent arg0) {
			}

			@Override
			public void windowClosed(WindowEvent arg0) {
				splashFrame.setVisible(true);
				frame.setVisible(false);
			}

			@Override
			public void windowActivated(WindowEvent arg0) {
			}
		});
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		splashFrame = new JFrame("Input info:");
		splashFrame.setBounds(200, 200, 200, 100);
		splashFrame.setResizable(false);
		splashFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		splashFrame.setLayout(new BorderLayout());
		splashFrame.add(new JLabel("Input Address server and port"), BorderLayout.NORTH);
		JTextField host, port;
		host = new JTextField("localhost");
		port = new JTextField("6969");
		splashFrame.add(host, BorderLayout.CENTER);
		splashFrame.add(port, BorderLayout.EAST);
		JButton enterBtn = new JButton("Enter");
		splashFrame.add(enterBtn, BorderLayout.SOUTH);
		splashFrame.setVisible(true);
		enterBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				serverAddress = host.getText();
				serverPort = Integer.parseInt(port.getText());
				new Client();
			}
		});
	}

}
