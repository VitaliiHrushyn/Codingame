import java.util.*;
import java.io.*;
import java.math.*;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Player {

    public static void main(String args[]) {
    	
    	List<Integer> gatewaysList = new ArrayList<>();
    	List<Link> linkList = new ArrayList<>();
    	
        Scanner in = new Scanner(System.in);
        int N = in.nextInt(); // the total number of nodes in the level, including the gateways
        int L = in.nextInt(); // the number of links
        int E = in.nextInt(); // the number of exit gateways
        for (int i = 0; i < L; i++) {
            int N1 = in.nextInt(); // N1 and N2 defines a link between these nodes
            int N2 = in.nextInt();
            
            linkList.add(new Link(N1, N2));
        }
        for (int i = 0; i < E; i++) {
            int EI = in.nextInt(); // the index of a gateway node
            
            gatewaysList.add(EI);
        }

        // game loop
        while (true) {
            int SI = in.nextInt(); // The index of the node on which the Skynet agent is positioned this turn

            // Write an action using System.out.println()
            // To debug: System.err.println("Debug messages...");

// System.err.println("gatewaysList.size() "+gatewaysList.size());
// System.err.println("linkList.size() "+linkList.size());
 			
 			ArrayList<Integer> nodes = getLinkedNodes(SI, linkList);
 			int gateway = getGateways(SI, nodes, gatewaysList);
 			
 			int node1 = -2;
 			int node2 = -3;
 			
 			if (gateway != -1) {
System.err.println("near gat");
 				node1 = SI; //getAnotherNode(gateway, linkList);
 				node2 = gateway;
 				linkList.remove(getLink(node1, node2, linkList));
 			} else {
 System.err.println("not near gat ");
 				node1 =  SI;
 				node2 =  getAnotherNode(SI, linkList);
 				linkList.remove(getLink(node1, node2, linkList));
 			}
 
 
            // Example: 0 1 are the indices of the nodes you wish to sever the link between
            System.out.println(node1+" "+node2);
        }
    }
    
    static int getGateways(int SI, List<Integer> nodes, List<Integer> gatewaysList) {
    	for (Integer gateway : gatewaysList) {
			for (Integer node : nodes) {
				if (node == gateway) return gateway;
			}
		}
    	return -1;
    }
    
    static ArrayList<Integer> getLinkedNodes(int SI, List<Link> linkList) {
    	ArrayList<Integer> nodes = new ArrayList<>();
		for (Link link : linkList) {
			if (SI == link.n1) {
				nodes.add(link.n2);
			}
			else if (SI == link.n2) {
				nodes.add(link.n1);
			}
		}		
		return nodes;
	}
    
    static int getAnotherNode(int node, List<Link> linkList) {
    	int anotherNode = -4;
    	for (Link link : linkList) {
			if (link.n1 == node) anotherNode = link.n2;
			if (link.n2 == node) anotherNode = link.n1;
		}
    	return anotherNode;
    }
    
    static Link getLink(int node1, int node2, List<Link> linkList) {
		Link searchedLink = null;
		for (Link link : linkList) {
			if (link.n1 == node1 && link.n2 == node2) {
				searchedLink = link;
			}
		}    	
    	return searchedLink;
	}
    
    static class Link {
    	int n1;
    	int n2;
		public Link(int n1, int n2) {
			super();
			this.n1 = n1;
			this.n2 = n2;
		}
    	
    	boolean isNear(int SI) {
    		if (SI == this.n1 || SI == this.n2) {
    			return true;
    		}
    		return false;
    	}
    	
    	
    }
}