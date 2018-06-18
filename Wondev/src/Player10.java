import java.util.*;
import java.io.*;
import java.math.*;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Player10v {
	
	static List<Unit> myUnitList = new ArrayList<>();
	static List<Unit> enemyUnitList = new ArrayList<>();
	static List<Cell> cellList = new ArrayList<>();
	static List<LegalAction> actionList = new ArrayList<>();
	static Cell unit0Cell;
	static Cell unit1Cell;
//	static int coef = 1;
	

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int size = in.nextInt();
        int unitsPerPlayer = in.nextInt();

		
		Poem poem = new Poem();


        // game loop
        while (true) {
        	
            for (int i = 0; i < size; i++) { 
                String row = in.next();
				
                fillCellList(i, row, cellList);  // fills cellList row by row for every call in the loop   //////////////////				

            }
            int unit0X = -1;
            int unit0Y = -1;
            int unit1X = -1;
            int unit1Y = -1;
            
            for (int i = 0; i < unitsPerPlayer; i++) {
                int unitX = in.nextInt();
                int unitY = in.nextInt();

                if(i == 0) {
                	unit0X = unitX;
                    unit0Y = unitY;
                    unit0Cell = getCell(unit0X, unit0Y, cellList);
                } else {
                	unit1X = unitX;
                    unit1Y = unitY;
                    unit1Cell = getCell(unit1X, unit1Y, cellList);
                }
            }           
            	myUnitList.add(new Unit(0, unit0X, unit0Y, cellList)); // add my units to myUnitList    //////////////////////////////////
            	myUnitList.add(new Unit(1, unit1X, unit1Y, cellList)); // add my units to myUnitList    //////////////////////////////////
            
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
 
            }

            // Write an action using System.out.println()
            // To debug: System.err.println("Debug messages...");
            
      //      Graph graph = new Graph();
            
       //     for (Point point : graph.allPointsList) {
		///		System.err.println("x y rate size: "+point.position.x+" "+point.position.y+" "+point.position.rate+" "+point.connectedPoints.size());
		//	}
//            for (int i = 0; i < 1; i++) {
//            	Point point = graph.allPointsList.get(i);
//            	System.err.println("x y rate size: "+point.position.x+" "+point.position.y+" "+point.position.rate+" "+point.connectedPoints.size());
//            	
//            	for (int j = 0; j < point.connectedPoints.size(); j++) {
//                	Point point1 = point.connectedPoints.get(j);
//                	System.err.println("inner: x y rate size: "+point1.position.x+" "+point1.position.y+" "+point1.position.rate+" "+point1.connectedPoints.size());
//                	
//                	for (int k = 0; k < point.connectedPoints.size(); k++) {
//                    	Point point2 = point.connectedPoints.get(k);
//                    	System.err.println("double inner: x y rate size: "+point2.position.x+" "+point2.position.y+" "+point2.position.rate+" "+point2.connectedPoints.size());
//        			}
//            	
//            	}
//            }
            
            
            
            

		//	int unit1Rate = getTotalPointsRate(getPoint(myUnitList.get(1).unitCell, graph.allPointsList), graph.allPointsList);
//System.err.println("unit 1 rate = " + unit1Rate);
            
            for (Cell cell : cellList) {
				cell.setConnectedCellsList();
			}
            
            List<Cell> copy0CellList = new ArrayList<>(cellList);
            List<Cell> copy1CellList = new ArrayList<>(cellList);
            
           
//            int unit0Rate = getTotalCellRate(myUnitList.get(0).unitCell, copyCellList);
//System.err.println("unit 0 rate = " + unit0Rate);

            int unit0Rate = myUnitList.get(0).unitCell.getTotalCellRate(copy0CellList);
System.err.println("unit 0 x y: rate = " + myUnitList.get(0).unitCell.x+" "+myUnitList.get(0).unitCell.y+": "+ unit0Rate);
            int unit1Rate = myUnitList.get(1).unitCell.getTotalCellRate(copy1CellList);
System.err.println("unit 1 x y: rate = " + myUnitList.get(1).unitCell.x+" "+myUnitList.get(1).unitCell.y+": "+ unit1Rate);
            
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

//System.err.println(" "+this.index+" "+atype+" "+this.dir1+" "+this.dir2+" "+this.dir1Cell.level+" "+this.dir2Cell.level);
					
		}
		
		private int setRate() {
			int rate = -10;
			int dir1Level = dir1Cell.level;
			int dir2Level = dir2Cell.level;
			
			if (atype.equals("PUSH&BUILD")) {
				
				if(dir1Level == 0) {
					if(dir2Level == 0) rate = -100;
					if(dir2Level == 1) rate = -100;
					if(dir2Level == 2) rate = -100;
					if(dir2Level == 3) rate = -100;
				}
				if(dir1Level == 1) {
					if(dir2Level == 0) rate = 10;
					if(dir2Level == 1) rate = -100;
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
			    
			   // dir2Level = dir2Cell.level+1;
				
				if(dir1Level == 0) {
					if(dir2Level == 0) rate = 8;
					if(dir2Level == 1) rate = 0;
					if(dir2Level == 2) rate = 0;
					if(dir2Level == 3) rate = 0;
				}
				if(dir1Level == 1) {
					if(dir2Level == 0) rate = 10;
					if(dir2Level == 1) rate = 11;
					if(dir2Level == 2) rate = 0;
					if(dir2Level == 3) rate = 0;
				}
				if(dir1Level == 2) {
					if(dir2Level == 0) rate = 6;
					if(dir2Level == 1) rate = 12;
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
	   int rate; // for new opportunities :)
	   List<Cell> connectedCells;
	   
		public Cell(int x, int y, int level) {
			super();
			this.x = x;
			this.y = y;
			this.level = level;
	//		this.rate  = ((level) + 1) * (level + 1);
		}		
		
		@Override
		public boolean equals(Object obj) {
			Cell other = (Cell) obj;
			if (this.x == other.x && this.y == other.y) return true;
			return false;
		}
		
		public void setConnectedCellsList() {
			this.connectedCells = new ArrayList<>();
			int cellLevel;
			for (Cell cell : cellList) {
				cellLevel = cell.level;
				if (!cell.equals(unit0Cell) && !cell.equals(unit1Cell) && !this.equals(cell) && Math.abs(this.x - cell.x) < 2 && Math.abs(this.y - cell.y) < 2 && 
						cellLevel < 4 && cellLevel > -1 &&  this.level - cellLevel > -2) {
					this.connectedCells.add(cell);
				}
			}
		}
		
		public void setAllCellsRate() {
			for (Cell cell : cellList) {
				
			}
		}
		
		 
		
/*		void setConnectedCellsList(List<Cell> cellList, List<Line> lineList) {
			Cell startCell = this;
			this.connectedCells = new ArrayList<>();
			List<Cell> accessibleCells = startCell.getAccessibleCellsList(cellList);
			Line line;
			for (Cell cell : accessibleCells) {			
				if(!lineList.contains(line = new Line(startCell, cell))) {  
					lineList.add(line);					
					this.connectedCells.add(getPoint(startCell, allPointsList));
				}
			}			
		}
	*/
	
		
   }
   
//=========================================================================================================================
   
   static Cell getCell(int x, int y, List<Cell> cellList) { // get cell by coord or by unit (position)
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
	   
	   public Unit(int unitX, int unitY, List<Cell> cellList) { // used for enemies, index = -1
			super();
			this.unitX = unitX;
			this.unitY = unitY;
			this.index = -1;
			this.unitCell = getCell(this, cellList);
		}
	
	   public Unit(int index, int unitX, int unitY, List<Cell> cellList) {
			this(unitX, unitY, cellList);
			this.index = index;		
	   }

//		Map<Integer, Cell> getPriorityMap() {
//			Map<Integer, Cell> map = new HashMap<>();
//			List<Cell> connectedCells = this.unitCell.connectedCells;
//			for (Cell cell : connectedCells) {
//				cell.getTotalCellRate(copyCellList)
//			}
//		}
	   
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
   
   static Unit getUnit (int index, List<Unit> unitList) {  // return unit by index from unitList
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
	   int rate = -10000;
	   for (LegalAction legalAction : actions) {
		   if(legalAction.rate > rate) {
			   rate = legalAction.rate;
			   action = legalAction;
		   }
	   }
	   return action;
   }
//==========================================================================================================================
/*  
   
	static class Line {
	   Cell begin;
	   Cell end;
	
	   public Line(Cell begin, Cell end) {
		super();
		this.begin = begin;
		this.end = end;
	   }

		@Override
		public boolean equals(Object obj) {
			Line other = (Line) obj;
			if (this.begin.equals(other.begin) && this.end.equals(other.end)) {
				return true;
			}
			return false;
		}	   
	   
   }
   */

//==========================================================================================================================
/*
	static class Point {
		Cell position;
		List<Point> connectedPoints;
		
//		int rate;
		
		
		
		Point(Cell position, List<Cell> cellList, List<Line> lineList) {
			super();
			this.position = position;
		}		
		
		void setConnectedPointsList(List<Cell> cellList, List<Line> lineList, List<Point> allPointsList) {
			Cell startCell = this.position;
			this.connectedPoints = new ArrayList<>();
			List<Cell> accessibleCells = startCell.getAccessibleCellsList(cellList);
			Line line;
			for (Cell cell : accessibleCells) {			
				if(!lineList.contains(line = new Line(startCell, cell))) {  
					lineList.add(line);					
					this.connectedPoints.add(getPoint(startCell, allPointsList));
				}
			}			
		}
		
		int getConnectedPointsRate () {
//System.err.println("getConnectedPR: start");
			int rate = - 1;
			for (Point point : this.connectedPoints) {
				
				rate += point.position.rate;
System.err.println("Cell rate = "+point.position.rate+", point rate = "+rate);
			}
			return rate;
		}
	}
	
*/
//=========================================================================================================================	
	/*
	static class Graph {
		List<Line> lineList;
		
		public Graph() {
			super();
			
			lineList = new ArrayList<>();
			for (Cell cell : cellList) {
				if (cell.level < 4 && cell.level > -1 ) {
					allPointsList.add(new Point(cell, cellList, lineList));
				}
			}
			for (Point point : allPointsList) {
				point.setConnectedPointsList(cellList, lineList, allPointsList);
			}
/*
System.err.println("point 15: x, y, level, con.size " + allPointsList.get(15).position.x+" "+ allPointsList.get(15).position.y+
		" "+ allPointsList.get(15).position.level+" "
		+ allPointsList.get(15).connectedPoints.size());
*//*
		}
	} */
//=======================================================================================================================
	/*	
		static Point getPoint(Cell position, List<Point> allPointsList) {
			for (Point point : allPointsList) {
				if (point.position.equals(position)) {					
					return point;
				}				
			}			
			return null;
		}
	*/	
//=======================================================================================================================
		
		static int getTotalCellRate (Cell startCell, List<Cell> copyCellList) {
			int totalRate = startCell.rate;
					
			if (!copyCellList.contains(startCell)) {
				return totalRate;
			} else {
				for (Cell cell : startCell.connectedCells) {
				//	totalRate += cell.rate;
					copyCellList.remove(cell);
					totalRate += getTotalCellRate(cell, copyCellList);
				}
			}
			return -1;
		}
		
//=======================================================================================================================
		
		
		
}