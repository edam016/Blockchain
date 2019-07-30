import java.sql.*;

public class Block {
	 
	private int index; // the index of the block in the list
	 private Timestamp timestamp; // time at which transaction
	 // has been processed
	 private Transaction transaction; // the transaction object
	 private String nonce; // random string (for proof of work)
	 private String previousHash; // previous hash (set to "" in first block)
	 //(in first block, set to string of zeroes of size of complexity "00000")
	 private String hash; // hash of the block (hash of string obtained from previous variables via toString() method)
	 
	 public Block(int index, Transaction transaction,String nonce, String hash, long timestamp) {
		 /*Constructor for block using index, transactiom, nonce, hash and timestamp as the input parameters
		  * 
		  */
		 this.previousHash=this.previousHash;
		 if(index==0) {
		 this.previousHash="00000";
		 }
		 this.timestamp= new Timestamp(timestamp);
		 this.transaction=transaction;
		 this.index = index;
		 this.hash=hash;
		 this.nonce=nonce;
	 }
	 public Block(int index, Transaction transaction) {
		 /*Constructor for block using index and transaction as the input parameters
		  * 
		  */
		 this.transaction=transaction;
		 this.timestamp = new Timestamp(System.currentTimeMillis());
		 this.index = index;
	 }
	
	 public void setpreviousHash(String newhash) {
		 //Method that sets previous hash
		 this.previousHash=newhash;
	 }
	 public void setHash(String hash) {
		 //method that sets hash
		 this.hash=hash;
	 }
	 public void setnonce(String nonce) {
		 //method that sets nonce
		 this.nonce=nonce;
	 }
	 public String toString() {//Tostring method
		 return timestamp.toString() + ":" + transaction.toString()
		 + "." + nonce+ previousHash;
		}
	 public Timestamp gettimestamp() {
		 return timestamp;//method that returns timestamp
	 }
	 public int getindex() {
		 return index;//method that returns index
	 }
	 public String gethash() {
		 return hash;//method that returns hash
	 }
	 public String getpreviousHash() {
		 return previousHash;//method that returns previoushash
	 }
	 public Transaction gettransaction(){
		 return transaction;//method that returns transaction
		}
	 public String getnonce() {
		 return nonce;
	 }//method that returns nonce
	 }