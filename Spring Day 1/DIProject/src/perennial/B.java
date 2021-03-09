package perennial;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class B {
	C c ;
	B(C c){
		this.c = c;
		System.out.println("B created with C");
	}
	@Autowired
	B(C c, D d){
		this.c = c;
		System.out.println("B created with both C and D");
	}

}
