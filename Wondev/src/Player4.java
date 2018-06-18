import java.util.*;
import java.io.*;
import java.math.*;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Player4v {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int size = in.nextInt();
        int unitsPerPlayer = in.nextInt();

		List<Unit> myUnitList = new ArrayList<>();
		List<Unit> enemyUnitList = new ArrayList<>();
		List<Cell> cellList = new ArrayList<>();


        // game loop
        while (true) {
            for (int i = 0; i < size; i++) { 
                String row = in.next();
				
                fillCellList(i, row, cellList);  // fills cellList row by row for every call in the loop   //////////////////				

            }
            
            for (int i = 0; i < unitsPerPlayer; i++) {
                int unitX = in.nextInt();
                int unitY = in.nextInt();

                myUnitList.add(new Unit(i, unitX, unitY)); // add my units to myUnitList    //////////////////////////////////
            }
            
            for (int i = 0; i < unitsPerPlayer; i++) {
                int otherX = in.nextInt();
                int otherY = in.nextInt();
                
                enemyUnitList.add(new Unit(otherX, otherY));
                
            }
            
            int legalActions = in.nextInt();
//System.err.println("legalActions count = "+legalActions);
            for (int i = 0; i < legalActions; i++) {
                String atype = in.next();
                int index = in.nextInt();
                String dir1 = in.next();
                String dir2 = in.next();
                
                Unit myUnit = myUnitList.get(index); // add legalActions to Unit's LegalActionList for each my unit //////////                
                myUnit.legalActionList.add(new LegalAction(atype, index, dir1, dir2, myUnit.unitX, myUnit.unitY, cellList));
                
                
//System.err.println("atype = "+atype);
//System.err.println("index = "+index);
//System.err.println("dir1 = "+dir1);
//System.err.println("dir2 = "+dir2);
            }

            // Write an action using System.out.println()
            // To debug: System.err.println("Debug messages...");
            
            Unit unit = myUnitList.get(0);
            if (unit.legalActionList.size() < 1) {
            	unit = myUnitList.get(1);
            }
            
            String action = unit.legalActionList.size() > 0 ? getAction(unit.legalActionList) : "no way";

System.err.println("= "+action);
            
            System.out.println(action);
            
            myUnitList.clear();
            cellList.clear();
        }
    }
    
   static String getAction(List<LegalAction> legalActionList) {
	   
	   for (LegalAction action : legalActionList) {
		   if (action.atype.equals("PUSH&BUILD")) {
			   return action.toString();
		   }
	   }
	    
	   for (LegalAction action : legalActionList) {
		   if (action.dir1Level == 3 && action.dir2Level ==2) {
System.err.println("dir1 Level = "+action.dir1Level);
System.err.println("dir2 Level = "+action.dir2Level);
System.err.println("dir1 = "+action.dir1);
				return action.toString();
				}
	   }
	   for (LegalAction action : legalActionList) {
		   if (action.dir1Level == 3 && action.dir2Level ==1) {
System.err.println("dir1 Level = "+action.dir1Level);
System.err.println("dir2 Level = "+action.dir2Level);
System.err.println("dir1 = "+action.dir1);
				return action.toString();
				}
	   }
	   for (LegalAction action : legalActionList) {
		   if (action.dir1Level == 3 && action.dir2Level ==0) {
System.err.println("dir1 Level = "+action.dir1Level);
System.err.println("dir2 Level = "+action.dir2Level);
System.err.println("dir1 = "+action.dir1);
				return action.toString();
				}
	   }
	   for (LegalAction action : legalActionList) {
		   if (action.dir1Level == 3 && action.dir2Level ==3) {
System.err.println("dir1 Level = "+action.dir1Level);
System.err.println("dir2 Level = "+action.dir2Level);
System.err.println("dir1 = "+action.dir1);
				return action.toString();
				}
	   }
	   //////////////////////////////////////////////////////////////////////////////////////////////////////
	   for (LegalAction action : legalActionList) {
			if (action.dir1Level == 2 && action.dir2Level ==2) {
System.err.println("dir1 Level = "+action.dir1Level);
System.err.println("dir2 Level = "+action.dir2Level);
System.err.println("dir1 = "+action.dir1);
				return action.toString();
				}
		   }
	   for (LegalAction action : legalActionList) {
			if (action.dir1Level == 2 && action.dir2Level ==1) {
System.err.println("dir1 Level = "+action.dir1Level);
System.err.println("dir2 Level = "+action.dir2Level);
System.err.println("dir1 = "+action.dir1);
				return action.toString();
				}
		   }
	   for (LegalAction action : legalActionList) {
			if (action.dir1Level == 2 && action.dir2Level ==0) {
System.err.println("dir1 Level = "+action.dir1Level);
System.err.println("dir2 Level = "+action.dir2Level);
System.err.println("dir1 = "+action.dir1);
				return action.toString();
				}
		   }
	   ///////////////////////////////////////////////////////////////////////////////////////////////////
	   for (LegalAction action : legalActionList) {
			if (action.dir1Level == 1 && action.dir2Level ==1) {
System.err.println("dir1 Level = "+action.dir1Level);
System.err.println("dir2 Level = "+action.dir2Level);
System.err.println("dir1 = "+action.dir1);
				return action.toString();
				}
		   }
	   for (LegalAction action : legalActionList) {
			if (action.dir1Level == 1 && action.dir2Level ==0) {
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
    
   static class LegalAction { // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    	
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
//System.err.println("---------------------------------------------");
//System.err.println("index   dir1    dir2     dir1-level   dir2-level");
//System.err.println(" "+this.index+"    "+this.dir1+"   "+this.dir2+"    "+this.dir1Level+"     "+this.dir2Level);
		
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
   
   static class Unit {
	   
	   int index;
	   int unitX;
	   int unitY;
	   List<LegalAction> legalActionList = new ArrayList<>();
	
	   public Unit(int index, int unitX, int unitY) {
			super();
			this.index = index;
			this.unitX = unitX;
			this.unitY = unitY;
	   }

		public Unit(int unitX, int unitY) {
			super();
			this.unitX = unitX;
			this.unitY = unitY;
			this.index = -5;
		}
		
		boolean isNear(Unit otherUnit) {
			if (Math.abs(this.unitX - otherUnit.unitX) <= 1 && Math.abs(this.unitY - otherUnit.unitY) <= 1) {
				return true;
			}
			return false;
		}
	   
   }
   
   static void fillCellList(int i, String row, List<Cell> cellList) { // fills cellList row by row for every call
		
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