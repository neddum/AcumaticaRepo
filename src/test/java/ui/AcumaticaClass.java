package ui;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.support.ui.Select;
import java.time.Duration;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;

public class AcumaticaClass
{

	public static void main(String[] args) throws InterruptedException
	{

		WebDriverManager.chromedriver();

		// Replace the Path with C:\Users\ryabes\OneDrive - Classic Fire + Life
		// Safety\SCHEDULING - ALL\ACUMATICA NOTES\RawData
		String downloadPath = "C:\\Users\\Bondu\\Documents\\Testing";

		// Clean up the directory before starting the WebDriver
		cleanupDirectory(downloadPath);

		HashMap<String, Object> chromePref = new HashMap<String, Object>();
		chromePref.put("profile.default_content_settings.popups", 0);
		chromePref.put("download.default_directory", downloadPath);
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("prefs", chromePref);

		ChromeDriver driver = new ChromeDriver(options); // creates new object of the chrome-driver

		driver.get("https://cflsi.acumatica.com/(W(7))/Frames/Login.aspx?ReturnUrl=%2fMain");
		driver.manage().window().maximize();

		WebElement ddown = driver.findElement(By.name("ctl00$phUser$cmbCompany"));
		Select select = new Select(ddown);
		select.selectByVisibleText("Classic Fire and Life Safety Inc");

		driver.findElement(By.id("txtUser")).sendKeys("ryabes");
		driver.findElement(By.id("txtPass")).sendKeys("Acumatica14!");
		driver.findElement(By.id("btnLogin")).click();

		// Wait for the Services element to be click-able
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		WebElement servicesElement = wait.until(
				ExpectedConditions.elementToBeClickable(By.cssSelector("div[data-id='f0cf4498-99c4-48f3-8dd8-bde9ed67f9d3']")));

		// Click on the Services element
		servicesElement.click();

		Thread.sleep(2000);

		// Wait for the "Service Orders" element to be click-able
		WebElement serviceOrdersElement = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Service Orders']")));

		// Click on the "Service Orders" element
		serviceOrdersElement.click();

		Thread.sleep(20000);

		driver.switchTo().defaultContent();
		driver.switchTo().frame("main");

		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();",
				driver.findElement(By.xpath("//div[@class='au-target main-icon-img main-Excel']")));

		WebElement ignoreButton = wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("/html/body/form/table[4]/tbody/tr[2]/td/table/tbody/tr[2]/td/button[2]")));
		ignoreButton.click();

		// Thread.sleep(900000); to download the first file
		CompletableFuture<Void> delayFuture = CompletableFuture.runAsync(() -> {
			try
			{
				TimeUnit.MINUTES.sleep(10); // Sleep for 20 minutes
			} catch (InterruptedException e)
			{
				// Handle the exception if needed
				e.printStackTrace();
			}
		});

		try
		{
			delayFuture.get();
		} catch (ExecutionException e)
		{
			e.printStackTrace();
		}

		driver.switchTo().defaultContent();
		// Click on the Services element the second time
		WebElement servicesElement2 = wait.until(
				ExpectedConditions.elementToBeClickable(By.cssSelector("div[data-id='f0cf4498-99c4-48f3-8dd8-bde9ed67f9d3']")));

		servicesElement2.click();

		Thread.sleep(2000);

		WebElement appointmentElement = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Appointments']")));

		// Click on the "appointment Orders" element
		appointmentElement.click();

		Thread.sleep(20000);

		driver.switchTo().defaultContent();
		driver.switchTo().frame("main");

		executor.executeScript("arguments[0].click();",
				driver.findElement(By.xpath("//div[@class='au-target main-icon-img main-Excel']")));

		WebElement ignoreButton2 = wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("/html/body/form/table[4]/tbody/tr[2]/td/table/tbody/tr[2]/td/button[2]")));
		ignoreButton2.click();

		// Thread.sleep(900000); to download the Second file
		CompletableFuture<Void> delayFuture2 = CompletableFuture.runAsync(() -> {
			try
			{
				TimeUnit.MINUTES.sleep(20); // Sleep for 40 minutes
			} catch (InterruptedException e)
			{
				// Handle the exception if needed
				e.printStackTrace();
			}
		});

		try
		{
			delayFuture2.get();
		} catch (ExecutionException e)
		{
			e.printStackTrace();
		}

		driver.close();

	}
	
	
	private static void cleanupDirectory(String directoryPath) {
    try {
        // Use FileUtils to clean the directory
        FileUtils.cleanDirectory(new File(directoryPath));
        System.out.println("Directory cleaned successfully.");
    } catch (Exception e) {
        System.err.println("Error cleaning directory: " + e.getMessage());
    }
}

}
