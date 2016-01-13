import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;


public class Driver {
	public static void main(String[] args){
		Scanner kb = new Scanner(System.in);
		boolean stop = false;
		Graph g = new Graph();
		while(stop==false){
			System.out.println("command (q,an,ae,dn,de,s,pg,pn)?");
			String command = kb.next();
			if(command.equals("q")){
				stop = true;
			}
			else if(command.equals("an")){
				System.out.println("Enter a name for the node: ");
				String name = kb.next();
				boolean addedNode = g.addNode(name);
				System.out.println(addedNode);
			}
			else if(command.equals("ae")){
				System.out.println("Name of node that the edge will be from?");
				String fromName = kb.next();
				System.out.println("Name of node that the edge will be to?");
				String toName = kb.next();
				System.out.println("Enter a name for the edge, enter null for no name");
				String name = kb.next();
				
				boolean addedEdge = g.addEdge(name, toName, fromName);
				System.out.println(addedEdge);
			}
			else if(command.equals("dn")){
				System.out.println("Enter the name of the node you want to delete: ");
				String nameOfNode = kb.next();
				boolean deletedNode = g.deleteNode(nameOfNode);
				System.out.println(deletedNode);
			}
			else if(command.equals("de")){
				System.out.println("Enter the id value of the edge you want to delete: ");
				int id = kb.nextInt();
				System.out.println(g.deleteEdge(id));
			}
			else if(command.equals("s")){
				System.out.println("Number of nodes: " + g.numNodes());
				System.out.println("Number of edges: " + g.numEdges());
			}
			else if(command.equals("pg")){
				g.printGraph();
			}
			else if(command.equals("pn")){
				System.out.println("Enter name of the node you wish to print: ");
				String nodeName = kb.next();
				g.printNode(nodeName);
			}
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
	
	public boolean addEdge(String name, String to, String from){
		if(!nodeNames.contains(to) || !nodeNames.contains(from)){
			return false;
		}
		Node toNode = nodeFromString.get(to);
		Node fromNode = nodeFromString.get(from);
		if(toNode == null || fromNode == null){
			return false;
		}
		Edge newEdge = new Edge(name, edgeId, toNode, fromNode);
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
					if(e.name.equals("null")){
						System.out.println("  (" + e.id + ")" + "--> " + e.to.name);
					}else{
						System.out.println("  (" + e.id + ")" + "(" + e.name + ")" +"--> " + e.to.name);
					}
				}
			}
			System.out.println("");
		}
	}
}

class Node {
	int id; 
	String name;
	public Node(int id, String name){
		this.id = id;
		this.name = name;
	}
}

class Edge{
	String name;
	int id;
	Node to, from;
	public Edge(String name, int id, Node to, Node from){
		if(name == null){
			this.name = null;
		}else{
			this.name = name;
		}
		this.id = id;
		this.to = to;
		this.from = from;
	}
	
}