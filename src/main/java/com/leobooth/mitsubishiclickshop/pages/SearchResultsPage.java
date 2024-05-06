package com.leobooth.mitsubishiclickshop.pages;

import com.leobooth.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class SearchResultsPage extends BasePage {

    private final String pageUrl = "";
    private final String pageName = "Search Results Page (SRP)";

    public SearchResultsPage(WebDriver driver) {
        super(driver);
        this.setPageUrl(pageUrl);
        this.setPageName(pageName);
    }

    public SearchResultsPage(WebDriver driver, String url) {
        super(driver);
        this.setPageUrl(url);
        this.setPageName(pageName);
    }

    By srpTileSelector = By.xpath(
            "//div[contains(@class,'col-span')][descendant::span[@role='button'][child::a[contains(@href,'/vehicle/')]]]"
    );

    By srpTileInfoSelector = By.xpath(
            "//descendant::div[contains(@class,'relative')]/following-sibling::span"
    );

        By chainDealerName = By.xpath(
                "/descendant::div[contains(@class,'flex-col')][position()=2]/descendant::p[position()=1]"
        );

        By chainDealerDistance = By.xpath(
                "/descendant::div[contains(@class,'flex-col')][position()=2]/descendant::p[position()=3]"
        );


    By srpTileDealerNameSelector = By.xpath(
            "//descendant::div[contains(@class,'relative')]/following-sibling::span/descendant::div[contains(@class,'flex-col')][position()=2]/descendant::p[position()=1]"
    );

    By srpTileDealerDistanceSelector = By.xpath(
            "//descendant::div[contains(@class,'relative')]/following-sibling::span/descendant::div[contains(@class,'flex-col')][position()=2]/descendant::p[position()=3]"
    );

    By srpNoVehiclesFound = By.xpath(
            "//h4[contains(text(),'No vehicles found')]"
    );

    public void waitUntilSrpTitleVisible(int timeoutInSeconds) {
        WebDriverWait waitForTitle = new WebDriverWait(getDriver(), Duration.ofSeconds(timeoutInSeconds));
        waitForTitle.until(ExpectedConditions.titleContains("Mitsubishi"));
    }

    public void waitUntilDealerNameVisible(int timeoutInSeconds) {
        WebDriverWait waitForDealerName = new WebDriverWait(getDriver(), Duration.ofSeconds(timeoutInSeconds));
        waitForDealerName.until(
                ExpectedConditions.or(
                    ExpectedConditions.textToBePresentInElementLocated(srpTileDealerNameSelector, "Mitsubishi"),
                    ExpectedConditions.textToBePresentInElementLocated(srpTileDealerNameSelector, "MITSUBISHI"),
                    ExpectedConditions.visibilityOfElementLocated(srpTileDealerNameSelector)
                )
        );
    }

    public boolean isNoVehiclesFoundVisible() {
        try {
            WebElement noVehiclesFoundElement = getDriver().findElement(srpNoVehiclesFound);
            return noVehiclesFoundElement.isDisplayed();
        } catch (NoSuchElementException | TimeoutException e) {
            return false;
        }
    }

    public String getSRPTileDealerName(int tileIndex) {
        ArrayList<WebElement> dealerNames = new ArrayList<>(getDriver().findElements(srpTileDealerNameSelector));

        if(tileIndex >= dealerNames.size()) {
            return "requested tile index outside range of available tiles: " + tileIndex;
        }

        if(!dealerNames.isEmpty()) {
            return dealerNames.get(tileIndex).getText();
        } else {
            return "no result";
        }
    }

    public String getSRPTileDistance(int tileIndex) {
        ArrayList<WebElement> dealerDistances = new ArrayList<>(getDriver().findElements(srpTileDealerDistanceSelector));

        if(tileIndex >= dealerDistances.size()) {
            return "requested tile index outside range of available tiles: " + tileIndex;
        }

        if(!dealerDistances.isEmpty()) {
            return dealerDistances.get(tileIndex).getText();
        } else {
            return "no result";
        }
    }
}
