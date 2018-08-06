package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.MyListsPageObject;
import lib.ui.NavigationUI;
import lib.ui.SearchPageObject;
import org.junit.Test;

public class ExTests extends CoreTestCase
{
    // Ex3 Create test: Cancel search result
    @Test
    public void testCancelSearchResult()
    {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

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
    @Test
    public void testSaveArticlesInOneFolder() {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        String search_line = "Android";

        // Search and add FIRST article
        SearchPageObject.typeSearchLine(search_line);
        SearchPageObject.clickByArticleWithSubstring("An open source operating system for mobile devices created by Google");

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);

        ArticlePageObject.waitForTitleElement();
        String first_article_title = ArticlePageObject.getArticleTitle();
        String name_of_folder = "Android OS";
        ArticlePageObject.addArticleToMyList(name_of_folder);
        ArticlePageObject.closeArticle();

        // Search and add SECOND article
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(search_line);
        SearchPageObject.clickByArticleWithSubstring("Android software development");
        ArticlePageObject.waitForTitleElement();
        String second_article_title = ArticlePageObject.getArticleTitle();

        ArticlePageObject.addArticleToExistList(name_of_folder);
        ArticlePageObject.closeArticle();

        // Transfer to My list and remove first article
        NavigationUI NavigationUI = new NavigationUI(driver);
        NavigationUI.clickMyLists();

        MyListsPageObject MyListsPageObject = new MyListsPageObject(driver);

        MyListsPageObject.openFolderByName(name_of_folder);
        MyListsPageObject.swipeByArticleToDelete(first_article_title);
        MyListsPageObject.waitArticleToDisappearByTitle(first_article_title);
        MyListsPageObject.openArticleByTitle(second_article_title);

        ArticlePageObject.waitForTitleElement();
        String title_of_opened_article = ArticlePageObject.getArticleTitle();

        assertEquals(
                "Titles articles don't match",
                second_article_title,
                title_of_opened_article
        );
    }

    // Ex6 Create test: Assert title
    @Test
    public void testAssertTitle()
    {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Android");
        SearchPageObject.clickByArticleWithSubstring("An open source operating system for mobile devices created by Google");

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        ArticlePageObject.assertArticleTitlePresent();
    }

    // Ex9* Create test with refactoring of template
    @Test
    public void testCheckArticleByTemplate()
    {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

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
