package advent04;

public class Solution {
	private static final int START = 134792;
	private static final int END = 675810;
	
	public static void main(String[] args) {
		Password p = new Password(START);
		
		int count = 0;
		while(p.getInt() <= END) {
			count++;
			p.next();
		}
		
		System.out.println(count);
	}
}
