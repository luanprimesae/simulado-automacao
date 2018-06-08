package digital.sae.stage.simulado;

import org.testng.annotations.Test;

public class DeleteSchoolFlow {

	static Flows flows = new Flows();

	@Test
	public void deleteSchoolFlow() throws Exception { 
		flows.deleteSchoolFlow();
		flows.closeDriver();
	}
	
}
