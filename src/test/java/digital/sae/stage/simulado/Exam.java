package digital.sae.stage.simulado;

import org.testng.annotations.Test;

public class Exam {
	static Flows flows = new Flows();

	@Test
	public void exam() throws Exception {
		flows.changeExamData();
		flows.closeDriver();
	}
}
