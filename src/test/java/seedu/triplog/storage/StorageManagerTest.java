package seedu.triplog.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.triplog.testutil.TypicalTrips.getTypicalTripLog;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.triplog.commons.core.GuiSettings;
import seedu.triplog.model.ReadOnlyTripLog;
import seedu.triplog.model.TripLog;
import seedu.triplog.model.UserPrefs;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonTripLogStorage tripLogStorage = new JsonTripLogStorage(getTempFilePath("tl"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(tripLogStorage, userPrefsStorage);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.resolve(fileName);
    }

    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(new GuiSettings(300, 600, 4, 6));
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void tripLogReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonTripLogStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonTripLogStorageTest} class.
         */
        TripLog original = getTypicalTripLog();
        storageManager.saveTripLog(original);
        ReadOnlyTripLog retrieved = storageManager.readTripLog().get();
        assertEquals(original, new TripLog(retrieved));
    }

    @Test
    public void getTripLogFilePath() {
        assertNotNull(storageManager.getTripLogFilePath());
    }

}
