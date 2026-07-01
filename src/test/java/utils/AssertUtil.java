package utils;

import org.testng.Assert;

/**
 * Utility class for common test assertions.
 */
public final class AssertUtil {

    // =========================================================
    // PUBLIC METHODS
    // =========================================================

    private AssertUtil() {
        // Prevent instantiation
    }

    /**
     * Verify that a field value is neither null nor blank.
     *
     * @param actualValue Actual field value.
     * @param fieldName Field name used in assertion messages.
     */
    public static void assertFieldIsNotBlank(
            String actualValue,
            String fieldName
    ) {

        Assert.assertNotNull(
                actualValue,
                fieldName + " should not be null"
        );

        Assert.assertFalse(
                actualValue.trim().isEmpty(),
                fieldName + " should not be empty"
        );
    }
}