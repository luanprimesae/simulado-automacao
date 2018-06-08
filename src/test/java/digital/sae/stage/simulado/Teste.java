package digital.sae.stage.simulado;

import java.util.ArrayList;

import resources.CreateExcelFile;

public class Teste {

	public static void main(String[] args) {

		CreateExcelFile cef = new CreateExcelFile();
	 	ArrayList<String> array =  cef.students();
	 	System.out.println(array.get(1));
	}

}
