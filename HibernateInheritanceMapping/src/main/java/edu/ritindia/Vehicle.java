package edu.ritindia;

public class Vehicle {

	int modelNo;
	String vehicleBrand;
	public int getModelNo() {
		return modelNo;
	}
	public void setModelNo(int modelNo) {
		this.modelNo = modelNo;
	}
	public String getVehicleBrand() {
		return vehicleBrand;
	}
	public void setVehicleBrand(String vehicleBrand) {
		this.vehicleBrand = vehicleBrand;
	}
	@Override
	public String toString() {
		return "Vehicle [modelNo=" + modelNo + ", vehicleBrand=" + vehicleBrand + "]";
	}
	
	
	
	
}
