package digital.sae.stage.simulado;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import resources.CreateExcelFile;
import resources.Elements;

public class Flows {
	Elements elements = new Elements();
	CreateExcelFile create = new CreateExcelFile();

	private static WebDriver driver;
	private WebElement commonElement;
	private WebDriverWait wait;
	private Select dropDownEstado, dropDownCidade, dropDownAcao, dropDownSegments, dropDownOptions, dropDownSchool;

	private ArrayList<String> alunos;

	private String confirmationRegisterLink[];
	private String totalProvas[];

	// Acessar o admin do simulado para excluir o usu�rio cadastrado
	public void deleteUserFlow() throws Exception {
		driver = new FirefoxDriver();
		driver.get("http://simulado.stage.sae.digital/admin");

		wait = new WebDriverWait(driver, 20);

		commonElement = driver.findElement(By.id("id_username"));
		commonElement.sendKeys(elements.getLoginAdmin());

		commonElement = driver.findElement(By.id("id_password"));
		commonElement.sendKeys(elements.getPasswordAdmin());

		commonElement = driver.findElement(By.cssSelector("div.submit-row>input"));
		commonElement.click();

		commonElement = driver.findElement(By.linkText("Usuários"));
		commonElement.click();

		commonElement = driver.findElement(By.id("searchbar"));
		commonElement.sendKeys(elements.getEmail());
		commonElement.submit();

		try {
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//tr[@class='row2']")));
			commonElement = driver.findElement(By.xpath("//tr[@class='row1']/td[@class='action-checkbox']"));
			commonElement.click();

			dropDownAcao = new Select(driver.findElement(By.name("action")));
			dropDownAcao.selectByIndex(1);

			commonElement = driver.findElement(By.name("index"));
			commonElement.click();

			commonElement = driver.findElement(By.xpath("//input[@value='Sim, tenho certeza']"));
			commonElement.click();
		} catch (NoSuchElementException e) {
			System.out.println("Usuário não encontrado.");
		}
	}

	// Acessa o sistema do simulado e executa todo o fluxo do 'caminho feliz'
	public void registerFlow() throws Exception {
		driver = new FirefoxDriver();
		driver.get("http://simulado.stage.sae.digital/perfil/registrar-diretor");

		wait = new WebDriverWait(driver, 20);

		commonElement = driver.findElement(By.id("btn_agreement"));
		commonElement.click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("school")));

		commonElement = driver.findElement(By.id("school"));
		commonElement.sendKeys(elements.getEscola());

		commonElement = driver.findElement(By.id("cnpj"));
		commonElement.sendKeys("65.498.491/6511-13");

		dropDownEstado = new Select(driver.findElement(By.id("state")));
		dropDownEstado.selectByIndex(18);

		dropDownCidade = new Select(driver.findElement(By.id("city")));
		dropDownCidade.selectByIndex(10);

		commonElement = driver.findElement(By.id("director_name"));
		commonElement.sendKeys("Diretor da Escola Automacao");

		commonElement = driver.findElement(By.id("director_email"));
		commonElement.sendKeys(elements.getEmail());

		commonElement = driver.findElement(By.id("send_data"));
		commonElement.click();

		commonElement = driver.findElement(By.id("send-data"));
		commonElement.click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ok")));
		commonElement = driver.findElement(By.id("ok"));
		commonElement.click();

		String confirmationRegisterLink = confirmRegisterEmail();

		driver = new FirefoxDriver();
		driver.get(confirmationRegisterLink);

		commonElement = driver.findElement(By.id("id_new_password1"));
		commonElement.sendKeys(elements.getSenha());

		commonElement = driver.findElement(By.id("id_new_password2"));
		commonElement.sendKeys(elements.getSenha());

		commonElement = driver.findElement(By.xpath("//input[@value='Salvar']"));
		commonElement.click();

	}

	// Acessar o admine deleta a escola cadastrada
	public void deleteSchoolFlow() throws Exception {
		driver = new FirefoxDriver();
		driver.get("http://simulado.stage.sae.digital/admin");

		wait = new WebDriverWait(driver, 20);

		commonElement = driver.findElement(By.id("id_username"));
		commonElement.sendKeys(elements.getLoginAdmin());

		commonElement = driver.findElement(By.id("id_password"));
		commonElement.sendKeys(elements.getPasswordAdmin());

		commonElement = driver.findElement(By.cssSelector("div.submit-row>input"));
		commonElement.click();

		commonElement = driver.findElement(By.linkText("Escolas"));
		commonElement.click();

		try {
			commonElement = driver.findElement(By.linkText("Mostrar tudo"));
			commonElement.click();
		} catch (NoSuchElementException e) {
			System.out.println("Botão não encontrado.");
		}

		try {
			commonElement = driver.findElement(By.linkText(elements.getEscola()));
			commonElement.click();

			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("id_code")));
			commonElement = driver.findElement(By.linkText("Apagar"));
			commonElement.click();

			commonElement = driver.findElement(By.xpath("//input[@value='Sim, tenho certeza']"));
			commonElement.click();
		} catch (NoSuchElementException e) {
			System.out.println("Escola não encontrada.");
		}

	}

	// Acessa o e-mail para pegar o link de confirma��o de cadastro
	public String confirmRegisterEmail() throws Exception {
		driver.get("https://putsbox.com/automacaoteste/inspect");

		boolean isPresent = false;
		int i = 1;

		do {

			if (i != 1) {
				Thread.sleep(30000);
				driver.navigate().refresh();
			}

			try {
				commonElement = driver.findElement(By.linkText("Text"));
				commonElement.click();

				isPresent = true;
			} catch (NoSuchElementException e) {
				isPresent = false;
				System.out.println(i);
			}
			i++;
		} while (isPresent != true);

		Thread.sleep(2000);

		ArrayList<String> guias = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(guias.get(1));

		commonElement = driver.findElement(By.xpath("/html/body/pre"));
		String emailBody = commonElement.getText().toString();

		confirmationRegisterLink = emailBody.split("\n\n");

		guias = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(guias.get(0));

		commonElement = driver.findElement(By.linkText("Destroy"));
		commonElement.click();

		Thread.sleep(1000);

		driver.switchTo().alert().accept();

		closeDriver();

		return confirmationRegisterLink[2];
	}

	public void completeUsabilityFlow() throws Exception {
		driver = new FirefoxDriver();
		driver.get("http://simulado.stage.sae.digital/perfil/entrar/?next=/");

		commonElement = driver.findElement(By.name("username"));
		commonElement.sendKeys(elements.getEmail());

		commonElement = driver.findElement(By.name("password"));
		commonElement.sendKeys(elements.getSenha());

		commonElement = driver.findElement(By.name("btn-enviar"));
		commonElement.click();

		registerStudents();

		uploadCards();

	}

	// Deleta as deadlines que est�o no sistema (caso existam) e as cria
	// na data do teste
	public void deadlinesConfig() throws Exception {
		int index;

		driver = new FirefoxDriver();
		driver.get("http://simulado.stage.sae.digital/admin");

		wait = new WebDriverWait(driver, 20);

		commonElement = driver.findElement(By.id("id_username"));
		commonElement.sendKeys(elements.getLoginAdmin());

		commonElement = driver.findElement(By.id("id_password"));
		commonElement.sendKeys(elements.getPasswordAdmin());

		commonElement = driver.findElement(By.cssSelector("div.submit-row>input"));
		commonElement.click();

		commonElement = driver.findElement(By.linkText("Deadliness"));
		commonElement.click();

		try {
			commonElement = driver.findElement(By.id("action-toggle"));
			commonElement.click();

			dropDownAcao = new Select(driver.findElement(By.name("action")));
			dropDownAcao.selectByIndex(1);

			commonElement = driver.findElement(By.name("index"));
			commonElement.click();

			commonElement = driver.findElement(By.xpath("//input[@value='Sim, tenho certeza']"));
			commonElement.click();

		} catch (NoSuchElementException e) {
			System.out.println("Não há deadlines cadastradas.");
		}

		commonElement = driver.findElement(By.linkText("Adicionar Deadlines"));
		commonElement.click();

		// Quantidade de Segmentos que existem
		for (int s = 1; s < 5; s++) {

			dropDownSegments = new Select(driver.findElement(By.id("id_segment")));
			dropDownSegments.selectByValue(Integer.toString(s));

			index = s;

			// Quantidade de Opções que existem
			for (int o = 1; o < 7; o++) {
				if (o != 1) {
					dropDownSegments = new Select(driver.findElement(By.id("id_segment")));
					dropDownSegments.selectByValue(Integer.toString(index));
				}

				dropDownOptions = new Select(driver.findElement(By.id("id_option")));
				dropDownOptions.selectByValue(Integer.toString(o));

				Thread.sleep(1500);

				commonElement = driver.findElement(By.xpath(
						"//div[@class='form-row field-initial_date']/div/span[@class='datetimeshortcuts']/a[1]"));
				commonElement.click();

				commonElement = driver.findElement(By
						.xpath("//div[@class='form-row field-final_date']/div/span[@class='datetimeshortcuts']/a[1]"));
				commonElement.click();

				commonElement = driver.findElement(By.name("_addanother"));
				commonElement.click();

			}

		}

	}

	// Metodo para adicionar todas as provas que est�o cadastradas no admin, para a
	// escola
	public void examSchool() throws Exception {
		driver = new FirefoxDriver();
		driver.get("http://simulado.stage.sae.digital/admin");

		wait = new WebDriverWait(driver, 20);

		commonElement = driver.findElement(By.id("id_username"));
		commonElement.sendKeys(elements.getLoginAdmin());

		commonElement = driver.findElement(By.id("id_password"));
		commonElement.sendKeys(elements.getPasswordAdmin());

		commonElement = driver.findElement(By.cssSelector("div.submit-row>input"));
		commonElement.click();

		commonElement = driver.findElement(By.linkText("Escola - Provas"));
		commonElement.click();

		commonElement = driver.findElement(By.linkText("Adicionar Escola - Prova"));
		commonElement.click();

		dropDownSchool = new Select(driver.findElement(By.id("id_school")));
		dropDownSchool.selectByVisibleText(elements.getEscola());

		Select exams = new Select(driver.findElement(By.id("id_exam")));
		java.util.List<WebElement> l = exams.getOptions();

		for (int p = 1; p < l.size(); p++) {
			if (p != 1) {
				dropDownSchool = new Select(driver.findElement(By.id("id_school")));
				dropDownSchool.selectByVisibleText(elements.getEscola());

				exams = new Select(driver.findElement(By.id("id_exam")));
				l = exams.getOptions();
			}
			exams.selectByIndex(p);

			commonElement = driver.findElement(By.name("_addanother"));
			commonElement.click();

		}
	}

	// Metodo para validar e alterar a data das provas para o dia que est� rodando o
	// teste
	public void changeExamData() throws Exception {
		String paginator;

		driver = new FirefoxDriver();
		driver.get("http://simulado.stage.sae.digital/admin");

		wait = new WebDriverWait(driver, 20);

		commonElement = driver.findElement(By.id("id_username"));
		commonElement.sendKeys(elements.getLoginAdmin());

		commonElement = driver.findElement(By.id("id_password"));
		commonElement.sendKeys(elements.getPasswordAdmin());

		commonElement = driver.findElement(By.cssSelector("div.submit-row>input"));
		commonElement.click();

		commonElement = driver.findElement(By.linkText("SAE - Provas"));
		commonElement.click();

		commonElement = driver.findElement(By.xpath("//p[@class='paginator']"));
		paginator = commonElement.getText().toString();

		totalProvas = paginator.split(" ");

		for (int i = 1; i <= Integer.parseInt(totalProvas[0]); i++) {
			if (i != 1) {
				wait.until(ExpectedConditions.visibilityOfElementLocated(
						By.xpath("/html/body/div[1]/div[3]/div/div/form/div[2]/table/tbody/tr[" + i + "]/th/a")));
			}

			commonElement = driver.findElement(
					By.xpath("/html/body/div[1]/div[3]/div/div/form/div[2]/table/tbody/tr[" + i + "]/th/a"));
			commonElement.click();
			int j = i;

			if (j <= Integer.parseInt(totalProvas[0])) {
				commonElement = driver.findElement(By.linkText("Hoje"));
				commonElement.click();

				commonElement = driver.findElement(
						By.xpath("//div[@class='form-row field-date_day2']/div/span[@class='datetimeshortcuts']/a[1]"));
				commonElement.click();

				// commonElement = driver.findElement(By.id("id_template_pdf"));
				// commonElement.sendKeys("C:\\Users\\luan.alves\\eclipse-workspace\\simulado\\template_exam.pdf");

				commonElement = driver.findElement(By.name("_save"));
				commonElement.click();

			}

		}

	}

	// Faz a gera��o do arquivo Excel com os dados dos alunos e efetua o cadastro
	// deles na prova
	public void registerStudents() throws Exception {
		commonElement = driver.findElement(By.linkText("Inscrever Alunos"));
		commonElement.click();

		try {
			// Select exams = new Select(driver.findElement(By.id("school_exam")));

			commonElement = driver.findElement(By.xpath("//*[@serie='']"));
			commonElement.click();

			// commonElement =
			// driver.findElement(By.xpath("//div[@id='info-segment']/strong[2]"));
			// String numOfSerie = commonElement.getText().toString();

			create.geracaoArquivoExcel(" ");

			commonElement = driver.findElement(By.id("students_filename"));
			commonElement.sendKeys(elements.getPathToFile());

			commonElement = driver.findElement(By.xpath("//button[@class='btn btn-info']"));
			commonElement.click();

			commonElement = driver.findElement(By.xpath("//button[@id='btn_subscribe_students']"));
			commonElement.click();

			commonElement = driver.findElement(By.xpath(
					"/html/body[@class='modal-open']//div[@class='modal-dialog']/div[@class='modal-content']/div[@class='modal-footer']/div[@class='bootstrap-dialog-footer']/div[@class='bootstrap-dialog-footer-buttons']//button[@class='btn btn-info']"));
			commonElement.click();

			closeDriver();

			changeStudentBarCode();

		} catch (NoSuchElementException e) {
			e.printStackTrace();
		}

	}

	// Altera o codigo de barras dos alunos inscritos para de alguns arquivos de
	// prova do projeto
	public void changeStudentBarCode() throws Exception {
		ArrayList<String> listOfCodes = elements.listOfCodes();

		driver = new FirefoxDriver();
		driver.get("http://simulado.stage.sae.digital/admin");

		wait = new WebDriverWait(driver, 20);

		commonElement = driver.findElement(By.id("id_username"));
		commonElement.sendKeys(elements.getLoginAdmin());

		commonElement = driver.findElement(By.id("id_password"));
		commonElement.sendKeys(elements.getPasswordAdmin());

		commonElement = driver.findElement(By.xpath("//div/input[@value='Acessar']"));
		commonElement.click();

		commonElement = driver.findElement(By.linkText("Aluno - Provas"));
		commonElement.click();

		commonElement = driver.findElement(By.linkText(elements.getEscola()));
		commonElement.click();

		alunos = create.students();
		for (int i = 0; i <= 14; i++) {
			commonElement = driver.findElement(
					By.xpath("//th[@class='field-student nowrap']/a[contains(text(), \"" + alunos.get(i) + "\")]"));
			commonElement.click();

			commonElement = driver.findElement(By.id("id_barcode"));
			commonElement.clear();
			commonElement.sendKeys(listOfCodes.get(i));

			commonElement = driver.findElement(By.name("_save"));
			commonElement.click();

		}

		driver.close();

	}

	// Faz o upload dos cartões em formato .zip
	public void uploadCards() throws Exception {
		driver = new FirefoxDriver();
		driver.get("http://simulado.stage.sae.ital/perfil/entrar/?next=/");

		commonElement = driver.findElement(By.name("username"));
		commonElement.sendKeys(elements.getEmail());

		commonElement = driver.findElement(By.name("password"));
		commonElement.sendKeys(elements.getSenha());

		commonElement = driver.findElement(By.name("btn-enviar"));
		commonElement.click();

		commonElement = driver.findElement(By.linkText("Enviar cartões"));
		commonElement.click();

		commonElement = driver.findElement(By.id("sendBtn"));
		commonElement.click();

		StringSelection ss = new StringSelection("C:\\Users\\luan.alves\\eclipse-workspace\\simulado\\students.zip");
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);

		Robot robot = new Robot();

		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		Thread.sleep(2000);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);

		boolean isPresent = false;
		int i = 1;

		do {

			if (i != 1) {
				Thread.sleep(5000);
			}

			try {
				commonElement = driver.findElement(By.xpath(
						"/html/body/main/div[@class='container']/div[@class='tabs-animated']/div[@class='tab-content']/div[@id='enviar']/div[@class='tab-container']/div[@class='row'][2]/div[@class='col-sm-12']/ul[@id='uploadResponsible']/li[3]/div[@style='']"));

				isPresent = true;
			} catch (NoSuchElementException e) {
				isPresent = false;
			}
			i++;
		} while (isPresent != true);

	}

	public void uploadValidation() throws Exception {
		driver = new FirefoxDriver();

		driver.get("http://simulado.stage.sae.digital/perfil/entrar/?next=/");

		commonElement = driver.findElement(By.name("username"));
		commonElement.sendKeys(elements.getEmail());

		commonElement = driver.findElement(By.name("password"));
		commonElement.sendKeys(elements.getSenha());

		commonElement = driver.findElement(By.name("btn-enviar"));
		commonElement.click();

		commonElement = driver.findElement(By.linkText("Enviar cartões"));
		commonElement.click();

		int i = 0;
		if (i == 0) {
			driver.navigate().refresh();
			try {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
						"//table[@class='table table-responsive table-striped table-condensed']/tbody/tr/td[@class='info']/small[contains(text(), \"Em processamento\")]")));

				Thread.sleep(3000);

				i = 1;
			} catch (NoSuchElementException e) {
				i = 0;
			}
		}
	}

	public void closeDriver() throws Exception {
		driver.quit();
	}

}