package bai2_1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.Scanner;

public class Client {
	private DatagramSocket clientSocket = null;
	private String serverHostname = "localhost";
	private InetAddress IPAddress, returnIPAddress;
	private Integer portNumber = 9876;
	private byte[] sendData = new byte[1024];
	private byte[] receiveData = new byte[1024];
	private DatagramPacket sendPacket, receivePacket;
	private Scanner scanner;

	public Client() throws IOException {

	    clientSocket = new DatagramSocket();
	    IPAddress = InetAddress.getByName(serverHostname);
	    System.out.print("Input a string: ");
	    

	    scanner = new Scanner(System.in);
	    String line = scanner.nextLine();
	    sendData = line.getBytes();
	    System.out.print("sending... " + sendData.length
	        + " bytes đến Server...");
	    sendPacket = new DatagramPacket(sendData, sendData.length, 
	        IPAddress, portNumber);
	    clientSocket.send(sendPacket);
	    System.out.println("Sent!");
	    
	    //get data from server
	    receivePacket = new DatagramPacket(receiveData, receiveData.length);
	    System.out.println("Waiting replay...");
	    clientSocket.setSoTimeout(10000); 
	    try {
	      clientSocket.receive(receivePacket);
	      String string = new String(receivePacket.getData());
	      returnIPAddress = receivePacket.getAddress();
	      System.out.println("Từ Server: " + returnIPAddress + ":" 
	          + receivePacket.getPort());
	      System.out.println("Result:  \n\t" + string.trim());

	    } catch (SocketTimeoutException ste) {
	      System.out.println("Chờ quá lâu: Gói tin đã bị mất!");
	    }
	    
	    
	    clientSocket.close();
	    System.out.println("Stop.");
	  }

	public static void main(String[] args) throws IOException {
		new Client();

	}

}
