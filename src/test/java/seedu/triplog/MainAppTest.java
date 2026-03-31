package seedu.triplog;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Method;
import java.nio.file.Path;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.triplog.commons.exceptions.DataLoadingException;
import seedu.triplog.model.ReadOnlyTripLog;
import seedu.triplog.model.UserPrefs;
import seedu.triplog.storage.JsonUserPrefsStorage;
import seedu.triplog.storage.Storage;
import seedu.triplog.storage.StorageManager;
import seedu.triplog.storage.UserPrefsStorage;

public class MainAppTest {

    @TempDir
    public Path temporaryFolder;

    @Test
    public void initModelManager_corruptedStorage_setsErrorString() throws Exception {
        // Initialize a real UserPrefsStorage to prevent NPE during StorageManager internal calls
        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("prefs.json"));

        // Create a storage stub that explicitly throws DataLoadingException
        Storage storage = new StorageManager(null, userPrefsStorage) {
            @Override
            public Optional<ReadOnlyTripLog> readTripLog() throws DataLoadingException {
                throw new DataLoadingException(new Exception("Forced failure"));
            }
            @Override
            public Path getTripLogFilePath() {
                return temporaryFolder.resolve("triplog.json");
            }
        };

        MainApp mainApp = new MainApp();

        // Use reflection to access the private initModelManager method
        Method method = MainApp.class.getDeclaredMethod("initModelManager",
                Storage.class, seedu.triplog.model.ReadOnlyUserPrefs.class);
        method.setAccessible(true);

        // Invoke the method. This triggers the 'catch (DataLoadingException e)' block in MainApp.java
        method.invoke(mainApp, storage, new UserPrefs());

        // Verify the error string was captured and set correctly
        String actualError = mainApp.initialDataLoadError;
        assertNotNull(actualError, "initialDataLoadError should not be null");
        assertTrue(actualError.contains("Corrupted entry detected"),
                "Error message should contain 'Corrupted entry detected' but was: " + actualError);
    }
}
