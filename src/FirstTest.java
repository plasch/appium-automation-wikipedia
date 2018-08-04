import lib.CoreTestCase;
import lib.ui.*;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.LinkedList;
import java.util.List;

public class FirstTest extends CoreTestCase {

    private MainPageObject MainPageObject;

    protected void setUp() throws Exception
    {
        super.setUp();

        MainPageObject = new MainPageObject(driver);
    }


    // Ex2 Create method: Check presence placeholder 'Search…'
    @Test
    public void testCheckPlaceholderInSearchField() {
        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        WebElement element = MainPageObject.waitForElementPresent(
                By.id("org.wikipedia:id/search_src_text"),
                "Cannot find placeholder 'Search…'",
                15
        );

        String placeholderSearch = element.getAttribute("text");

        Assert.assertEquals(
                "We don't see placeholder 'Search…'",
                "Search…",
                placeholderSearch
        );

    }

    // Ex3 Create test: Cancel search result
    @Test
    public void testCancelSearchResult() {
        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input",
                10
        );

        MainPageObject.waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                "Android",
                "Cannot find search input",
                10
        );

        MainPageObject.waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='An open source operating system for mobile devices created by Google']"),
                "Cannot find first search result by 'Android'",
                10
        );

        MainPageObject.waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Wikimedia list article']"),
                "Cannot find second search result by 'Android'",
                10
        );

        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find X to cancel search",
                10
        );

        MainPageObject.waitForElementNotPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='An open source operating system for mobile devices created by Google']"),
                "First search result is still present on the page",
                20
        );

        MainPageObject.waitForElementNotPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Wikimedia list article']"),
                "Second search result is still present on the page",
                10
        );
    }

    // Ex4* Create test: Check words in search results
    @Test
    public void testCheckWordInSearchResults() {
        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input",
                10
        );

        String search_line = "Android";
        MainPageObject.waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                search_line,
                "Cannot find search input",
                10
        );

        List<WebElement> elements = driver.findElementsById("org.wikipedia:id/page_list_item_title");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.withMessage("Cannot find search results");
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id("org.wikipedia:id/page_list_item_title")));

        List<String> irrelevant_elements = new LinkedList<>();
        int counter = 0;
        for (int i = 0; i < elements.size(); i++) {
            if (elements.get(i).getAttribute("text").contains(search_line))
                counter++;
            else irrelevant_elements.add(elements.get(i).getAttribute("text"));
        }

        Assert.assertEquals("Next search results don't contain word '" + search_line + "': " + irrelevant_elements,
                elements.size(),
                counter
        );
    }

    // Ex5 Create test: Save two articles in one folder
    @Test
    public void testSaveArticlesInOneFolder() {
        // Search and add FIRST article
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        String search_line = "Android";
        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                search_line,
                "Cannot find search input",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='An open source operating system for mobile devices created by Google']"),
                "Cannot find first search result",
                5
        );

        MainPageObject.waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find first article title",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cannot find button to open first article options",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "Cannot find option to add first article to reading list",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/onboarding_button"),
                "Cannot find 'Got it' tip overlay",
                5
        );

        MainPageObject.waitForElementAndClear(
                By.id("org.wikipedia:id/text_input"),
                "Cannot find input to set name of articles folder",
                5
        );

        String name_of_folder = "Android OS";

        MainPageObject.waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                name_of_folder,
                "Cannot put text into article folder input",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='OK']"),
                "Cannot press OK button",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot close first article, cannot find X link",
                5
        );

        // Search and add SECOND article
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                search_line,
                "Cannot find search input",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Android software development']"),
                "Cannot find second search result",
                5
        );

        MainPageObject.waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find second article title",
                15
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cannot find button to open second article options",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "Cannot find option to add second article to reading list",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='" + name_of_folder + "']"),
                "Cannot find created folder " + name_of_folder,
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot close second article, cannot find X link",
                5
        );

        // Transfer to My list and remove first article
        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "Cannot find navigation button to My list",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='" + name_of_folder + "']"),
                "Cannot find created folder " + name_of_folder + " in My list",
                5
        );

        MainPageObject.swipeElementToLeft(
                By.xpath("//*[@text='An open source operating system for mobile devices created by Google']"),
                "Cannot find first saved article in My list"
        );

        MainPageObject.waitForElementNotPresent(
                By.xpath("//*[@text='An open source operating system for mobile devices created by Google']"),
                "Cannot delete first saved article from My list",
                5
        );

        String article_title_from_my_list = MainPageObject.waitForElementAndGetAttribute(
                By.xpath("//*[@text='Android software development']"),
                "text",
                "Cannot find second saved article",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='Android software development']"),
                "Cannot open second saved article",
                10
        );

        String title_of_opened_article = MainPageObject.waitForElementAndGetAttribute(
                By.xpath("//*[@resource-id='org.wikipedia:id/view_page_title_text'][@text='Android software development']"),
                "text",
                "Cannot find title of second opened article",
                10
        );

        Assert.assertEquals(
                "Titles articles don't match",
                article_title_from_my_list,
                title_of_opened_article
        );
    }

    // Ex6 Create test: Assert title
    @Test
    public void testAssertTitle() {
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Android",
                "Cannot find search input",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='An open source operating system for mobile devices created by Google']"),
                "Cannot find search results",
                5
        );

        MainPageObject.assertElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find title of article"
        );
    }

    // Ex7* Rotate Device
    @Test
    public void testCheckOrientation() {
        // Add before test for check device orientation
        String orientation_after_fail = driver.getOrientation().toString();
        if (orientation_after_fail.equals("LANDSCAPE")) {
            driver.rotate(ScreenOrientation.PORTRAIT);
        }
    }

}