package Page;

import io.cucumber.java.en_old.Ac;
import io.cucumber.java.eo.Do;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SearchBestBuyPage {

    private WebDriver driver;

    public SearchBestBuyPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "a[class=\"us-link\"]")
    WebElement unitedStateOption;
    @FindBy(css = "button[class=\"c-close-icon c-modal-close-icon\"]")
    WebElement closeModal;
    @FindBy(id = "gh-search-input")
    WebElement searchBox;
    @FindBy(css = "li[class=\"sku-item\"]")
    List<WebElement> resultsOfSearch;
    @FindBy(css = "h3[class=\"no-results-message\"]")
    WebElement messageForWrongSearchCriteria;
    @FindBy(css = "div[class=\"title-wrapper title\"]")
    WebElement resultMessage;
    @FindBy(css = "a[class=\"suggest-target suggest-wk-press-fix\"]")
    List<WebElement> listOfSuggestions;
    @FindBy(id = "voiceassistant_facet-Amazon-Alexa-0")
    WebElement voiceOverFilter;
    @FindBy(id = "brand_facet-Samsung-1")
    WebElement brandFilter;
    @FindBy(id = "colorcat_facet-Black-0")
    WebElement colorFilter;
    @FindBy(css = "button[aria-label=\"Remove Remove Filter: Black\"]")
    WebElement colorFilterLabel;
    @FindBy(css = "button[aria-label=\"Remove Remove Filter: Samsung\"]")
    WebElement brandFilterLabel;
    @FindBy(css = "button[aria-label=\"Remove Remove Filter: Amazon Alexa\"]")
    WebElement voiceOverFilterLabel;
    @FindBy(css = "h4>a[href=\"/site/insignia-50-class-f30-series-led-4k-uhd-smart-fire-tv/6401029.p?skuId=6401029\"]")
    WebElement firstResult;
    @FindBy(css = "h1[class=\"heading-5 v-fw-regular\"]")
    WebElement productName;
    @FindBy(xpath = "//div[@class=\"model product-data\"]/child::span[2]")
    WebElement productModel;
    @FindBy(xpath = "//div[@class=\"sku product-data\"]/child::span[2]")
    WebElement productSKU;
    @FindBy(xpath = "//li[@class=\"ugc-stat customer-review-stats\"]//child::span[@class=\"ugc-c-review-average\"]")
    WebElement productRating;
    @FindBy(css = ".percent")
    List<WebElement> ratingPerPoints;
    @FindBy(xpath = "//span[@class=\"star\"]")
    List<WebElement> listOfStars;
    @FindBy(css = ".pre-white-space")
    List<WebElement> reviews;


    public void clickCountry() {
        unitedStateOption.click();
    }

    public void waitUntilVisibilityOf(WebElement element) {
        FluentWait wait = new FluentWait(driver).
                pollingEvery(Duration.ofSeconds(1)).
                withTimeout(Duration.ofSeconds(30)).
                ignoring(NoSuchElementException.class);
        wait.until(ExpectedConditions.visibilityOf(element));
    }


    public void closeModal() {
        waitUntilVisibilityOf(closeModal);
        closeModal.click();
    }

    public void setSearchCriteria(String criteria) {
        waitForPageLoaded();
        searchBox.sendKeys(criteria);
    }

    public void submitSearch() {
        searchBox.submit();
    }

    public void waitForPageLoaded() {
        ExpectedCondition<Boolean> expectation = new
                ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver driver) {
                        return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete");
                    }
                };
        try {
            Thread.sleep(1000);
            WebDriverWait wait = new WebDriverWait(driver, 30);
            wait.until(expectation);
        } catch (Throwable error) {
            Assert.fail("Timeout waiting for Page Load Request to complete.");
        }
    }

    /*public void waitForLoad(WebDriver driver) {
        ExpectedCondition<Boolean> pageLoadCondition = new
                ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver driver) {
                        return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
                    }
                };
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(pageLoadCondition);
    }*/

    public boolean resultsOfSearch() {
        int total = resultsOfSearch.size();
        if (total > 0) {
            return true;
        } else {
            return false;
        }
    }

    public String getMessageFromWrongCriteriaSearch() {
        String message = messageForWrongSearchCriteria.getText();
        return message;
    }

    public String getTextFromMessage() {
        String message = resultMessage.getText();
        return message;
    }

    public boolean checkSuggestions(String searchCriteria) {
        String title;
        if (listOfSuggestions.size() > 0) {
            for (WebElement e : listOfSuggestions) {
                title = e.getAttribute("title");
                if (title.startsWith(searchCriteria)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void setFilters() {
        voiceOverFilter.click();
        brandFilter.click();
        colorFilter.click();
    }

    public String checkVoiceOverFilterLabel() {
        String text = voiceOverFilterLabel.getText();
        return text;
    }

    public String checkDiscountFilterLabel() {
        String text = brandFilterLabel.getText();
        return text;
    }

    public String checkColorFilterLabel() {
        String text = colorFilterLabel.getText();
        return text;
    }

    public void openFirstResult() {
        firstResult.click();
    }

    public boolean checkDetailsOfFirstProduct() {
        waitForPageLoaded();
        if (productName.isDisplayed() && productModel.isDisplayed() && productSKU.isDisplayed()) {
            return true;
        }
        return false;
    }

    public void clickReviews() {
        productRating.click();
    }

    public boolean checkRating() {
        productRating.click();
        double sum = 0d;
        double totalReviews = 0d;
       BigDecimal bgd1, bgd2;
       // double rating = 0d;
        int stars = 5;
        String element="";
        if (ratingPerPoints.size() > 0) {
            for (WebElement e : ratingPerPoints) {
                element=e.getText().replaceAll(",", "");
                sum += stars * Double.valueOf(element);
                totalReviews += Double.valueOf(element);
                stars--;
            }
           // rating = (sum / totalReviews);
            bgd1= new BigDecimal(sum);
            bgd2= new BigDecimal(totalReviews);
            if (bgd1.divide(bgd2,1, RoundingMode.FLOOR).toString().equals(productRating.getText())) {
                return true;
            }
        }
        return false;
    }

    public String[] getReviews(){
        String[] arr= new String[reviews.size()];
        int i=0;
        for(WebElement e:reviews){
            arr[i]=e.getText();
            i++;
        }

        return arr;
    }
}
