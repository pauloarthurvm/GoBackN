/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gobackn;

import static gobackn.GoBackN.listOfPackets;
import static gobackn.GoBackN.NUMBER_OF_PACKETS;
import static gobackn.GoBackN.WINDOW_SIZE;
import static gobackn.GoBackN.currentWindow;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author paulo
 */
public class A_Side {

	int packetsAcknowledged = 0;
	private List<Packet> listOfPacketsForTheWindow;
	List<Packet> listOfPacketsToBeResent1;
	boolean allGood;

	public void createListOfPackets() {
		int seqNumber = 0;
		listOfPackets = new ArrayList<>();
		for (int i = 0; i < NUMBER_OF_PACKETS; i++) {
			listOfPackets.add(new Packet(createMessage(), seqNumber));
			seqNumber += listOfPackets.get(listOfPackets.size() - 1).getCheckSum();
		}
	}

	private String createMessage() {	//Creates a message of same random char with a random size
		Random random = new Random();
		int randomCharNumber = random.nextInt(25) + 97;		//Alphabet with 25 letters and char 'a' is 97
		int randomMessageSize = random.nextInt(10) + 11;	//Message with min 11 and max 20 chars
		String message = "";
		for (int i = 0; i < randomMessageSize; i++) {
			message = message + (char) randomCharNumber;
		}
		while (message.length() <= 20) {
			message += ' ';		//Messages are completed with 'spaces'
		}
		return message;
	}

	public void packetsToBeSentInThisWindow() {	//Do the list of packets for the current window
		System.out.println("\tCreating window of packets");
		listOfPacketsForTheWindow = new ArrayList<>();
		for (int i = 0; i < WINDOW_SIZE; i++) {
			if (((currentWindow * WINDOW_SIZE) + i) < listOfPackets.size()) {
				listOfPacketsForTheWindow.add(listOfPackets.get((currentWindow * WINDOW_SIZE) + i));
				System.out.println("\t" + listOfPackets.get((currentWindow * WINDOW_SIZE) + i).toString());
			}
		}
		System.out.println("\tWindow of packets created\n");
	}

	void resendNecessaryPackets(List<Packet> listOfPacketsToBeChecked) throws InterruptedException {
		allGood = false;

		while (!allGood) {	//While all packets of this window aren't ACKed, keep resending necessary ones
			List<Packet> listOfPacketsToBeResent = new ArrayList<>();
//			for (Packet packet : getListOfPacketsForTheWindow()) {	//Do list of packets that must be resent
			for (int i = 0; i < listOfPacketsToBeChecked.size(); i++) {	//Do list of packets that must be resent
				if (listOfPacketsToBeChecked.get(i).getAckNumber() == 0) {	//Check the ACK, if it is not ok, add the packet to he resend list
					listOfPacketsToBeChecked.get(i).setMessage(listOfPacketsToBeChecked.get(i).getOriginalMessage());	//Turn the message back to original
					System.out.println("Adding this packet to the resent list");
					System.out.println(listOfPacketsToBeChecked.get(i).toString());
					listOfPacketsToBeResent.add(listOfPacketsToBeChecked.get(i));	//If ACK is not correct, add packet to be resent
				}
			}
			
			if (!listOfPacketsToBeResent.isEmpty()) {	//Check if resend list is empty
				//If it is not empty, resend necessary packets
				System.out.println("List of packets to be resent");
				System.out.println(listOfPacketsToBeResent.toString());
				
				//Simulate the send of packets
				Medium medium = new Medium();
				B_Side bSide = new B_Side();

				medium.setCorruptionForThisList(listOfPacketsToBeResent);
				Thread.sleep(3000);
				bSide.setAcksForThisList(listOfPacketsToBeResent);
				Thread.sleep(3000);

			}
			checkIfItsAllGood(listOfPacketsToBeResent);
		}
	}

	private void checkIfItsAllGood(List<Packet> listOfPacketsToBeChecked) {
		for (Packet packet : listOfPacketsToBeChecked) {	//Do list of packets that must be resent
			if (packet.getAckNumber() == 0) {
				return;
			}
		}
		allGood = true;
	}

	public List<Packet> getListOfPacketsForTheWindow() {
		return listOfPacketsForTheWindow;
	}

	public List<Packet> getListOfPacketsToBeResent() {
		return listOfPacketsToBeResent1;
	}

}
