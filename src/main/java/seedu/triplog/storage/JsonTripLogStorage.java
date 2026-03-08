package seedu.triplog.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.triplog.commons.core.LogsCenter;
import seedu.triplog.commons.exceptions.DataLoadingException;
import seedu.triplog.commons.exceptions.IllegalValueException;
import seedu.triplog.commons.util.FileUtil;
import seedu.triplog.commons.util.JsonUtil;
import seedu.triplog.model.ReadOnlyTripLog;

/**
 * A class to access TripLog data stored as a json file on the hard disk.
 */
public class JsonTripLogStorage implements TripLogStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonTripLogStorage.class);

    private Path filePath;

    public JsonTripLogStorage(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Path getTripLogFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyTripLog> readTripLog() throws DataLoadingException {
        return readTripLog(filePath);
    }

    @Override
    public Optional<ReadOnlyTripLog> readTripLog(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        Optional<JsonSerializableTripLog> jsonTripLog;
        try {
            jsonTripLog = JsonUtil.readJsonFile(filePath, JsonSerializableTripLog.class);
        } catch (DataLoadingException e) {
            throw e;
        }

        if (jsonTripLog.isEmpty()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonTripLog.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }

    @Override
    public void saveTripLog(ReadOnlyTripLog tripLog) throws IOException {
        saveTripLog(tripLog, filePath);
    }

    @Override
    public void saveTripLog(ReadOnlyTripLog tripLog, Path filePath) throws IOException {
        requireNonNull(tripLog);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableTripLog(tripLog), filePath);
    }
}
