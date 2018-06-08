package resources;

import java.io.File;
import java.util.ArrayList;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class CreateExcelFile {

	private static String students[];

	public void geracaoArquivoExcel(String serie) {
		CreateExcelFile cef = new CreateExcelFile();
		
		ArrayList<String> array = cef.students();
		
		try {
			WritableWorkbook planilha = Workbook
					.createWorkbook(new File("C:\\Users\\luan.alves\\Documents\\Lista_alunos.xls"));
			// Adicionando o nome da aba
			WritableSheet aba = planilha.createSheet("ListaAlunos", 0);

			// Cabe�alhos
			String cabecalho[] = new String[4];
			cabecalho[0] = "Nome_do_aluno";
			cabecalho[1] = "Segmento";
			cabecalho[2] = "Serie";
			cabecalho[3] = "Turma";

			// Write the Header to the excel file
			for (int i = 0; i < cabecalho.length; i++) {
				Label label = new Label(i, 0, cabecalho[i]);
				aba.addCell(label);
				WritableCell cell = aba.getWritableCell(i, 0);
			}

//			if (Integer.parseInt(serie) <= 3) {
				for (int linha = 1; linha <= 15; linha++) {

					Label label = new Label(0, linha, array.get(linha - 1));
					aba.addCell(label);

					label = new Label(1, linha, "PV");
					aba.addCell(label);

					label = new Label(2, linha, "PV");
					aba.addCell(label);

					label = new Label(3, linha, "A");
					aba.addCell(label);
					
//				}
//				for (int linha = 16; linha <= 30; linha++) {
//					String students[] = new String[15];
//					
//					Label label = new Label(0, linha, array.get(linha - 16));
//					aba.addCell(label);
//
//					label = new Label(1, linha, "EM");
//					aba.addCell(label);
//
//					label = new Label(2, linha, serie);
//					aba.addCell(label);
//
//					label = new Label(3, linha, "A");
//					aba.addCell(label);
//					
//				}
//			}else if(Integer.parseInt(serie) > 3 && Integer.parseInt(serie) <= 5) {
//				for (int linha = 1; linha <= 15; linha++) {
//
//					Label label = new Label(0, linha, array.get(linha - 1));
//					aba.addCell(label);
//
//					label = new Label(1, linha, "EF1");
//					aba.addCell(label);
//
//					label = new Label(2, linha, serie);
//					aba.addCell(label);
//
//					label = new Label(3, linha, "A");
//					aba.addCell(label);
//					
//				}
//			}else {
//				for (int linha = 1; linha <= 15; linha++) {
//
//					Label label = new Label(0, linha, array.get(linha - 1));
//					aba.addCell(label);
//
//					label = new Label(1, linha, "EF2");
//					aba.addCell(label);
//
//					label = new Label(2, linha, serie);
//					aba.addCell(label);
//
//					label = new Label(3, linha, "A");
//					aba.addCell(label);
//					
//				}
			}
			
			planilha.write();
			planilha.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public ArrayList<String> students() {
		ArrayList<String> estudantes = new ArrayList<String>();
		
		for(int i = 1;i <= 15;i++) {
			estudantes.add("Aluno "+i);
		}
				
		return estudantes; 
	}
}