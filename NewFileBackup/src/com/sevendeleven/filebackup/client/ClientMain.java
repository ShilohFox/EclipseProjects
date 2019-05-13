package com.sevendeleven.filebackup.client;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.sevendeleven.filebackup.file.FileData;
import com.sevendeleven.filebackup.packet.Packet;

import de.grobmeier.jjson.JSONObject;
import de.grobmeier.jjson.convert.JSONDecoder;

public class ClientMain {
	
	private Socket socket;
	private ReceiveThread receiveThread;
	private SendThread sendThread;
	
	public static ClientMain cmain;
	
	public static BufferedImage folderImage;
	
	public static void main(String[] args) {
		try {
			folderImage = (BufferedImage) ImageIO.read(ClientMain.class.getResourceAsStream("/folder.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		cmain = new ClientMain();
		cmain.start();
	}
	
	public void start() {
		try {
			socket = new Socket("192.168.1.123", 3300);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			System.exit(-1);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		receiveThread = new ReceiveThread();
		sendThread = new SendThread();
		run();
	}
	
	public void run() {
		FileChooser chooser = new FileChooser(new FileData(new File(System.getProperty("user.home"))), new FileData("/","/", FileData.FileType.DIRECTORY));
		JFrame frame = new JFrame("File Backup Program");
		JPanel panel = new JPanel();
		panel.add(chooser);
		frame.setContentPane(panel);
		frame.setSize(new Dimension(600,500));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public ReceiveThread getReceiveThread() {
		return this.receiveThread;
	}
	
	public SendThread getSendThread() {
		return this.sendThread;
	}
	
	public void parsePacket(JSONObject packet) {
		
	}
	
	public void sendPacket(Packet packet) {
		sendPacket(packet.getJSON());
	}
	
	public void sendPacket(JSONObject packet) {
		sendThread.sendPacket(packet);
	}
	
	private class ReceiveThread implements Runnable {
		private Thread thread;
		
		public ReceiveThread() {
			thread = new Thread(this, "ReceiveThread");
			thread.start();
		}
		
		public void run() {
			Scanner sc = null;
			try {
				sc = new Scanner(socket.getInputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
			while (socket.isConnected()) {
				String packetString = sc.nextLine();
				JSONDecoder dec = new JSONDecoder(packetString);
				JSONObject packet = (JSONObject) dec.decode();
				parsePacket(packet);
			}
			sc.close();
		}
	}
	
	private class SendThread implements Runnable {
		private List<String> buffer = new ArrayList<>();
		private Thread thread;
		
		public SendThread() {
			thread = new Thread(this, "SendThread");
			thread.start();
		}
		
		public void run() {
			PrintStream ps = null;
			try {
				ps = new PrintStream(socket.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(-1);
			}
			while (socket.isConnected()) {
				while (buffer.size() > 0) {
					ps.println(buffer.get(0));
					buffer.remove(0);
				}
			}
			ps.close();
		}
		
		void sendPacket(JSONObject packet) {
			sendData(packet.toJSON());
		}
		
		void sendData(String str) {
			buffer.add(str);
		}
		
	}
	
}
