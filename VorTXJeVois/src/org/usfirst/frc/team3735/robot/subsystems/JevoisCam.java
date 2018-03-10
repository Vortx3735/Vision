package org.usfirst.frc.team3735.robot.subsystems;

import edu.wpi.cscore.MjpegServer;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoMode.PixelFormat;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Timer;

public class JevoisCam {
	
	public int x=-1;
	public int y=-1;
	public int a=-1;

	private static final int IMG_WIDTH = 320;
	private static final int IMG_HEIGHT = 240;
	private static final int FPS = 30;
	static final int MJPG_STREAM_PORT = 1180;
	
	UsbCamera visionCam;
	SerialPort jevoisPort;
	MjpegServer camServer;
	
	static final int BAUD_RATE = 115200;
	
	public JevoisCam(int camNumber) {
		//setup cam
//		visionCam = new UsbCamera("VisionProcCam", camNumber);
//		visionCam.setVideoMode(PixelFormat.kMJPEG, IMG_WIDTH, IMG_HEIGHT, FPS); 
//		camServer = new MjpegServer("VisionCamServer", MJPG_STREAM_PORT);
//		camServer.setSource(visionCam);
//		System.out.println(camServer.isValid());
//	    System.out.println("Camera created");
	    //create port
		try {
			jevoisPort = new SerialPort(BAUD_RATE, SerialPort.Port.kUSB1);
			System.out.println("Jevois coms created");
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
		System.out.println("length: " + cam.length());
		if(!cam.equals("")) {
			String[] split = cam.split(" ");
			x = Integer.parseInt(split[0]);
			y = Integer.parseInt(split[1]);
			a = Integer.parseInt(split[2]);
			System.out.println("x: " + x + " y: " + y + " a: " + a);
		} 
	}
	
	public void checkCommand(String s) {
		sendString(s);
		System.out.println("Sent complete");
    	String testStr = "";
    	int count=0;
        while(true){
            if (jevoisPort.getBytesReceived() > 0) {
            	testStr += jevoisPort.readString();
            	System.out.println(testStr);
               	if(testStr.contains("OK")){
               		break;
               	}else if(testStr.contains("ERR")){
               		break;
               	}
            } else {
               	sleep(10);
               	count++;
               	System.out.println("Waited " + count);
            }
        }
	}
	
	public void sleep(int mills) {
		try {
			Thread.sleep(mills);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setUpNoVision() {
		checkCommand("setmapping 0");
		checkCommand("setpar serlog All");
		checkCommand("setpar serout All");
		checkCommand("streamon");
	}
}