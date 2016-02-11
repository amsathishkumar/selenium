/**
 * Copyright (c) 2015 by Cisco Systems, Inc.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Cisco Systems,  ("Confidential Information").  You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with Cisco Systems.
 *
 *
 * @Project: Utils
 * @Author: smuniapp
 * @Version: 
 * @Description:  
 * @Date created: Apr 9, 2015
 */

package com.cisco.dbds.utils.selenium;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.jar.JarFile;

import javax.imageio.ImageIO;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.junit.Assert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cisco.dbds.utils.configfilehandler.ConfigFileHandler;
import com.cisco.dbds.utils.cucumber.Hooks;
import com.cisco.dbds.utils.jarfilehandler.jarfilehandler;
import com.cisco.dbds.utils.logging.LogHandler;
import com.cisco.dbds.utils.validation.Validate;
import com.google.common.base.Function;

// TODO: Auto-generated Javadoc
/**
 * This is the main class that deals with initiating WebDriver, opening of a
 * browser.
 * 
 */
public class SeleniumUtilities {

	/** The driver. */
	private static WebDriver driver;

	/**
	 * Opens the Firefox instance for Selenium.
	 */
	public static void openBrowser() {
		if (System.getProperty("run.type") ==null || System.getProperty("run.type").equalsIgnoreCase("WUI"))
		{
			if (getDriver() != null)
				System.out.println(getDriver().toString()+"hi");
			if (getDriver() == null  || (!getDriver().toString().contains("-")) )
			{
				try {

					String browser = Validate.readsystemvariable("browser");
					LogHandler.info("Browser:" + browser);

					String digest = Validate.readsystemvariable("browser.digest");
					LogHandler.info("Digest Auth:" + digest);

					if (browser.equals("firefox") && digest.toUpperCase().equals("YES")) {
						LogHandler.info("Fire Fox Driver with addon configuration");
						FirefoxProfile profile = new FirefoxProfile();
						profile.setPreference("browser.link.open_newwindow", 2);
						File extentions = new File(System.getProperty("user.dir")
								+ Validate.readsystemvariable("firefox.addons.path"));
						if (extentions.exists()) {
							for (File extention : extentions.listFiles()) {
								if (extention.getName().endsWith(".xpi")) {
									try {
										profile.addExtension(new File(extention
												.getAbsolutePath()));
									} catch (IOException e) {
										Assert.assertTrue(
												"Exception occured while adding the extension to the profile..",
												false);
									}
									LogHandler.info("Added extention: "
											+ extention.getName());
								}
							}
						}
						driver = new FirefoxDriver(profile);
					} else if (browser.equals("firefox")
							&& digest.toUpperCase().equals("NO")) {
						LogHandler.info("Fire Fox Driver without addon configuration");
						driver = new FirefoxDriver();
					} else if (browser.equals("chrome"))

					{
						LogHandler.info("Chrome Driver configuration");
						JarFile jarFile = jarfilehandler.jarForClass(SeleniumUtilities.class);
						jarfilehandler.copyResourcesToDirectory(jarFile, "chrome",
								"src/it/resources/chrome");
						System.setProperty("webdriver.chrome.driver",
								"src/it/resources/chrome/chromedriver.exe");
						driver = new ChromeDriver();

					} else if (browser.equals("IE")) {

						LogHandler.info("IE Driver configuration");
						DesiredCapabilities capabilities = DesiredCapabilities
								.internetExplorer();
						String IEpath = (SeleniumUtilities.class).getResource("")
								.getPath();
						System.out.println(IEpath.substring(0, IEpath.length() - 31));

						capabilities
						.setCapability(
								InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
								true);
						capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS,
								true);

						JarFile jarFile = jarfilehandler
								.jarForClass(SeleniumUtilities.class);
						// JarFile jarFile = new
						// JarFile("C:\\Users\\smuniapp\\.m2\\repository\\com\\cisco\\dbds\\Utils\\0.0.1-Release\\Utils-0.0.1-Release.jar");
						jarfilehandler
						.copyResourcesToDirectory(jarFile, "internetexplorer",
								"src/it/resources/InternetExplorer");

						System.setProperty("webdriver.ie.driver",
								"src/it/resources/internetexplorer/IEDriverServer.exe");
						driver = new InternetExplorerDriver(capabilities);

						// driver = new InternetExplorerDriver();
					} else {
						Assert.assertTrue("include the browswer variable...", false);
					}
				} catch (Exception e) {
					LogHandler
					.info("Check the varaiables: browser,firefox.addons.path,user.dir");

				}
				//	driver.manage().timeouts().pageLoadTimeout(3, TimeUnit.SECONDS);
				//	driver.manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS);
				driver.manage().window().maximize();
				driver.manage()
				.timeouts()
				.implicitlyWait(
						Integer.parseInt(System.getProperty("implicit.wait")),
						TimeUnit.SECONDS);
				driver.manage()
				.timeouts()
				.pageLoadTimeout(
						Integer.parseInt(System.getProperty("explicit.wait")),
						TimeUnit.SECONDS);
				System.out.println(getDriver().toString()+"hi");
			}
		}
	}
	/**
	 * This function injects the password into the firefox profile.
	 * 
	 * @param url
	 *            the url
	 * @param uirealm
	 *            the uirealm
	 * @param uiusername
	 *            the uiusername
	 * @param uipassword
	 *            the uipassword
	 */
	public static void injectPasswordToFirefox(String url, String uirealm,
			String uiusername, String uipassword) {
		String script = "var addCredentialsEvent = new CustomEvent(\"add_credentials_to_passman\", {"
				+ "   detail: {"
				+ " host: '"
				+ url
				+ "',"
				+ " realm: '"
				+ uirealm
				+ "',"
				+ " username: '"
				+ uiusername
				+ "',"
				+ " password: '"
				+ uipassword
				+ "'"
				+ " } });"
				+ " window.dispatchEvent(addCredentialsEvent);";

		LogHandler.info("Executing script: " + script);
		driver.manage()
		.timeouts()
		.setScriptTimeout(
				Integer.parseInt(System.getProperty("implicit.wait")),
				TimeUnit.SECONDS);
		((JavascriptExecutor) driver).executeScript(script);

	}

	/**
	 * Always returns a non null value of webdriver instance.
	 * 
	 * @return the driver
	 */
	public static WebDriver getDriver() {
		if ((driver == null) && (Hooks.isBrowserNeeded == true)) {
			openBrowser();
		}
		return driver;
	}

	/**
	 * Nvigate to URL.
	 * 
	 * @param url
	 *            the url
	 */
	public static void navigateToUrl(String url) {
		driver.get(url);
		String browser = Validate.readsystemvariable("browser");
		if (browser.equals("IE")) {
			WebDriverWait wait = new WebDriverWait(driver, 10);
			WebElement ele = wait
					.until(ExpectedConditions.elementToBeClickable(driver.findElement(By
							.linkText("Continue to this website (not recommended)."))));
			ele.click();
		}
		// driver.get(driver.getCurrentUrl());
	}

	/**
	 * Performs a click on the Webelement.
	 * 
	 * @param element
	 *            the element
	 */
	public static void click(WebElement element) {
		// SeleniumUtilities.waitForElement(element);
		element.click();
	}

	/**
	 * Performs a clear action on the Webelement.
	 * 
	 * @param element
	 *            the element
	 */
	public static void clear(WebElement element) {
		SeleniumUtilities.waitForElement(element);
		element.clear();
	}

	/**
	 * Performs a type action on the Webelement.
	 * 
	 * @param element
	 *            the element
	 * @param contentToSend
	 *            the content to send
	 */
	public static void type(WebElement element, String contentToSend) {
		SeleniumUtilities.waitForElement(element);
		element.sendKeys(contentToSend);
	}

	/**
	 * Clear and type.
	 * 
	 * @param element
	 *            the element
	 * @param contentToSend
	 *            the content to send
	 */
	public static void clearAndType(WebElement element, String contentToSend) {
		SeleniumUtilities.waitForElement(element);
		SeleniumUtilities.clear(element);
		element.sendKeys(contentToSend);
	}

	/**
	 * Selects an option from dropdown.
	 * 
	 * @param dropDown
	 *            the drop down
	 * @param option
	 *            the option
	 */
	public static void selectFromDropdown(WebElement dropDown, WebElement option) {
		click(dropDown);
		waitForElement(option);
		click(option);

	}

	/**
	 * Selects an option from dropdown.
	 * 
	 * @param dropDown
	 *            the drop down
	 * @param optionsListXpath
	 *            the options list xpath
	 * @param option
	 *            the option
	 */
	public static void selectFromDropdown(WebElement dropDown,
			String optionsListXpath, String option) {
		waitForElement(dropDown);
		if (dropDown.isDisplayed() && dropDown.isEnabled()) {
			click(dropDown);

			String initialValue = driver.switchTo().activeElement().getText();
			boolean found = false;

			while (found == false) {
				driver.switchTo().activeElement().sendKeys(Keys.ARROW_DOWN);
				if (driver.switchTo().activeElement().getText()
						.equals(initialValue)) {
					break;
				} else {
					if (driver.switchTo().activeElement().getText()
							.equals(option)) {
						LogHandler.printToConsole("\tSelecting the option : "
								+ driver.switchTo().activeElement().getText());
						driver.switchTo().activeElement().click();
						found = true;
					}

				}
			}
		}

	}

	/**
	 * waits for the given webelement.
	 * 
	 * @param element
	 *            the element
	 */
	public static void waitForElement(WebElement element) {
		WebDriverWait wait = new WebDriverWait(getDriver(),
				Integer.parseInt(System.getProperty("explicit.wait")));
		wait.until(ExpectedConditions.visibilityOf(element));

	}

	//
	// /**
	// * waits for the given webelement
	// *
	// * @param element
	// */
	// public static void waitForElementContainsText(WebElement element,
	// String expectedText) {
	// WebDriverWait wait = new WebDriverWait(getDriver(),
	// Integer.parseInt(System.getProperty("explicit.wait")));
	// wait.until(new ExpectedCondition<Boolean>() {
	//
	// public Boolean apply(WebDriver driver) {
	//
	// return null;
	// }
	//
	// });
	//
	// }

	/**
	 * waits for the alert.
	 */
	public static void waitForAlert() {
		WebDriverWait wait = new WebDriverWait(getDriver(),
				Integer.parseInt(System.getProperty("explicit.wait")));
		wait.until(ExpectedConditions.alertIsPresent());

	}

	/**
	 * Accept alert.
	 */
	public static void acceptAlert() {
		getDriver().switchTo().alert().accept();

	}

	/**
	 * Dismiss alert.
	 */
	public static void dismissAlert() {
		getDriver().switchTo().alert().dismiss();

	}

	/**
	 * Scroll.
	 * 
	 * @param selectableWebelementforScroll
	 *            the selectable webelementfor scroll
	 * @param stepsize
	 *            the stepsize
	 */
	public static void scroll(WebElement selectableWebelementforScroll,
			int stepsize) {
		click(selectableWebelementforScroll);
		for (int i = 0; i < stepsize; i++) {
			Actions actionObject = new Actions(driver);
			actionObject.sendKeys(Keys.ARROW_DOWN).build().perform();
		}

	}

	/**
	 * Move to a given WebElement.
	 * 
	 * @param element
	 *            the element
	 */
	public static void moveToElement(WebElement element) {
		Actions builder = new Actions(driver);
		builder.moveToElement(element).build().perform();
	}

	/**
	 * Return the WebElement for the identifier provided.
	 * 
	 * @param identifierType
	 *            the identifier type
	 * @param identifierValue
	 *            the identifier value
	 * @return the web element
	 * @throws NoSuchMethodException
	 *             the no such method exception
	 * @throws SecurityException
	 *             the security exception
	 * @throws ClassNotFoundException
	 *             the class not found exception
	 * @throws IllegalAccessException
	 *             the illegal access exception
	 * @throws IllegalArgumentException
	 *             the illegal argument exception
	 * @throws InvocationTargetException
	 *             the invocation target exception
	 */
	public static WebElement findElement(Identifier identifierType,
			String identifierValue) throws NoSuchMethodException,
			SecurityException, ClassNotFoundException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		Method method = Class.forName("org.openqa.selenium.By")
				.getDeclaredMethod(identifierType.getName(), String.class);
		By by = (By) method.invoke(method, identifierValue);
		return getDriver().findElement(by);

	}

	/**
	 * Waits for the Element given by the locator.
	 * 
	 * @param locatorname
	 *            the locatorname
	 * @param timeout
	 *            the timeout
	 * @return the web element
	 */
	public static WebElement waitForElement(By locatorname, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		return wait.until(presenceOfElementLocated(locatorname));
	}

	/**
	 * Presence of element located.
	 * 
	 * @param locator
	 *            the locator
	 * @return the function
	 */
	private static Function<WebDriver, WebElement> presenceOfElementLocated(
			final By locator) {
		return new Function<WebDriver, WebElement>() {
			@Override
			public WebElement apply(WebDriver driver) {
				return driver.findElement(locator);
			}
		};
	}

	/**
	 * Return if the element is present in the page.
	 * 
	 * @param locator
	 *            the locator
	 * @return true, if is element present
	 */
	public static boolean isElementPresent(By locator) {
		return driver.findElements(locator).size() > 0;
	}

	/**
	 * Returns the screenshot as a byte array to embed in cucumber reports.
	 * 
	 * @return the byte[]
	 */
	public static byte[] takeScreenshot() {
		final byte[] screenshot = ((TakesScreenshot) driver)
				.getScreenshotAs(OutputType.BYTES);
		return screenshot;
	}

	/**
	 * Returns the screenshot as a File.
	 * 
	 * @return the file
	 */
	public static File takeScreenshotAsFile() {
		final File screenshot = ((TakesScreenshot) driver)
				.getScreenshotAs(OutputType.FILE);
		return screenshot;
	}

	/**
	 * This function captures the Screenshot of the screen and converts it into
	 * a byte array to embed in cucumber reports.
	 * 
	 * @return the byte[]
	 * @throws Exception
	 *             the exception
	 */
	public static byte[] captureScreen() throws Exception {

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Rectangle screenRectangle = new Rectangle(screenSize);
		Robot robot = new Robot();
		BufferedImage image = robot.createScreenCapture(screenRectangle);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(image, "png", baos);
		baos.flush();
		byte[] bytes = baos.toByteArray();
		baos.close();
		return bytes;

	}

	/**
	 * Waits till the Page gets the expected condition.
	 * 
	 * @param condition
	 *            the condition
	 */
	public static void waitforCondition(ExpectedCondition<Boolean> condition) {
		WebDriverWait wait = new WebDriverWait(getDriver(),
				Integer.parseInt(System.getProperty("explicit.wait")));
		wait.until(condition);

	}

	/**
	 * Verifies if element is displayed.
	 * 
	 * @param element
	 *            - WebElement
	 * @return - true if displayed, false otherwise
	 */
	public static boolean isElementDisplayed(WebElement element) {
		return element.isDisplayed();
	}

	/**
	 * Verifies proper error message has been displayed.
	 * 
	 * @param expectedAlertMessage
	 *            - Expected error message
	 * @return - True if proper message has been displayed, false otherwise
	 */
	public static boolean isValidAlertMessageDisplayed(
			String expectedAlertMessage) {
		boolean isValidMessageDisplayed = false;
		Alert alert = null;
		try {
			alert = getDriver().switchTo().alert();
			isValidMessageDisplayed = expectedAlertMessage.equals(alert
					.getText());
		} catch (Exception e) {
			throw e;
		}
		return isValidMessageDisplayed;
	}

	/**
	 * Finds list of elements which are matching given condition.
	 * 
	 * @param identifierType
	 *            - Identifier types
	 * @param identifierValue
	 *            - Value of identifier
	 * @return - List of {@link WebElement}
	 * @throws NoSuchMethodException
	 *             the no such method exception
	 * @throws SecurityException
	 *             the security exception
	 * @throws ClassNotFoundException
	 *             the class not found exception
	 * @throws IllegalAccessException
	 *             the illegal access exception
	 * @throws IllegalArgumentException
	 *             the illegal argument exception
	 * @throws InvocationTargetException
	 *             the invocation target exception
	 */
	public static List<WebElement> findElements(Identifier identifierType,
			String identifierValue) throws NoSuchMethodException,
			SecurityException, ClassNotFoundException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		Method method = Class.forName("org.openqa.selenium.By")
				.getDeclaredMethod(identifierType.getName(), String.class);
		By by = (By) method.invoke(method, identifierValue);
		return getDriver().findElements(by);
	}

	/**
	 * Retrieves text from web elements and returns them.
	 * 
	 * @param elements
	 *            - List of non null text from given web elements
	 * @return the string from web elements
	 */
	public static List<String> getStringFromWebElements(
			List<WebElement> elements) {
		List<String> elementStrings = new ArrayList<>();
		for (WebElement webElement : elements) {
			if (webElement != null) {
				String textValue = webElement.getText();
				if (textValue != null) {
					elementStrings.add(textValue);
				}
			}
		}
		return elementStrings;
	}

	/**
	 * Wait/Pause Execution.
	 * 
	 * @param seconds
	 *            the seconds
	 * @param aw
	 *            the aw
	 */
	public static void wait(int seconds, Autowait aw) {
		LogHandler.info("Waiting for " + seconds + " seconds");
		try {
			int offset = 0;
			try {
				offset = Integer.parseInt(System.getProperty(aw.getOffset()));
			} catch (NumberFormatException e) {
				LogHandler
				.warn("Failed to retrieve valid value from property file.."
						+ aw.getOffset());
			}
			Thread.sleep(offset + seconds * 1000);
		} catch (InterruptedException e) {
			LogHandler.warn("Failed to wait for " + seconds + " seconds");
		}
	}

	/**
	 * Wait.
	 * 
	 * @param seconds
	 *            the seconds
	 */
	public static void wait(int seconds) {
		LogHandler.info("Waiting for " + seconds + " seconds");
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException e) {
			LogHandler.warn("Failed to wait for " + seconds + " seconds");
		}
	}

	/**
	 * Deletes all cookies from browser cache.
	 */
	public static void deleteBrowserCookies() {
		driver.manage().deleteAllCookies();
	}

	/**
	 * Read browser cookies.
	 * 
	 * @param cookiename
	 *            the cookiename
	 * @return the string
	 */
	public static String readBrowserCookies(String cookiename) {
		return driver.manage().getCookieNamed(cookiename).toString();
	}

	/**
	 * This returns the message displayed on the pop-up.
	 * 
	 * @return Returns the text on the pop-up
	 */
	public static String returnAlertText() {
		return getDriver().switchTo().alert().getText().trim();
	}

	/**
	 * Returns true if alert is present else false.
	 * 
	 * @return true, if is alert present
	 */
	public static boolean isAlertPresent() {
		boolean isPresent = false;
		if (ExpectedConditions.alertIsPresent().apply(getDriver()) != null) {
			isPresent = true;
			LogHandler.info("Alert Message is:"
					+ getDriver().switchTo().alert().getText().trim());
		}

		return isPresent;
	}

	/**
	 * Return position.
	 * 
	 * @param screenHeight
	 *            the screen height
	 * @param entityHeight
	 *            the entity height
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	public static String returnPosition(float screenHeight, float entityHeight)
			throws Exception {
		String pos = null;

		float dividingFactor = (screenHeight / 4);
		float entityPos = (entityHeight / dividingFactor);

		if ((entityPos > 0.0) && (entityPos < 1.0)) {
			pos = "top";
		} else if ((entityPos > 1.0) && (entityPos < 2.0)) {
			pos = "top";
		} else if ((entityPos > 2.0) && (entityPos < 3.0)) {
			pos = "bottom";
		} else if ((entityPos > 3.0) && (entityPos < 4.0)) {
			pos = "bottom";
		} else if (entityPos == 0.0) {
			pos = "top";
		} else if (entityPos == 1.0) {
			pos = "top";
		} else if (entityPos == 3.0) {
			pos = "bottom";
		} else if (entityPos == 4.0) {
			pos = "bottom";
		} else {
			throw new Exception("Unknown entityPos value:" + entityPos);
		}

		return pos;
	}

	/**
	 * Return direction.
	 * 
	 * @param screenWidth
	 *            the screen width
	 * @param entityWidth
	 *            the entity width
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	public static String returnDirection(float screenWidth, float entityWidth)
			throws Exception {
		String direction = null;

		float dividingFactor = (screenWidth / 4);
		float entityPos = (entityWidth / dividingFactor);

		if ((entityPos > 0.0) && (entityPos < 1.0)) {
			direction = "left";
		} else if ((entityPos > 1.0) && (entityPos < 2.0)) {
			direction = "left";
		} else if ((entityPos > 2.0) && (entityPos < 3.0)) {
			direction = "right";
		} else if ((entityPos > 3.0) && (entityPos < 4.0)) {
			direction = "right";
		} else if (entityPos == 0.0) {
			direction = "left";
		} else if (entityPos == 1.0) {
			direction = "left";
		} else if (entityPos == 3.0) {
			direction = "right";
		} else if (entityPos == 4.0) {
			direction = "right";
		} else {
			throw new Exception("Unknown entityPos value:" + entityPos);
		}
		return direction;
	}

	/**
	 * Return position direction.
	 * 
	 * @param xpath
	 *            the xpath
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	public static String returnPositionDirection(String xpath) throws Exception {
		System.out.println("xpath>>>" + xpath);
		String posDir = null;

		org.openqa.selenium.Dimension dimesions = SeleniumUtilities.getDriver()
				.manage().window().getSize();

		System.out.println("Width : " + dimesions.width);
		System.out.println("Height : " + dimesions.height);

		org.openqa.selenium.Point point = SeleniumUtilities.findElement(
				Identifier.XPATH, xpath).getLocation();
		System.out.println("X Position : " + point.x);
		System.out.println("Y Position : " + point.y);

		posDir = returnPosition(dimesions.height, point.y) + " "
				+ returnDirection(dimesions.width, point.x);

		System.out.println("The location is:" + posDir);
		return posDir;
	}
}
