package seedu.triplog.model;

import javafx.collections.ObservableList;
import seedu.triplog.model.person.Trip;

/**
 * Unmodifiable view of a trip log
 */
public interface ReadOnlyTripLog {

    /**
     * Returns an unmodifiable view of the trips list.
     * This list will not contain any duplicate trips.
     */
    ObservableList<Trip> getTripList();

}
