
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class seleniumDown {

    public static String findUrl(String url) {
        WebDriver driver = new ChromeDriver();
        driver.get(url);
        WebDriverWait driverWait = new WebDriverWait(driver, 15);
        driverWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//video")));

        String secondUrl = findVideoAdr(driver);
        for (int i = 0; i < 3; i++) {
            if (!secondUrl.equals("")) {
                break;
            }
            secondUrl = findVideoAdr(driver);
        }
        driver.close();
        return secondUrl;
    }

    public static String findVideoAdr(WebDriver driver) {
        WebElement element = driver.findElement(By.xpath("//video"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).contextClick().perform();
        //div[@class="con-7"]//li/a[text()="视频地址"]
        WebElement addr = driver.findElement(By.xpath("//div[@class=\"con-7\"]//li/a[text()=\"视频地址\"]"));
        actions.click(addr).perform();
        WebElement adrURL = driver.findElement(By.xpath("//div[@class=\"ipt\"]/input"));
        return adrURL.getAttribute("value");
    }

}
