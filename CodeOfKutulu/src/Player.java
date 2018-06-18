
import java.util.*;
import java.io.*;
import java.math.*;

/**
 * Survive the wrath of Kutulu
 * Coded fearlessly by JohnnyYuge & nmahoude (ok we might have been a bit scared by the old god...but don't say anything)
 **/
class Player {
	
	static Explorer me;
	static List<Explorer> explorers = new ArrayList<>();
	static List<Wanderer> wanderers = new ArrayList<>();
	static CellMap cellMap = new CellMap();
	
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int width = in.nextInt();
        int height = in.nextInt();
        if (in.hasNextLine()) {
            in.nextLine();
        }
        for (int i = 0; i < height; i++) {
            String line = in.nextLine();  // # - wall,  w - spawn for wanderers,  . - empty cell
            cellMap.fillCellMapRow(line, i);
  //          System.err.println(height);
  //          System.err.println(line); // # - wall,  w - spawn for wanderers,  . - empty cell
           
        }
        
//        System.err.println("");
//        System.err.println(cellMap.toString(width, height));
        
  //      System.err.println("cells " + cellMap.cells.size());
        
        int sanityLossLonely = in.nextInt(); // how much sanity you lose every turn when alone, always 3 until wood 1
        int sanityLossGroup = in.nextInt(); // how much sanity you lose every turn when near another player, always 1 until wood 1
        int wandererSpawnTime = in.nextInt(); // how many turns the wanderer take to spawn, always 3 until wood 1
        int wandererLifeTime = in.nextInt(); // how many turns the wanderer is on map after spawning, always 40 until wood 1

        // game loop
        while (true) {
            int entityCount = in.nextInt(); // the first given entity corresponds to your explorer
            for (int i = 0; i < entityCount; i++) {
                String entityType = in.next(); // EXPLORER | WANDERER
                int id = in.nextInt();
                int x = in.nextInt();
                int y = in.nextInt();
                int param0 = in.nextInt();
                int param1 = in.nextInt();
                int param2 = in.nextInt();
        //        System.err.println("type:" + entityType+", id:" + id+", x:" + x+", y:" + y+", 0:" + param0+", 1:" + param1+", 2:" + param2);
                
                setAllEntities(i, entityType, x, y, id, param0, param1, param2);
                
            }

            // Write an action using System.out.println()
            // To debug: System.err.println("Debug messages...");

            System.out.println(doLogic()); // MOVE <x> <y> | WAIT
            
//            System.err.println("exp "+explorers.size());
//            System.err.println("wan "+wanderers.size());
            
            cleanEntityLists();
            
        }
    }
    
   
    
    static Explorer createExplorer(int x, int y, int id, int param0, int param1, int param2) {
		return new Explorer(x, y, id, param0, param1, param2);
    }
    
    static Wanderer createtWanderer(int x, int y, int id, int param0, int param1, int param2) {
    	return new Wanderer(x, y, id, param0, param1, param2);
    }
    
    static void setAllEntities(int i, String entityType, int x, int y, int id, int param0, int param1, int param2) {
    	if (i == 0) {
System.err.print("ME - ");
    		me = createExplorer(x, y, id, param0, param1, param2);    		
    	} 
    	else if (entityType.equals("EXPLORER")) {
    		explorers.add(createExplorer(x, y, id, param0, param1, param2));
    	}
    	else if (entityType.equals("WANDERER")) {
    		wanderers.add(createtWanderer(x, y, id, param0, param1, param2));
    	}    	
    }
    
    static void cleanEntityLists() {
    	explorers.clear();
    	wanderers.clear();
    }
    
    static String doLogic() {
    	String action = "WAIT";  // MOVE <x> <y> | WAIT
    	return action;
    }
    
    static class Entity {    	
    	int x;
    	int y;
    	int id;
		public Entity(int x, int y, int id) {
			super();
			this.x = x;
			this.y = y;
			this.id = id;
		}     	
    }
    
    static class Explorer extends Entity {
    	int sanity;
    	int param1;
    	int param2;
		
    	public Explorer(int x, int y, int id, int param0, int param1, int param2) {
			super(x, y, id);
			this.sanity = param0;
			this.param1 = param1;
			this.param2 = param2;
System.err.println(this);
		}

		@Override
		public String toString() {
			return "Explorer [x=" + x + ", y=" + y	+ ", id=" + id + ", sanity=" + sanity + ", param1=" + param1 + ", param2=" + param2 + "]";
		}   	
    }
    
    static class Wanderer extends Entity {
    	int beforeSpawnRecall;
    	int state; // SPAWNING = 0 , WANDERING = 1
    	int target; // -1 if no target (occurs only on spawn)    	
    	
    	public Wanderer(int x, int y, int id, int param0, int param1, int param2) {
			super(x, y, id);
			this.beforeSpawnRecall = param0;
			this.state = param1;
			this.target = param2;
System.err.println(this);
		}   	

		@Override
		public String toString() {
			return "Wanderer [x=" + x + ", y=" + y + ", id=" + id +", beforeSpawnRecall=" + beforeSpawnRecall + ", state=" + state + ", target=" + target
					+ "]";
		}
		
		interface State {
    		int SPAWNING = 0;
    		int WANDERING = 1;
    	}
    }
    
    static class CellMap {
    	
    	List<Cell> cells = new LinkedList<>();
    	
    	void fillCellMapRow(String line, int y) {
    		for (int x = 0; x < line.length(); x++) {
				Cell cell = new Cell(x, y, line.charAt(x));
    			cells.add(cell);
//System.err.print(cell.type.sign);
			}
//System.err.print('\n');
    	}   	
    	
		public String toString(int width, int height) {
			StringBuffer buf = new StringBuffer();
			int c = 0;
			for (int i = 0; i < height; i++) {			
				for (int j = 0; j < width; j++) {
					buf.append(cells.get(c++).toString());
				}
				buf.append('\n');
			}
    		return buf.toString();
		}

		static class Cell {
    		int x;
    		int y;
    		CellType type; // # - wall,  w - spawn for wanderers,  . - empty cell
    		
    		static enum CellType {
    			WALL('#'), SPAWN('w'), EMPTY('.');
    			
    			char sign;

				private CellType(char sign) {
					this.sign = sign;
				}    			
    		}

			public Cell(int x, int y, char type) {
				super();
				this.x = x;
				this.y = y;
				this.type = getType(type);
			}

			private CellType getType(char type) {
				switch (type) {
				case '#':
					return CellType.WALL;
				case 'w':
					return CellType.SPAWN;
				case '.':
					return CellType.EMPTY;
				default:
					throw new RuntimeException("unknown type " + type);
				}				
			}

			@Override
			public String toString() {
				return "" + type.sign;
			}			
    		
    	}
    }
    
        
}