package ExceptionHandlerLibrary;

import java.io.IOException;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
			
			m1();
		}
		catch(Exception ex)
		{
			ExceptionHandler.handleException("projName","module1",ex);
		}
	}

	private static void m1() throws IOException {
		// TODO Auto-generated method stub
		throw new IOException();
		
	}

}
