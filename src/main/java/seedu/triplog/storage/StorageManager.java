package seedu.triplog.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.triplog.commons.core.LogsCenter;
import seedu.triplog.commons.exceptions.DataLoadingException;
import seedu.triplog.model.ReadOnlyTripLog;
import seedu.triplog.model.ReadOnlyUserPrefs;
import seedu.triplog.model.UserPrefs;

/**
 * Manages storage of TripLog data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private TripLogStorage tripLogStorage;
    private UserPrefsStorage userPrefsStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code TripLogStorage} and {@code UserPrefStorage}.
     */
    public StorageManager(TripLogStorage tripLogStorage, UserPrefsStorage userPrefsStorage) {
        this.tripLogStorage = tripLogStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataLoadingException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ TripLog methods ==============================

    @Override
    public Path getTripLogFilePath() {
        return tripLogStorage.getTripLogFilePath();
    }

    @Override
    public Optional<ReadOnlyTripLog> readTripLog() throws DataLoadingException {
        return readTripLog(tripLogStorage.getTripLogFilePath());
    }

    @Override
    public Optional<ReadOnlyTripLog> readTripLog(Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read data from file: " + filePath);
        return tripLogStorage.readTripLog(filePath);
    }

    @Override
    public void saveTripLog(ReadOnlyTripLog tripLog) throws IOException {
        saveTripLog(tripLog, tripLogStorage.getTripLogFilePath());
    }

    @Override
    public void saveTripLog(ReadOnlyTripLog tripLog, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        tripLogStorage.saveTripLog(tripLog, filePath);
    }

}
