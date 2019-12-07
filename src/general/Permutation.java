package general;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Permutation {
	private Permutation() {}
	
	public static List<Integer[]> getDigitPermutation(int max) {
		return getDigitPermutation(0, max);
	}
	public static List<Integer[]> getDigitPermutation(int min, int max) {
		return getDigitPermutationList(min, max).stream().map(p -> p.toArray(Integer[]::new)).collect(Collectors.toList());
	}
	
	public static List<List<Integer>> getDigitPermutationList(int min, int max) {
		if(max <= min) {
			List<List<Integer>> permutations = new ArrayList<>();
			permutations.add(Arrays.asList(min));
			return permutations;
		}
		
		List<List<Integer>> subPermutations = getDigitPermutationList(min, max - 1);
		List<List<Integer>> permutations = new ArrayList<>();
		for(int i = min; i <= max; i++) {
			for(List<Integer> perm : subPermutations) {
				List<Integer> newPerm = new ArrayList<>(perm);
				newPerm.add(i - min, max);
				permutations.add(newPerm);
			}
		}
		
		return permutations;
	}
}
