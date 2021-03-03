import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class AddAccountClass {
	
	public void add(int accno,String name,String address,int amount)
	{
		System.out.println("acc="+accno);
		
		try
		   {
		   Class.forName("com.mysql.jdbc.Driver");
		   Connection conn = null;
		   conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydatabase","root", "");
		   
		   String sql="insert into account (accno,name,address,amount) values(?,?,?,?)";
		   PreparedStatement stmt=conn.prepareStatement(sql);
		   
		   stmt.setInt(1, accno);
		   stmt.setString(2, name);
		   stmt.setString(3, address);
		   stmt.setInt(4, amount);
		   
		   stmt.executeUpdate();

		   
		   conn.close();
		   }
		   catch(Exception e)
		   {
		   System.out.print("Error:"+e);
		   }
		   System.out.print("Success insertion\n\n");

	}

}
