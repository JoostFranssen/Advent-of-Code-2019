package general;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Permutation {
	private Permutation() {}
	
	public static List<Integer[]> getDigitPermutation(int range) { //e.g. range = 3: permutations are of 0, 1, 2, 3;
		return getDigitPermutationList(range).stream().map(p -> p.toArray(Integer[]::new)).collect(Collectors.toList());
	}
	
	public static List<List<Integer>> getDigitPermutationList(int range) {
		if(range <= 0) {
			List<List<Integer>> permutations = new ArrayList<>();
			permutations.add(Arrays.asList(0));
			return permutations;
		}
		
		List<List<Integer>> subPermutations = getDigitPermutationList(range - 1);
		List<List<Integer>> permutations = new ArrayList<>();
		for(int i = 0; i <= range; i++) {
			for(List<Integer> perm : subPermutations) {
				List<Integer> newPerm = new ArrayList<>(perm);
				newPerm.add(i, range);
				permutations.add(newPerm);
			}
		}
		
		return permutations;
	}
}
