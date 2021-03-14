package ExceptionHandlerLibrary;


import java.util.Map;

public class ShutDownAction implements Action
{

	public void takeAction(Map<String,String> nvPair) {
		// TODO Auto-generated method stub
		System.out.println("Shutting down...");
		System.exit(-1);
		
	}

}
