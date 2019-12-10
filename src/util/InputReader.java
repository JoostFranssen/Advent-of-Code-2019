package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class InputReader<T> {
	private String filename;
	
	public InputReader(String filename) {
		this.filename = filename;
	}
	
	public List<String> readLines() {
		return (new InputReader<String>(filename)).readConvertLines(s -> s);
	}
	
	public List<T> readConvertLines(Function<String, T> converter) {
		List<T> input = new ArrayList<T>();

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
		
		return input;
	}
}
