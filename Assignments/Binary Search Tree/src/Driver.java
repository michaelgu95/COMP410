import java.util.*;

public class Driver {
	public static void main(String[] args){
		Scanner kb = new Scanner(System.in);
		
		boolean stop = false;
		BST BST  = new BST(null,null,null,null);
		while(!stop){
			System.out.println("Type an operation to perform: ");
			String command = kb.next();
			if(command.equals("new")){
				BST = new BST(null,null,null,null);
			}
			else if(command.equals("i")){
				System.out.println("Enter a string to insert into the BST: ");
				String i = kb.next();
				BST.insert(i);
			}else if(command.equals("c")){
				System.out.println("Enter a string to check if BST contains: \n");
				String c = kb.next();
				System.out.println(BST.contains(c));
			}else if(command.equals("h")){
					System.out.println(BST.height());
			}else if(command.equals("r")){
				System.out.println("Enter a string to remove from the BST: ");
				String r = kb.next();
				BST.remove(r);
			}else if(command.equals("g")){
				System.out.println("Enter a string, and the program will return a node containing that string");
				String g = kb.next();
				BST node = BST.get(g);
				if(node != null){
					System.out.println("The Node that contains the string is: " + node + " and its value is: " + node.value);
				}
			}else if(command.equals("x")){
				System.out.println(BST.findMax());
			}else if(command.equals("n")){
				System.out.println(BST.findMin());
			}else if(command.equals("v")){
				System.out.println(BST.val());
			}else if(command.equals("s")){
				System.out.println(BST.size);
			}else if(command.equals("e")){
				int s = BST.size;
				if(s>0){
					System.out.println("false");
				}else{
					System.out.println("true");
				}
			}else if(command.equals("q")){
				stop = true;
			}else if(command.equals("p")){
				BST.print(0);
			}else if(command.equals("f")){
				BST.insert(MyRandom.nextString());
			}else{
				System.out.println("Not a valid command.");
			}
		}
	}
}

/*
 ADT:
new:                  BST
insert:   BST x Elt   BST
remove:   BST x Elt   BST
findMin:  BST         Elt
findMax:  BST         Elt
contains: BST x Elt   Boolean (searching)
get:      BST x Elt   BST     (return a cell) 
val:      BST         Elt     (get root value)
size:     BST         Nat     (natural number)
empty:    BST         Boolean
   */

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
