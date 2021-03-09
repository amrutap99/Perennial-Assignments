package perennial;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")   //due to prototype scope container does not hold 
						//reference and hence destroy method is not called
public class A {
	B b;
	String s;
	@Autowired
	A(B b,@Value("Hello World")String msg){
		System.out.println("A created with parameterized constructor and msg is "+msg);

	}
	
	A(B b){
		System.out.println("A created with parameterized constructor..");
	}
	//when in xml file we dont provide <constructor-arg ref="a"/> default automatically gets called
	//means no dependency.. hence A is created before D,C,B only for default constructor
	
	A() //this is not called because of lazy-init="true"
	{
		
	System.out.println("A created with default constructor..");
	}
	
	
	//use <property name="b" ref="b"></property> for setter methods in xml
	@Autowired
	public void setB(B b) 
	{
		System.out.println("setB() called with b");
	}
	
	@Autowired
	public void setS(@Value("Adira")String str) {
		System.out.println("setS() called "+str);
	}
	
	@PostConstruct
	public void initialize()   
	{
		System.out.println("\nAfter bean creation setting up environment for a1 bean as i am in a1 bean\n");
	}
	
	@PreDestroy
	public void cleanup() {
		System.out.println("Object is about to removed from container");
	}

}
