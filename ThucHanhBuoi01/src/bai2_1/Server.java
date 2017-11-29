package bai2_1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Server implements Runnable{
	private DatagramSocket serverSocket;
	private DatagramPacket receivePacket, sendPacket;
	private InetAddress IPAddress;
	private Integer portNumber = 9876;
	private byte[] receiveData = new byte[1024];
	private byte[] sendData = new byte[1024];

	public Server() {
		try {
			this.serverSocket = new DatagramSocket(portNumber);
			System.out.println("Server started.");
		} catch (IOException e) {
			System.err.println("\nProcess in/out error: " + e);
		}
	}

	public static void main(String[] args) throws IOException {
		Thread thread = new Thread(new Server());
		thread.start();
	}

	@Override
	public void run() {
		String input, output;
		while (true) {
			
			try {
				receivePacket = new DatagramPacket(receiveData, receiveData.length);
				System.out.println("Waitting package...");
				serverSocket.receive(receivePacket);
				IPAddress = receivePacket.getAddress();
				portNumber = receivePacket.getPort();
				System.out.println("Từ: " + IPAddress + ":" + portNumber);
				
				input = new String(receivePacket.getData()).trim();
				System.out.println("\nServer got a string: " + input);
				output = "\t\"" + input.toUpperCase() + "\"\n\t\"" + input.toLowerCase() + "\"";
				output += "\n\t Number of string: " + this.countWords(input);
				System.out.println("Result: \n" + output);
				sendData = output.getBytes();
				sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, portNumber);
				serverSocket.send(sendPacket);
				System.out.println("Send to Client.\n");
			} catch (IOException e) {
				
				e.printStackTrace();
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
