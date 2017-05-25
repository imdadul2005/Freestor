package Manange;

import Base.Common;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Created by Administrator on 5/25/2017.
 */
public class CreateClient extends Common{

    //=============================================================================================//
        String topNavigation = "ul.nav.navbar-nav>li";
        String tabSelections = "ul.nav.nav-tabs > li.ng-scope > a.ng-binding";
        String tabsCss = ".panel-heading.clearfix>div>div>button";
        String tabXpath = "//div[@class='resource-definition-menu1']/div[1]/button";
        String labels = ".col-sm-3.control-label";
        String clientForm = ".form-group";
        String clientFormInputLocation = ".form-group >div>input";
        String processICON = "html/body/div[3]/div/div/div[3]/form-error/p/i";
        String protocolLocation = ".form-group>div>button";
    //=============================================================================================//

    @BeforeMethod
    public void login(){
        login("superadmin","","freestor");
    }

    //   @Parameters ({"clientName"})
    @Test
    public void createFCClient() throws InterruptedException {
        findFromListAndSelect(getListOfWebElementsByCss(topNavigation),"Manage");
        Thread.sleep(3000);
        findFromListAndSelect(getListOfWebElementsByCss(tabSelections),"Clients");
        Thread.sleep(3000);
        clickByXpath(tabXpath);

      /*  List<WebElement> list =  driver.findElements(By.cssSelector(".form-group"));
        for (WebElement a:list) {
            System.out.println("item :------" +a.getText()+ "----");
            if(a.getText().equalsIgnoreCase("IP Address")) {
                System.out.println("Found");
                waitUntilDisappear(20,By.xpath("html/body/div[3]/div/div/div[3]/form-error/p/i"));
                a.findElement(By.cssSelector(".form-group >div>input")).sendKeys("10.2.3.4");
                break;
            }
        }*/


        labelCheck(By.cssSelector(clientForm),"Name *",By.cssSelector(clientFormInputLocation), "clientName",10,By.xpath(processICON),true,false,"");
        labelCheck(By.cssSelector(clientForm),"IP Address",By.cssSelector(clientFormInputLocation), "10.6.13.41",10,By.xpath(processICON),true,false,"");
        labelCheck(By.cssSelector(clientForm),"Protocols",By.cssSelector(clientFormInputLocation), "",10,By.xpath(processICON),true,false," iSCSI ");

        sleepFor(10);


     //   WebElement location = findFromListAndSendLocation(driver.findElements(By.cssSelector(labels)),"Name *");
      //  location.findElement(By.cssSelector(".col-sm-3.control-label~div")).sendKeys("Test");
    }

}

