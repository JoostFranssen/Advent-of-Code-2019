package advent23;

import java.util.List;

import intcode.Program;

public class Computer {
	private Program program;
	private int address;
	
	public Computer(List<Long> sourceCode, int address) {
		this.address = address;
		program = new Program(sourceCode, (long)address);
	}
	
	public void run() {
		program.run();
	}
	
	public void supplyPacket(Packet packet) {
		if(packet == null) {
			program.supplyInput(-1L);
		} else {
			if(packet.getDestination() == address) {
				program.supplyInput(packet.getX());
				program.supplyInput(packet.getY());
			}
		}
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
