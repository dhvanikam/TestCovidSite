package example;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import utilities.ElementUtils;
import utilities.ExcelSheetHandling;

public class CoronasafeData {
	WebDriver driver;

	public void collectData(String stateName, String districtName) throws InterruptedException, IOException {
		int numberofFacility = driver.findElements(By.xpath("//*[@id='__next']//main/div")).size();
		System.out.println(numberofFacility);
		String[] facilityNames = new String[numberofFacility];
		for (int i = 2; i <= numberofFacility; i++) {

			WebElement facilitynm = new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions
					.visibilityOf(driver.findElement(By.xpath("//*[@id='__next']//main/div[" + (i - 1) + "]//h1"))));
			String facilityName = facilitynm.getText();
			System.out.println("Facility Name : " + facilityName);
			System.out.println("i "+ i);
			// Thread.sleep(500);
			facilityNames[i - 2] = facilityName;
		}

		ExcelSheetHandling exl = new ExcelSheetHandling();
		exl.writeExcelSheet(facilityNames,stateName,districtName);
	}

	public void selectStates() throws InterruptedException, IOException {
		System.setProperty("webdriver.chrome.driver", "/opt/homebrew/bin/chromedriver");
		driver = new ChromeDriver();

		// driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));

		driver.get("https://life.coronasafe.network/");

		WebElement service = driver.findElement(By.xpath("//*[@id='__next']//*[span[text()='Oxygen']]"));
		service.click();
		WebElement serachField = driver.findElement(By.id("searchField"));
		new Actions(driver).sendKeys(serachField, "Maharashtra").sendKeys(Keys.ENTER).perform();

		int noOfStates = driver.findElements(By.xpath("//*[@id=\"Select State\"]/option")).size();

		for (int s = 1; s < 3; s++) {
			WebElement selectState = new WebDriverWait(driver, Duration.ofSeconds(30))
					.until(ExpectedConditions.visibilityOfElementLocated(By.id("Select State")));
			selectState.click();
			// Thread.sleep(1000);
			Select state = new Select(selectState);
			state.selectByIndex(s);
			String stateName = driver.findElement(By.id("Select State")).getAttribute("value");
			System.out.println("State is-----" + stateName);
			// Thread.sleep(1000);

			int noOfDistrict = driver.findElements(By.xpath("//*[@id=\"Select District\"]/option")).size();

			for (int d = 1; d < 3; d++) {
				Thread.sleep(1000);

				WebElement selectDistrict = new WebDriverWait(driver, Duration.ofSeconds(30))
						.until(ExpectedConditions.visibilityOfElementLocated(By.id("Select District")));
				selectDistrict.click();

				Select district = new Select(selectDistrict);
				district.selectByIndex(d);
				String districtName = driver.findElement(By.id("Select District")).getAttribute("value");
				System.out.println("District is-----" + districtName);
				collectData(stateName, districtName);
			}

			System.out.println("==============");

		}
		driver.quit();
	}

	public static void main(String[] args) throws InterruptedException, IOException {

		new CoronasafeData().selectStates();
	}
}
