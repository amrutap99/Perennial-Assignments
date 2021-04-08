package edu.ritindia;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;


public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		SessionFactory factory = new Configuration().configure().buildSessionFactory();
		Session session = factory.openSession();

		Transaction txn = session.beginTransaction();
		
		Vehicle v1 = new Vehicle();
		v1.setModelNo(1234);
		v1.setVehicleBrand("Mercedes Benz Skoda");
		
		Scooter scooter = new Scooter();
		scooter.setModelNo(567);
		scooter.setModelName("Honda Dio");
		scooter.setRegNo(9090);
		scooter.setVehicleBrand("PQR");
		
		
		Car car = new Car();
		car.setVehicleBrand("Jeep Volkswagen");
		car.setEngineType("STY");
		car.setModelType("XYZ");
		car.setModelNo(3455);
		
		session.save(v1);
		session.save(car);
		session.save(scooter);
		
		txn.commit();

	}

}
