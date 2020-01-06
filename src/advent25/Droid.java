package advent25;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import intcode.Program;
import intcode.ProgramStatus;

public class Droid {
	private Program program;
	
	public Droid(List<Long> sourceCode) {
		program = new Program(sourceCode);
	}
	
	public void run() {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		while(true) {
			program.run();
			while(program.hasOutput()) {
				System.out.print((char)program.getNextOutput().intValue());
			}
			if(program.getStatus() == ProgramStatus.FINISHED) {
				break;
			}
			
			try {
				String input = reader.readLine();
				if(input.equals("exit")) {
					break;
				}
				program.supplyInput(input + "\n");
			} catch(IOException e) {}
		}
	}
}
