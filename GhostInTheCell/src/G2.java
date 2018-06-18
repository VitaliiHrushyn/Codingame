import java.util.*;
import java.util.Map.Entry;
import java.io.*;
import java.math.*;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Player2 {
	
	static Set<Factory> factories = new HashSet<>();
	static Set<Factory> myFactories = new HashSet<>();
	static Set<Factory> enemyFactories = new HashSet<>();
	static Set<Factory> neutralFactories = new HashSet<>();
	static Set<Factory> excludeFactories = new HashSet<>();
	static Set<Factory> neutralTargetFactories = new HashSet<>();
	static Set<Factory> enemyTargetFactories = new HashSet<>();
	

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int factoryCount = in.nextInt(); // the number of factories
//System.err.println("factory count= "+factoryCount);
        int linkCount = in.nextInt(); // the number of links between factories
        for (int i = 0; i < linkCount; i++) {
            int factory1 = in.nextInt();
//System.err.println("factory1= "+factory1);
            int factory2 = in.nextInt();
//System.err.println("factory2= "+factory2);
            int distance = in.nextInt();
            
            Factory newfactory1 = getFactory(factories, factory1); // create new factory if there is no such factory (by id) or choose factory with such id
            Factory newfactory2 = getFactory(factories, factory2); // the same
            
            newfactory1.setDistance(factory2, distance); // put distance, see method in class Factory
            newfactory2.setDistance(factory1, distance);
            
            factories.add(newfactory1);
            factories.add(newfactory2);
           
        }

        // game loop
        while (true) {
            int entityCount = in.nextInt(); // the number of entities (e.g. factories and troops)
            for (int i = 0; i < entityCount; i++) {
                int entityId = in.nextInt();
                String entityType = in.next();
                int arg1 = in.nextInt(); // owner: 1 - mine, -1 - not mine, 0 - neutral
                int arg2 = in.nextInt(); // numberOfCyborgs in the factory
                int arg3 = in.nextInt(); // production cyborgs of the factory
                int arg4 = in.nextInt(); // not use
                int arg5 = in.nextInt(); // not use
                
                if(entityType.equals("FACTORY")) {
                	Factory factory = getFactory(factories, entityId);
                	factory.arg1_owner = arg1;
            		factory.arg2_numberOfCyborgs = arg2;
            		factory.arg3_production = arg3;
            		
                	switch(arg1) {
                	case 1:                		
                		myFactories.add(factory);
                		break;
                	case -1:
                		enemyFactories.add(factory);
                		break;
                	case 0:
                		neutralFactories.add(factory);
                		break;
                	default:
                		System.err.println("SWITCH ERROR!");
                		}
                }
                
            }

            // Write an action using System.out.println()
            // To debug: System.err.println("Debug messages...");


            // Any valid action, such as "WAIT" or "MOVE source destination cyborgs"
/*            
 System.err.println("all factories.size= "+factories.size());
 System.err.println("my factories.size= "+myFactories.size());
 System.err.println("enemy factories.size= "+enemyFactories.size());
 System.err.println("neutral factories.size= "+neutralFactories.size());
*/ 
 //for (Factory factory : neutralFactories) {	 System.err.println(factory);}
// for (Factory factory : myFactories) {	 System.err.println(factory.getNextTargetKit(neutralFactories));}
 
 
 		
            
            System.out.println(getAction());
            
       	myFactories.clear();
 		enemyFactories.clear();
 		neutralFactories.clear();
        }
    }
    
    public static String getAction() {
    	StringBuffer sb = new StringBuffer();
    	int targetRating = -1;
    	int targetId = -1;
    	int sourseId = -1;
    	
    	Set<Factory> someFactories;
    	if(neutralFactories.size() == 0) {
    		someFactories = enemyFactories;
    	} else {
    		someFactories = neutralFactories;
    	}
    	for (Factory factory : myFactories) {    			 
    		Set<Entry<Integer, Integer>> targetSet = factory.getNextTargetKit(someFactories).entrySet();
    		
    		for (Entry<Integer, Integer> entry : targetSet) {
    			int newTargetRating = entry.getValue();
   System.err.println(targetRating+" "+newTargetRating);
    			if(targetRating < newTargetRating) {

    				targetRating = newTargetRating;
    				targetId = entry.getKey();
    				sourseId = factory.id;				
    			}
    		}
    	}
    	Factory targetFactory = getFactory(factories, targetId);
    	Factory sourceFactory = getFactory(factories, sourseId);
    	int countCyb = targetFactory.arg2_numberOfCyborgs+1;
    	
    
  //  	if(countCyb <= sourceFactory.arg2_numberOfCyborgs) {    	excludeFactories.add(targetFactory);    	} // ???????
    	
    	return "MOVE "+sourseId+" "+targetId+" "+countCyb;
    }
    
    public static Factory getFactory(Set<Factory> factories, int id) {
		Factory factory = new Factory(id);
//System.err.println("factory. ID= "+id);		
		for (Factory factoryIterator : factories) {
//System.err.println("factory iterator.id= "+factoryIterator.id);
			if(factoryIterator.equals(factory)) {
//System.err.println("return old factory iterator.id= "+factoryIterator.id);				
				return factoryIterator;
			}
		}
//System.err.println("return new factory. ID= "+id);	
		return factory;
	}
    
    static class Factory {
    	private int id;
    	private int arg1_owner; // 1 - mine, -1 - not mine, 0 - neutral
    	private int arg2_numberOfCyborgs; // in the factory
    	private int arg3_production; // of the factory
    	private int arg4;
    	private int arg5;    	
    	private Map<Integer, Integer> distances = new HashMap<>();
   // 	private static Map<Integer, Integer> TargetKit = getT;
   // 	private int nextTargetRating;
   // 	private int nextTargetId;
    	    	
		public Factory(int id) {
			super();
			this.id = id;
		}
		
/*		public int getArg1_owner() {
			return arg1_owner;
		}

		public void setArg1_owner(int arg1_owner) {
			this.arg1_owner = arg1_owner;
		}

		public int getArg2_numberOfCyborgs() {
			return arg2_numberOfCyborgs;
		}

		public void setArg2_numberOfCyborgs(int arg2_numberOfCyborgs) {
			this.arg2_numberOfCyborgs = arg2_numberOfCyborgs;
		}

		public int getArg3_production() {
			return arg3_production;
		}

		public void setArg3_production(int arg3_production) {
			this.arg3_production = arg3_production;
		}

		public int getArg4() {
			return arg4;
		}

		public void setArg4(int arg4) {
			this.arg4 = arg4;
		}

		public int getArg5() {
			return arg5;
		}

		public void setArg5(int arg5) {
			this.arg5 = arg5;
		}
*/
		public void setDistance(int factoryId, int distance) {
			distances.put(factoryId, distance);
		}

		/////////////////////////////////////// important method TARGET KIT
		public Map<Integer, Integer> getTargetKit(Set<Factory> factories) {
			
			int rating = -1;
			Map<Integer, Integer> targetKit = new HashMap<>(); // <targetFactory id, rating>
			Set<Factory> workFactories = new HashSet<>();
			workFactories.addAll(factories);
			workFactories.removeAll(excludeFactories);
			for (Factory factory : workFactories) {
				int targetFactoryId = factory.id;
				double numOfCyb = (double)factory.arg2_numberOfCyborgs;
				double prod = (double)factory.arg3_production;
				double dist = (double)factory.distances.get(this.id); // get distance from calling factory to 'factory'
				
				rating = (int) ((prod / (numOfCyb * dist)) * 1000);
				targetKit.put(targetFactoryId, rating);
				
			}
			return targetKit;
		}
/*		
		public int getNextTargetId(Set<Factory> factories) {
		
			int rating = -1;
			int targetId = -1;
			for (Map.Entry<Integer, Integer> targetEntry : this.getTargetKit(factories).entrySet()) {
				int newRating = targetEntry.getValue();
				if(rating < newRating) {
					rating = newRating;
					targetId = targetEntry.getKey();
				}
			}
			
			return targetId;
		}
*/
		
		public Map<Integer, Integer> getNextTargetKit(Set<Factory> factories) {
			Map<Integer, Integer> nexTargetKit = new HashMap<>();
			int rating = -1;
			int targetId = -1;
			for (Map.Entry<Integer, Integer> targetEntry : this.getTargetKit(factories).entrySet()) {
				int newRating = targetEntry.getValue();
				if(rating < newRating) {
					rating = newRating;
					targetId = targetEntry.getKey();
				}
			}
			nexTargetKit.put(targetId, rating);
			return nexTargetKit;
		}
		
		public Factory getNearestFactory() {
			int lowestDistance = 21;
			int nearestFactoryId = -1;
			for (Map.Entry<Integer,Integer> pair : this.distances.entrySet()) {
				if(pair.getValue() < lowestDistance) {
					lowestDistance = pair.getValue();
					nearestFactoryId = pair.getKey();
				}
			}			
			return getFactory(factories, nearestFactoryId);		
		}


		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + id;
			return result;
		}


		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Factory other = (Factory) obj;
			if (id != other.id)
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "Factory [id=" + id + ", arg1_owner=" + arg1_owner + ", arg2_numberOfCyborgs=" + arg2_numberOfCyborgs
					+ ",\n arg3_production=" + arg3_production + ", distances=\n" + distances + "]";
		}
		
		
    	
    	
    }
}