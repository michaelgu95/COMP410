import java.awt.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;


public class Driver {
	public static void main (String[] args){
		if(args.length != 0){
			BHEAP b = new BHEAP();
			ArrayList <String> strings = new ArrayList<String>();
			ArrayList<Integer> integers = new ArrayList<Integer>();
			for(int i=0; i<args.length; i+=2){
				//regular insert
				b.insert(Integer.parseInt(args[i]), args[i+1]);
				//save priority/string pairs for build
				integers.add(Integer.parseInt(args[i]));
				strings.add(args[i+1]);
			}
			print(b);
			
			//create new heap before building
			b = new BHEAP();
			
			//convert ArrayLists to arrays as arguments for build function
			String[] s = new String[strings.size()];
			int[] v = new int[integers.size()];
			for(int i =0; i<strings.size(); i++){
				s[i] = strings.get(i);
				v[i] = integers.get(i);
			}
			b.build(v, s);
			print(b);
			
			//print order sort
			orderPrint(b);
			
		}else if(args.length == 0){
			Scanner kb = new Scanner(System.in);
			boolean stop = false;
			BHEAP b = new BHEAP();
			while(stop==false){
				System.out.println("command (q,n,i,d,g,s,b,f,p,o)?");
				String command = kb.next();
				if(command.equals("q")){
					stop = true;
				}else if(command.equals("n")){
					b = new BHEAP();
				}else if(command.equals("i")){
					System.out.println("type a priority value(integer):");
					int p = kb.nextInt();
					System.out.println("type a string value: ");
					String s = kb.next();
					b.insert(p,s);
				}else if(command.equals("d")){
					b.delMin();
				}else if(command.equals("g")){
					System.out.println(b.getMin());
				}else if(command.equals("s")){
					System.out.println(b.size() + " elements");
				}else if(command.equals("f")){
					System.out.println("How many items do you wish to insert?");
					int num = kb.nextInt();
					for(int i=0; i<num; i++){
						int random = Math.abs(new Random().nextInt());
						b.insert(random, MyRandom.nextString());
					}
				}else if(command.equals("b")){
					int p = 0;
					//store user input into arraylists
					ArrayList <String> strings = new ArrayList<String>();
					ArrayList<Integer> values = new ArrayList<Integer>();
					while(p != -1){
						System.out.println("type a priority value(integer):");
						p= kb.nextInt();
						if(p != -1){
							values.add(p);
							System.out.println("type a string value: ");
							String s = kb.next();
							strings.add(s);
						}
					}
					//convert ArrayLists to arrays as arguments for build function
					String[] s = new String[strings.size()];
					int[] v = new int[values.size()];
					for(int i =0; i<strings.size(); i++){
						s[i] = strings.get(i);
						v[i] = values.get(i);
					}
					b.build(v, s);
					
				}else if(command.equals("p")){
					print(b);
				}else if(command.equals("o")){
					orderPrint(b);
				}
			}
		}
	}
	
	private static void print(BHEAP b){
		for(int i=1; i<=b.size();){
			int priority = b.values.get(i);
			String str= b.strings.get(i);
			System.out.print(priority + ":" + str);
			i++;
			if(i<=b.size()){
				System.out.print(", ");
			}else{
				System.out.println("");
			}
		}
	}
	
	private static void orderPrint(BHEAP b){
		int size = b.size();
		for(int i=0; i<size;){
			System.out.print(b.getMin());
			b.delMin();
			i++;
			if(i<size){
				System.out.print(", ");
			} 
		}
		System.out.println("");
	}
}


class BHEAP {
	public ArrayList <String> strings = new ArrayList<String>();
	public ArrayList<Integer> values = new ArrayList<Integer>();
	private int root = 1;
	private int last = 0;
	
	public int size(){
		return this.last;
	}
	
	public String getMin(){
		if(last == 0){
			return "No values in BHEAP";
		}
		int min = values.get(root);
		int minIndex = values.indexOf(min);
		String str = strings.get(minIndex);
		return min + ":" + str;
	}
	
	public void insert(int value, String str){
		if(size() == 0){
			values.add(last, null);
			strings.add(last, null);
		}	
		last++;
		values.add(last, value);
		strings.add(last, str);
		int n = last;
		int p = PN(n);
		while((p!=0) && value<=values.get(p)){
			int pVal = values.get(p);
			int nVal = values.get(n);
			String pStr = strings.get(p);
			String nStr = strings.get(n);
			
			values.remove(p);
			values.add(p, nVal);
			values.remove(n);
			values.add(n, pVal);
			
			strings.remove(p);
			strings.add(p, nStr);
			strings.remove(n);
			strings.add(n, pStr);
			n=p;
			p=PN(n);
		}
	}
	
	public void delMin(){
		if(size() == 0){
			return;
		}
		if(size() == 1){
			values.remove(1);
			last--;
			return;
		}
		
		last--;
		values.remove(root);
		values.add(root, values.get(last));
		values.remove(last+1);
		
		strings.remove(root);
		strings.add(root, strings.get(last));
		strings.remove(last+1);
		
		bubbleDown(root);
	}
	
	public void build(int[] priorities, String[] vals){
		values.add(last, null);
		strings.add(last, null);
		for(int i=0; i<priorities.length; i++){
//			insert(priorities[i], vals[i]);
			last++;
			values.add(priorities[i]);
			strings.add(vals[i]);
		}
		int sn = PN(last);
		for(int j=sn; j>0; j--){
			bubbleDown(j);
		}
	}
	
	private void bubbleDown(int n){
		boolean done = false;
		int c = 0;
		while(!done){
			if(isLeaf(n)){
				done = true;
			}else{
				if(hasOnlyLC(n)){
					c=LC(n);
				}else{
					if(LCV(n) < RCV(n)){
						c=LC(n);
					}else{
						c=RC(n);
					}
				}
				if(values.get(n) > values.get(c)){
					int cVal = values.get(c);
					int nVal = values.get(n);
					String cStr = strings.get(c);
					String nStr = strings.get(n);
					
					values.remove(c);
					values.add(c, nVal);
					values.remove(n);
					values.add(n, cVal);
					
					strings.remove(c);
					strings.add(c, nStr);
					strings.remove(n);
					strings.add(n, cStr);
					n=c;
				}else{
					done = true;
				}
			}
			
		}
	}
	
	private int LC(int n){
		return 2*n;
	}
	
	private int RC(int n){
		return 2*n +1;
	}
	
	private int PN(int n){
		return (int) Math.floor(n/2);
	}
	
	private int LCV(int n){
		return values.get(LC(n));
	}
	
	private int RCV(int n){
		return values.get(RC(n));
	}
	
	private int PNV(int n){
		return values.get(PN(n));
	}
	
	private boolean isLeaf(int n){
		return LC(n) > last && RC(n) > last;
	}
	
	private boolean hasOnlyLC(int n){
		return RC(n) > last && LC(n) <= last;
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
	     return new String(b);
	  }

	  public static String nextString() {
	     return nextString(5, 25);
	  }
	  
}
