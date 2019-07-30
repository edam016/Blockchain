public class Transaction {
	 private String sender;
	 private String receiver;
	 private int amount;
	 
	 public Transaction(String sender, String receiver, int amount) {
		 this.sender=sender;
		 this.receiver=receiver;
		 this.amount=amount;
	}//Constructor for transaction that takes in sender reciever and amount as input variables
	 
	 public String toString() {//tostring method for transaction
		 return sender + ":" + receiver + "=" + amount;
		}	
	 public String getsender() {//Returns sender from transaction class
		 return sender;
	 }
	 public String getreceiver() {
		 return receiver;//Returns receiver from Transaction class
	 }
	 public int getamount() {//Returns amount from Transaction class
		 return amount;
	 }
	 
	 }