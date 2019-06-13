/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gobackn;

import static gobackn.GoBackN.CORRUPT_PROBABILITY;
import static gobackn.GoBackN.LOST_PROBABILITY;
import java.util.List;
import java.util.Random;

/**
 *
 * @author paulo
 */
public class Medium {
	
	Random random = new Random();
	
	public void setLost(Packet packet){
		if(random.nextInt(100) < LOST_PROBABILITY){
			packet.setLost(true);
			System.out.println("\tThis packet was lost:");
			System.out.println("\t" + packet.toString());
		}
	}
	
	private void setCorruption(Packet packet){
		if(random.nextInt(100) < CORRUPT_PROBABILITY){
			packet.setMessage(doMessageCorruption(packet));
			System.out.println("\tThis packet was corrupted:");
			System.out.println("\t" + packet.toString());
		}
	}

	private String doMessageCorruption(Packet packet) {	//Corruption change a char in the message
		return packet.getMessage().substring(0, 5) + 'X' + packet.getMessage().substring(6);
	}
	
	public void setCorruptionForThisList(List<Packet> listToBeCorrupted){
		System.out.println("Calculating corruption\n");
		for(Packet packet : listToBeCorrupted){
			setLost(packet);	//Apply lost algorithm
			if(!packet.getLost()){
				setCorruption(packet);	//If packet is not lost, apply corruption algorithm
			}
		}
//		for(int i = 0; i < listToBeCorrupted.size(); i++){
//			setLost(listToBeCorrupted.get(i));
//			if(!listToBeCorrupted.get(i).getLost()){
//				setCorruption(listToBeCorrupted.get(i));
//			}
//		}

	}
	
}
