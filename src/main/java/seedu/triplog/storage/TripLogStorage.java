package seedu.triplog.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.triplog.commons.exceptions.DataLoadingException;
import seedu.triplog.model.ReadOnlyTripLog;
import seedu.triplog.model.TripLog;

/**
 * Represents a storage for {@link TripLog}.
 */
public interface TripLogStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getTripLogFilePath();

    /**
     * Returns TripLog data as a {@link ReadOnlyTripLog}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<ReadOnlyTripLog> readTripLog() throws DataLoadingException;

    /**
     * @see #getTripLogFilePath()
     */
    Optional<ReadOnlyTripLog> readTripLog(Path filePath) throws DataLoadingException;

    /**
     * Saves the given {@link ReadOnlyTripLog} to the storage.
     * @param tripLog cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveTripLog(ReadOnlyTripLog tripLog) throws IOException;

    /**
     * @see #saveTripLog(ReadOnlyTripLog)
     */
    void saveTripLog(ReadOnlyTripLog tripLog, Path filePath) throws IOException;

}
