package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

/**
 * Singleton manager for creating and providing
 * a shared ExtentReports instance.
 */
public final class ExtentManager {

    // =========================================================
    // FIELDS
    // =========================================================

    private static ExtentReports extent;

    private ExtentManager() {
        // Prevent instantiation
    }

    // =========================================================
    // PUBLIC METHODS
    // =========================================================

    public static ExtentReports getInstance() {

        if (extent == null) {

            String reportName =
                    "test-output/ExtentReport_"
                            + System.currentTimeMillis()
                            + ".html";

            ExtentSparkReporter reporter =
                    new ExtentSparkReporter(reportName);

            reporter.config().setReportName("Automation Test Report");

            reporter.config().setDocumentTitle("QA Automation Report");

            reporter.config().setTimeStampFormat("dd MMM yyyy HH:mm:ss");

            extent = new ExtentReports();

            extent.attachReporter(reporter);

            extent.setSystemInfo(
                    "Application",
                    "ParaBank"
            );

            extent.setSystemInfo(
                    "Environment",
                    "Demo"
            );

            extent.setSystemInfo(
                    "Browser",
                    ConfigReader.getProperty("browser")
            );

            extent.setSystemInfo(
                    "OS",
                    System.getProperty("os.name")
            );

            extent.setSystemInfo(
                    "Java Version",
                    System.getProperty("java.version")
            );

            extent.setSystemInfo(
                    "Framework",
                    "Selenium + TestNG + REST Assured"
            );
        }

        return extent;
    }
}
