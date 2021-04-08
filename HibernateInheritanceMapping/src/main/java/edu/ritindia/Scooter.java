package edu.ritindia;

public class Scooter extends Vehicle{
	
	int regNo;
	String modelName;
	public int getRegNo() {
		return regNo;
	}
	public void setRegNo(int regNo) {
		this.regNo = regNo;
	}
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	@Override
	public String toString() {
		return "Scooter [regNo=" + regNo + ", modelName=" + modelName + "]";
	}
	
	
	
	
	

}
