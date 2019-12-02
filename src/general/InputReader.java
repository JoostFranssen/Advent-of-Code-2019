package general;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class InputReader<T> {
	private List<T> input;
	
	public InputReader(String filename, Function<String, T> converter) {
		
		input = new ArrayList<T>();

		BufferedReader reader = null;
		
		try {
			reader = new BufferedReader(new FileReader(filename));
			
			String line = null;
			while((line = reader.readLine()) != null) {
				input.add(converter.apply(line));
			}
			
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public List<T> getInput() {
		return new ArrayList<>(input);
	}
}
