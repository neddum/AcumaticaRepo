package ui;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.Dimension;

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

public class CreateAppointments
{
	public static void main(String[] args) throws InterruptedException {
		WebDriverManager.chromedriver();
		ChromeDriver driver = new ChromeDriver();
		
		driver.get("https://cflsi.acumatica.com/(W(7))/Frames/Login.aspx?ReturnUrl=%2fMain");
		driver.manage().window().maximize();
		Dimension size = driver.manage().window().getSize();
		
		WebElement ddown = driver.findElement(By.name("ctl00$phUser$cmbCompany"));
		Select select = new Select(ddown);
		select.selectByVisibleText("Classic Fire and Life Safety Inc");
		
		driver.findElement(By.id("txtUser")).sendKeys("ryabes");
		driver.findElement(By.id("txtPass")).sendKeys("Acumatica14!");
		driver.findElement(By.id("btnLogin")).click();
		
		// Wait for the Services element to be click-able
    WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(60));
    WebElement servicesElement = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div[data-id='f0cf4498-99c4-48f3-8dd8-bde9ed67f9d3']")));

    // Click on the Services element
    servicesElement.click();
    
    Thread.sleep(3000);
    
 // Wait for the "Service Orders" element to be click-able
    WebElement serviceOrdersElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Service Orders']")));

    // Click on the "Service Orders" element
    serviceOrdersElement.click();
    Thread.sleep(20000);
    
    driver.switchTo().defaultContent();
    driver.switchTo().frame("main");
    
    if(size.width <= 1552) {
    	WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"ctl00_phG_grid_at_ft_ul\"]/li[21]/div/div")));
    	element.click();

    }
    driver.findElement(By.xpath("//*[@class=\"menuItem\"]/div[@class=\"menuSpacer\"]/nobr[text() =\"FA TBS\"]")).click();
    Thread.sleep(2000);
    driver.findElement(By.id("ctl00_phG_grid_tf_fb_text")).sendKeys("DEF-0010355");
    
    driver.switchTo().defaultContent();
    driver.switchTo().frame("main");
    Thread.sleep(7000);
    
    WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"ctl00_phG_grid_scrollDiv\"]/table[2]/tbody/tr/td[12]/a")));
    element.click();
    
    Thread.sleep(7000);
    driver.findElement(By.xpath("//*[@id=\"ctl00_phDS_ds_ToolBar_ScheduleAppointment\"]/div")).click();
    
    Thread.sleep(7000);
    driver.findElement(By.xpath("//*[@id=\"ctl00_phG_tab_tab1\"]")).click();
    String searchText = "Non-Stock Item";
    
    List<WebElement> elements = driver.findElements(By.xpath("//tr[.//td[contains(text(), '" + searchText + "')]]"));
    
    for(WebElement item : elements) {
    	Thread.sleep(7000);
    	item.click();
//    	driver.findElement(By.xpath("//*[@id=\"ctl00_phG_tab_t1_PXGridDetails_at_tlb_ul\"]/li[4]/div/div/div")).click();
    }
//    driver.findElement(By.className("buttonsCont")).click();
	}
}
