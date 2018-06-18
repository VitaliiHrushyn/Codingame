import java.util.*;
import java.io.*;
import java.math.*;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Player5v {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int size = in.nextInt();
        int unitsPerPlayer = in.nextInt();

		List<Unit> myUnitList = new ArrayList<>();
		List<Unit> enemyUnitList = new ArrayList<>();
		List<Cell> cellList = new ArrayList<>();
		List<LegalAction> actionList = new ArrayList<>();
		Poem poem = new Poem();


        // game loop
        while (true) {
            for (int i = 0; i < size; i++) { 
                String row = in.next();
				
                fillCellList(i, row, cellList);  // fills cellList row by row for every call in the loop   //////////////////				

            }
            
            for (int i = 0; i < unitsPerPlayer; i++) {
                int unitX = in.nextInt();
                int unitY = in.nextInt();

                myUnitList.add(new Unit(i, unitX, unitY, cellList)); // add my units to myUnitList    //////////////////////////////////
            }
            
            for (int i = 0; i < unitsPerPlayer; i++) {
                int otherX = in.nextInt();
                int otherY = in.nextInt();
                
                enemyUnitList.add(new Unit(otherX, otherY, cellList));
                
            }
            
            int legalActions = in.nextInt();
//System.err.println("legalActions count = "+legalActions);
            for (int i = 0; i < legalActions; i++) {
                String atype = in.next();
                int index = in.nextInt();
                String dir1 = in.next();
                String dir2 = in.next();
                    
                actionList.add(new LegalAction(atype, index, dir1, dir2, cellList, myUnitList));
                
                
//System.err.println("atype = "+atype);
//System.err.println("index = "+index);
//System.err.println("dir1 = "+dir1);
//System.err.println("dir2 = "+dir2);
            }

            // Write an action using System.out.println()
            // To debug: System.err.println("Debug messages...");
            
//        Unit unit = myUnitList.get(0);
//        if (unit.legalActionList.size() < 1) {
//        	unit = myUnitList.get(1);
//        }
            
            String action = actionList.size() > 0 ? getAction(actionList).toString() : "no way";

//System.err.println("= "+action);
            
            System.out.println(action+" "+poem.getNextRow());
            
            myUnitList.clear();
            enemyUnitList.clear();
            cellList.clear();
            actionList.clear();
        }
    }
    
//=====================================================================================================================

   static class LegalAction { // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    	
    	String atype;
    	int index;
    	String dir1;
    	String dir2;
    	Cell dir1Cell;
    	Cell dir2Cell;    	
  
    	int rate = -7;
    	
		public LegalAction(String atype, int index, String dir1, String dir2, List<Cell> cellList, List<Unit> unitList) {
			super();
			this.atype = atype;
			this.index = index;
			this.dir1 = dir1;
			this.dir2 = dir2;
			setDirCell(index, cellList, unitList);
			this.rate = setRate();

System.err.println(" "+this.index+" "+atype+" "+this.dir1+" "+this.dir2+" "+this.dir1Cell.level+" "+this.dir2Cell.level);
					
		}
		
		private int setRate() {
			int rate = -10;
			int dir1Level = dir1Cell.level;
			int dir2Level = dir2Cell.level;
			
			if (atype.equals("PUSH&BUILD")) {
				
				if(dir1Level == 0) {
					if(dir2Level == 0) rate = 8;
					if(dir2Level == 1) rate = -100;
					if(dir2Level == 2) rate = -100;
					if(dir2Level == 3) rate = -100;
				}
				if(dir1Level == 1) {
					if(dir2Level == 0) rate = 10;
					if(dir2Level == 1) rate = 9;
					if(dir2Level == 2) rate = -100;
					if(dir2Level == 3) rate = -100;
				}
				if(dir1Level == 2) {
					if(dir2Level == 0) rate = 14;
					if(dir2Level == 1) rate = 11;
					if(dir2Level == 2) rate = -100;
					if(dir2Level == 3) rate = -100;
				}
				if(dir1Level == 3) {
					if(dir2Level == 0) rate = 16;
					if(dir2Level == 1) rate = 15;
					if(dir2Level == 2) rate = 13;
					if(dir2Level == 3) rate = 12;
				}
				
				rate += 100;
			}
			
			else if (atype.equals("MOVE&BUILD")) {
				
				if(dir1Level == 0) {
					if(dir2Level == 0) rate = 10;
					if(dir2Level == 1) rate = 0;
					if(dir2Level == 2) rate = 0;
					if(dir2Level == 3) rate = 0;
				}
				if(dir1Level == 1) {
					if(dir2Level == 0) rate = 8;
					if(dir2Level == 1) rate = 14;
					if(dir2Level == 2) rate = 7;
					if(dir2Level == 3) rate = 0;
				}
				if(dir1Level == 2) {
					if(dir2Level == 0) rate = 6;
					if(dir2Level == 1) rate = 11;
					if(dir2Level == 2) rate = 13;
					if(dir2Level == 3) rate = 0;
				}
				if(dir1Level == 3) {
					if(dir2Level == 0) rate = 14;
					if(dir2Level == 1) rate = 15;
					if(dir2Level == 2) rate = 16;
					if(dir2Level == 3) rate = 9;
				}				
			}
			
			return rate;
		}
		
		private void setDirCell(int index, List<Cell> cellList, List<Unit> unitList) {
				Unit unit = getUnit(index, unitList);
				int unitX = unit.unitX;
				int unitY = unit.unitY;
				int x = -1;
				int y = -1;

				switch(dir1) {
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
						dir1Cell = cell;
					}
			   }
				int cellX = dir1Cell.x;
				int cellY = dir1Cell.y;			   
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
							dir2Cell = cell;
						}
				   }	
		}
		
		@Override
		public String toString() {
			return atype + " " + index + " " + dir1 + " " + dir2;
		}
  }
   
//======================================================================================================================   
   
   static class Cell {
	   
	   int x;
	   int y;
	   int level;
	   
		public Cell(int x, int y, int level) {
			super();
			this.x = x;
			this.y = y;
			this.level = level;
//System.err.println("new Cell x = "+this.x+", y = "+this.y+", level = "+this.level);
		}

		public boolean equals(Cell cell) {
			if (this.x == cell.x && this.y == cell.y) return true;
			return false;
		}
		
		
   }
   
//=========================================================================================================================
   
   static Cell getCell(int x, int y, List<Cell> cellList) {
		for (Cell cell : cellList) {
			if (cell.x == x && cell.y == y) {
				return cell;
			}
		}
		return null;
	}
   static Cell getCell(Unit unit, List<Cell> cellList) {
		for (Cell cell : cellList) {
			if (cell.x == unit.unitX && cell.y == unit.unitY) {
				return cell;
			}
		}
		return null;
	}
   
//==========================================================================================================================
   
   static class Unit {
	   
	   int index;
	   int unitX;
	   int unitY;
	   Cell unitCell;
	
	   public Unit(int index, int unitX, int unitY, List<Cell> cellList) {
			super();
			this.index = index;
			this.unitX = unitX;
			this.unitY = unitY;
			this.unitCell = getCell(this, cellList);
	   }

		public Unit(int unitX, int unitY, List<Cell> cellList) {
			super();
			this.unitX = unitX;
			this.unitY = unitY;
			this.index = -5;
			this.unitCell = getCell(this, cellList);
		}
		
		boolean isNear(Unit otherUnit) {
			if (Math.abs(this.unitX - otherUnit.unitX) <= 1 && Math.abs(this.unitY - otherUnit.unitY) <= 1) {
				return true;
			}
			return false;
		}
	   
   }
   
 //=====================================================================================================================
   
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
   
//=======================================================================================================================
   
   static class Poem {
	   String[] rows = {"To be, or not to be -","- that is the question:",
		   "Whether 'tis nobler...","...in the mind to suffer",
		   "The slings and arrows...","...of outrageous fortune",
		   "Or to take arms...","...against a sea of troubles",
		   "And by opposing...","...end them."};
	 
	   int index = 0;	   
	   
	   String getNextRow() {
		   if (index == rows.length) index = 0;
		   return rows[index++];
	   }
   }
   
//=========================================================================================================================
   
   static Unit getUnit (int index, List<Unit> unitList) {
	   for (Unit unit : unitList) {
		   if (unit.index == index) {
				return unit;
		   }
	   }
	   return null;
   }
//==========================================================================================================================
   
   static LegalAction getAction(List<LegalAction> actions) {
	   LegalAction action = null;
	   int rate = -11;
	   for (LegalAction legalAction : actions) {
		   if(legalAction.rate > rate) {
			   rate = legalAction.rate;
			   action = legalAction;
		   }
	   }
	   return action;
   }
   
}