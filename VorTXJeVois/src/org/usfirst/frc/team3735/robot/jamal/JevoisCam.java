package org.usfirst.frc.team3735.robot.jamal;

import edu.wpi.cscore.MjpegServer;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoMode.PixelFormat;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Timer;

public class JevoisCam {
	
	public int x=-1;
	public int y=-1;
	public int a=-1;
	
	private boolean waiting;

	private static final int IMG_WIDTH = 320;
	private static final int IMG_HEIGHT = 240;
	private static final int FPS = 30;
	static final int MJPG_STREAM_PORT = 1180;
	static final int BAUD_RATE = 115200;
	
	UsbCamera visionCam;
	SerialPort jevoisPort;
	MjpegServer camServer;
	JevoisListener listen;
	
	public JevoisCam() {
		//setup cam
		boolean cam = createCamera();
		if(cam) {
			System.out.println("Camera created successfully");
		}
	    //create port
		try {
			jevoisPort = new SerialPort(BAUD_RATE, SerialPort.Port.kUSB1);
			System.out.println("Jevois coms created");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean createCamera() {
		visionCam = new UsbCamera("VisionProcCam", 0);
		visionCam.setVideoMode(PixelFormat.kMJPEG, IMG_WIDTH, IMG_HEIGHT, FPS); 
		camServer = new MjpegServer("VisionCamServer", MJPG_STREAM_PORT);
		camServer.setSource(visionCam);
		return camServer.isValid();
	}
	

	public void sendString(String s) {
		try {
			jevoisPort.writeString(s);
			System.out.println("Sent " + s);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	public void checkCommand(String s) {
		sendString(s);
//    	listen = new JevoisListener();
//    	listen.start();
//    	waiting = true;
//    	String fetch = listen.fetch();
//    	if(fetch.length()>0) {
//           	System.out.println(fetch);
//          	if(fetch.contains("OK")||fetch.contains("ERR")){
//           		waiting = false;
//           		listen.stop();
//           	}
//        }
	}
	
	public void sleep(int mills) {
		try {
			Thread.sleep(mills);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setUpVision() {
		checkCommand("setmapping 0");
		checkCommand("setpar serlog All");
		checkCommand("setpar serout All");
		checkCommand("streamon");
	}
	
	public void setUpCam() {
		checkCommand("streamoff");
		checkCommand("setpar serlog Hard");
		checkCommand("setpar serout Hard");
		checkCommand("setmapping 1");
	}
	
	
//	public void checkCommand(String s) {
//		sendString(s);
//		System.out.println("Sent complete");
//    	String testStr = "";
//    	int count=0;
//    	
//        while(true){
//            if (jevoisPort.getBytesReceived() > 0) {
//            	testStr += jevoisPort.readString();
//            	System.out.println(testStr);
//               	if(testStr.contains("OK")){
//               		break;
//               	}else if(testStr.contains("ERR")){
//               		break;
//               	}
//            } else {
//               	sleep(10);
//               	count++;
//               	System.out.println("Waited " + count);
//            }
//        }
//	}
	
//	public void sleep(int mills) {
//		try {
//			Thread.sleep(mills);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
}