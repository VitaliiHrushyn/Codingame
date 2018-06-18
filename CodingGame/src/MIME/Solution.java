package MIME;

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
        String LON = in.next();
        String LAT = in.next();
        
        double lon = Double.valueOf(LON.replace(',', '.'));
        double lat = Double.valueOf(LAT.replace(',', '.'));
        
        int N = in.nextInt();
        if (in.hasNextLine()) {
            in.nextLine();
        }
        List<Defib> defibList = new ArrayList<Solution.Defib>();
        for (int i = 0; i < N; i++) {
            String DEFIB = in.nextLine();
  System.err.println(DEFIB);
            defibList.add(new Defib(DEFIB));
        }

        // Write an action using System.out.println()
        // To debug: System.err.println("Debug messages...");
        
        Defib defib = getNearestDefib(defibList, lon, lat);

        System.out.println(defib.name);
    }
    
    static Defib getNearestDefib(List<Defib> defibList, double lon, double lat) {
    	double distance = 10000000000.0;
    	Defib defib = null;
    	for (Defib def : defibList) {
			double x = (lon - def.longitude) * (Math.cos((lat + def.latitude)/2));
			double y = lat - def.latitude;
			double d = Math.sqrt(x*x + y*y) * 6371;
			if (distance > d) {
				distance = d;
				defib = def;
			}
		}
    	return defib;    	
    }
    
    static class Defib {
    	int num;
    	String name;
    	String address;
    	String phone;
    	double longitude;
    	double latitude;
    	
		public Defib(String DEFIB) {
			super();
			String[] defib = DEFIB.split(";"); // 1;Maison de la ;6 rue Maguelone 340000;;3,87952263361082;43,6071285339217

			this.num = Integer.valueOf(defib[0]);
			this.name = defib[1];
			this.address = defib[2];
			this.phone = defib[3];
			this.longitude = Double.valueOf(defib[4].replace(',', '.'));
			this.latitude = Double.valueOf(defib[5].replace(',', '.'));
		}
    	
    	
    }
}