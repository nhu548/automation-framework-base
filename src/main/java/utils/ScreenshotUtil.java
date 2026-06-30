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

/**
 * Utility class for capturing screenshots.
 * Used mainly when a test fails so the screenshot
 * can be attached to the test report.
 */
public final class ScreenshotUtil {

    // =========================================================
    // CONSTANTS
    // =========================================================

    // Logger for recording success or failure messages
    private static final Logger logger =
            LoggerFactory.getLogger(ScreenshotUtil.class);

    // Folder where screenshots will be stored
    private static final String SCREENSHOT_FOLDER = "test-output/screenshots/";

    private ScreenshotUtil() {
        // Prevent instantiation
    }

    // =========================================================
    // PUBLIC METHODS
    // =========================================================

    /**
     * Capture the current browser screen.
     *
     * @param driver Current WebDriver instance.
     * @param testName Current test name.
     * @return Relative screenshot path if successful; otherwise {@code null}.
     */
    public static String captureScreenshot(WebDriver driver, String testName) {

        ensureScreenshotFolderExists();

        // Generate a unique timestamp to avoid duplicate file names
        String timestamp =
                new SimpleDateFormat("yyyyMMdd_HHmmss")
                        .format(new Date());

        String fileName =
                testName + "_" + timestamp + ".png";

        String screenshotPath =
                SCREENSHOT_FOLDER + fileName;

        try {

            TakesScreenshot ts = (TakesScreenshot) driver;

            // Take a screenshot from the current browser
            File source = ts.getScreenshotAs(OutputType.FILE);

            // Destination file
            File destination = new File(screenshotPath);

            // Save screenshot to local folder
            FileUtils.copyFile(source, destination);

            logger.info("Screenshot saved successfully: {}", screenshotPath);

            return "screenshots/" + fileName;

        } catch (IOException e) {

            logger.error("Failed to save screenshot: {}", screenshotPath, e);

            return null;
        }
    }

    // =========================================================
    // PRIVATE HELPERS
    // =========================================================
    /**
     * Create screenshot folder if it does not exist.
     */
    private static void ensureScreenshotFolderExists() {

        File folder =
                new File(SCREENSHOT_FOLDER);

        if (!folder.exists()) {
            folder.mkdirs();
        }
    }
}
