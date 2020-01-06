package advent23;

import java.util.ArrayList;
import java.util.List;

public class Network extends Thread {
	private Computer[] computers;
	private Packet nat, previouslySuppliedNat;
	private boolean keepRunning;
	private List<NatChangeListener> natChangeListeners;
	private List<NatSupplyListener> natSupplyListeners;
	
	public Network(int size, List<Long> sourceCode) {
		computers = new Computer[size];
		for(int i = 0; i < size; i++) {
			computers[i] = new Computer(sourceCode, i);
		}
		natChangeListeners = new ArrayList<>();
		natSupplyListeners = new ArrayList<>();
	}
	
	private void updateNat(Packet packet) {
		Packet newNat = new Packet(0, packet.getX(), packet.getY());
		
		NatChangedEvent event = new NatChangedEvent(nat, newNat);
		nat = newNat;
		
		for(NatChangeListener listener : natChangeListeners) {
			listener.natChanged(event);
		}
	}
	
	private void natSupplies() {
		computers[0].supplyPacket(nat);
		
		NatSupplyEvent event = new NatSupplyEvent(previouslySuppliedNat, nat);
		previouslySuppliedNat = nat;
		
		for(NatSupplyListener listener : natSupplyListeners) {
			listener.natSuppliedPacket(event);
		}
	}
	
	@Override
	public void run() {
		keepRunning = true;
		while(keepRunning) {
			boolean anyPacket = false;
			
			for(Computer computer : computers) {
				boolean hadPacket = false;
				while(hadPacket = computer.processNextPacket()) {
					anyPacket |= hadPacket;
				}
			}
			
			for(Computer computer : computers) {
				boolean didSent;
				do {
					didSent = false;
					Packet sentPacket = computer.retrievePacket();
					if(sentPacket != null) {
						didSent = true;
						if(sentPacket.getDestination() == 255) {
							updateNat(sentPacket);
						} else {
							computers[sentPacket.getDestination()].supplyPacket(sentPacket);
						}
					}
					anyPacket |= didSent;
				} while(didSent);
			}
			
			if(!anyPacket) {
				natSupplies();
			}
		}
	}
	
	public void addNatChangeListener(NatChangeListener listener) {
		natChangeListeners.add(listener);
	}
	
	public void addNatSupplyListener(NatSupplyListener listener) {
		natSupplyListeners.add(listener);
	}
	
	public void interruptPacketing() {
		keepRunning = false;
	}
}
