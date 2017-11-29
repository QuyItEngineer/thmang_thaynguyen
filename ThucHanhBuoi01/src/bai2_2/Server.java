package bai2_2;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Server implements Runnable {
	private static DatagramSocket serverSocket;
	private DatagramPacket receivePacket, sendPacket;
	private InetAddress IPAddress;
	private static Integer portNumber = 9876;
	private byte[] receiveData;
	private byte[] sendData;
	static ScriptEngineManager sem;
	static ScriptEngine engine;
	static boolean isRunning = false;
	static JLabel activeThread;
	static JButton switchBtn;
	static JTextField portTf;
	static JLabel portLabel;

	public Server() {
		sem = new ScriptEngineManager();
		engine = sem.getEngineByName("JavaScript");
	}

	public static void main(String[] args) throws IOException {
		JFrame frame = new JFrame("ServerUDP - Hoàng Bùi Ngọc Quý");
		frame.setBounds(200, 200, 400, 60);
		frame.setResizable(false);
		frame.setLayout(new GridLayout(1, 4));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		portLabel = new JLabel("port");
		portTf = new JTextField("6969");
		activeThread = new JLabel("0");
		switchBtn = new JButton();
		switchBtn.setText("Start");
		frame.add(portLabel);
		frame.add(portTf);
		frame.add(activeThread);
		frame.add(switchBtn);
		frame.setVisible(true);
		Thread thread = new Thread(new Server());
		switchBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (isRunning) {
					isRunning = false;
					thread.interrupt();
					serverSocket.close();
					JOptionPane.showMessageDialog(null, "Closed Server");
				} else {
					portNumber = Integer.parseInt(portTf.getText());
					try {
						serverSocket = new DatagramSocket(portNumber);
						JOptionPane.showMessageDialog(null, "Started Server");
					} catch (IOException e2) {
						JOptionPane.showMessageDialog(null, "Error start to Server");
					}
					isRunning = true;
					thread.start();
				}
				portTf.setEditable(true);
				switchBtn.setText("Start");
				activeThread.setText("Luồng: " + Thread.activeCount());
			}
		});
	}

	@Override
	public void run() {
		while (isRunning) {
			switchBtn.setText("Stop");
			portTf.setEditable(false);
			activeThread.setText("Luồng: " + Thread.activeCount());
			String input, output;
			try {
				receiveData = new byte[1024];
				receivePacket = new DatagramPacket(receiveData, receiveData.length);
				serverSocket.receive(receivePacket);
				IPAddress = receivePacket.getAddress();
				portNumber = receivePacket.getPort();
				input = new String(receivePacket.getData()).trim();
				System.out.print("\nServer nhận được chuỗi: " + input);
				output = String.valueOf(engine.eval(input));
				System.out.print("\n=======================> " + output);
				sendData = new byte[1024];
				sendData = output.getBytes();
				sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, portNumber);
				serverSocket.send(sendPacket);
			} catch (ScriptException e) {
				output = "Lỗi cú pháp";
				try {
					sendData = new byte[1024];
					sendData = output.getBytes();
					sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, portNumber);
					serverSocket.send(sendPacket);
				} catch (IOException e1) {
				}
				System.out.print("\n=======================> " + output);
				continue;
			} catch (IOException e) {
				output = "Lỗi kết nối";
				try {
					sendData = new byte[1024];
					sendData = output.getBytes();
					sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, portNumber);
					serverSocket.send(sendPacket);
				} catch (IOException e1) {
				}
				System.out.print("\n=======================> " + output);
				continue;
			}
		}
	}

}
