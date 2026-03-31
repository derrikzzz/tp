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
        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("prefs.json"));

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
        Method method = MainApp.class.getDeclaredMethod("initModelManager",
                Storage.class, seedu.triplog.model.ReadOnlyUserPrefs.class);
        method.setAccessible(true);
        method.invoke(mainApp, storage, new UserPrefs());

        assertNotNull(mainApp.initialDataLoadError);
        assertTrue(mainApp.initialDataLoadError.contains("Corrupted entry detected"));
    }
}
