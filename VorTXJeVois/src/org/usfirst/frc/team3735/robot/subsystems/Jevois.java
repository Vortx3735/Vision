package org.usfirst.frc.team3735.robot.subsystems;

import edu.wpi.cscore.MjpegServer;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoMode.PixelFormat;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Timer;

public class Jevois {
	
	public double x=0;
	public double y=0;
	public double a=0;

	private static final int IMG_WIDTH = 320;
	private static final int IMG_HEIGHT = 240;
	private static final int FPS = 30;
	static final int MJPG_STREAM_PORT = 1180;
	
	UsbCamera visionCam;
	SerialPort jevoisPort;
	MjpegServer camServer;
	
	static final int BAUD_RATE = 115200;
	
	public Jevois() {
	    //create port
		try {
			jevoisPort = new SerialPort(BAUD_RATE, SerialPort.Port.kUSB1);
			System.out.println("Jevois coms created successfully");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String readString() {
		try {
			return jevoisPort.readString();
		} catch (Exception e) {
			return e.toString();
		}
	}
	
	public void sendString(String s) {
		try {
			jevoisPort.writeString(s);
			System.out.println("Sent " + s);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void refresh() {
		String cam = readString();
		if(!cam.equals("")) {
			try {
				String[] split = cam.split(" ");
				x = Double.parseDouble(split[0]);
				y = Double.parseDouble(split[1]);
				a = Double.parseDouble(split[2]);
				System.out.println("x: " + x + " y: " + y + " a: " + a);
			} catch (Exception e) {
				System.out.println("Error on string: " + cam);
			}
			
		} 
	}

	
	public void setUpVision() {
		sendString("setmapping 0");
		sendString("setpar serlog All");
		sendString("setpar serout All");
		sendString("streamon");
	}
	
//	public void setUpCamera() {
//		sendString("streamoff");
//		sendString("setpar serlog None");
//		sendString("setpar serout None");
//		sendString("setmapping 1");
//		
//		//setup cam
//		visionCam = new UsbCamera("VisionProcCam", 0);
//		visionCam.setVideoMode(PixelFormat.kMJPEG, IMG_WIDTH, IMG_HEIGHT, FPS); 
//		camServer = new MjpegServer("VisionCamServer", MJPG_STREAM_PORT);
//		camServer.setSource(visionCam);
//		System.out.println("Is Valid: " + camServer.isValid());
//		System.out.println("Camera created");
//	}
}