package advent14;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.stream.Collectors;

import util.InputReader;
import util.QuantityMap;

public class Solution {
	private static final String FILENAME = "src/advent14/input.txt";
	private static Map<String, Reaction> reactionsByProductName;
	
	public static void main(String[] args) {
		InputReader<Reaction> inputReader = new InputReader<>(FILENAME);
		List<Reaction> input = inputReader.readConvertLines(l -> new Reaction(l));
		
		reactionsByProductName = input.stream().collect(Collectors.toMap(r -> r.getProduct().getName(), Function.identity()));
		
		//part 1
		QuantityMap<String> requirements = new QuantityMap<>();
		requirements.put("FUEL", 1L);
		
		requirements = computeRequiredOre(requirements);
		
		long requiredOre = requirements.get("ORE");
		System.out.println(requiredOre); //1920219
		
		//part 2
		final long oreAmount = 1_000_000_000_000L;
		long obtainedFuel = 0L;
		
		requirements = new QuantityMap<>();
		while(true) {
			requirements.put("FUEL", 1L);
			requirements = computeRequiredOre(requirements);
			if(requirements.get("ORE") <= oreAmount) {
				obtainedFuel++;
			} else {
				break;
			}
		}
		
		System.out.println(obtainedFuel); //1330066
	}

	private static QuantityMap<String> computeRequiredOre(QuantityMap<String> desire) {
		QuantityMap<String> requirements = new QuantityMap<>(desire);
		while(anyMatch(requirements, (k, v) -> k.equals("ORE") ? false : v > 0)) {
			QuantityMap<String> newRequirements = new QuantityMap<>(requirements);
			Iterator<String> it = requirements.keySet().iterator();
			while(it.hasNext()) {
				String name = it.next();
				long amount = requirements.get(name);
				if(amount <= 0) {
					continue;
				}
				
				Reaction reaction = reactionsByProductName.get(name);
				if(reaction != null) {
					int repeatReaction = (int)Math.ceil((double)amount / reaction.getProduct().getQuantity());
					for(Chemical ingredient : reaction.getIngredients()) {
						long newAmount = repeatReaction * ingredient.getQuantity();
						newRequirements.putAdd(ingredient.getName(), newAmount);
					}
					newRequirements.putAdd(name, -repeatReaction * reaction.getProduct().getQuantity());
					it.remove();
				}
			}
			requirements = newRequirements;
		}
		return requirements;
	}
	
	private static <K, V> boolean anyMatch(final Map<K, V> map, BiPredicate<K, V> predicate) {
		return map.keySet().stream().anyMatch(k -> predicate.test(k, map.get(k)));
	}
}
