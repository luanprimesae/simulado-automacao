package digital.sae.stage.simulado;

import org.testng.annotations.Test;

public class DeadLineFlow {
	static Flows flows = new Flows();

	@Test
	public void deadLineFlow() throws Exception {
		flows.deadlinesConfig();
		flows.closeDriver();
	}

}
