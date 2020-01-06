package advent23;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import util.InputReader;

public class Solution {
	private static final String FILENAME = "src/advent23/input.txt";
	
	public static void main(String[] args) {
		InputReader<String> inputReader = new InputReader<>(FILENAME);
		String input = inputReader.readLines().get(0);
		List<Long> intcode = Arrays.asList(input.split(",")).stream().map(Long::valueOf).collect(Collectors.toList());
		
		final Network network = new Network(50, intcode);
		
		//part 1
		network.addNatChangeListener(e -> {
			if(e.getOldPacket() == null) {
				System.out.println(e.getNewPacket().getY()); //16250
			}
		});
		
		//part 2
		network.addNatSupplyListener(e -> {
			if(e.getPreviousNat() != null && e.getSuppliedPacket() != null) {
				if(e.getPreviousNat().getY() == e.getSuppliedPacket().getY()) {
					System.out.println(e.getSuppliedPacket().getY()); //11046
					network.interruptPacketing();
				}
			}
		});
		
		network.run();
	}
}
