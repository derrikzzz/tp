package seedu.triplog.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.triplog.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.triplog.commons.exceptions.IllegalValueException;
import seedu.triplog.commons.util.JsonUtil;
import seedu.triplog.model.TripLog;
import seedu.triplog.testutil.TypicalTrips;

public class JsonSerializableTripLogTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableTripLogTest");
    private static final Path TYPICAL_TRIPS_FILE = TEST_DATA_FOLDER.resolve("typicalTripsTripLog.json");
    private static final Path INVALID_TRIP_FILE = TEST_DATA_FOLDER.resolve("invalidTripTripLog.json");
    private static final Path DUPLICATE_TRIP_FILE = TEST_DATA_FOLDER.resolve("duplicateTripTripLog.json");

    @Test
    public void toModelType_typicalTripsFile_success() throws Exception {
        Optional<JsonSerializableTripLog> dataFromFile = JsonUtil.readJsonFile(TYPICAL_TRIPS_FILE,
                JsonSerializableTripLog.class);
        TripLog tripLogFromFile = dataFromFile.get().toModelType();
        TripLog typicalTripsTripLog = TypicalTrips.getTypicalTripLog();
        assertEquals(tripLogFromFile, typicalTripsTripLog);
    }

    @Test
    public void toModelType_invalidTripFile_throwsIllegalValueException() throws Exception {
        Optional<JsonSerializableTripLog> dataFromFile = JsonUtil.readJsonFile(INVALID_TRIP_FILE,
                JsonSerializableTripLog.class);
        assertThrows(IllegalValueException.class, dataFromFile.get()::toModelType);
    }

    @Test
    public void toModelType_duplicateTrips_throwsIllegalValueException() throws Exception {
        Optional<JsonSerializableTripLog> dataFromFile = JsonUtil.readJsonFile(DUPLICATE_TRIP_FILE,
                JsonSerializableTripLog.class);
        assertThrows(IllegalValueException.class, JsonSerializableTripLog.MESSAGE_DUPLICATE_TRIP,
                dataFromFile.get()::toModelType);
    }
}
