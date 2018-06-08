package digital.sae.stage.simulado;

import org.testng.annotations.Test;

public class SchoolExam {

	static Flows flows = new Flows();

	@Test
	public void schoolExam() throws Exception {
		flows.examSchool();
		flows.closeDriver();
	}
	
}