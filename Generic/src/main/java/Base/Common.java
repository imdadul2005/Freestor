package Base;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.lang.System.exit;

/**
 * Created by Administrator on 5/22/2017.
 */
public class Common {

    String searchLocation = ".form-control.ng-valid.ng-dirty.ng-valid-parse.ng-touched";
            //"//input[@placeholder='Search for...']";
    public static WebDriver driver = null;
    @Parameters ({"url","browser"})
    @BeforeMethod
    public void start(String url, String browser){
        driver = localMachine(browser);
        driver.navigate().to(url);
    }

    @AfterMethod
    public void end (ITestResult result){
        if (ITestResult.FAILURE==result.getStatus())
            captureScreenshot("TEST-");
        driver.quit();
    }
    public WebDriver localMachine(String browserName) {
        if (browserName.equalsIgnoreCase("chrome")) {
            System.setProperty("webdriver.chrome.driver", "../Generic/driver/chromedriver.exe");
            driver = new ChromeDriver();
        } else if (browserName.equalsIgnoreCase("firefox")) {
            System.setProperty("webdriver.gecko.driver", "../Generic/driver/geckodriver.exe");
            driver = new FirefoxDriver();
        } else if (browserName.equalsIgnoreCase("ie")) {
            System.setProperty("webdriver.ie.driver", "../Generic/driver/IEDriverServer.exe");
            driver = new InternetExplorerDriver();
        }
        return driver;
    }
    public void clickByCss(String locator) {
        driver.findElement(By.cssSelector(locator)).click();
    }
    public void clickByXpath(String locator) {
        driver.findElement(By.xpath(locator)).click();
    }
    public void typeByCss(String locator, String value) {
        driver.findElement(By.cssSelector(locator)).sendKeys(value);
    }
    public void typeByCssNEnter(String locator, String value) {
        driver.findElement(By.cssSelector(locator)).sendKeys(value, Keys.ENTER);
    }
    public void typeByXpath(String locator, String value) {
        driver.findElement(By.xpath(locator)).sendKeys(value);
    }
    public void takeEnterKeys(String locator) {
        driver.findElement(By.cssSelector(locator)).sendKeys(Keys.ENTER);
    }
    public void clearInputField(String locator){
        driver.findElement(By.cssSelector(locator)).clear();
    }
    public List<WebElement> getListOfWebElementsById(String locator) {
        List<WebElement> list = new ArrayList<WebElement>();
        list = driver.findElements(By.id(locator));
        return list;
    }
    public List<String> getTextFromWebElements(String locator){
        List<WebElement> element = new ArrayList<WebElement>();
        List<String> text = new ArrayList<String>();
        element = driver.findElements(By.cssSelector(locator));
        for(WebElement web:element){
            text.add(web.getText());
        }
        return text;
    }
    public List<WebElement> getListOfWebElementsByCss(String locator) {
        List<WebElement> list = new ArrayList<WebElement>();
        list = driver.findElements(By.cssSelector(locator));
        return list;
    }
    public List<WebElement> getListOfWebElementsByXpath(String locator) {
        List<WebElement> list = new ArrayList<WebElement>();
        list = driver.findElements(By.xpath(locator));
        return list;
    }
    public String  getCurrentPageUrl(){
        String url = driver.getCurrentUrl();
        return url;
    }
    public void navigateBack(){
        driver.navigate().back();
    }
    public void navigateForward(){
        driver.navigate().forward();
    }
    public String getTextByCss(String locator){
        String st = driver.findElement(By.cssSelector(locator)).getText();
        return st;
    }
    public String getTextByXpath(String locator){
        String st = driver.findElement(By.xpath(locator)).getText();
        return st;
    }
    public String getTextById(String locator){
        return driver.findElement(By.id(locator)).getText();
    }
    public String getTextByName(String locator){
        String st = driver.findElement(By.name(locator)).getText();
        return st;
    }
    public List<String> getListOfString(List<WebElement> list) {
        List<String> items = new ArrayList<String>();
        for (WebElement element : list) {
            items.add(element.getText());
        }
        return items;
    }
    public void selectOptionByVisibleText(WebElement element, String value) {
        Select select = new Select(element);
        select.selectByVisibleText(value);
    }
    public void sleepFor(int sec)throws InterruptedException{
        Thread.sleep(sec * 1000);
    }
    public void mouseHoverByCSS(String locator){
        try {
            WebElement element = driver.findElement(By.cssSelector(locator));
            Actions action = new Actions(driver);
            Actions hover = action.moveToElement(element);
        }catch(Exception ex){
            System.out.println("First attempt has been done, This is second try");
            WebElement element = driver.findElement(By.cssSelector(locator));
            Actions action = new Actions(driver);
            action.moveToElement(element).perform();
        }
    }
    public void mouseHoverByXpath(String locator){
        try {
            WebElement element = driver.findElement(By.xpath(locator));
            Actions action = new Actions(driver);
            Actions hover = action.moveToElement(element);
        }catch(Exception ex){
            System.out.println("First attempt has been done, This is second try");
            WebElement element = driver.findElement(By.cssSelector(locator));
            Actions action = new Actions(driver);
            action.moveToElement(element).perform();

        }
    }
    //handling Alert
    public void okAlert(){
        Alert alert = driver.switchTo().alert();
        alert.accept();
    }
    public void cancelAlert(){
        Alert alert = driver.switchTo().alert();
        alert.dismiss();
    }
   //iFrame Handle
    public void iframeHandle(WebElement element){
        driver.switchTo().frame(element);
    }
    public void goBackToHomeWindow(){
        driver.switchTo().defaultContent();
    }

    //get Links
    public void getLinks(String locator){
        driver.findElement(By.linkText(locator)).findElement(By.tagName("a")).getText();
    }
    public static void captureScreenshot(String screenshotName){

    //yyMMddHHmmssZ         MM.dd.yyyy-HH:mma
        DateFormat df = new SimpleDateFormat("(yyMMddHHmmssZ)");
        Date date = new Date();
        df.format(date);

        File file = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(file, new File(System.getProperty("user.dir")+ "/screenshots/"+screenshotName+" "+df.format(date)+".png"));
            System.out.println("Screenshot captured");
        } catch (Exception e) {
            System.getProperty("user.dir");
            System.out.println("Exception while taking screenshot "+e.getMessage());;
        }

    }
    //Taking Screen shots
    public void takeScreenShot()throws IOException {
        File file = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(file,new File("screenShots.png"));
    }
    //Synchronization
    public void waitUntilClickAble(By locator){
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
    public void waitUntilVisible(int sec, By locator){
        WebDriverWait wait = new WebDriverWait(driver, sec);
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    public void waitUntilSelectable(By locator){
        WebDriverWait wait = new WebDriverWait(driver, 10);
        boolean element = wait.until(ExpectedConditions.elementToBeSelected(locator));
    }
    public void upLoadFile(String locator,String path){
        driver.findElement(By.cssSelector(locator)).sendKeys(path);
        /* path example to upload a file/image
           path= "C:\\Users\\rrt\\Pictures\\ds1.png";
         */
    }
    public void clearInput(String locator){
        driver.findElement(By.cssSelector(locator)).clear();
    }
    public void keysInput(String locator){
        driver.findElement(By.cssSelector(locator)).sendKeys(Keys.ENTER);
    }
    public String converToString(String st){
        String splitString ;
        splitString = StringUtils.join(StringUtils.splitByCharacterTypeCamelCase(st), ' ');
        return splitString;
    }
    public static String parseString(String string, String left,String right) {
        int leftIndex = string.indexOf(left);
        int rightIndex = string.indexOf(right);
        // pull out the text inside the parens
        return string.substring(leftIndex + 1, rightIndex);
    }
    public void login(String username, String domain, String password) {
        findElementAndSend(By.xpath("//input[@name='username']"), username);
        findElementAndSend(By.xpath("//input[@name='domain']"), domain);
        findElementAndSend(By.xpath("//input[@name='password']"), password);
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys(Keys.ENTER);

    }
    public void findElementAndSend(By locator,  String information) {
        driver.findElement(locator).sendKeys(information);
    }
    public  void findFromListAndSelect(List<WebElement> list, String searchItem) {
        System.out.println("findFromList()--Requested item : " + searchItem);
        for (WebElement item : list) {
            System.out.println("Listed item " + item.getText());
            if (item.getText().equalsIgnoreCase(searchItem)) {
                System.out.println("findFromList() --Found item : " + item.getText());
                item.click();
                break;
            }
        }
    }
    public  void findFromListAndSelect(By LocatorList, By selectedLocation, String stringToSend) {
        waitUntilVisible(20, selectedLocation);
        driver.findElement(selectedLocation).click();
        List<WebElement> navList = driver.findElements(LocatorList);
        findFromListAndSelect(navList, stringToSend);
    }

    public static void waitUntilDisappear(int sec, By locator) {
        WebDriverWait wait = new WebDriverWait(driver, sec);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }
    public void existsElement( By locator) {
        boolean status = false;
        try {
            driver.findElement(locator);
        } catch (NoSuchElementException e) {
            status = false;
        }
        status = true;
        try{
            Assert.assertEquals(status,true);
        }catch (AssertionError e){
            exit(1);
        }
    }

    public  void selectServer(String serverIP) {
        waitUntilVisible(50, By.cssSelector(searchLocation));
        findElementAndSend(By.cssSelector(searchLocation), serverIP);
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.findElement(By.cssSelector("ul.array-list>li")).click();
     //   driver.findElement(By.xpath("html/body/div[1]/div/div[1]/div[2]/ul/li")).click();
    }

    public void waitUntilVisibleAndSend(By locator, String sendme) {
        waitUntilVisible(200,locator);
        WebElement element = driver.findElement(locator);
        element.sendKeys(sendme);
        element.sendKeys(Keys.ENTER);
    }

    public WebElement findFromListAndSendLocation(List<WebElement> list, String searchItem) {
        WebElement notFound= null;
        System.out.println("findFromList()--Requested item : " + searchItem);
        for (WebElement item : list) {
            System.out.println("Listed item " + item.getText());
            if (item.getText().equalsIgnoreCase(searchItem)) {
                System.out.println("findFromList() --Found item : " + item.getText());
                return item;
            }
        }
        return notFound;
    }

    public void labelCheck(By locator,String label, By sendLocation, String sendItem,int waitTime,By checkItem,Boolean send,boolean isMultiple,String innertext){
        List<WebElement> list =  driver.findElements(locator);
        for (WebElement a:list) {
            System.out.println("item :------" +a.getText()+ "----");
            if(a.getText().equalsIgnoreCase(label)) {
               // System.out.println("Found");
                waitUntilDisappear(20,checkItem);
                if (!isMultiple){
                    if (send)
                        a.findElement(sendLocation).sendKeys(sendItem);
                    else
                        a.findElement(sendLocation).click();
                    break;
                }
                if(isMultiple){
                    List<WebElement> temp = a.findElements(sendLocation);
                    for (WebElement item:temp) {
                        if(item.getText().equalsIgnoreCase(innertext))
                            item.click();
                    }
                }
            }
        }
    }
}




