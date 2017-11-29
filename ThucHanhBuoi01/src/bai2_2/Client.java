package bai2_2;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.swing.*;

public class Client {
	private DatagramSocket clientSocket = null;
	private static String serverHostname = "localhost";
	private InetAddress IPAddress, returnIPAddress;
	private static Integer portNumber = 9876;
	private byte[] sendData;
	private byte[] receiveData;
	private DatagramPacket sendPacket, receivePacket;
	static JFrame splashFrame, frame;
	JPanel outputPanel, inputPanel;
	JTextField output, input;

	public Client() {
		initGUI();
		addListeners();
	}

	private void initGUI() {
		frame = new JFrame("ClientUDP - Hoàng Bùi Ngọc Quý - " + serverHostname + ":" + portNumber);
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
					System.out.println("Đang gửi: " + expresion);
					clientSocket = new DatagramSocket();
					IPAddress = InetAddress.getByName(serverHostname);
					sendData = new byte[1024];
					sendData = expresion.getBytes();
					sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, portNumber);
					clientSocket.send(sendPacket);
					System.out.println("Đã gửi!");
					receiveData = new byte[1024];
					receivePacket = new DatagramPacket(receiveData, receiveData.length);
					System.out.println("Đang đợi phản hồi...");
					clientSocket.setSoTimeout(10000);
					String result = "";
					try {
						clientSocket.receive(receivePacket);
						result = new String(receivePacket.getData());
						returnIPAddress = receivePacket.getAddress();
						System.out.println("Từ Server: " + returnIPAddress + ":" + receivePacket.getPort());
					} catch (SocketTimeoutException ste) {
						System.out.println("Chờ quá lâu: Gói tin đã bị mất!");
					}
					output.setText(result.trim());
					System.out.println("Kết quả: " + result.trim());
					clientSocket.close();
				} catch (UnknownHostException e) {
					JOptionPane.showMessageDialog(null, "Not find to Server.");
					e.printStackTrace();
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "Error connect in/out.");
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
				serverHostname = host.getText();
				portNumber = Integer.parseInt(port.getText());
				new Client();
			}
		});
	}

}
