
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.*;

public class DatabaseConnection 
{
	private static Connection conn = null;
	private static Statement stmt = null;
	private ResultSet rs;

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydatabase","root", "");
		     conn.setAutoCommit(false);  //it won't let mysql to commit changes automatically

			stmt = conn.createStatement();
		}
		catch(Exception ex) {
			
			ex.printStackTrace();
		}
	}
	
	
	public boolean searchAccount(int accno)
	{
		int accountnum = 0;
		try {
			String query = "select accno from account where accno ='"+accno+"' ";
			rs = stmt.executeQuery(query);
			if(rs.next())
				accountnum = rs.getInt("accno");
			if(accountnum == accno)
				return true;
			
		}
		catch(Exception ex) {
			
			ex.printStackTrace();
		}
		return false;
		
	}
	
	public int getBalance(int accno)
	{
		
		int amount=0;
		try {
			String query = "select amount from account where accno = " + accno;
			rs = stmt.executeQuery(query);
			if(rs.next())
				amount = rs.getInt("amount");
			
			
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return amount;
	}
	
	public int insertAccount(int accno, String name, String address, int amount) {
		try {
			String query = "select accno from account ORDER BY accno DESC LIMIT 1;";
			//String query = "select accno from account order by accno desc";

			rs = stmt.executeQuery(query);
			
			 if(rs.next())
				 accno = rs.getInt("accno");
				
			//System.out.print("acc no="+accno);

			accno++;
			

			
			String query1 = "INSERT INTO account VALUES (" + accno + ",\'" + name + "\',\'" + address + "\'," + amount + ")";
			 stmt.executeUpdate(query1);
		    // conn.commit();

			
			String query2 = "INSERT INTO transaction1 VALUES (" + accno + ",\'" + new Date() + "\'," + amount + ",\'CREDIT\')";
			stmt.executeUpdate(query2);
		     conn.commit();

		}
		catch(Exception ex) {
			ex.printStackTrace();
			System.out.println("Rolling back data here....");
			  try{
				 if(conn!=null)
		            conn.rollback();
		      }catch(SQLException se2){
		         se2.printStackTrace();
		      }
		}
		return accno;
	}
	
	public void updateAccount(int accno, int amount, String type) {
		int trnAmount = getBalance(accno);
		try {			
			
			if(type.equals("CREDIT"))
				trnAmount += amount;
			else
				trnAmount -= amount;
			
			String query1 = "UPDATE account SET amount = " + trnAmount + " WHERE accno = " + accno;
			stmt.executeUpdate(query1);
		    // conn.commit();



			String query2 = "INSERT INTO transaction1 VALUES (" + accno + ",\'" + new Date() + "\'," + amount + ",\'" + type  +"\')";
			stmt.executeUpdate(query2);
			
		     conn.commit();
		     

		}
		catch(Exception ex) {
			ex.printStackTrace();
			System.out.println("Rolling back data here....");
			  try{
				 if(conn!=null)
		            conn.rollback();
		      }catch(SQLException se2){
		         se2.printStackTrace();
		      }
		}
	}
	
	public void deleteAccount(int accno) {
		try {
			String query1 = "DELETE FROM account WHERE accno = " + accno;
			stmt.executeUpdate(query1);
		     conn.commit();

		}
		catch(Exception ex) {
			ex.printStackTrace();
			System.out.println("Rolling back data here....");
			  try{
				 if(conn!=null)
		            conn.rollback();
		      }catch(SQLException se2){
		         se2.printStackTrace();
		      }
		}
	}
	
	public List<Transaction> printLastestTransaction(int accno) {
		List<Transaction> txns = new ArrayList<>();
		try {
			String query1 = "select * from transaction1 where accno = " + accno;
			rs = stmt.executeQuery(query1);
			while(rs.next()) {
				Date date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(rs.getString("dateoftrans"));
				Transaction objt = new Transaction(date, rs.getString("transtype"), rs.getInt("amount"));
				//Transaction objt = new Transaction(rs.getString(2), rs.getString(4), rs.getInt(3));
				txns.add(objt);
				//System.out.print("date="+date);
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}		
		return txns;
	}
	

}
