package bai1_1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Client {

	Socket socket;
	DataOutputStream dataFromServer;
	DataInputStream dataSentServer;
	Scanner scanner;
	String serverAddress = "localhost";
	Integer serverPort = 6969;

	public static void main(String[] args) throws UnknownHostException, IOException {
		new Client();
	}

	public Client() {

		try {
			socket = new Socket(serverAddress, serverPort);
		} catch (UnknownHostException e) {
			System.err.println("\nNot connect to server: " + e);
			System.exit(1);
		} catch (IOException e) {
			System.err.println("\nNot correct port: " + serverPort);
			System.exit(1);
		}

		try {
			dataFromServer = new DataOutputStream(socket.getOutputStream());
			dataSentServer = new DataInputStream(socket.getInputStream());
		} catch (IOException e) {
			System.err.println("\nProcess in/out error: " + e);
			System.exit(1);
		}

		System.out.print("Input a string: ");
		this.scanner = new Scanner(System.in);
		String outString = scanner.nextLine();
		try {
			dataFromServer.writeUTF(outString);
			System.out.print("\nSend a value to Server");
		} catch (IOException e) {
			System.err.println("\nProcess out error: " + e);
		}
		// get data from server
		String inString = "";
		try {
			inString = dataSentServer.readUTF();
			System.out.print("\nServer returned :\n" + inString);
		} catch (IOException e) {
			System.err.println("\nProcess in error: " + e);
			System.exit(1);
		}

		try {
			dataFromServer.close();
			dataSentServer.close();
			socket.close();
		} catch (IOException e) {
			System.err.println("\nError closes connected: " + e);
		}
		System.out.println("\nCloses connected!");
	}

}
