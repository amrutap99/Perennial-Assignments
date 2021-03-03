package ExceptionHandlerLibrary;

import java.util.Map;

public class AbortAction implements Action {

	@Override
	public void takeAction(Map<String, String> nvPair) {
		// TODO Auto-generated method stub
		System.out.println("Aborting Transaction");
		
	}

}

