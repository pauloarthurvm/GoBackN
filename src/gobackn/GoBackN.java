/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gobackn;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author paulo
 */
public class GoBackN {
	
	public static List<Packet> listOfPackets;
	public static List<Packet> packetsOnRoad;
	public static final int WINDOW_SIZE = 4;
	public static final int NUMBER_OF_PACKETS = 20;
	public static final int LOST_PROBABILITY = 0;
	public static final int CORRUPT_PROBABILITY = 20;
	public static int currentWindow = 0;
	public static boolean itsOver;

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) throws InterruptedException {
		
		A_Side aSide = new A_Side();
		B_Side bSide = new B_Side();
		Medium medium = new Medium();
		packetsOnRoad = new ArrayList<>();
		itsOver = false;
		
		aSide.createListOfPackets();
		System.out.println(listOfPackets.toString());
		
		for(int i = 0; i < NUMBER_OF_PACKETS/WINDOW_SIZE; i++){	//For loop for the windows of packets
			System.out.println("WINDOW " + currentWindow);
			aSide.packetsToBeSentInThisWindow();
			medium.setCorruptionForThisList(aSide.getListOfPacketsForTheWindow());
			Thread.sleep(3000);
			bSide.setAcksForThisList(aSide.getListOfPacketsForTheWindow());
			Thread.sleep(3000);
			aSide.resendNecessaryPackets(aSide.getListOfPacketsForTheWindow());
			
			currentWindow++;
		}
		
		System.out.println(listOfPackets.toString());	//	Print all packets with correct ACK
	}
	
}
