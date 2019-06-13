/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gobackn;

/**
 *
 * @author paulo
 */
public class Packet {
	private String message, originalMessage;
	private int seqNumber, ackNumber;
	private int checkSum;
	private boolean lost = false;
	

	public Packet(String message, int seqNumber) {	//Packet constructor
		setMessage(message);
		setOriginalMessage(message);
		setSeqNumber(seqNumber);
		setAckNumber(0);
		setCheckSum(originalMessage);
	}

	@Override
	public String toString() {
		return "Packet{" + "message=" + message + ", seqNumber=" + seqNumber + ", ackNumber=" + ackNumber + ", checkSum=" + getCheckSum() + "}\n";
	}

	private void setCheckSum(String originalMessage) {	//Apply checkSum to the packet according to the message
		for(int i = 0; i < originalMessage.length(); i++){
			checkSum += (int) originalMessage.charAt(i);
		}
	}
	
	public void setOriginalMessage(String originalMessage) {
		this.originalMessage = originalMessage;
	}

	
	String getOriginalMessage() {
		return originalMessage;
	}

	int getCheckSum() {
		return checkSum;
	}

	void setNack() {
		ackNumber = -1;
	}

	void setLost(boolean lost) {
		this.lost = lost;
	}

	void setMessage(String message) {
		this.message = message;
	}

	String getMessage() {
		return message;
	}

	boolean getLost() {
		return lost;
	}
	
	public int getSeqNumber() {
		return seqNumber;
	}

	public void setSeqNumber(int seqNumber) {
		this.seqNumber = seqNumber;
	}

	public int getAckNumber() {
		return ackNumber;
	}

	public void setAckNumber(int ackNumber) {
		this.ackNumber = ackNumber;
	}
}
