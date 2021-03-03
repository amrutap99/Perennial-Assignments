import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class DeleteAccountClass 
{

	public void delete(int accno)
	{
		try
		   {
		   Class.forName("com.mysql.jdbc.Driver");
		   Connection conn = null;
		   conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydatabase","root", "");
		   
		   String sql="delete from account where accno=?";
		   PreparedStatement stmt=conn.prepareStatement(sql);
		   
		   stmt.setInt(1, accno);
		   
		   
		   stmt.executeUpdate();
		   
		   conn.close();
		   }
		   catch(Exception e)
		   {
		   System.out.print("Error:"+e);
		   }
		   System.out.print("Successfully deleted");

	}
}
