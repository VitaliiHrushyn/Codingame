import java.util.*;
import java.io.*;
import java.math.*;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Solution {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        String s = in.next();

        // Write an action using System.out.println()
        // To debug: System.err.println("Debug messages...");
        
        char[] ch = s.toCharArray();
       
        ArrayList<Integer> ints = new ArrayList<>();
        
        for (int i = 0; i < ch.length; i+=2) {
			ints.add(Integer.valueOf(new String(""+ch[i])));
		}
        ints.sort(null);
        
        for (int i = 0; i < ints.size(); i++) {
			System.out.print(ints.get(i));
			if (i > ints.size()-1) System.out.print("+");
		}

    //    System.out.println("answer");
    }
}