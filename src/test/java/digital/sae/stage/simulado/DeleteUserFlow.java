package digital.sae.stage.simulado;

import org.testng.annotations.Test;

public class DeleteUserFlow {

	static Flows flows = new Flows();

	@Test
	public void deleteUserFlow() throws Exception { 
		flows.deleteUserFlow();
		flows.closeDriver();
	}
	
}