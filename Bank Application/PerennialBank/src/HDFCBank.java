import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class HDFCBank implements Bank {
	private List<Account> accountList = new ArrayList<Account>();
	private int accno=11111;
	
	private DatabaseConnection db=new DatabaseConnection();
	
	@Override
	public String openAccount(String name, String address, int amount) throws InsufficientBalanceException {
		
		//AddAccountClass add=new AddAccountClass();
		

		if(amount < Bank.MIN_BALANCE)
			throw new InsufficientBalanceException("Min balance required to open Account is "+Bank.MIN_BALANCE);
		//Account acct = new Account(accno++,name, address,amount);
		
		accno = db.insertAccount(accno, name, address, amount);

		//accountList.add(acct);
		//add.add(accno,name,address,amount);

		//System.out.print("acc no="+accno);
		return "HDFC Thanks u for opening acc new accno is "+accno;
		
	}

	@Override
	public int withdraw(int accno, int amount) throws InsufficientBalanceException, InvalidAccountException {
		//Account acct = searchAccount(accno);
		
		if(!db.searchAccount(accno))  //no account in database
			throw new InvalidAccountException();
		
		int oldBalance = db.getBalance(accno);

		
		 oldBalance = db.getBalance(accno);
		if(oldBalance-amount > Bank.MIN_BALANCE) {
			//acct.setBalance(oldBalance-amount);
			db.updateAccount(accno, amount, "DEBIT");  //update in database
			//Transaction txn = new Transaction(new Date(),"DEBIT",amount);
			//acct.getTxns().add(txn);
		}else {
			throw new InsufficientBalanceException("Min balance requires is"+Bank.MIN_BALANCE);
		}
		return db.getBalance(accno);
	}

	/*private Account searchAccount(int accno2) {
		// TODO Auto-generated method stub
		for(Account acct : accountList) {
			if(acct.getAccno()==accno2)
				return acct;
		}
		return null;
	}*/

	@Override
	public int deposit(int accno, int amount) throws InvalidAccountException {
		//Account acct = searchAccount(accno);
		if(!db.searchAccount(accno))
			throw new InvalidAccountException();
		
		int oldBalance = db.getBalance(accno);
		
		db.updateAccount(accno, oldBalance+amount, "CREDIT");

		
		//acct.setBalance(oldBalance+amount);
		//Transaction txn = new Transaction(new Date(),"CREDIT",amount);
		//acct.getTxns().add(txn);
		return db.getBalance(accno);
	}

	@Override
	public int transfer(int accfrom, int accto, int amount)
			throws InvalidAccountException, InsufficientBalanceException {     
		int newbalance = withdraw(accfrom,amount);
		try {
			deposit(accto,amount);			
		}catch(Exception ex) {
			deposit(accfrom,amount);			
		}
		return newbalance;

	}

	@Override
	public int closeAccount(int accno) throws InvalidAccountException {
		
		//DeleteAccountClass delete=new DeleteAccountClass();
		//Account acct = searchAccount(accno);
		
		if(!db.searchAccount(accno))
			throw new InvalidAccountException();
		
		int balance = db.getBalance(accno);
		
		db.deleteAccount(accno);
		

		//accountList.remove(acct);
		//delete.delete(accno);

		return balance;
	}

	@Override
	public String printRecentTransaction(int accno, int notxns) throws InvalidAccountException {
		// TODO Auto-generated method stub
		//Account acct = searchAccount(accno);
		if(!db.searchAccount(accno)) 
			throw new InvalidAccountException();
		List<Transaction> txns = db.printLastestTransaction(accno);
		Collections.sort(txns,(txn1,txn2)->{return txn2.getDate().compareTo(txn1.getDate());});
		StringBuilder builder = new StringBuilder();
		for(int i=0; i < notxns; i++) {
			builder.append(txns.get(i).toString());
		}
		return builder.toString();
	}

}
