import java.util.Random;
import java.util.Scanner;

public class Driver {
	public static void main(String[] args){
		Scanner kb = new Scanner(System.in);
		
		boolean stop = false;
		SPLT SPLT = new SPLT(null,null,null,null);
		while(!stop){
			System.out.println("Type an operation to perform: ");
			String command = kb.next();
			if(command.equals("new")){
				SPLT = new SPLT(null,null,null,null);
			}
			else if(command.equals("i")){
				System.out.println("Enter a string to insert into the SPLT: ");
				String i = kb.next();
				SPLT.getRoot().insert(i);
			}else if(command.equals("c")){
				System.out.println("Enter a string to check if SPLT contains: \n");
				String c = kb.next();
				System.out.println(SPLT.contains(c));
			}else if (command.equals("h")){
				System.out.println(SPLT.getRoot().height());
			}else if(command.equals("r")){
				System.out.println("Enter a string to remove from the SPLT: ");
				String r = kb.next();
				SPLT.getRoot().remove(r);
			}else if(command.equals("g")){
				System.out.println("Enter a string, and the program will return a node containing that string");
				String g = kb.next();
				SPLT node = SPLT.getRoot().get(g);
				if(node != null){
					System.out.println("The Node that contains the string is: " + node + " and its value is: " + node.value);
				}
			}else if(command.equals("x")){
				System.out.println(SPLT.findMax());
			}else if(command.equals("n")){
				System.out.println(SPLT.findMin());
			}else if(command.equals("v")){
				System.out.println(SPLT.val());
			}else if(command.equals("s")){
				System.out.println(SPLT.size());
			}else if(command.equals("splay")){
				System.out.println("Which node would you like to splay?");
				String n = kb.next();
				SPLT.get(n).splay();
			}else if(command.equals("e")){
				int s = SPLT.size();
				if(s>0){
					System.out.println("false");
				}else{
					System.out.println("true");
				}
			}else if(command.equals("q")){
				stop = true;
			}else if(command.equals("p")){
				SPLT.getRoot().print(0);
			}else if(command.equals("f")){
				String random = MyRandom.nextString();
				while(SPLT.contains(random)){
					random = MyRandom.nextString();
				}
				SPLT.insert(random);
			}else{
				System.out.println("Not a valid command.");
			}
		}
	}
}


class SPLT{
	String value;
	SPLT lChild;
	SPLT rChild;
	SPLT parent;
	
	public SPLT(String v, SPLT l, SPLT r, SPLT p){
		this.value = v;
		this.lChild = l;
		this.rChild = r;
		this.parent = p;
	}
	
	public int height(){
		if(value == null){
			return -1;
		}
		int height = 0;
		if(lChild != null || rChild != null){
			int rHeight = 0;
			int lHeight = 0;
			if(lChild != null){
				lHeight++;
				lHeight += lChild.height();
			}
			if(rChild != null){
				rHeight++;
				rHeight += rChild.height();
			}
			if(lHeight > rHeight){
				height = lHeight;
			}else if (rHeight > lHeight){
				height = rHeight;
			}else{
				height = lHeight;
			}
		}
		return height;
	}
	
	public String val(){
		if(parent != null){
			parent.val();
		}
		return value;
	}
	
	public SPLT getRoot(){
		SPLT root = null;
		if(parent == null){
			root = this;
		}else{
			root = parent.getRoot();
		}
		return root;
	}
	
	
	 public int size() {
		 return size(getRoot());
	}

	 private int size(SPLT x) {
		 if (x == null) {
			 return 0;
		 } else {
			 return 1 + size(x.lChild) + size(x.rChild);
		 }
	}
	
	public String findMax(){
		if(value == null){
			return "No strings in SPLT";
		}
		String max = getRoot().findMaxHelper();
		SPLT maxNode = get(max);
		maxNode.splay();
		return max;
	}
	 
	public String findMaxHelper(){
		if(rChild==null){
			return value;
		}
		return rChild.findMaxHelper();
	}
	
	public String findMin(){
		if(value == null){
			return "No strings in SPLT";
		}
		String min = getRoot().findMinHelper();
		SPLT minNode = get(min);
		minNode.splay();
		return min;
	}
	
	public String findMinHelper(){
		if(lChild==null){
			return value;
		}
		return lChild.findMinHelper();
	}
	
	public void remove(String v){
		SPLT nodeToRemove = getRoot().getHelper(v);
		SPLT parent = null;
		if(nodeToRemove != null){
			parent = nodeToRemove.parent;
			getRoot().removeHelper(v);
		}
		
		if(parent != null){
			parent.splay();
		}
	}
	
	public void removeHelper(String v){
		if (value.equals(v)) { 
			if (lChild==null && rChild==null) { 
				if(parent == null){
					this.rChild = null;
					this.lChild = null;
					this.value = null;
				}else{
					if(parent.rChild == this){
						parent.rChild = null;
						this.parent = null;
						this.rChild = null;
						this.lChild = null;
						this.value = null;
					}
					else if(parent.lChild == this){
						parent.lChild = null;
						this.parent = null;
						this.rChild = null;
						this.lChild = null;
						this.value = null;
					}
				}
			}
			else if (lChild!=null && rChild==null) { 
				if(parent == null){
					this.value = lChild.value;
					this.rChild = lChild.rChild;
					this.lChild = lChild.lChild;
				}else{
					if(parent.rChild == this){
						parent.rChild = lChild;
						lChild.parent = parent;
						this.parent = null;
						this.rChild = null;
						this.lChild = null;
					}
					else if(parent.lChild == this){
						parent.lChild = lChild;
						lChild.parent = parent;
						this.parent = null;
						this.rChild = null;
						this.lChild = null;
					}
				}
			}
			else if (lChild==null && rChild!=null) {
				if(parent == null){
					this.value = rChild.value;
					this.lChild = rChild.lChild;
					this.rChild = rChild.rChild;
				}else{
					if(parent.rChild == this){
						parent.rChild = rChild;
						rChild.parent = parent;
						this.parent = null;
						this.rChild = null;
						this.lChild = null;
					}
					else if(parent.lChild == this){
						parent.lChild = rChild;
						rChild.parent = parent;
						this.parent = null;
						this.rChild = null;
						this.lChild = null;
					}
				}
			}
			else { 
				String min = rChild.findMinHelper();
				value = min;
				rChild.removeHelper(min);
			}
			return;
		}
		if (rChild!=null) {
			if (rChild.containsHelper(v)) {
				rChild.removeHelper(v);
				return;
			}
		}
		if (lChild!=null) {
			if (lChild.containsHelper(v)) {
				lChild.removeHelper(v);
				return;
			}
		}					
	}
	
	

	public SPLT getHelper(String v){
		SPLT containedNode = null;
		String val = this.value;
		if(value==null){
			System.out.println("No items in SPLT");
		}else if(v.equals(val)){
			return this;
		}else if(v.compareTo(val) > 0){
			if(this.rChild != null){
				containedNode = rChild.getHelper(v);
			}else{
				System.out.println("String not in SPLT");
			}
			
		}else if (v.compareTo(val) < 0){
			if(lChild != null){
				containedNode = lChild.getHelper(v);
			}else{
				System.out.println("String not in SPLT");
			}
		}
		return containedNode;
	}
	
	public SPLT get(String v){
		SPLT nodeWithValue = getRoot().getHelper(v);
		return nodeWithValue;
	}
	
	public SPLT insertHelper(String v){	
		SPLT insertedNode = null;
		if(value == null){
			value = v;
			insertedNode = this;
		}
		else if(v.compareTo(value) > 0){
			if(rChild != null){
				insertedNode = rChild.insertHelper(v);
			}else{
				rChild = new SPLT(v, null,null,this);
				insertedNode = rChild;
			}
		}else if(v.compareTo(value) < 0){
			if(lChild != null){
				insertedNode = lChild.insertHelper(v);
			}else{
				lChild = new SPLT(v,null,null,this);
				insertedNode = lChild;
			}
		}else if(v.equals(value)){
			insertedNode = null;
		}
		return insertedNode;
	}
	
	public SPLT insert(String v){
		if(getRoot().contains(v)){
			System.out.println("Duplicate value are not allowed");
			return this;
		}
		SPLT insertedNode = getRoot().insertHelper(v);
		insertedNode.splay();
		return this;
	}
	
	public boolean containsHelper(String v){
		boolean found = false;
		if(value == null){
			found = false;
		}
		else if(v.equals(value)){
			found = true;
		}else if(v.compareTo(value) > 0){
			if(rChild != null){
				found = rChild.containsHelper(v);
			}else{
				found = false;
			}
			
		}else if (v.compareTo(value) < 0){
			if(lChild != null){
				found = lChild.containsHelper(v);
			}else{
				found = false;
			}
		}
		return found;
	}
	
	public boolean contains(String v){
		boolean found = getRoot().containsHelper(v);
		if(found){
			SPLT nodeWithValue = get(v);
			nodeWithValue.splay();
		}
		return found;
	}
	
	public void print(int count){
		String whitespace = "";
		for(int i = 0;i<count;i++){
			whitespace += "  ";
		}
		if(rChild != null){
			count++;
			rChild.print(count);
			if(value!=null){
				System.out.println(whitespace + value);
			}
			if (lChild != null){
				lChild.print(count);
			}
			
		}else if(lChild != null){
			count++;
			if(rChild == null){
				System.out.println(whitespace + value);
			}else{
				if (rChild != null){
					rChild.print(count);
				}
			}
			if(lChild!=null){
				lChild.print(count);
			}
			
		}else{
			if(value!=null){
				System.out.println(whitespace + value);
			}
			
		}
	}
	
	private void rotateLeft(SPLT n) {
		SPLT p = n.parent;
		SPLT g = p.parent;
		
		if (p != null) {
			p.rChild = n.lChild;
		}
		
		if (n.lChild != null) {
			n.lChild.parent = p;
		}

		n.lChild = p;
		if (p != null) {
			p.parent = n;
		}

		n.parent = g;
		if (g == null) {
			n.parent = null;
		} else if (g.rChild == p) {
			g.rChild = n;
		} else {
			g.lChild = n;
		}
	}
	
	private void rotateRight(SPLT n) {

		SPLT p = n.parent;
		SPLT g = p.parent;

		if (p != null) {
			p.lChild = n.rChild;
		}

		if (n.rChild != null) {
			n.rChild.parent = p;
		}

		n.rChild = p;
		if (p != null) {
			p.parent = n;
		}

		n.parent = g;
		if (g == null) {
			n.parent = null;
		} else if (g.rChild == p) {
			g.rChild = n;
		} else {
			g.lChild = n;
		}
	}
	
	private void zigzigL(SPLT n){
		SPLT p = n.parent;
		SPLT g = p.parent;
		
		n.parent = g.parent;
		if(g.parent != null){
			if(g == g.parent.lChild){
				g.parent.lChild = n;
			}else if (g == g.parent.rChild){
				g.parent.rChild = n;
			}
		}
		
		p.rChild = n.lChild;
		if(n.lChild != null){
			n.lChild.parent = p;
		}
		
		if(g != null){
			g.rChild = p.lChild;
			if(p.lChild != null){
				p.lChild.parent = g;
			}
		}
		
		n.lChild = p;
		p.parent = n;
		if(g != null){
			p.lChild = g;
			g.parent = p;
		}
	}
	
	private void zigzigR(SPLT n){
		SPLT p = n.parent;
		SPLT g = p.parent;
		
		n.parent = g.parent;
		if(g.parent != null){
			if(g == g.parent.lChild){
				g.parent.lChild = n;
			}else if (g == g.parent.rChild){
				g.parent.rChild = n;
			}
		}
		
		p.lChild = n.rChild;
		if(n.rChild != null){
			n.rChild.parent = p;
		}
		
		if(g != null){
			g.lChild = p.rChild;
			if(p.rChild != null){
				p.rChild.parent = g;
			}
		}
		
		n.rChild = p;
		p.parent = n;
		if(g != null){
			p.rChild = g;
			g.parent = p;
		}
		
	}
	
	public void splay(){
		SPLT p = this.parent;
		if(p == null){
			return;
		}
		if(p.parent == null){
			if(this == p.rChild){
				rotateLeft(this);
			}else if(this == p.lChild){
				rotateRight(this);
			}
		}else{
			SPLT g = p.parent;
			if(p == g.lChild && this == p.lChild){
				zigzigR(this);
			}else if(p == g.rChild && this == p.lChild){
				rotateRight(this);
				rotateLeft(this);
			}else if(p == g.lChild && this == p.rChild){
				rotateLeft(this);
				rotateRight(this);
			}else if (p == g.rChild && this == p.rChild){
				zigzigL(this);
			}
		}
		if(this != getRoot()){
			splay();
		}
		return;
	}
}


class MyRandom {

	  private static Random rn = new Random();

	  private MyRandom(){ }

	  public static int rand(int lo, int hi) {
	     int n = hi - lo + 1;
	     int i = rn.nextInt() % n;
	     if (i < 0) i = -i;
	     return lo + i;
	  }

	  public static String nextString(int lo, int hi) {
	     int n = rand(lo, hi);
	     byte b[] = new byte[n];
	     for (int i = 0; i < n; i++)
	     b[i] = (byte)rand('a', 'z');
	     return new String(b, 0);
	  }

	  public static String nextString() {
	     return nextString(5, 25);
	  }
	  
}
