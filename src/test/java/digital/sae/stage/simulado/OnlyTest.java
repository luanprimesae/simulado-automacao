package digital.sae.stage.simulado;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import resources.Elements;

public class OnlyTest {

	private static WebDriver driver;
	private static WebElement commonElement;
	static Elements elements = new Elements();

	public static void main(String[] args) throws AWTException, InterruptedException {

		driver = new FirefoxDriver();
		driver.get("https://simulado.stage.sae.digital/");

		new WebDriverWait(driver, 20);

		commonElement = driver.findElement(By.name("username"));
		commonElement.sendKeys(elements.getEmail());

		commonElement = driver.findElement(By.name("password"));
		commonElement.sendKeys(elements.getSenha());

		commonElement = driver.findElement(By.name("btn-enviar"));
		commonElement.click();

		driver.get("https://simulado.stage.sae.digital/gerenciamento/envio-cartoes/");
		
		commonElement = driver.findElement(By.id("sendBtn"));
		commonElement.click();
		
		StringSelection ss = new StringSelection("C:\\Users\\luan.alves\\eclipse-workspace\\simulado\\students.zip");
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
		
		Robot robot = new Robot();

		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		Thread.sleep(1000);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
	
		boolean isPresent = false;
		int i = 1;

		do {

			if (i != 1) {
				Thread.sleep(15000);
			}

			try {
				commonElement = driver.findElement(By.linkText("aqui"));
				commonElement.click();

				isPresent = true;
			} catch (NoSuchElementException e) {
				isPresent = false;
				System.out.println(i);
			}
			i++;
		} while (isPresent != true);
		
		
	}
}
