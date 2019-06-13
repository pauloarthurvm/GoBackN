/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gobackn;

import java.util.List;

/**
 *
 * @author paulo
 */
public class B_Side {

	boolean confirmCheckSum(Packet packet) {	//Return false if packet is corrupted
		int checkSum = 0;
		for (int i = 0; i < packet.getOriginalMessage().length(); i++) {
			checkSum += (int) packet.getMessage().charAt(i);
		}
		if (packet.getCheckSum() != checkSum) {
			return false;
		}
		return true;
	}

	void setAcksForThisList(List<Packet> listOfPacketsForTheWindow) {
		System.out.println("Doing acknowledge\n");
		for(int i = 0; i < listOfPacketsForTheWindow.size(); i++){
			if(!listOfPacketsForTheWindow.get(i).getLost()){	//Check if packet was not lost
				if(confirmCheckSum(listOfPacketsForTheWindow.get(i))){	//Check if it is not corrupted
					listOfPacketsForTheWindow.get(i).setAckNumber(listOfPacketsForTheWindow.get(i).getSeqNumber() + listOfPacketsForTheWindow.get(i).getCheckSum());	
					//If packet is ok, set the ACK correctly, that is the SEQ + Lenght (CheckSum)
				} else{
					System.out.println("CORRUPTED: " + listOfPacketsForTheWindow.get(i).toString());
				}
			}
		}
		
	}

}
