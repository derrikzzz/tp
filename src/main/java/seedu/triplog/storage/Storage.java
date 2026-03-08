package seedu.triplog.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.triplog.commons.exceptions.DataLoadingException;
import seedu.triplog.model.ReadOnlyTripLog;
import seedu.triplog.model.ReadOnlyUserPrefs;
import seedu.triplog.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends TripLogStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataLoadingException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getTripLogFilePath();

    @Override
    Optional<ReadOnlyTripLog> readTripLog() throws DataLoadingException;

    @Override
    void saveTripLog(ReadOnlyTripLog tripLog) throws IOException;

}
