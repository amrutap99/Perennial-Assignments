package bankapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.sun.jdi.Type;

import java.util.*;
public class DatabaseConnection {
	
	private static Connection con = null;
	private static Statement st = null;
	private ResultSet rs;

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bankdatabase1", "root", "root");
			st = con.createStatement();
			con.setAutoCommit(false);
			
			if (con != null) {
				System.out.println("Connected !");
				// conn.close();
			}

		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public boolean searchAccount(int accno) {
		int acc = 0;
		try {
			String query = "select accountnumber from account where accountnumber= " + accno;
			rs = st.executeQuery(query);
			if(rs.next())
				acc = rs.getInt("accountnumber");
			if(acc == accno)
				return true;
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}
	
	public int getBalance(int accno) {
		int balance = 0;
		try {	
			String query = "select balance from account where accountnumber = " + accno;
			rs = st.executeQuery(query);
			
			if(rs.next())
				balance = rs.getInt("balance");
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		return balance;
	}
	
	public int insertAccount(int accno, String name, String address, int balance) {
		try {
			String query = "select accountnumber from account ORDER BY accountnumber DESC LIMIT 1";
			rs = st.executeQuery(query);
			
			if(rs.next())
				accno = rs.getInt("accountnumber");
			
			accno++;
			
			
			String query1 = "INSERT INTO account(accountnumber,name,address,balance) VALUES('"+accno+"', '"+name+"', '"+address+"','"+balance+"')";
			st.executeUpdate(query1);
			
			
			String query2 ="INSERT INTO transaction(accountnumber,dateoftrs,amount,type) VALUES('"+accno+"','"+new Date()+"', '"+balance+"','CREDIT' )   ";
			st.executeUpdate(query2);
			
			con.commit();
		}
		catch(Exception ex) {
			//ex.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
		return accno;
	}
	
	public void updateAccount(int accno, int amount, String type) {
		try {
			StringBuilder query1 = new StringBuilder();
			
			if(type.equals("CREDIT"))
				query1 = query1.append("UPDATE account SET balance = balance + " + amount + " WHERE accountnumber = " + accno);
			else
				query1 = query1.append("UPDATE account SET balance = balance - " + amount + " WHERE accountnumber = " + accno);
			
			st.executeUpdate(query1.toString());
			
			String query2 ="INSERT INTO transaction (accountnumber,dateoftrs,amount,type) VALUES('"+accno+"','"+new Date()+"' , '"+amount+"','"+type+"')";
			st.executeUpdate(query2);
			
			con.commit();
		}
		catch(Exception ex) {
			//ex.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
	}
	
	public void deleteAccount(int accno) {
		try {
			String query1 = "DELETE FROM account WHERE accountnumber = " + accno;
			st.executeUpdate(query1);
			con.commit();
		}
		catch(Exception ex) {
			ex.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public List<Transaction> printLastestTransaction(int accno) {
		List<Transaction> txns = new ArrayList<>();
		try {
			String query1 = "select * from transaction where accountnumber = " + accno;
			rs = st.executeQuery(query1);
			while(rs.next()) {
				Date date = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy").parse(rs.getString("dateoftrs"));
				Transaction objt = new Transaction(date, rs.getString("type"), rs.getInt("amount"));
				txns.add(objt);
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}		
		return txns;
	}

}
