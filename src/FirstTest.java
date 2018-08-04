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
}