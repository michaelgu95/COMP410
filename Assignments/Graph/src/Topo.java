import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;


public class Topo {
	private static final String FILE_NAME = "p2graphData.txt";
	private static Scanner sc;

	public static void main(String[] args) {
		File file = new File(FILE_NAME);
		try {
			sc = new Scanner(file);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		while(sc.hasNextLine()){
			Graph g = new Topo().new Graph();
			while(sc.hasNextLine()){
				String sourceName = sc.next();
				String destinationName = sc.next();
				String edgeWeight = sc.next();	

				g.addNode(sourceName);
				g.addNode(destinationName);
				g.addEdge("", Integer.parseInt(edgeWeight), destinationName, sourceName);
				if(sc.hasNextInt()){
					if(sc.nextInt() == -1){
						break;
					}
				}
			}
			System.out.println("\n########################################");
			g.printGraph();
			System.out.println("");
			System.out.println("Topological Sort");
//			g.topoSort();
			//skip next two lines
			sc.nextLine();
			sc.nextLine();
			if(sc.hasNextLine()){
				sc.nextLine();
			}
		}
	}

	class Graph{
		ArrayList<String> nodeNames = new ArrayList<String>();
		ArrayList<Node> nodes = new ArrayList<Node>();
		Hashtable<Integer, Edge> edgeFromId = new Hashtable<Integer,Edge>();
		Hashtable<String, Node> nodeFromString = new Hashtable<String, Node>();
		Hashtable<Node, ArrayList<Edge>> edgesFromNode =  new Hashtable<Node, ArrayList<Edge>>();
		int numId = 0;
		int edgeId = 0;

		public boolean addNode(String name){
			if(nodeNames.contains(name)){
				return false;
			}else{
				Node newNode = new Node(numId, name);
				nodes.add(newNode);
				nodeNames.add(name);
				nodeFromString.put(name, newNode);
				numId++;
				return true;
			}
		}

		public boolean addEdge(String name, int weight, String to, String from){
			if(!nodeNames.contains(to) || !nodeNames.contains(from)){
				return false;
			}
			Node toNode = nodeFromString.get(to);
			Node fromNode = nodeFromString.get(from);
			if(toNode == null || fromNode == null){
				return false;
			}
			Edge newEdge = new Edge(name, edgeId, weight, toNode, fromNode);
			if(edgesFromNode.containsKey(fromNode)){
				ArrayList<Edge> edgesOfNode = edgesFromNode.get(fromNode);
				// traverse all edges from fromNode and see if there is one that goes to toNode
				for(int i=0; i<edgesOfNode.size(); i++){
					if(edgesOfNode.get(i).to == toNode){
						return false;
					}
				}
				//otherwise add edge 
				edgesOfNode.add(newEdge);

			}else{
				ArrayList<Edge> edges = new ArrayList<Edge>();
				edges.add(newEdge);
				edgesFromNode.put(fromNode, edges);
			}
			toNode.inDegree++;
			edgeFromId.put(edgeId, newEdge);
			edgeId++;
			return true;
		}

		public boolean deleteNode(String name){
			if(!nodeNames.contains(name)){
				return false;
			}

			Node nodeToRemove = nodeFromString.get(name);
			//remove edges that node points to
			ArrayList<Edge> edgesToRemove = edgesFromNode.get(nodeToRemove);
			if(edgesToRemove != null){
				for(int i=0; i<edgesToRemove.size(); i++){
					//decrement in degree of destination node
					edgeFromId.get(edgesToRemove.get(i).id).to.inDegree--;
					//remove edge
					edgeFromId.remove(edgesToRemove.get(i).id);
				}
			}
			//remove edges that point to node
			for(int i=0; i<nodeNames.size(); i++){
				ArrayList<Edge> edges = edgesFromNode.get(nodeFromString.get(nodeNames.get(i)));
				ArrayList<Edge> edgesPointingToNode = new ArrayList<Edge>();
				if(edges != null){
					for(Edge e : edges){
						if(e.to.name.equals(name)){
							edgesPointingToNode.add(e);
						}
					}
					for(Edge e : edgesPointingToNode){
						edges.remove(e);
						deleteEdge(e.id);
					}
				}
			}
			nodeNames.remove(name);
			nodes.remove(nodeToRemove);
			nodeFromString.remove(name);
			edgesFromNode.remove(nodeToRemove);
			return true;
		}

		public boolean deleteEdge(int id){
			if(!edgeFromId.containsKey(id)){
				return false;
			}
			Edge edgeToDelete = edgeFromId.get(id);
			Node fromNode = edgeToDelete.from;
			//decrement in degree of destination node
			edgeToDelete.to.inDegree--;
			edgesFromNode.get(fromNode).remove(edgeToDelete);
			edgeFromId.remove(id);
			return true;
		}

		public int numNodes(){
			return nodes.size();
		}

		public int numEdges(){
			return edgeFromId.size();
		}

		public void printNode(String name){
			Node n = nodeFromString.get(name);
			if(n == null){
				System.out.println("No node in the graph contains that name");
				return;
			}
			System.out.println("(" + n.id + ")" + n.name);
			ArrayList<Edge> edges = edgesFromNode.get(n);
			if(edges != null){
				for(int j=0; j<edges.size(); j++){
					Edge e = edges.get(j);
					if(e.name.equals("null")){
						System.out.println("  (" + e.id + ")" + "--> " + e.to.name);
					}else{
						System.out.println("  (" + e.id + ")" + "(" + e.name + ")" +"--> " + e.to.name);
					}
				}
			}
			System.out.println("");		
		}

		public void printGraph(){
			ArrayList<String> names = nodeNames;
			for(int i=0;i<numNodes(); i++){
				Node n = nodeFromString.get(names.get(i));
				System.out.println("(" + n.id + ")" + n.name);
				ArrayList<Edge> edges = edgesFromNode.get(n);
				if(edges != null){
					for(int j=0; j<edges.size(); j++){
						Edge e = edges.get(j);
						if(e.name.equals("")){
							System.out.println("  (" + e.id + ")" + "---> " + e.to.name);
						}else{
							System.out.println("  (" + e.id + ")" + "(" + e.name + ")" +"--> " + e.to.name);
						}
					}
				}
				System.out.println("");
			}
		}

		private boolean topoSortHelper(ArrayList<Node> nodesToRemove){
			boolean acyclic = true;
			for(int i=0; i<nodes.size(); i++){
				if(nodes.get(i).inDegree == 0){
					acyclic = false;
					nodesToRemove.add(nodes.get(i));
					deleteNode(nodes.get(i).name);
				}
			}
			return acyclic;
		}

		public void topoSort(){
			ArrayList<Node> nodesRemoved = new ArrayList<Node>();
			int graphSize = nodes.size();
			while(nodesRemoved.size()<graphSize){
				boolean acyclic = topoSortHelper(nodesRemoved);
				if(acyclic){
					System.out.println("Cycle found... no sort possible");
					break;
				}
			}
			for(int i=0; i<nodesRemoved.size(); i++){
				Node n = nodesRemoved.get(i);
				System.out.print(" (" + n.id + ")" + n.name);
				if(i<nodesRemoved.size()-1){
					System.out.print(",");
				}
				if(i==4){
					System.out.println("");
				}
			}
		}
	}

	class Node {
		int id; 
		int inDegree = 0;
		String name;
		public Node(int id, String name){
			this.id = id;
			this.name = name;
		}
	}

	class Edge{
		String name;
		int id;
		int weight;
		Node to, from;
		public Edge(String name, int id, int weight, Node to, Node from){
			if(name == null){
				this.name = null;
			}else{
				this.name = name;
			}
			this.id = id;
			this.to = to;
			this.from = from;
			this.weight = weight;
		}

	}


}


