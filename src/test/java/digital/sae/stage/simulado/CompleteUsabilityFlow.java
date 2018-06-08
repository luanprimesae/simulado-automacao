package digital.sae.stage.simulado;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import resources.Database;

public class CompleteUsabilityFlow {

	static Flows flows = new Flows();
	static boolean validaUsuario = false;

	@BeforeTest
	public static void beforeClass() throws Exception {
		Database.getConnectionMySQL();

		validaUsuario = Database.selectUser();
	}

	@Test
	public void completeUsabilityFlow() throws Exception {
		flows.completeUsabilityFlow();
		flows.closeDriver();
	}

}
