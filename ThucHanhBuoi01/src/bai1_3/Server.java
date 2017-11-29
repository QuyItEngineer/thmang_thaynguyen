package bai1_3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.JOptionPane;

public class Server {
	ServerSocket serverSocket;

	public Server(int port) {
		try {
			System.out.println(port);
			serverSocket = new ServerSocket(port);
			while (true) {
				Socket clientSocket = serverSocket.accept();
				if (clientSocket.isConnected()) {
					ClientConnection connectionThread = new ClientConnection(serverSocket, clientSocket);
					connectionThread.start();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		int port = Integer.parseInt(JOptionPane.showInputDialog("Nhập cổng: ", "12345"));
		new Server(port);
	}

	static class ClientConnection extends Thread {
		private Socket clientSocket;
		private ServerSocket serverSocket;
		private int connectionCount;
		private DataInputStream inStream;
		private DataOutputStream outStream;
		private static Hashtable<Socket, ClientConnection> connectionList = new Hashtable<Socket, ClientConnection>();

		public ClientConnection(ServerSocket server, Socket client) {
			this.connectionCount = connectionList.size();
			connectionList.put(client, this);
			this.serverSocket = server;
			this.clientSocket = client;
		}

		public void run() {
			try {
				inStream = new DataInputStream(clientSocket.getInputStream());
				outStream = new DataOutputStream(clientSocket.getOutputStream());
				String user = inStream.readUTF();
				this.sendToAll(user + " đã tham gia phòng chat.");
				String data;
				while ((data = inStream.readUTF()) != null) {
					this.sendToAll(user + ": " + data);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		/**
		 * Phát thông điệp đến tất cả client đang kết nối
		 * 
		 * @param message
		 *            nội dung
		 */
		public void sendToAll(String message) {
			synchronized (connectionList) {
				for (Enumeration<?> element = connectionList.elements(); element.hasMoreElements();) {
					ClientConnection cc = (ClientConnection) element.nextElement();
					try {
						cc.outStream.writeUTF(message);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

}
