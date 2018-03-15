package org.usfirst.frc.team3735.robot.jamal;

import edu.wpi.first.wpilibj.SerialPort;

public class JevoisListener extends Thread {

	SerialPort jevoisPort;
	String s;
	static final int BAUD_RATE = 115200;
	int count=0;
	
	public JevoisListener() {
		try {
			jevoisPort = new SerialPort(BAUD_RATE, SerialPort.Port.kUSB1);
			System.out.println("Jevois coms created");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		while(true) {
			if(jevoisPort.getBytesReceived()>0) {
				s += jevoisPort.readString();
				count = 0;
			} else {
				count++;
				sleep(10);
			}
		}
	}
	
	
	public void sleep(int mill) {
		try {
			super.sleep(mill);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public String fetch() {
		String fetch = s.toString();
		s = "";
		return count + " " + fetch;
	}
	
}
