package advent23;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

import intcode.Program;

public class Computer {
	private Program program;
	private int address;
	private Queue<Packet> queue;
	
	public Computer(List<Long> sourceCode, int address) {
		this.address = address;
		queue = new ArrayDeque<>();
		program = new Program(sourceCode, (long)address);
		program.run();
	}
	
	public void supplyPacket(Packet packet) {
		if(packet != null) {
			if(packet.getDestination() == address) {
				queue.offer(packet);
			}
		}
	}
	
	public boolean processNextPacket() {
		Packet packet = queue.poll();
		boolean hadPacket;
		if(packet == null) {
			program.supplyInput(-1L);
			hadPacket = false;
		} else {
			program.supplyInput(packet.getX());
			program.supplyInput(packet.getY());
			hadPacket = true;
		}
		program.run();
		return hadPacket;
	}
	
	public Packet retrievePacket() {
		if(program.getOutputCount() >= 3) {
			int destination = program.getNextOutput().intValue();
			long x = program.getNextOutput();
			long y = program.getNextOutput();
			return new Packet(destination, x, y);
		}
		return null;
	}
	
	public long getAddress() {
		return address;
	}
}
