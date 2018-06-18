
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

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int width = in.nextInt();
        int height = in.nextInt();
        if (in.hasNextLine()) {
            in.nextLine();
        }
        for (int i = 0; i < height; i++) {
            String line = in.nextLine();
 //           System.err.println(line); // # - wall,  w - spawn for wanderers,  . - empty cell
        }
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

            System.out.println("WAIT"); // MOVE <x> <y> | WAIT
            
            System.err.println("exp "+explorers.size());
            System.err.println("wan "+wanderers.size());
            
            cleanEntityLists();
            
        }
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
    
    
    static Explorer getExplorer(int x, int y, int id, int param0, int param1, int param2) {
		return new Explorer(x, y, id, param0, param1, param2);
    }
    
    static Wanderer getWanderer(int x, int y, int id, int param0, int param1, int param2) {
    	return new Wanderer(x, y, id, param0, param1, param2);
    }
    
    static void setAllEntities(int i, String entityType, int x, int y, int id, int param0, int param1, int param2) {
    	if (i == 0) {
    		me = getExplorer(x, y, id, param0, param1, param2);
    		System.err.print("ME - ");
    	} 
    	else if (entityType.equals("EXPLORER")) {
    		explorers.add(getExplorer(x, y, id, param0, param1, param2));
   // 		System.err.println("add explorer");
    	}
    	else if (entityType.equals("WANDERER")) {
    		wanderers.add(getWanderer(x, y, id, param0, param1, param2));
  //  		System.err.println("add wanderer");
    	}
    	
    }
    
    static void cleanEntityLists() {
    	explorers.clear();
    	wanderers.clear();
    }
    
    
}