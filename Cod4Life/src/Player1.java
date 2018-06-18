import java.util.*;
import java.io.*;
import java.math.*;

/**
 * Bring data on patient samples from the diagnosis machine to the laboratory with enough molecules to produce medicine!
 **/
class Player1 {
	
	static DataList dataList = new DataList();   // collection of sample data objects
	static Bot myBot = null;
	static Bot enemyBot = null;

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int projectCount = in.nextInt();
        for (int i = 0; i < projectCount; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            int c = in.nextInt();
            int d = in.nextInt();
            int e = in.nextInt();
        }

        // game loop
        while (true) {
            for (int i = 0; i < 2; i++) {  // 0 - me, 1 - enemy
                String target = in.next();   // module where the player is
                int eta = in.nextInt();
                int score = in.nextInt();   // the player's number of health points
                
                int storageA = in.nextInt();   // number of molecules held by the player for each molecule type.
                int storageB = in.nextInt();   //
                int storageC = in.nextInt();   //
                int storageD = in.nextInt();   //
                int storageE = in.nextInt();   //
                
                int expertiseA = in.nextInt();
                int expertiseB = in.nextInt();
                int expertiseC = in.nextInt();
                int expertiseD = in.nextInt();
                int expertiseE = in.nextInt();
                
                if (i == 0) {
                	myBot =  new Bot(target, eta, score, storageA, storageB, storageC, storageD, storageE, expertiseA, expertiseB, expertiseC, expertiseD, expertiseE);
                } else {
                	enemyBot = new Bot(target, eta, score, storageA, storageB, storageC, storageD, storageE, expertiseA, expertiseB, expertiseC, expertiseD, expertiseE);
                }
            }
            int availableA = in.nextInt();
            int availableB = in.nextInt();
            int availableC = in.nextInt();
            int availableD = in.nextInt();
            int availableE = in.nextInt();
            
            int sampleCount = in.nextInt();   // the number of samples currently in the game.
            for (int i = 0; i < sampleCount; i++) {
                int sampleId = in.nextInt();   // unique id for the sample.
                int carriedBy = in.nextInt();   // 0 if the sample is carried by you, 1 by the other robot, -1 if the sample is in the cloud.
                int rank = in.nextInt();
                String expertiseGain = in.next();                
                int health = in.nextInt();   // number of health points you gain from this sample.
                int costA = in.nextInt();   // number of molecules of each type needed to research the sample
                int costB = in.nextInt();
                int costC = in.nextInt();
                int costD = in.nextInt();
                int costE = in.nextInt();
                
                dataList.addData(new SampleData(sampleId, carriedBy, rank, expertiseGain, health, costA, costB, costC, costD, costE));
            }
            
            // Each turn issue one of the following command:
            // GOTO module: move towards the target module - DIAGNOSIS, MOLECULES or LABORATORY.
            // CONNECT id/type: connect to the target module with the specified sample id or molecule type.

            // Write an action using System.out.println()
            // To debug: System.err.println("Debug messages...");

            System.out.println(action());
            
// System.err.println("dataList.size "+dataList.size());
// System.err.println("highest dataList.id "+dataList.getBestData().getSampleId());
// System.err.println("highest dataList.helth "+dataList.getBestData().getHealth());
 
 
 
            dataList.clear();
            
        }
    }
    
    
    
    public static String action() {
    	
    	String target = myBot.getTarget();
    	
    	if ((target.equals("LABORATORY") || target.equals("START_POS")) && (!myBot.isAnyData() && !myBot.isAnyMolecule())){ 
    		return goTo();
    	}
    	if (target.equals("DIAGNOSIS") && myBot.isAnyData()){ 
    		return goTo();
    	}
    	if (target.equals("MOLECULES") && myBot.isFullOfMolecules()){ 
    		return goTo();
    	}
    	else {return connect();}
  //  	return "action ERROR";
    	
    }
    
    public static String goTo() { // DIAGNOSIS, MOLECULES or LABORATORY
    	String target = myBot.getTarget();
    	if (target.equals("LABORATORY") || target.equals("START_POS")) {
    		return "GOTO DIAGNOSIS";
    	} else if (target.equals("MOLECULES")) {
    		return "GOTO LABORATORY";
    	} else {
    		return "GOTO MOLECULES";
    	}   	
    }
    
    public static String connect() {
    	String id = null;
    	String target = myBot.getTarget();
    	
    	if(target.equals("DIAGNOSIS")) {
    		id = ""+dataList.getBestData().getSampleId(); // concatenation: ""+int
    	}
    	else if (target.equals("MOLECULES")) {
    		id = getMolecule();
    	}
    	else if (target.equals("LABORATORY")) {
    		id = ""+dataList.getMyData().getSampleId(); // concatenation
    	}
    	
    	return "CONNECT "+id;
    }
    
    public static String getMolecule() { // return name of one needed molecule
   
    	SampleData myData = dataList.getMyData();
    	
    	if ((myData.getCostA() - myBot.getStorageA()) > 0) {
    		return "A";
    	}
    	if ((myData.getCostB() - myBot.getStorageB()) > 0) {
    		return "B";
    	}
    	if ((myData.getCostC() - myBot.getStorageC()) > 0) {
    		return "C";
    	}
    	if ((myData.getCostD() - myBot.getStorageD()) > 0) {
    		return "D";
    	}
    	if ((myData.getCostE() - myBot.getStorageE()) > 0) {
    		return "E";
    	}
    	return "getMolecule ERROR";
    	
    	
    	
    }
    
   static class Bot {
	   String target;   // module where the player is
	   private int eta;
	   private int score;   // the player's number of health points
	   private int storageA;   // number of molecules held by the player for each molecule type.
	   private int storageB;   //
	   private int storageC;   //
	   private int storageD;   //
	   private int storageE;   //
	   private int expertiseA;
	   private int expertiseB;
	   private int expertiseC;
	   private int expertiseD;
	   private int expertiseE;
	   
	public Bot(String target, int eta, int score, int storageA, int storageB, int storageC, int storageD, int storageE,
			int expertiseA, int expertiseB, int expertiseC, int expertiseD, int expertiseE) {
		super();
		this.target = target;
		this.eta = eta;
		this.score = score;
		this.storageA = storageA;
		this.storageB = storageB;
		this.storageC = storageC;
		this.storageD = storageD;
		this.storageE = storageE;
		this.expertiseA = expertiseA;
		this.expertiseB = expertiseB;
		this.expertiseC = expertiseC;
		this.expertiseD = expertiseD;
		this.expertiseE = expertiseE;
	}
	
	public String getTarget() {
		return target;
	}
	public int getEta() {
		return eta;
	}
	public int getScore() {
		return score;
	}
	public int getStorageA() {
		return storageA;
	}
	public int getStorageB() {
		return storageB;
	}
	public int getStorageC() {
		return storageC;
	}
	public int getStorageD() {
		return storageD;
	}
	public int getStorageE() {
		return storageE;
	}
	public int getExpertiseA() {
		return expertiseA;
	}
	public int getExpertiseB() {
		return expertiseB;
	}
	public int getExpertiseC() {
		return expertiseC;
	}
	public int getExpertiseD() {
		return expertiseD;
	}
	public int getExpertiseE() {
		return expertiseE;
	}
	
	public boolean isAnyData() {
		if (dataList.getMyData() == null) {
			return false;
		}
		return true;
	}
	
	public boolean isAnyMolecule() {
		if (storageA+storageB+storageC+storageD+storageE > 0) {
			return true;
		}
		return false;
	}
	
	public boolean isFullOfMolecules() {
		SampleData myData = dataList.getMyData();
		int needMolecules = myData.getCostA()+myData.getCostB()+myData.getCostC()+myData.getCostD()+myData.getCostE();
		if (storageA+storageB+storageC+storageD+storageE < needMolecules) {
			return false;
		}
		return true;
	}
 }
    
   static class DataList {
    	List<SampleData> sampleDataList = new ArrayList<>();
    	
    	public void addData (SampleData sampleData) {
    		sampleDataList.add(sampleData);
    	}
    	public int size() {
    		return sampleDataList.size();
    	}
    	public void clear() {
    		sampleDataList.clear();
    	}
    	public SampleData getBestData () {  // now by health score
    		SampleData findingSampleData = null;
    		for (SampleData sampleData : sampleDataList) {
    			if (sampleData.getCarriedBy() == -1) {
	    			if (findingSampleData == null || sampleData.getHealth() > findingSampleData.getHealth()) {
	    				findingSampleData = sampleData;
	    			}
    			}
			}
    		return findingSampleData;    		
    	}
    	
    	public SampleData getMyData () {  
    		SampleData findingSampleData = null;
    		for (SampleData sampleData : sampleDataList) {
    			if (sampleData.getCarriedBy() == 0) {
	    			findingSampleData = sampleData;
    			}
			}
    		return findingSampleData;    		
    	}
    }
    
    static class SampleData {
	    private int sampleId;   // unique id for the sample.
	    private int carriedBy;   // 0 if the sample is carried by you, 1 by the other robot, -1 if the sample is in the cloud.
	    private int rank;
	    private String expertiseGain;                
	    private int health;   // number of health points you gain from this sample.
	    private int costA;   // number of molecules of each type needed to research the sample
	    private int costB;
	    private int costC;
	    private int costD;
	    private int costE;
		
	    public SampleData(int sampleId, int carriedBy, int rank, String expertiseGain, int health, int costA, int costB,
				int costC, int costD, int costE) {
			super();
			this.sampleId = sampleId;
			this.carriedBy = carriedBy;
			this.rank = rank;
			this.expertiseGain = expertiseGain;
			this.health = health;
			this.costA = costA;
			this.costB = costB;
			this.costC = costC;
			this.costD = costD;
			this.costE = costE;			
		}

		public int getSampleId() {
			return sampleId;
		}

		public int getCarriedBy() {
			return carriedBy;
		}

		public int getRank() {
			return rank;
		}

		public String getExpertiseGain() {
			return expertiseGain;
		}

		public int getHealth() {
			return health;
		}

		public int getCostA() {
			return costA;
		}

		public int getCostB() {
			return costB;
		}

		public int getCostC() {
			return costC;
		}

		public int getCostD() {
			return costD;
		}

		public int getCostE() {
			return costE;
		}
	    
	    
    }
}