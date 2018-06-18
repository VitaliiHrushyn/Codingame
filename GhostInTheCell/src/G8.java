import java.util.*;
import java.io.*;
import java.math.*;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Player { /// rename to Player (version 8)
	
	static Set<Factory> allFactories = new HashSet<>();
	
	
	
	static final int MINE = 1;
	static final int ENEMY = -1;
	static final int NEUTRAL = 0;
	static final String MOVE = "; MOVE ";
	static final String BOMB = "; BOMB ";
	static final String INC = "; INC ";
	static final int INC_VALUE = 1;
	static final int MIN_MY_FACTORIES_AMOUNT = 3;
	
	static int bombCount = 2;
	static int myFactoriesAmount;
	static int bombTargetId;

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int factoryCount = in.nextInt(); // the number of factories
        int linkCount = in.nextInt(); // the number of links between factories
        for (int i = 0; i < linkCount; i++) {
            int factory1 = in.nextInt();
            int factory2 = in.nextInt();
            int distance = in.nextInt();
/////////////// create 'allFactories' and put all distances to them            
            Factory newfactory1 = getFactory(allFactories, factory1); // create new factory if there is no such factory (by id) or choose factory with such id
            Factory newfactory2 = getFactory(allFactories, factory2); // the same   
            newfactory1.setDistance(factory2, distance); // put distance, see method in class Factory
            newfactory2.setDistance(factory1, distance);            
            allFactories.add(newfactory1);
            allFactories.add(newfactory2);
////////////////////////////////           
        }

        // game loop
        
        
        
        while (true) {
        	
        	int bombCybCount = 0;
        	
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
                	factory.init(arg1, arg2, arg3);
                	
                	if (factory.arg1_owner == MINE) {
                		myFactoriesAmount++;
                	}
                	
                	if (factory.arg1_owner == ENEMY) {
                		
                		if (arg2 > bombCybCount && arg3 > 0) {
                			bombCybCount = arg2;
                			bombTargetId = entityId;
                		}
System.err.println("cyb, id= "+arg2+", "+entityId);
                	}
                } 

            }           
System.err.println("bombTargetId= "+bombTargetId);
     
        System.out.println(getAction());
        
        myFactoriesAmount = 0;
  //      bombTargetId = -1;

        }
    }
    
//////// GET ACTION ////////////////////////////////    
    
    public static String getAction() {

    	StringBuilder sb = new StringBuilder();
		sb.append("WAIT");
				
		for (Factory sourceFactory : allFactories) {
			
			if (sourceFactory.arg1_owner == MINE) {				
				Set<Factory> targetFactories = new HashSet<>();
				targetFactories.addAll(allFactories);
				
				while (targetFactories.size() > 0) {
					if (sourceFactory.arg2_numberOfCyborgs < 1) {
						break;
					}
					Factory target = sourceFactory.getNearestFactory(targetFactories);
					sb.append(target.getAction(sourceFactory));
					targetFactories.remove(target);

					if (sourceFactory.increase == true) {
						break;
					}
				}									
			}
		}		
		return sb.toString();	
    }
    
  //---------------------------------------------------------------------------------------------------------------------------
    
   
    
    
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
    	private Map<Integer, Integer> distances = new HashMap<>(); // 1-st int - factory id, 2-nd int - distance to that factory from this.factory
//    	private int needCyborg = 0;
    	private boolean increase = false;
  //  	private boolean attacKMe = false;
     	    	
		public Factory(int id) {
			super();
			this.id = id;
		}		

		public void setDistance(int factoryId, int distance) {
			distances.put(factoryId, distance);
		}
		

		
		public String getAction (Factory sourceFactory) {
//System.err.println("get action: factory id= "+this.id+", source id= "+sourceFactory.id);
			int sourceId = sourceFactory.id;
			int targetId = this.id;
			int cybCount = -1;
			int distance = 0; 
			String details = ""+sourceId+" "+targetId; 

			
			boolean permitIncrease = (this.arg3_production < INC_VALUE && this.arg2_numberOfCyborgs > 10 && myFactoriesAmount >= MIN_MY_FACTORIES_AMOUNT);
			boolean permitMove = (INC_VALUE <= sourceFactory.arg3_production || myFactoriesAmount < MIN_MY_FACTORIES_AMOUNT || 
					myFactoriesAmount < 2);
			boolean permitToNeutral = (this.arg3_production > 0);
			
//System.err.println("myFactoriesAmount= "+myFactoriesAmount);
			if (this.equals(sourceFactory)) {
//System.err.println("equals: factory id= "+this.id+", source id= "+sourceFactory.id);
				if (permitIncrease) {
					increase = true;
					return INC+this.id;
				} else {
				return "";
				}
			}
			
			distance = this.distances.get(sourceId);
////////////////   MINE   ////////////////////////////			
			if (this.arg1_owner == MINE) {				
				if (this.arg3_production < INC_VALUE && this.arg2_numberOfCyborgs < 10) {  // increase divide
					cybCount = 10 - this.arg2_numberOfCyborgs;
			//		this.arg2_numberOfCyborgs += cybCount; // ???????????????????????????????????????????????????
System.err.println("increase factory id, source id= "+details);
					sourceFactory.removeSyborg(cybCount);
			//		increase = true; //??????????????????????????????????????????????
					if (permitMove) {
						return MOVE+details+" "+cybCount;
						}
				}
				return "";				
			}
//////////////////  ENEMY  ////////////////////			
			if (this.arg1_owner == ENEMY) {
				cybCount = this.arg2_numberOfCyborgs + (this.arg3_production * distance) + 1;
		    	//   BOMB   //
System.err.println("this.id == bombTargetId= "+(this.id == bombTargetId));
				if (this.id == bombTargetId && bombCount > 0) {
					bombCount--;
					bombTargetId = -1;
					return BOMB+details;
				}
				//   MOVE   //
				sourceFactory.removeSyborg(cybCount);
				if (permitMove) {
				return MOVE+details+" "+cybCount;
				}
				return "";
			}
///////////////////    NEUTRAL   ///////////////////////			
			if (this.arg1_owner == NEUTRAL) {
				cybCount = this.arg2_numberOfCyborgs + 1;
				
				sourceFactory.removeSyborg(cybCount);
				if (permitMove && permitToNeutral) {  // ????????????????????????????????/
					return MOVE+details+" "+cybCount;
					}
					return "";
			}
			
			return "!!!-ERROR, factory id= "+this.id+", source factory id= "+sourceId;
		}
		
		private void removeSyborg(int cybCount) {
			this.arg2_numberOfCyborgs -= cybCount;
			if (this.arg2_numberOfCyborgs < 0) {
				this.arg2_numberOfCyborgs = 0;
			}
			
		}

		public void init(int arg1, int arg2, int arg3) {
			this.arg1_owner = arg1;
			this.arg2_numberOfCyborgs = arg2;
			this.arg3_production = arg3;
			this.increase = false;
//System.err.println("init: factory id= "+this.id+", owner id= "+this.arg1_owner+", num cyb= "+this.arg2_numberOfCyborgs+
//		", prod= "+this.arg3_production);		
		}

		public Factory getNearestFactory(Set<Factory> someFactorySet) { // get nearest factory to calling factory from 'someFactorySet'
			int lowestDistance = 21;
			int nearestFactoryId = -1;			
			if (someFactorySet.contains(this)) {
				return this;
			}			
			for (Map.Entry<Integer,Integer> pair : this.distances.entrySet()) {
				if((pair.getValue() < lowestDistance) && someFactorySet.contains(getFactory(allFactories, pair.getKey()))) {
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