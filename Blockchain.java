import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.io.*;


public class Blockchain {
	static Blockchain bc = new Blockchain();
	static int counter=0;
	static ArrayList<Block> list =new ArrayList<Block>();
	public static void main(String []args) throws IOException {
		fromFile("bitcoinBank.txt");//Main method which calls upon other methods 
		if(validateBlockchain()==true) {
			System.out.println("Current blockchain is valid");//Checks if blockchain is valid
			prompt();
		}
		else {
			System.out.println("Current blockchain is invalid");
		}
		System.out.println("Enter your minerID");
		Scanner input = new Scanner(System.in);
		String a = input.nextLine();//Writes the new blocks to a textfile
		toFile("blockchain_"+a+".txt");
	}
	
	public Blockchain() {}
	public static void prompt() throws UnsupportedEncodingException {
		try {/*This is the method that asks the user the information in order to input into each block
		It also recursively asks the user for more transactions if needed*/
		System.out.println("Please input the sender");
		Scanner input = new Scanner(System.in);
		String sender = input.nextLine();
		System.out.println("Please input the reciever");
		Scanner input2 = new Scanner(System.in);
		String reciever = input2.nextLine();
		System.out.println("Please input amount being sent in integers");
		Scanner input3 = new Scanner(System.in);
		int amount = input3.nextInt();
		if(getBalance(sender)>amount) {
			Transaction transaction = new Transaction(sender, reciever, amount);
			Block newblock = new Block(counter,transaction);
			add(newblock);
			System.out.println("Would you like another Transaction? Enter 1 for yes and 0 for no");
			Scanner input4 = new Scanner(System.in);
			int decision = input.nextInt();
			if(decision==1) {
				prompt();
			}
		}
		else {
			System.out.println("Insufficient funds");
			prompt();//executes if the user does not have sufficient funds
		}}
		catch(Exception e) {
			System.out.println("Invalid input, try again");
		}
	}
	
	public static Blockchain fromFile(String fileName) throws FileNotFoundException {//reads file
		/*Here we scan the textfile and then from the information found on the file, individual blocks are created and added to the 
		 * list of blockchain array
		 */
		 File file = new File(fileName); 
		 Scanner info = new Scanner(file);
		 
		 int i=0;
		 while(info.hasNextLine()) {
			 String index1 =(info.nextLine());
			 int index2 = Integer.parseInt(index1);
			 String timestamp =(info.nextLine());
			 String sender = (info.nextLine());
			 String reciever =(info.nextLine());
			 String amount1 = (info.nextLine());
			 String nonce = (info.nextLine());
			 String hash = (info.nextLine());
			 int amount2 =Integer.parseInt(amount1);
			 Transaction transaction = new Transaction(sender, reciever, amount2);
			 if(i==0) {
				 Block blocki = new Block(index2, transaction, nonce, hash, Long.parseLong(timestamp));//add previoushash
				 list.add(blocki);
			 }
			 else {	
				 Block blocki = new Block(index2, transaction, nonce, hash, Long.parseLong(timestamp));
				 blocki.setpreviousHash(list.get(index2-1).gethash());
				 list.add(blocki);
			 }
			 counter++;
			 i++; 
		 }
		 return bc;
	}
	
	public static void toFile(String fileName) throws IOException {//adds blocks to blockchain
		/*Here the information from the blockchain created is then written on the text file in which each block
		 * has its information written on it
		 */
		
		String nameoffile = (fileName+".txt");
		FileWriter filewrite = new FileWriter(nameoffile);
		PrintWriter printwrite = new PrintWriter(filewrite);
		
		for(int i=0;i<list.size();i++) {
			printwrite.println(list.get(i).getindex());
			printwrite.println(list.get(i).gettimestamp().getTime());
			printwrite.println(list.get(i).gettransaction().getsender());
			printwrite.println(list.get(i).gettransaction().getreceiver());
			printwrite.println(list.get(i).gettransaction().getamount());
			printwrite.println(list.get(i).getnonce());
			printwrite.println(list.get(i).gethash());
		}	
		printwrite.close();
	}
	
	public static boolean validateBlockchain() throws UnsupportedEncodingException {
		/*Here the blockchain is validated in which the conditions of the hash, previoushash, index and balance are
		 * checked in order to confirm the blockchain is correct
		 */
		int a=0;
		for(int j=0;j<list.size();j++) {
			Block ab=list.get(j);
			if(ab.gethash().equals(Sha1.hash(ab.toString()))) {
				if(ab.getindex()==j) {
					if(j==0) {
						if(ab.getpreviousHash()=="00000"){
							a++;	
						}
					}
					else if(ab.getpreviousHash()==list.get(j-1).gethash()) {
						if(currentBalance(ab.gettransaction().getsender(),j)>=(ab.gettransaction().getamount())) {
								a++;			
						}
					}
			}
		}
	}
		
	if(a==list.size()) {
		return true;
	}
	else {
		return false;
	}
		
		}
	public static int currentBalance(String username, int index) {
		/*Self made method:Method I made to calculate the balance of a user up until a certain transaction point to check if the user
		 * had the necessary funds in order to transfer money
		 */
		int total=0;
		for(int i =0;i<index;i++) {
			if(username.equals(list.get(i).gettransaction().getreceiver())) {
				total=list.get(i).gettransaction().getamount()+total;
				
			}
			if(username.equals(list.get(i).gettransaction().getsender())) {
				total-=list.get(i).gettransaction().getamount();		
				
			}
		}
		return total;
	}
	public static int getBalance(String username){
		/*Here the total balance of an individual is calculated  by iterating through each block and adding/subtracting
		 * the amount transferred per transaction
		 */
		int total=0;
		for(int i=0; i<list.size();i++) {
			if(username.equals(list.get(i).gettransaction().getreceiver())) {
				total+=list.get(i).gettransaction().getamount();
			}
			if(username.equals(list.get(i).gettransaction().getsender())) {
				total-=list.get(i).gettransaction().getamount();			
			}
		}
		return total;
	}
	
	public static void add(Block block) throws UnsupportedEncodingException {
		/*Here this method prompts the user to add another transaction in which the information
		 * pertaining to a transaction is asked and when the block is created, it adds the block to the list
		 */
		
		block.setpreviousHash(list.get(list.size()-1).gethash());
		String a="";
		int b=0;
		double initial=System.currentTimeMillis();
		int hashcount=0;
		while(b==0) {
			block.setnonce(generatenonce());
			a=Sha1.hash(block.toString());
			System.out.println(a);
			hashcount++;
			if(a.startsWith("00000")) {
				break;
			}
		}
		System.out.println("Time taken to acquire Hash :"+ (System.currentTimeMillis()-initial));
		System.out.println("Number of Hashs created: "+hashcount);
		block.setHash(a);
		counter++;
		list.add(block);
		
	}
	public static String generatenonce() {
		//This method generates the nonce for a block by randomly generating ASCII values from 33 to 126 
		//in which it generates 1 to 18 values randomly in each iteration
		Random r = new Random();
		int n = r.nextInt(18)+1;
			String a="";
				for(int i=0;i!=n;i++) {
					char c = (char) ThreadLocalRandom.current().nextInt(33, 126 + 1);
					a+=c;
				}
			return a;
		}
}
