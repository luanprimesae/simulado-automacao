package resources;

import java.util.ArrayList;

public class Elements {

	private String email = "automacaoteste@putsbox.com";
	private String escola = "Escola de Teste Automacao";
	private String senha = "1a2b3c4d5e67";
	private String pathToFile = "C:\\Users\\luan.alves\\Documents\\Lista_alunos.xls";
	
	public String getPathToFile() {
		return pathToFile;
	}

	public void setPathToFile(String pathToFile) {
		this.pathToFile = pathToFile;
	}

	private String loginAdmin = "simulado";
	private String passwordAdmin = "localadm";
	
	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getLoginAdmin() {
		return loginAdmin;
	}

	public void setLoginAdmin(String loginAdmin) {
		this.loginAdmin = loginAdmin;
	}

	public String getPasswordAdmin() {
		return passwordAdmin;
	}

	public void setPasswordAdmin(String passwordAdmin) {
		this.passwordAdmin = passwordAdmin;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getEscola() {
		return escola;
	}
	
	public void setEscola(String escola) {
		this.escola = escola;
	}
	
	public ArrayList<String> listOfCodes(){
		ArrayList<String> listOfCodes = new ArrayList<String>();
		
		listOfCodes.add("45611001630086");
		listOfCodes.add("67432057006030");
		listOfCodes.add("45611967050658");
		listOfCodes.add("45611283166514");
		listOfCodes.add("45611725692360");
		listOfCodes.add("79062586898852");
		listOfCodes.add("79061741016582");
		listOfCodes.add("79067458829229");
		listOfCodes.add("79062913777841");
		listOfCodes.add("79063016213143");
		listOfCodes.add("79063025223022");
		listOfCodes.add("79067199473310");
		listOfCodes.add("79061387878814");
		listOfCodes.add("79061280065555");
		listOfCodes.add("13692954073167");

		return listOfCodes;
	}
	
}