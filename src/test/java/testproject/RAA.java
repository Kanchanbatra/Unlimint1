package testproject;

import static org.testng.Assert.assertEquals;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.LoggerNameAwareMessage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


public class RAA {
	
	static Map<String,String> hm;
	static WebDriver driver;
	
	private static Logger logger =LogManager.getLogger(RAA.class);

	public static void main(String[] args) throws Throwable {
		
		randomuser();
		
		logger.info("First random user data lodded successfully");
		
		System.out.println(hm);

		System.setProperty("webdriver.chrome.driver","C:\\Users\\rohit.grover\\Downloads\\Rohit\\chromedriver.exe");
		
		logger.info("Webdriver properties set successfully");
		
		driver=new ChromeDriver();
		
		driver.manage().window().maximize();
		
		driver.get("https://parabank.parasoft.com/parabank/index.html");
		
		logger.info("Website launched successfully");
		
		clickC("xpath","//a[text()='Register']");
		
		send("id","customer.firstName","firstname");
		
		logger.info("first name entered successfully");
		
		send("id","customer.lastName","lastname");
		
		logger.info("Last name entered successfully");
		
		send("id","customer.address.street","address");
		
		logger.info("Address entered successfully");
		
		send("id","customer.address.city","city");
		
		logger.info("City entered successfully");
		
		send("id","customer.address.state","state");
		
		logger.info("State entered successfully");
		
		send("id","customer.address.zipCode","zipcode");
		
		logger.info("Postal Code entered successfully");
		
		send("id","customer.phoneNumber","phone");
		
		logger.info("Phone Number entered successfully");
		
		send("id","customer.ssn","ssn");
		
		logger.info("SSN entered successfully");
		
		send("id","customer.username","username");
		
		logger.info("username entered successfully");
		
		send("id","customer.password","password");
		
		logger.info("Password entered successfully");
		
		send("id","repeatedPassword","password");
		
		logger.info("Confirm Password entered successfully");
		
		clickC("xpath","//input[@value='Register']");
		
		boolean flag=false;
		try{
			flag=driver.findElement(By.xpath("//span[text()='This username already exists.']")).isDisplayed();
		}
		catch(Exception e) {
		}
		
		if(flag) {
			logger.info("username already exists");
			driver.findElement(By.id("")).sendKeys(hm.get("username")+"11");
			logger.info("New username added successfully");
			send("id","customer.password","password");
			logger.info("Again Password entered successfully");
			send("id","repeatedPassword","password");
			logger.info("Again Confirm Password entered successfully");
			clickC("xpath","//input[@value='Register']");
		}
		
		
		randomuser();	
		
		logger.info("Second Random user data captured successfully");
		
		clickC("xpath","//a[text()='Bill Pay']");
		
		send("name","payee.name","fullname");
		
		logger.info("Sencond random user as payee full name entered successfully");
		
		send("name","payee.address.street","address");
		
		logger.info("Sencond random user as payee address entered successfully");
		
		send("name","payee.address.city","city");
		
		logger.info("Sencond random user as payee city entered successfully");
		
		send("name","payee.address.state","state");
		
		logger.info("Sencond random user as payee state entered successfully");
		
		send("name","payee.address.zipCode","zipcode");
		
		logger.info("Sencond random user as payee postal code entered successfully");
		
		send("name","payee.phoneNumber","phone");
		
		logger.info("Sencond random user as payee phone number entered successfully");
		
		send("name","payee.accountNumber","accountnum");
		
		logger.info("Sencond random user as payee account number entered successfully");
		
		send("name","verifyAccount","accountnum");
		
		logger.info("Sencond random user as payee confirm account number entered successfully");
		
		send("name","amount","amount");
		
		logger.info("Sencond random user as payee amount to be transferred entered successfully");
		
		clickC("xpath","//input[@value='Send Payment']");
		
		Thread.sleep(1000);
		boolean b=driver.findElement(By.xpath("//h1[text()='Bill Payment Complete']")).isDisplayed();
		assertEquals(b,true);
		logger.info("Payment done successfully");
		
		String rec=hm.get("fullname");
		System.out.println(rec);
		boolean b1=driver.findElement(By.xpath("//span[@id='payeeName' and text()='"+rec+"']")).isDisplayed();
		assertEquals(b1,true);
		logger.info("Verfied that payment was successful to the correct user:"+rec);
		System.out.println("Test");
	
	}
	
	public static void send(String locator,String locatorV, String value) {
		
		switch(locator){
			
		case "id":
			
			driver.findElement(By.id(locatorV)).sendKeys(hm.get(value));
			break;
		
		case "xpath":
			driver.findElement(By.xpath(locatorV)).sendKeys(hm.get(value));
			break;
		
		case "name":
		driver.findElement(By.name(locatorV)).sendKeys(hm.get(value));
		break;
	}	
		
	}
	
public static void clickC(String locator,String locatorV) {
		
		switch(locator){
			
		case "id":
			
			driver.findElement(By.id(locatorV)).click();;
			break;
		
		case "xpath":
			driver.findElement(By.xpath(locatorV)).click();
			break;
		
		}
			
		
	}
	
	
public static Map<String,String> randomuser() {
		
		hm= new HashMap<String,String>();
		
		RestAssured.baseURI = "https://randomuser.me/api";
		RequestSpecification httpRequest = RestAssured.given();
		Response response = httpRequest.get("/?nat=us&randomapi");
		
		JsonPath js = response.jsonPath();
	
		String fname1=js.get("results[0].name.first");
		String lname1 = js.get("results[0].name.last");
		String fullname=fname1+" " + lname1;
		String city=js.get("results[0].location.city");
		String state=js.get("results[0].location.state");
		String zipcode=String.valueOf(js.get("results[0].location.postcode"));
		String phone=js.get("results[0].phone");
		String ssn=js.get("results[0].id.value");
		String username=js.get("results[0].login.username");
		String password=js.get("results[0].login.password");
		String housenum = String.valueOf(js.get("results[0].location.street.number"));
		String housename = js.get("results[0].location.street.name");
		String addrs=housenum+" "+housename;
		String accountnum="12345";
		String amount="500";
		
		hm.put("firstname", fname1);
		hm.put("lastname", lname1);
		hm.put("address", addrs);
		hm.put("city", city);
		hm.put("state", state);
		hm.put("zipcode", zipcode);
		hm.put("phone", phone);
		hm.put("ssn",ssn);
		hm.put("username",username);
		hm.put("password", password);
		hm.put("fullname", fullname);
		hm.put("accountnum", accountnum);
		hm.put("amount", amount);
		
		return hm;
		
	}

}
