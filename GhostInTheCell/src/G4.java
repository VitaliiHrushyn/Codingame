import java.util.*;
import java.io.*;
import java.math.*;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Player4 { /// rename to Player (version 4)
	
	static Set<Factory> allFactories = new HashSet<>();
	static Set<Factory> myFactories = new HashSet<>();
	static Set<Factory> enemyFactories = new HashSet<>();
	static Set<Factory> neutralFactories = new HashSet<>();
//	static Set<Factory> excludeFactories = new HashSet<>();
	

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int factoryCount = in.nextInt(); // the number of factories
        int linkCount = in.nextInt(); // the number of links between factories
        for (int i = 0; i < linkCount; i++) {
            int factory1 = in.nextInt();
            int factory2 = in.nextInt();
            int distance = in.nextInt();
            
            Factory newfactory1 = getFactory(allFactories, factory1); // create new factory if there is no such factory (by id) or choose factory with such id
            Factory newfactory2 = getFactory(allFactories, factory2); // the same
            
            newfactory1.setDistance(factory2, distance); // put distance, see method in class Factory
            newfactory2.setDistance(factory1, distance);
            
            allFactories.add(newfactory1);
            allFactories.add(newfactory2);
           
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
                	Factory factory = getFactory(allFactories, entityId);
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
// 		excludeFactories.clear();
        }
    }
    
//////// GET ACTION ////////////////////////////////    
    
    public static String getAction() {
    	Set<Factory> targetSet = new HashSet<>();
    	targetSet.addAll(neutralFactories);
    	targetSet.addAll(enemyFactories);
    	StringBuilder sb = new StringBuilder();
		sb.append("WAIT");
System.err.println("myFactories size "+myFactories.size()); 
System.err.println("targetSet size "+targetSet.size()); 

    	for (Factory sourceFactory : myFactories) {  // !!!!!!!!!!!!!!!!!!

System.err.println("sourceFactory id "+sourceFactory.id);

			

    		while (true) {
		
    			Factory target = sourceFactory.getNearestFactory(targetSet);
System.err.println("target id "+target.id);
				
				int sourceId = sourceFactory.id;
				int targetId = target.id;
				int cybCount = target.arg2_numberOfCyborgs + 1;
								
				int cybLeft = sourceFactory.arg2_numberOfCyborgs - cybCount;
System.err.println("cybLeft "+cybLeft);			
				if(cybLeft >= 0 && target.id >=0) {
//System.err.println("append");
					sb.append(";MOVE "+sourceId+" "+targetId+" "+cybCount+"");
					targetSet.remove(target);
System.err.println("append");
				} else {
System.err.println("break");
					break;
				}
			}
			
		}
System.err.println("sb.toString() "+sb.toString());
    	return sb.toString();
    }
    
//////////////////////////////////////////////////
    
    
//////////// GET FACTORY /////////////////////////
    
    public static Factory getFactory(Set<Factory> factories, int id) {
		Factory factory = new Factory(id);
		for(Factory factoryIterator : factories) {
			if(factoryIterator.equals(factory)) {			
				return factoryIterator;
			}
		}	
		return factory;
	}
    
/////////////////////////////////////////////////////

/////////////////////////////////////////////////////
//// CLASS FACTORY   ////////////////////////////////
    
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

		public void setDistance(int factoryId, int distance) {
			distances.put(factoryId, distance);
		}

/*		/////////////////////////////////////// important method TARGET KIT
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
/*		
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
	*/	
		public Factory getNearestFactory(Set<Factory> targetSet) {
			int lowestDistance = 21;
			int nearestFactoryId = -1;
			for (Map.Entry<Integer,Integer> pair : this.distances.entrySet()) {
				if((pair.getValue() < lowestDistance) && targetSet.contains(getFactory(allFactories, pair.getKey()))) {
					lowestDistance = pair.getValue();
					nearestFactoryId = pair.getKey();
				}
			}			
			return getFactory(allFactories, nearestFactoryId);		
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
 ////////////////////////////////////////
 ///////////////////////////////////////
}