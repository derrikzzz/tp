package seedu.triplog.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.triplog.testutil.Assert.assertThrows;
import static seedu.triplog.testutil.TypicalTrips.ALICE;
import static seedu.triplog.testutil.TypicalTrips.HOON;
import static seedu.triplog.testutil.TypicalTrips.IDA;
import static seedu.triplog.testutil.TypicalTrips.getTypicalTripLog;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.triplog.commons.exceptions.DataLoadingException;
import seedu.triplog.model.ReadOnlyTripLog;
import seedu.triplog.model.TripLog;

public class JsonTripLogStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonTripLogStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readTripLog_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readTripLog(null));
    }

    private Optional<ReadOnlyTripLog> readTripLog(String filePath) throws Exception {
        return new JsonTripLogStorage(Paths.get(filePath != null ? filePath : "dummy.json"))
                .readTripLog(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readTripLog("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataLoadingException.class, () -> readTripLog("notJsonFormatTripLog.json"));
    }

    @Test
    public void readTripLog_invalidTripTripLog_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readTripLog("invalidTripTripLog.json"));
    }

    @Test
    public void readTripLog_invalidAndValidTripTripLog_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readTripLog("invalidAndValidTripTripLog.json"));
    }

    @Test
    public void readAndSaveTripLog_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempTripLog.json");
        TripLog original = getTypicalTripLog();
        JsonTripLogStorage jsonTripLogStorage = new JsonTripLogStorage(filePath);

        // Save in new file and read back
        jsonTripLogStorage.saveTripLog(original, filePath);
        ReadOnlyTripLog readBack = jsonTripLogStorage.readTripLog(filePath).get();
        assertEquals(original, new TripLog(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addTrip(HOON);
        original.removeTrip(ALICE);
        jsonTripLogStorage.saveTripLog(original, filePath);
        readBack = jsonTripLogStorage.readTripLog(filePath).get();
        assertEquals(original, new TripLog(readBack));

        // Save and read without specifying file path
        original.addTrip(IDA);
        jsonTripLogStorage.saveTripLog(original);
        readBack = jsonTripLogStorage.readTripLog().get();
        assertEquals(original, new TripLog(readBack));
    }

    @Test
    public void saveTripLog_nullTripLog_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveTripLog(null, "SomeFile.json"));
    }

    private void saveTripLog(ReadOnlyTripLog tripLog, String filePath) {
        try {
            new JsonTripLogStorage(Paths.get(filePath))
                    .saveTripLog(tripLog, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveTripLog_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveTripLog(new TripLog(), null));
    }
}
