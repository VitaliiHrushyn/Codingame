import java.util.*;
import java.io.*;
import java.math.*;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Player7v {

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
		
		public Cell getNextAvaliableCell(List<Cell> cellList) {
//System.err.println("Cell List size = " + cellList.size());
	/*		for (Cell cell : cellList) {
				if (cell.level < 4 && cell.level > -1 && Math.abs(this.x - cell.x) <= 1 && 
						Math.abs(this.y - cell.y) <= 1 && this.level - cell.level >= -1) {
//System.err.println("next Cell x = "+cell.x+", y = "+cell.y);
					return cell;
				}
			}   */
//System.err.println("--- !!! --- next Cell - NULL ");
			return cellList.get(0); //null;
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
//	   Graph graph;
	   List<Line> lineList;
	   Point point;
	   
	   
	   public Unit(int unitX, int unitY, List<Cell> cellList) { // used for enemies, index = -1
			super();
			this.unitX = unitX;
			this.unitY = unitY;
			this.index = -1;
			this.unitCell = getCell(this, cellList);
//			this.graph = setGraph(this, cellList);
			this.lineList = new ArrayList<>();
System.err.println("Unit constructor before point ");
			this.point = new Point(unitCell, cellList, lineList);
System.err.println("Unit constructor before point ");
		}
	
	   public Unit(int index, int unitX, int unitY, List<Cell> cellList) {
			this(unitX, unitY, cellList);
			this.index = index;
			

			
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
   
   static class Graph {
	   Point startPosition;
	   //List<Point> pointList = new ArrayList<>();
	   List<Line> lineList = new ArrayList<>();
	//   int rate;
	//   int size;
   }
   
//==========================================================================================================================
   
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
			if ((this.begin.equals(other.begin) && this.end.equals(other.end)) || (this.begin.equals(other.end) && this.end.equals(other.begin))) {
				return true;
			}
			return false;
		}	   
	   
   }
   
//==========================================================================================================================
   /* it's old version 
   
   static Graph getGraph(Unit unit, List<Cell> cellList) {
	   List<Graph> graphList = new ArrayList<>();
	   Graph graph = new Graph();
	   Cell begin = unit.unitCell;
	   Cell end = begin.getNextCell(cellList);
	   if (end != null) {
		   graphList.add(new Graph());
		   graphList.get(0).lineList.add(new Line(begin, end));
	   }
	   
	   
	   return graphList;
   } */
//==========================================================================================================================

	static class Point {
		Cell position;
		List<Point> connectedPoints = new ArrayList<>();
		
		int getRate; // summ rates of all connected positions
		
		public Point(Cell position, List<Cell> cellList, List<Line> lineList) {
			super();
			this.position = position;
			this.connectedPoints = getPointList(this, cellList, lineList);
System.err.println("new Point: x = "+this.position.x+", y = "+this.position.y+", level = "+this.position.level);
		}
		
		
	}
	
//===========================================================================================================================
/*
	static Graph createGraph(Unit unit, List<Cell> cellList) {
		Cell start = unit.unitCell;
		Graph graph = new Graph();
		graph.pointList.addAll(getPointList(graph, start, cellList, graph.lineList));
		
		return graph;	
		
	}  */
//==========================================================================================================================	
	static List<Point> getPointList(Point startPoint, List<Cell> cellList, List<Line> lineList) {
System.err.println("getPointList 1 method");
		List<Point> pointList = new ArrayList<>();
System.err.println("getPointList 2 method");
		Point nextPoint;
System.err.println("getPointList 3 method");
if(getNextPointCell(startPoint, cellList, lineList) == null) {
	System.err.println("getPointList 4 method IS NULL");
}
if(getNextPointCell(startPoint, cellList, lineList) != null) {
	System.err.println("getPointList 4 method NOT NULL");
}

		while(getNextPointCell(startPoint, cellList, lineList) != null) {
System.err.println("getPointList while 1 method");
			nextPoint = new Point(getNextPointCell(startPoint, cellList, lineList), cellList, lineList);
System.err.println("getPointList while 2 method");
			pointList.add(nextPoint);
System.err.println("+++pointList.size() "+pointList.size());
		}		
		return pointList;
	}
//==========================================================================================================================
	static Cell getNextPointCell (Point startPoint, List<Cell> cellList, List<Line> lineList) {
		Cell findingCell;
		Line newLine;
		
			if ((findingCell = startPoint.position.getNextAvaliableCell(cellList)) != null && 
					!lineList.contains(newLine = new Line(startPoint.position, findingCell))) {
				lineList.add(newLine);
System.err.println("findingCell x = "+findingCell.x+", y = "+findingCell.y);
System.err.println("lineList.size = "+lineList.size());
System.err.println("newLine = "+newLine);
				return findingCell;
			}
		
System.err.println("--- return NULL - getNextPointCell");		
		return null;
	}
}