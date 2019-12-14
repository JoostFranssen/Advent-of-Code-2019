package advent14;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Reaction {
	private List<Chemical> ingredients;
	private Chemical product;
	
	public Reaction(String data) {
		String[] inOut = data.split(" => ");
		String[] in = inOut[0].split(", ");
		
		ingredients = Arrays.asList(in).stream().map(s -> new Chemical(s)).collect(Collectors.toList());
		product = new Chemical(inOut[1]);
	}
	
	public List<Chemical> getIngredients() {
		return new ArrayList<>(ingredients);
	}
	
	public Chemical getProduct() {
		return new Chemical(product);
	}
	
	@Override
	public String toString() {
		return String.join(" + ", ingredients.stream().map(Chemical::toString).toArray(String[]::new)) + " => " + product.toString();
	}
}
