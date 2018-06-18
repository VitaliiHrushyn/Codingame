import java.util.*;
import java.io.*;
import java.math.*;
import java.time.temporal.IsoFields;

/**
 * Bring data on patient samples from the diagnosis machine to the laboratory with enough molecules to produce medicine!
 **/
class Player {
	
	static DataList dataList = new DataList();   // collection of sample data objects
	static ProjectList projectList = new ProjectList();
	static Bot myBot = null;
	static Bot enemyBot = null;
	static boolean goAhead = true;
	static boolean goBack = false;
	static enum Location { SAMPLES, DIAGNOSIS, MOLECULES, LABORATORY }
	static int availableA;
	static int availableB;
	static int availableC;
	static int availableD;
	static int availableE;

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int projectCount = in.nextInt();
        for (int i = 0; i < projectCount; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            int c = in.nextInt();
            int d = in.nextInt();
            int e = in.nextInt();
            
System.err.println("project a-"+a+", b-"+b+", c-"+c+", d-"+d+", e-"+e);
            
            projectList.addProject(new Project(a, b, c, d, e));
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
            availableA = in.nextInt();
            availableB = in.nextInt();
            availableC = in.nextInt();
            availableD = in.nextInt();
            availableE = in.nextInt();
            
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
            // GOTO module: move towards the target module - SAMPLES, DIAGNOSIS, MOLECULES or LABORATORY.
            // CONNECT id/type: connect to the target module with the specified sample id or molecule type.

            // Write an action using System.out.println()
            // To debug: System.err.println("Debug messages...");
            
            

            System.out.println(action());
            
// System.err.println("dataList.size "+dataList.size());
// System.err.println("highest dataList.id "+dataList.getBestData().getSampleId());
// System.err.println("highest dataList.helth "+dataList.getBestData().getHealth());
 
 
 
            dataList.clear();
            projectList.clear();
           
            
        }
    }
    
    
    
    public static String action() {
    	
    	if (goAhead || goBack) {
    		return goTo();
    	}
    	else if (myBot.getEta() == 0) {
    		return connect();
    	}
    	else {
    		return "WAIT";
    	}
    }
    
    public static String goTo() { // DIAGNOSIS, MOLECULES or LABORATORY
    	String target = myBot.getTarget();
    	Location location;
    	if (target.equals("START_POS")) {
    		location = Location.LABORATORY;
    	} else {
    		location = Location.valueOf(target);
    	}
    	
    	if (goAhead) {
    		switch (location) {
			case LABORATORY:
				goAhead = !goAhead;
				return "GOTO SAMPLES";
			case SAMPLES:
				goAhead = !goAhead;
				return "GOTO DIAGNOSIS";
			case DIAGNOSIS:
				goAhead = !goAhead;
				return "GOTO MOLECULES";
			case MOLECULES:
				goAhead = !goAhead;
				return "GOTO LABORATORY";
			default:
				return "ERROR: goTo goAhead";
			}
    	}
    	
    	if (goBack) {
    		switch (location) {
			case LABORATORY:
				goBack = !goBack;
				return "GOTO MOLECULES";
			case SAMPLES:
				goBack = !goBack;
				return "GOTO LABORATORY";
			case DIAGNOSIS:
				goBack = !goBack;
				return "GOTO SAMPLES";
			case MOLECULES:
				goBack = !goBack;
				return "GOTO DIAGNOSIS";
			default:
				return "ERROR: goTo goBack";
			}
    	}
    	return "ERROR: goTo";
    }
    
    public static String connect() {
    	String id = null;
    	String target = myBot.getTarget();
    	Location location = Location.valueOf(myBot.getTarget());
    	
    	switch (location) {
    	
		case SAMPLES:
			return id = doSamples();
		case DIAGNOSIS:
			return id = doDiagnosis();
		case MOLECULES:
			return id = doMolecules();
		case LABORATORY:
			return id = doLaboratory();
		default:
			return "ERROR: connect";
		}    	
    }
    
    public static String doSamples() {
    	
		return "CONNECT " + dataList.getBestRankData(); 
    }
    
    public static String doDiagnosis() {
    	List<SampleData> myDatas = dataList.getMyDatas();
    	int c = 0;
    	int id = -1;
    	for (SampleData sd : myDatas) {
			if (sd.getCostA() == -1) {
				c++;
				id = sd.getSampleId();
			}			
		}
    	if (c == 1) goAhead = !goAhead;
		
    	return "CONNECT " + id;
    }
    
    public static String doMolecules() { // return name of one needed molecule one by one for each turn
    	
    	String moleculeType = "doMolecules ERROR";
    	
    	int needA = 0;
    	int needB = 0;
    	int needC = 0;
    	int needD = 0;
    	int needE = 0;
    	
    	int haveA = myBot.getStorageA() + myBot.getExpertiseA();
    	int haveB = myBot.getStorageB() + myBot.getExpertiseB();
    	int haveC = myBot.getStorageC() + myBot.getExpertiseC();
    	int haveD = myBot.getStorageD() + myBot.getExpertiseD();
    	int haveE = myBot.getStorageE() + myBot.getExpertiseE();
    	
    	int totalStorage = myBot.getStorageA() + myBot.getStorageB() + myBot.getStorageC() + myBot.getStorageD() + myBot.getStorageE();
    	
    	List<SampleData> myDataList = new ArrayList<>(dataList.getMyDatas());
    	
    	for (SampleData myData : myDataList) {
 
			needA += myData.getCostA();
			needB += myData.getCostB();
			needC += myData.getCostC();
			needD += myData.getCostD();
			needE += myData.getCostE();
		}
    	
System.err.println("need A "+needA+", B "+needB+", C "+needC+", D "+needD+", E "+needE);    
System.err.println("have A "+haveA+", B "+haveB+", C "+haveC+", D "+haveD+", E "+haveE);   
    	
		if (myBot.isFullOfMolecules()) return "GOTO LABORATORY"; // !!!!!!!!!!!!!!!!!!!
    	    	
    	if      (needA > haveA && availableA > 0) {moleculeType = "A"; haveA++;}
    	else if (needB > haveB && availableB > 0) {moleculeType = "B"; haveB++;}
    	else if (needC > haveC && availableC > 0) {moleculeType = "C"; haveC++;}
    	else if (needD > haveD && availableD > 0) {moleculeType = "D"; haveD++;}
    	else if (needE > haveE && availableE > 0) {moleculeType = "E"; haveE++;}
    	else return "WAIT";
    	
    	if ((needA <= haveA && needB <= haveB && needC <= haveC && needD <= haveD && needE <= haveE) ||
    			(totalStorage == 9)) goAhead = !goAhead;
    	    	
    	return "CONNECT " + moleculeType;    	
    }
    
   public static String doLaboratory() {
	    List<SampleData> myDatas = dataList.getMyDatas();
	   	int c = 0;
	   	int id = -1;
	   	for (SampleData sd : myDatas) {
				if (sd.isEnoughMolecules()) id = sd.getSampleId();			
			}
	   	if (myDatas.size() == 1 && myBot.isFullOfMolecules()) {
	   		goAhead = !goAhead;
	   		return "GOTO SAMPLES";
	   	}
	   	if (myDatas.size() == 1) goAhead = !goAhead;
	   	
		if (id == -1) return "GOTO MOLECULES";	
	   	return "CONNECT " + id;
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
		
		if (this.getStorageA()+this.getStorageB()+this.getStorageC()+this.getStorageD()+this.getStorageE() == 10) {
			return true;
		}
		
//		SampleData myData = dataList.getMyData();
//		int needMolecules = myData.getCostA()+myData.getCostB()+myData.getCostC()+myData.getCostD()+myData.getCostE();
//		if (storageA+storageB+storageC+storageD+storageE < needMolecules) {
//			return false;
//		}
		return false;
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
    	
    	public int getBestRankData () {
    		int count = dataList.getMyDatas().size();
    		if (count == 2) goAhead = !goAhead;
    		
    		int myExpertise = myBot.getExpertiseA()+myBot.getExpertiseB()+myBot.getExpertiseC()+
    				myBot.getExpertiseD()+myBot.getExpertiseE();
    		if ( myExpertise < 5) return 1;
    		if ( myExpertise < 9) return 2;
    		return 3;    		
    	}
    	
    	public List<SampleData> getMyDatas () {
    		List<SampleData> sd = new ArrayList<Player.SampleData>();
    		for (SampleData sampleData : sampleDataList) {
    			if (sampleData.getCarriedBy() == 0) {
	    			sd.add(sampleData);
    			}
			}
    		return sd;    		
    	}
    	
 @Deprecated
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
		
		public boolean isEnoughMolecules() {
			if (costA <= myBot.getStorageA() + myBot.getExpertiseA() &&
					costB <= myBot.getStorageB() + myBot.getExpertiseB() &&
						costC <= myBot.getStorageC() + myBot.getExpertiseC() &&
							costD <= myBot.getStorageD() + myBot.getExpertiseD() &&
								costE <= myBot.getStorageE() + myBot.getExpertiseE()) return true;
			return false;
		}
     }
    
    static class Project {
    	int a;
        int b;
        int c;
        int d;
        int e;
        
		public Project(int a, int b, int c, int d, int e) {
			super();
			this.a = a;
			this.b = b;
			this.c = c;
			this.d = d;
			this.e = e;
		}

		public int getA() {
			return a;
		}

		public int getB() {
			return b;
		}

		public int getC() {
			return c;
		}

		public int getD() {
			return d;
		}

		public int getE() {
			return e;
		}
        
    }
    
    static class ProjectList {
    	List<Project> projectsList = new ArrayList<Player.Project>();
    	public void addProject (Project project) {
    		projectsList.add(project);
    	}
    	public int size() {
    		return projectsList.size();
    	}
    	public void clear() {
    		projectsList.clear();
    	}
    }
}