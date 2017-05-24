package StartUp;
import Base.Common;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Administrator on 5/23/2017.
 */
public class Login extends Common {

    String u1= null;
    String u2=null;
    String u3= null;
    String p1=null;
    String p2=null;
    String p3=null;
    String d1=null;
    String d2=null;
    String d3=null;
    String browser = "chrome";  //default browser
    String url="http:\\";

    //Here is the list used xpath in this test
    String errorMessage = "//*[@ng-bind-html='form.serverErrorMessage']";
    String navRight = "//div[@class='navbar-right']/ul/li[2]";
    String navRightUserName =  "//div[@class='navbar-right']/ul/li[2]/ul/li/div[2]/strong";

    @BeforeTest
    public void setup(){
        Properties prop = new Properties();
        try {
            FileInputStream fin = new FileInputStream("C:\\Users\\Administrator\\IdeaProjects\\Freestor\\MSPV2\\src\\test\\resources\\config.properties");
            prop.load(fin);
            browser=prop.getProperty("browser");
            u1=prop.getProperty("u1");
            u2=prop.getProperty("u2");
            u3=prop.getProperty("u3");
            p1=prop.getProperty("p1");
            p2=prop.getProperty("p2");
            p3=prop.getProperty("p3");
            d1=prop.getProperty("d1");
            d2=prop.getProperty("d2");
            d3=prop.getProperty("d3");
            url=url+prop.getProperty("url");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
   /* @BeforeMethod
    public void start(){
        driver = localMachine(browser);
        driver.navigate().to(url);
    }*/

    @AfterMethod
    public void testEnd(){
        driver.close();
    }
    @Test //(dependsOnMethods = {"incorrectLogin3"})
    public void inCorrectLogin() {
        login(u1,d1,p1);
        WebElement message = driver.findElement(By.xpath(errorMessage));
        Assert.assertEquals(message.getText(),"Login failed. The username or password is incorrect.");
        System.out.println(getCurrentPageUrl());
    }
    @Test
    public void correctLogin() {
        login(u2,d2,p2);
        // Click the corner icon
        WebElement icon = driver.findElement(By.xpath(navRight));
        icon.click();
        waitUntilClickAble(By.xpath(navRight));
        // Check the name of the user and compare
        String loginUserName = driver.findElement(By.xpath(navRightUserName)).getText();
        System.out.println(loginUserName);
        Assert.assertEquals(loginUserName,u2);
        System.out.println(getCurrentPageUrl());
    }
    @Test
    public void wrongDomain() {
        login(u3,d3,p3);
        WebElement message = driver.findElement(By.xpath(errorMessage));
        Assert.assertEquals(message.getText(),"Login failed. The customer domain does not exist.");
        System.out.println(getCurrentPageUrl());

    }
}
