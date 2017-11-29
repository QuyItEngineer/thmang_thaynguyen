package bai1_1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.management.RuntimeMXBean;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;

public class Server implements Runnable {
	Socket socket;
	ServerSocket serverSocket;
	DataOutputStream dataSentClient;
	DataInputStream dataFromClient;
	String serverAddress = "localhost";
	Integer serverPort = 6969;

	public static void main(String[] args) throws IOException {
		Thread thread = new Thread(new Server());
		thread.start();
	}

	public Server() {
		try {
			this.serverSocket = new ServerSocket(serverPort);
			System.out.println("Server started.");
		} catch (IOException e) {
			System.err.println("\nProcess in/out error: " + e);
		}
	}

	@Override
	public void run() {
		String input, output;
		while (true) {
			try {
				this.socket = this.serverSocket.accept();
				System.out.print("\nConnect to Client.");
				this.dataSentClient = new DataOutputStream(socket.getOutputStream());
				this.dataFromClient = new DataInputStream(socket.getInputStream());
				input = this.dataFromClient.readUTF();
				System.out.print("\nServer got a string: " + input);
				output = "\t\"" + input.toUpperCase() + "\"\n\t\"" + input.toLowerCase() + "\"";
				output += "\n\tNumber of string: " + this.countWords(input);
				this.dataSentClient.writeUTF(output);
				System.out.print("\nReturned to Client.");
				this.socket.close();
				System.out.print("\nClosed socket. \n\n");
			} catch (IOException e) {
				System.err.println("\nProcess in/out error: " + e);
				new Server();
				continue;
			}
		}

	}

	private int countWords(String str) {
		int num = 0;

		if (str == null || "\\s*".equals(str)) {
			return 0;
		} else {
			str = str.trim().replaceAll("\\s+", " ");
			String splitter1 = "\\s\\!\\,\\.\\?\\:\\;\\'\\\"\\)\\(\\[\\]\\{\\}";
			String splitter2 = "\\@\\#\\$\\%\\^\\&\\*\\+\\-\\|\\~\\`\\>\\<\\/";
			String splitters = "[" + splitter1 + splitter2 + "]";
			String arr[] = str.split(splitters);

			for (int i = 0; i < arr.length; i++) {
				if (arr[i] == null || "".equals(arr[i])) {
					continue;
				} else {
					num++;
				}
			}
			return num;
		}
	}

}
