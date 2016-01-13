import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;


public class Solution {
	private static Scanner kb;

	public static void main(String[] args){
		kb = new Scanner(System.in);
		String s = kb.nextLine();
		System.out.println(compress(s));
	}
	
	static String compress(String str) {
		if(str == null){
			throw new RuntimeException("Cannot compress null string");
		}
		if(str.isEmpty()){
			throw new RuntimeException("Cannot compress empty string");
		}

		String compressedString = "";
		
		for(int i=0; i<str.length();){
			char c = str.charAt(i);
			int counter = 0;
			int startRepIndex = i;
			int next = i+1;
			if(startRepIndex != str.length()-1){
				if(next >=0 && next<str.length()){
					while(str.charAt(startRepIndex+1) == str.charAt(startRepIndex)){
						counter++;
						startRepIndex++;
					}
				}
				
			}
			
			int repetitions = counter+1;
			compressedString += c;
			if(counter > 0){
				compressedString += repetitions;
			}
			i+=repetitions;
		}

		return compressedString;
	}
}
