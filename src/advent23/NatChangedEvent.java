package advent23;

public class NatChangedEvent {
	private Packet oldPacket;
	private Packet newPacket;
	
	public NatChangedEvent(Packet oldPacket, Packet newPacket) {
		this.oldPacket = oldPacket;
		this.newPacket = newPacket;
	}

	public Packet getOldPacket() {
		return oldPacket;
	}

	public Packet getNewPacket() {
		return newPacket;
	}
}
