package Test;

import Utils.Util;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

public class BaseTest {
   private WebDriver driver;
   private Util util;


    @BeforeTest
    public void setUp(){
        WebDriverManager.chromedriver().setup();
        driver= new ChromeDriver();
        util= new Util();
        driver.get(util.Url);
    }

    @AfterTest
    public void tearDown(){
        //driver.close();
    }

}
