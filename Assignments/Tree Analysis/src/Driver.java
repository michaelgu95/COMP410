import java.util.Random;
import java.util.Scanner;

public class Driver {
	public static void main(String[] args){
		BST[] bTrees = new BST[15];
		SPLT[] sTrees = new SPLT[15];
		int[] bHeights = new int[15];
		int[] sHeights = new int[15];
		
		for(int i = 0; i<15; i++){
			BST b = new BST(MyRandom.nextString(), null, null, null);
			while(b.size() < 65535){
				String random = MyRandom.nextString();
				while(b.contains(random)){
					random = MyRandom.nextString();
				}
				b.insert(random);
				System.out.println("inserting into BST " + (i+1) + " , size is : " + b.size());
			}
			bHeights[i] = b.height();
			
			SPLT s = new SPLT(MyRandom.nextString(), null, null, null);
			while(s.size() < 65535){
				String random = MyRandom.nextString();
				while(s.contains(random)){
					random = MyRandom.nextString();
				}
				s.insert(random);
				System.out.println("inserting into SPLT " + (i+1) + " , size is : " + s.size());
			}
			sHeights[i] = s.getRoot().height();
		}
		
		
		
		System.out.println("BST average height: " + average(bHeights));
		System.out.println("SPLT average height: " + average(sHeights));
		System.out.println("BST runs, each tree with 65,535 random strings");
		for(int i = 0; i<15; i++){
			System.out.println(i + ": height is " + bHeights[i]);
		}
		
		System.out.println("SPLT runs, each tree with 65,535 random strings");
		for(int i = 0; i<15; i++){
			System.out.println((i+1) + ": height is " + sHeights[i]);
		}
		
	}
	
	public static double average(int[] list) {
	    long sum = 0;
	    int n = list.length;
	    for (int i = 0; i < n; i++)
	        sum += list[i];
	    return ((double) sum) / n;
	}
}

class BST{
	String value;
	BST lChild;
	BST rChild;
	BST parent;
	int size;
	
	public BST(String v, BST l, BST r, BST p){
		this.value = v;
		this.lChild = l;
		this.rChild = r;
		this.parent = p;
		this.size = 0;
	}
	
	public String val(){
		if(parent != null){
			parent.val();
		}
		return value;
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
	
	public int size(){
		return size;
	}
	
	public String findMax(){
		if(rChild==null){
			return value;
		}
		return rChild.findMax();
	}
	
	public String findMin(){
		if(lChild==null){
			return value;
		}
		return lChild.findMin();
	}
	
	public void remove(String v){
		if(get(v)!=null){
			size--;
		}
		if (value.equals(v)) { 
			if (lChild==null && rChild==null) { 
				if(parent == null){
					this.rChild = null;
					this.lChild = null;
					this.value = null;
					this.size = 0;
				}else{
					if(parent.rChild == this){
						parent.rChild = null;
						this.parent = null;
						this.rChild = null;
						this.lChild = null;
						this.value = null;
					}
					else{
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
						this.parent = null;
						this.rChild = null;
						this.lChild = null;
					}
					else{
						parent.lChild = lChild;
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
						this.parent = null;
						this.rChild = null;
						this.lChild = null;
					}
					else{
						parent.lChild = rChild;
						this.parent = null;
						this.rChild = null;
						this.lChild = null;
					}
				}
			}
			else { 
				String min = rChild.findMin();
				value = min;
				rChild.remove(min);
			}
			return;
		}
		if (rChild!=null) {
			if (rChild.contains(v)) {
				rChild.remove(v);
				return;
			}
		}
		if (lChild!=null) {
			if (lChild.contains(v)) {
				lChild.remove(v);
				return;
			}
		}					
	}
	
	public BST get(String v){
		BST containedNode = null;
		String val = this.value;
		if(v.equals(val)){
			return this;
		}else if(v.compareTo(val) > 0){
			if(this.rChild != null){
				containedNode = rChild.get(v);
			}else{
				System.out.println("String not in BST");
			}
			
		}else if (v.compareTo(val) < 0){
			if(lChild != null){
				containedNode = lChild.get(v);
			}else{
				System.out.println("String not in BST");
			}
		}
		return containedNode;
	}
	
	public BST insert(String v){	
		size++;
		if(value == null){
			value = v;
		}
		else if(v.compareTo(value) > 0){
			if(rChild != null){
				rChild.insert(v);
			}else{
				rChild = new BST(v, null,null,this);
			}
		}else if(v.compareTo(value) < 0){
			if(lChild != null){
				lChild.insert(v);
			}else{
				lChild = new BST(v,null,null,this);
			}
		}
		return this;
	}
	
	public boolean contains(String v){
		boolean found = false;
		if(v.equals(value)){
			found = true;
		}else if(v.compareTo(value) > 0){
			if(rChild != null){
				found = rChild.contains(v);
			}else{
				found = false;
			}
			
		}else if (v.compareTo(value) < 0){
			if(lChild != null){
				found = lChild.contains(v);
			}else{
				found = false;
			}
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
		}
		getRoot().removeHelper(v);
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
		if(v.equals(val)){
			return this;
		}else if(v.compareTo(val) > 0){
			if(this.rChild != null){
				containedNode = rChild.getHelper(v);
			}else{
				System.out.println("String not in BST");
			}
			
		}else if (v.compareTo(val) < 0){
			if(lChild != null){
				containedNode = lChild.getHelper(v);
			}else{
				System.out.println("String not in BST");
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
