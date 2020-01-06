package advent23;

public class NatSupplyEvent {
	private Packet previousNat;
	private Packet suppliedPacket;
	
	public NatSupplyEvent(Packet previousNat, Packet suppliedPacket) {
		this.previousNat = previousNat;
		this.suppliedPacket = suppliedPacket;
	}
	
	public Packet getPreviousNat() {
		return previousNat;
	}
	
	public Packet getSuppliedPacket() {
		return suppliedPacket;
	}
}
