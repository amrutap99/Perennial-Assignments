package perennial;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class C {
	D d;
	@Autowired
	C(D d){
		this.d = d;
		System.out.println(" C Created !!");
	}

}
