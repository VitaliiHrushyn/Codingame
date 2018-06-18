import java.util.*;
import java.io.*;
import java.math.*;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Player2v {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int size = in.nextInt();
        int unitsPerPlayer = in.nextInt();
//System.err.println("size = "+size);
//System.err.println("unitsPerPlayer = "+unitsPerPlayer);

		List<LegalAction> legalActionList = new ArrayList<>();
		List<Cell> cellList = new ArrayList<>();
		int unitX = -1;
		int unitY = -1;

        // game loop
        while (true) {
            for (int i = 0; i < size; i++) { // y=i, x=j, level=rowChars[j]
                String row = in.next();
//System.err.println("row = "+row);
				
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
//System.err.println("unitX = "+unitX);
//System.err.println("unitY = "+unitY);
            }
            for (int i = 0; i < unitsPerPlayer; i++) {
                int otherX = in.nextInt();
                int otherY = in.nextInt();
//System.err.println("otherX = "+otherX);
//System.err.println("otherY = "+otherY);
            }
            int legalActions = in.nextInt();
//System.err.println("legalActions = "+legalActions);
            for (int i = 0; i < legalActions; i++) {
                String atype = in.next();
                int index = in.nextInt();
                String dir1 = in.next();
                String dir2 = in.next();
                
                legalActionList.add(new LegalAction(atype, index, dir1, dir2, unitX, unitY, cellList));
                
//System.err.println("atype = "+atype);
//System.err.println("index = "+index);
//System.err.println("dir1 = "+dir1);
//System.err.println("dir2 = "+dir2);
            }

            // Write an action using System.out.println()
            // To debug: System.err.println("Debug messages...");
            
            String action = legalActionList.size() > 0 ? action = getAction(legalActionList) : "***";

System.err.println("= "+action);
            
            System.out.println(action);
            
            legalActionList.clear();
            cellList.clear();
        }
    }
    
   static String getAction(List<LegalAction> legalActionList) {
	   	   
	   for (LegalAction action : legalActionList) {
		   if (action.dir1Level == 3 && action.dir2Level !=3) {
System.err.println("dir1 Level = "+action.dir1Level);
System.err.println("dir2 Level = "+action.dir2Level);
System.err.println("dir1 = "+action.dir1);
				return action.toString();
				}
	   }
	   for (LegalAction action : legalActionList) {
			if (action.dir1Level == 2) {
System.err.println("dir1 Level = "+action.dir1Level);
System.err.println("dir2 Level = "+action.dir2Level);
System.err.println("dir1 = "+action.dir1);
				return action.toString();
				}
		   }
	   for (LegalAction action : legalActionList) {
			if (action.dir1Level == 1) {
System.err.println("dir1 Level = "+action.dir1Level);
System.err.println("dir2 Level = "+action.dir2Level);
System.err.println("dir1 = "+action.dir1);
				return action.toString();
				}
		   }
System.err.println("dir1 Level = "+legalActionList.get(0).dir1Level);
System.err.println("dir2 Level = "+legalActionList.get(0).dir2Level);
System.err.println("dir1 = "+legalActionList.get(0).dir1);
	   return legalActionList.get(0).toString();
	   
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
			getCellLevels(unitX, unitY, cellList);
		
		}

		@Override
		public String toString() {
			return atype + " " + index + " " + dir1 + " " + dir2;
		}
		void getCellLevels(int unitX, int unitY, List<Cell> cellList) {
			   
			   int x = -2;
			   int y = -2;
			   
			   Cell cella = null;
			   
			   switch(dir1){
				   case "N":
					   x = unitX;
					   y = unitY - 1;
					   break;
				   case "NE":
					   x = unitX + 1;
					   y = unitY - 1;
					   break;
				   case "E":
					   x = unitX + 1;
					   y = unitY;
					   break;
				   case "SE":
					   x = unitX + 1;
					   y = unitY + 1;
					   break;
				   case "S":
					   x = unitX;
					   y = unitY + 1;
					   break;
				   case "SW":
					   x = unitX - 1;
					   y = unitY + 1;
					   break;
				   case "W":
					   x = unitX - 1;
					   y = unitY;
					   break;
				   case "NW":
					   x = unitX - 1;
					   y = unitY - 1;
					   break;
			   }
			   
			   for (Cell cell : cellList) {
					if (cell.x == x && cell.y == y) {
						dir1Level = cell.level;
						cella = cell;
					}
			   }
			   
			   int cellX = cella.x;
			   int cellY = cella.y;
			   
			   switch(dir2) {
			   
			   case "N":
				   x = cellX;
				   y = cellY - 1;
				   break;
			   case "NE":
				   x = cellX + 1;
				   y = cellY - 1;
				   break;
			   case "E":
				   x = cellX + 1;
				   y = cellY;
				   break;
			   case "SE":
				   x = cellX + 1;
				   y = cellY + 1;
				   break;
			   case "S":
				   x = cellX;
				   y = cellY + 1;
				   break;
			   case "SW":
				   x = cellX - 1;
				   y = cellY + 1;
				   break;
			   case "W":
				   x = cellX - 1;
				   y = cellY;
				   break;
			   case "NW":
				   x = cellX - 1;
				   y = cellY - 1;
				   break;
			   }
			   
			   for (Cell cell : cellList) {
					if (cell.x == x && cell.y == y) {
						dir2Level = cell.level;
					}
			   }
			   
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