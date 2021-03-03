import java.sql.Connection;
import java.sql.DriverManager;

public class GetConnection {
	
	public void connect() 
	{
	
	try
	   {
	   Class.forName("com.mysql.jdbc.Driver");
	   Connection conn = null;
	   conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydatabase","root", "");
	   System.out.print("Database is connected\n\n");
	   conn.close();
	   }
	   catch(Exception e)
	   {
	   System.out.print("Do not connect to DB - Error:"+e);
	   }
	
}
}

