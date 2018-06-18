import java.util.*;
import java.io.*;
import java.math.*;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Player1v {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int size = in.nextInt();
        int unitsPerPlayer = in.nextInt();
System.err.println("size = "+size);
System.err.println("unitsPerPlayer = "+unitsPerPlayer);

		List<LegalAction> legalActionList = new ArrayList<>();
		List<Cell> cellList = new ArrayList<>();
		int unitX = -1;
		int unitY = -1;

        // game loop
        while (true) {
            for (int i = 0; i < size; i++) { // y=i, x=j, level=rowChars[j]
                String row = in.next();
System.err.println("row = "+row);
				
				{  // this block fill cellList through parsing "row"
					int level;
					char[] rowChars = row.toCharArray();
					
					for (int j = 0; j < rowChars.length; j++) {
						if (rowChars[j] == '.') {
							level = -1;
						} else {
							level = Integer.parseInt(""+rowChars[j]);
						}						
						cellList.add(new Cell(j, i, level));
					}
				}

            }
            for (int i = 0; i < unitsPerPlayer; i++) {
                unitX = in.nextInt();
                unitY = in.nextInt();
System.err.println("unitX = "+unitX);
System.err.println("unitY = "+unitY);
            }
            for (int i = 0; i < unitsPerPlayer; i++) {
                int otherX = in.nextInt();
                int otherY = in.nextInt();
System.err.println("otherX = "+otherX);
System.err.println("otherY = "+otherY);
            }
            int legalActions = in.nextInt();
System.err.println("legalActions = "+legalActions);
            for (int i = 0; i < legalActions; i++) {
                String atype = in.next();
                int index = in.nextInt();
                String dir1 = in.next();
                String dir2 = in.next();
                
                legalActionList.add(new LegalAction(atype, index, dir1, dir2, unitX, unitY, cellList));
                
System.err.println("atype = "+atype);
System.err.println("index = "+index);
System.err.println("dir1 = "+dir1);
System.err.println("dir2 = "+dir2);
            }

            // Write an action using System.out.println()
            // To debug: System.err.println("Debug messages...");
            
            String action = legalActionList.get(0).toString();

System.err.println("= "+action);
            
            System.out.println(action);
            
            legalActionList.clear();
        }
    }
    
   static class LegalAction {
    	
    	String atype;
    	int index;
    	String dir1;
    	String dir2;
    	int dir1Level;
    	int dir2Level;
    	
		public LegalAction(String atype, int index, String dir1, String dir2, int unitX, int unitY, List<Cell> cellList) {
			super();
			this.atype = atype;
			this.index = index;
			this.dir1 = dir1;
			this.dir2 = dir2;
		}

		@Override
		public String toString() {
			return atype + " " + index + " " + dir1 + " " + dir2;
		}    	
    }
   
   static class Cell {
	   
	   int x;
	   int y;
	   int level;
	   
		public Cell(int x, int y, int level) {
			super();
			this.x = x;
			this.y = y;
			this.level = level;
		}	   
   }
}