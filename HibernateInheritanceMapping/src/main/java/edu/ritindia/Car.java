package edu.ritindia;

public class Car extends Vehicle{
	
	String modelType;
	String engineType;
	
	public String getEngineType() {
		return engineType;
	}
	public void setEngineType(String engineType) {
		this.engineType = engineType;
	}
	public String getModelType() {
		return modelType;
	}
	public void setModelType(String modelType) {
		this.modelType = modelType;
	}
	@Override
	public String toString() {
		return "Car [modelType=" + modelType + ", engineType=" + engineType + "]";
	}
	
	
	

}
