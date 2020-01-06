package advent23;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

public class Network {
	private Computer[] computers;
	
	public Network(int size, List<Long> sourceCode) {
		computers = new Computer[size];
		for(int i = 0; i < size; i++) {
			computers[i] = new Computer(sourceCode, i);
		}
	}
	
	public Packet run() {
		Queue<Packet> packetQueue = new ArrayDeque<>();
		
		while(true) {
			for(Computer computer : computers) {
				computer.run();
				Packet sentPacket = computer.retrievePacket();
				if(sentPacket != null) {
					packetQueue.offer(sentPacket);
				}
				
				Packet retrievedPacket = packetQueue.peek();
				if(retrievedPacket != null) {
					if(computer.getAddress() == retrievedPacket.getDestination()) {
						computer.supplyPacket(packetQueue.poll());
						continue;
					}
					if(retrievedPacket.getDestination() == 255) {
						return retrievedPacket;
					}
				}
				computer.supplyPacket(null);
			}
		}
	}
}
