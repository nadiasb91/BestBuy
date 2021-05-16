package Test;

import Page.SearchBestBuyPage;
import Utils.Util;
import com.google.common.base.Verify;
import com.google.common.io.Files;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en_scouse.An;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;


public class SearchBesBuyTest {
    WebDriver driver;
    Util util;
    SearchBestBuyPage searchBestBuyPage;

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        //options.setHeadless(true);
        driver = new ChromeDriver(options);
        util = new Util();
        searchBestBuyPage = new SearchBestBuyPage(driver);
        driver.get(util.Url);
    }

    @Given("user is the best buy page")
    public void user_is_the_best_buy_page() {
        Assert.assertEquals(driver.getTitle(), "Best Buy International: Select your Country - Best Buy");

    }

    @And("select a us country")
    public void select_a_us_country() {
        searchBestBuyPage.clickCountry();
    }

    @When("insert a {string} on the search box")
    public void insert_a_criteria_on_the_search_box(String criteria) {
        searchBestBuyPage.closeModal();
        searchBestBuyPage.waitForPageLoaded();
        searchBestBuyPage.setSearchCriteria(criteria);
    }

    @And("click submit")
    public void click_submit() {
        searchBestBuyPage.submitSearch();
    }

    @Then("a list of results must be display")
    public void a_list_of_results_must_be_display() {
        Assert.assertTrue(searchBestBuyPage.resultsOfSearch());
        System.out.println(searchBestBuyPage.resultsOfSearch());
    }

    @Then("a message must be display with search {string}")
    public void a_message_must_be_display_with_search_criteria(String criteria) {
        String message = searchBestBuyPage.getMessageFromWrongCriteriaSearch();
        System.out.println(message);
        Assert.assertEquals(message, "Hmmm, we didn't find anything for " + "\"" + criteria + "\"");
    }

    @Then("the result display should start with the {string}")
    public void an_error_message_must_be_display(String criteria) {
        String message = searchBestBuyPage.getTextFromMessage();
        Assert.assertEquals(message, "Results for" + " " + "\"" + criteria + "\".");
    }

    @Then("the suggestion must match with the {string}")
    public void the_suggestion_must_match_with_the_criteria(String criteria) {
        boolean result = searchBestBuyPage.checkSuggestions(criteria);
        Assert.assertTrue(result);
    }

    @Then("nothing should happen")
    public void nothing_should_happen() {
        String title = driver.getTitle();
        Assert.assertEquals(title, "Best Buy | Official Online Store | Shop Now & Save");
    }

    @And("apply 3 filters to the results")
    public void apply_3_filters_to_the_results() {
        searchBestBuyPage.setFilters();
    }

    @Then("check that the list is updated with the filters")
    public void check_that_the_list_is_updated_with_the_filters() {
        Assert.assertEquals(searchBestBuyPage.checkDiscountFilterLabel(), "Samsung");
        Assert.assertEquals(searchBestBuyPage.checkVoiceOverFilterLabel(), "Amazon Alexa");
        Assert.assertEquals(searchBestBuyPage.checkColorFilterLabel(), "Black");
    }

    @Then("open the first result")
    public void open_the_first_result() {
        searchBestBuyPage.openFirstResult();
        //Assert.assertEquals(driver.getTitle(), "Insignia™ 50\" Class F30 Series LED 4K UHD Smart Fire TV NS-50DF710NA21 - Best Buy");
        Assert.assertTrue(searchBestBuyPage.checkDetailsOfFirstProduct());
        Assert.assertTrue(searchBestBuyPage.checkRating());
    }

    @And("display the first three reviews")
    public void display_the_first_three_reviews() {
        if (searchBestBuyPage.getReviews().length > 0) {
            for (int i = 0; i < 3; i++) {
                System.out.println(i + 1 + "- " + searchBestBuyPage.getReviews()[i]);
            }
        }
    }


    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            var camera = (TakesScreenshot) driver;
            File screenshot = camera.getScreenshotAs(OutputType.FILE);
            try {
                Files.move(screenshot, new File("src/test/resources/screenshots/" + scenario.getName() + ".png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        driver.close();
    }


}
