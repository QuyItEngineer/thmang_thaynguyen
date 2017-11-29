package bai1_2;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Server implements Runnable {
	static Socket socket;
	static ServerSocket server;
	static int port;
	static String input, output;
	static DataOutputStream dos;
	static DataInputStream dis;
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
		JFrame frame = new JFrame("Server - Hoàng Bùi Ngọc Quý");
		frame.setBounds(200, 200, 400, 60);
		frame.setResizable(false);
		frame.setLayout(new GridLayout(1, 4));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		portLabel = new JLabel("Cổng");
		portTf = new JTextField();
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
					try {
						server.close();
						JOptionPane.showMessageDialog(null, "Closed Server");
					} catch (IOException e) {
						JOptionPane.showMessageDialog(null, "Error stop Server");
					}
				} else {
					port = Integer.parseInt(portTf.getText());
					try {
						server = new ServerSocket(port);
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
			try {
				socket = server.accept();
				dos = new DataOutputStream(socket.getOutputStream());
				dis = new DataInputStream(socket.getInputStream());
				input = dis.readUTF();
				System.out.print("\nServer nhận được chuỗi: " + input);
				output = String.valueOf(engine.eval(input));
				System.out.print("\n=======================> " + output);
				dos.writeUTF(output);
				dos.close();
				dis.close();
				socket.close();
			} catch (ScriptException e) {
				output = "Error Syntax.";
				try {
					dos.writeUTF(output);
				} catch (IOException e1) {
				}
				System.out.print("\n=======================> " + output);
				continue;
			} catch (IOException e) {
				output = "Eror connect";
				try {
					dos.writeUTF(output);
				} catch (IOException e1) {
				}
				System.out.print("\n=======================> " + output);
				continue;
			}
		}
	}

}
