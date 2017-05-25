package Configurations;

import Base.Common;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Created by Administrator on 5/25/2017.
 */
public class iocluster extends Common{

    //=============================================================================================//

    String clusterNameLocation = "//input[@ng-model='ioclusterItem.clusterName']";
    String managementipLocation = "//input[@ng-model='ioclusterItem.mgmtIp']";
    String ipmiUsernameLocation = "//input[@ng-model='ioclusterItem.powercontrol.ipmi.username']";
    String ipmiPasswordLocation = "//input[@ng-model='ioclusterItem.powercontrol.ipmi.password']";
    String ipmiPassword2Location = "//input[@ng-model='forms.panelFailoverForm.passwordAgain2']";
    String topNavigation = "ul.nav.navbar-nav>li";
    String tabSelections = "ul.nav.nav-tabs > li.ng-scope > a.ng-binding";
    String qurom1Location = "html/body/div[1]/div/div[3]/div/div[2]/div/div/div/div[2]/div[2]/div/form/div[2]/div[4]/div[2]/div[1]/div/div/div/div/span";
    String qurom2Location = "html/body/div[1]/div/div[3]/div/div[2]/div/div/div/div[2]/div[2]/div/form/div[2]/div[4]/div[2]/div[2]/div/div/div/span";
    String psdeploymentonLocation = "//input[@type='password'][@placeholder='Deployment password']";
    String serverListLocation = ".array-name.ng-binding.ng-scope";
    //=============================================================================================//

    @BeforeMethod
    public void login(){
        login("superadmin","","freestor");
    }

    @Parameters({"primaryServer","partnerServer","clustername","clusterIP","qurom1","qurom2","ipmiUsername","ipmiPassword"})
    @Test
    public void setUpIOC(String primaryServer, String partnerServer, String clustername, String clusterIP, String qurom1, String qurom2, String ipmiUserName, String ipmiPassword) throws InterruptedException {

        findFromListAndSelect(getListOfWebElementsByCss(topNavigation),"Manage");
        Thread.sleep(3000);
        findFromListAndSelect(getListOfWebElementsByCss(tabSelections),"Settings");
        Thread.sleep(3000);
       // findFromListAndSelect(getListOfWebElementsByCss(serverListLocation),primaryServer);
        selectServer(primaryServer);

       // manageTab("Settings");

        // Go to the iocluster page
        driver.navigate().to(driver.getCurrentUrl() + "iocluster");

        // type password for psdeployment
        waitUntilVisibleAndSend(By.xpath(psdeploymentonLocation),"psdeploymenton");

        //Select the partner node
        selectServer(partnerServer);

        //Click Validation
        driver.findElement(By.xpath("//button[contains(text(),'Validate IO Cluster')]")).click();

        //Check if validation fail or validation process is stuck
        waitUntilDisappear(15, By.cssSelector(".modal-title"));

        //Cluster name
        findElementAndSend(By.xpath(clusterNameLocation), clustername);

        //Management IP address
        findElementAndSend(By.xpath(managementipLocation), clusterIP);

        //Detect Quorum Repository physical device need a better coding here for sure


        findFromListAndSelect(By.cssSelector(".ng-binding.ng-scope"),By.xpath(qurom1Location), qurom1);
        findFromListAndSelect(By.cssSelector(".ng-binding.ng-scope"),By.xpath(qurom2Location), qurom2);

        //IPMI username, password and confirm password

        findElementAndSend(By.xpath(ipmiUsernameLocation), ipmiUserName);
        findElementAndSend(By.xpath(ipmiPasswordLocation), ipmiPassword);
        findElementAndSend(By.xpath(ipmiPassword2Location), ipmiPassword);
        //Click Validate IPMI option

        List<WebElement> buttonList = getListOfWebElementsByCss(".btn.btn-sm.btn-primary");
        findFromListAndSelect(buttonList, "Validate IPMI");
        findFromListAndSelect(buttonList, "Set Up IO Cluster");

        //This part needs to be re-arranged to handle ipmi validation error
        //Wait until Configure IO Cluster appear then click Configure
        waitUntilVisible(10,By.cssSelector(".modal-title"));

    }


}
