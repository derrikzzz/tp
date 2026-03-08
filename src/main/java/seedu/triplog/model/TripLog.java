package seedu.triplog.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.triplog.commons.util.ToStringBuilder;
import seedu.triplog.model.person.Trip;
import seedu.triplog.model.person.UniqueTripList;

/**
 * Wraps all data at the trip-log level
 * Duplicates are not allowed (by .isSameTrip comparison)
 */
public class TripLog implements ReadOnlyTripLog {

    private final UniqueTripList trips;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors.
     */
    {
        trips = new UniqueTripList();
    }

    public TripLog() {}

    /**
     * Creates a TripLog using the Trips in the {@code toBeCopied}
     */
    public TripLog(ReadOnlyTripLog toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the trip list with {@code trips}.
     * {@code trips} must not contain duplicate trips.
     */
    public void setTrips(List<Trip> trips) {
        this.trips.setTrips(trips);
    }

    /**
     * Resets the existing data of this {@code TripLog} with {@code newData}.
     */
    public void resetData(ReadOnlyTripLog newData) {
        requireNonNull(newData);

        setTrips(newData.getTripList());
    }

    //// trip-level operations

    /**
     * Returns true if a trip with the same identity as {@code trip} exists in the trip log.
     */
    public boolean hasTrip(Trip trip) {
        requireNonNull(trip);
        return trips.contains(trip);
    }

    /**
     * Adds a trip to the trip log.
     * The trip must not already exist in the trip log.
     */
    public void addTrip(Trip t) {
        trips.add(t);
    }

    /**
     * Replaces the given trip {@code target} in the list with {@code editedTrip}.
     * {@code target} must exist in the trip log.
     * The trip identity of {@code editedTrip} must not be the same as another existing trip in the trip log.
     */
    public void setTrip(Trip target, Trip editedTrip) {
        requireNonNull(editedTrip);

        trips.setTrip(target, editedTrip);
    }

    /**
     * Removes {@code key} from this {@code TripLog}.
     * {@code key} must exist in the trip log.
     */
    public void removeTrip(Trip key) {
        trips.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("trips", trips)
                .toString();
    }

    @Override
    public ObservableList<Trip> getTripList() {
        return trips.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TripLog)) {
            return false;
        }

        TripLog otherTripLog = (TripLog) other;
        return trips.equals(otherTripLog.trips);
    }

    @Override
    public int hashCode() {
        return trips.hashCode();
    }
}
