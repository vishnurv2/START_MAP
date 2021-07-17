
import com.lambdatest.tunnel.Tunnel;
import cucumber.api.java.eo.Se;
import io.appium.java_client.AppiumDriver;
import org.apache.commons.lang3.time.StopWatch;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.annotations.*;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class demoDesktop {

    public String username = "vishnukdas";
    public String accesskey = "mSAoW7DC449kuIz9mk0uYRpS6hgxpHqlgc1eRzXXPFNUChd0nZ";
    public RemoteWebDriver driver;
    public String gridURL = "stage-hub.lambdatest.com";
    String status;
    String hub;
    SessionId sessionId;


    @org.testng.annotations.Parameters(value = {"browser", "version", "platform"})
    @BeforeTest
    public void setUp(String browser, String version, String platform) throws Exception {
        try {

            DesiredCapabilities caps = new DesiredCapabilities();
            caps.setCapability("build", platform + "- Jenkins : "+System.getProperty("BUILD_NUMBER") + " S_M ");
            caps.setCapability("platform", "Win 10"); 
            caps.setCapability("browserName", "chrome");
            caps.setCapability("version", "latest");
//             caps.setCapability("fixedIP","10.80.82.117");


            hub = "https://" + username + ":" + accesskey + "@" + gridURL + "/wd/hub";
            driver = new RemoteWebDriver(new URL(hub), caps);
            sessionId = driver.getSessionId();

            System.out.println("--------------------"+sessionId+"-----------------------");


            StopWatch driverStart = new StopWatch();
            driverStart.start();


        } catch (Exception f) {
            System.out.println(f);

        }

    }

    @Test()
    public void DesktopScript() {
        try {
            driver.manage().window().maximize();
            driver.get("https://fast.com");
            Thread.sleep(30000);
            driver.get("https://platform.boomi.com");
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            WebDriverWait el = new WebDriverWait(driver,10);
            el.until(ExpectedConditions.visibilityOf(driver.findElementById("gwt-uid-25")));
            driver.findElementById("gwt-uid-25").sendKeys("abc");
            System.out.println(driver.getTitle());
            
            
            status = "passed";
        } catch (Exception e) {
            System.out.println(e+"-----------Test---------"+sessionId +"-----------------");
            status = "failed";
        }
    }

    @AfterMethod
    public void tearDown() throws Exception {
        if (driver != null) {
            ((JavascriptExecutor) driver).executeScript("lambda-status=" + status);
            driver.quit();
        }
    }
}

