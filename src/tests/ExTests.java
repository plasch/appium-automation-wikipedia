package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.ArticlePageObject;
import lib.ui.MyListsPageObject;
import lib.ui.NavigationUI;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListsPageObjectFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

public class ExTests extends CoreTestCase
{
    // Ex3 Create test: Cancel search result
    @Test
    public void testCancelSearchResult()
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        String search_line = "Android";
        SearchPageObject.typeSearchLine(search_line);
        int search_results = SearchPageObject.getAmountOfFoundArticles();
        assertTrue(
                "Not enough articles in search results",
                search_results != 0
        );
        SearchPageObject.clickCancelSearch();
        SearchPageObject.waitForSearchResultsToDisappear();
    }

    // Ex5 Create test: Save two articles in one folder
    // Ex10 Refactoring Ex5 Test for iOS
    @Test
    public void testSaveArticlesInOneFolder() {

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        String name_of_folder = "Android OS";
        String first_article = "Android (operating system)";
        String second_article = "Android software development";

        // Search and add FIRST article
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Android");
        SearchPageObject.clickByArticleWithSubstring(first_article);

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);

        if (Platform.getInstance().isAndroid()) {
            ArticlePageObject.waitForTitleElement();
            ArticlePageObject.addArticleToMyList(name_of_folder);
        } else {
            ArticlePageObject.addArticleToMySaved();
            ArticlePageObject.closeSyncPopup();
        }
        ArticlePageObject.closeArticle();

        // Search and add SECOND article
        SearchPageObject.initSearchInput();
        if (Platform.getInstance().isAndroid()) {
            SearchPageObject.typeSearchLine("Android");
        }
        SearchPageObject.clickByArticleWithSubstring(second_article);

        if (Platform.getInstance().isAndroid()) {
            ArticlePageObject.waitForTitleElement();
            ArticlePageObject.addArticleToExistList(name_of_folder);
        } else {
            ArticlePageObject.addArticleToMySaved();
        }
        ArticlePageObject.closeArticle();

        // Transfer to My list and remove first article
        NavigationUI NavigationUI = NavigationUIFactory.get(driver);
        NavigationUI.clickMyLists();

        MyListsPageObject MyListsPageObject = MyListsPageObjectFactory.get(driver);

        if (Platform.getInstance().isAndroid()) {
            MyListsPageObject.openFolderByName(name_of_folder);
        }

        MyListsPageObject.swipeByArticleToDelete(first_article);

        if (Platform.getInstance().isAndroid()) {
            MyListsPageObject.openArticleByTitle(second_article);
            String article_title_after_deleting = ArticlePageObject.getArticleTitle();
            assertEquals(
                    "Titles articles don't match",
                    article_title_after_deleting,
                    second_article
            );
        } else {
            MyListsPageObject.waitArticleToAppearByTitle(second_article);

        }
    }

    // Ex6 Create test: Assert title
    @Test
    public void testAssertTitle()
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Android");
        SearchPageObject.clickByArticleWithSubstring("An open source operating system for mobile devices created by Google");

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        ArticlePageObject.assertArticleTitlePresent();
    }

    // Ex9* Create test with refactoring of template
    @Test
    public void testCheckArticleByTemplate()
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Android");
        int amount_of_search_results = SearchPageObject.getAmountOfFoundArticles();
        System.out.print(amount_of_search_results);
        assertTrue(
                "Less than 3 articles in search results",
                amount_of_search_results >= 3
        );
        SearchPageObject.waitForElementByTitleAndDescription("Android", "An open source operating system for mobile devices created by Google");
    }
}
