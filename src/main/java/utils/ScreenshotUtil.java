package utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtil {

    private static final Logger logger =
            LoggerFactory.getLogger(api.testdata.utils.ScreenshotUtil.class);

    private static final String SCREENSHOT_FOLDER =
            "screenshots/";

    public static void captureScreenshot(WebDriver driver, String testName) {

        TakesScreenshot ts = (TakesScreenshot) driver;

        File source = ts.getScreenshotAs(OutputType.FILE);

        String timestamp =
                new SimpleDateFormat("yyyyMMdd_HHmmss")
                        .format(new Date());

        String screenshotPath =
                SCREENSHOT_FOLDER
                        + testName
                        + "_"
                        + timestamp
                        + ".png";

        File destination = new File(screenshotPath);

        try {

            FileUtils.copyFile(source, destination);

            logger.info(
                    "Screenshot saved successfully: {}",
                    screenshotPath
            );

        } catch (IOException e) {

            logger.error(
                    "Failed to save screenshot: {}",
                    screenshotPath,
                    e
            );
        }
    }
}
