package seedu.triplog.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.triplog.commons.exceptions.IllegalValueException;
import seedu.triplog.model.ReadOnlyTripLog;
import seedu.triplog.model.TripLog;
import seedu.triplog.model.trip.Trip;

/**
 * An Immutable TripLog that is serializable to JSON format.
 */
public class JsonSerializableTripLog {

    public static final String MESSAGE_DUPLICATE_TRIP = "Trips list contains duplicate trip(s).";

    private final List<JsonAdaptedTrip> trips = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableTripLog} with the given trips.
     */
    @JsonCreator
    public JsonSerializableTripLog(@JsonProperty("trips") List<JsonAdaptedTrip> trips) {
        if (trips != null) {
            this.trips.addAll(trips);
        }
    }

    /**
     * Converts a given {@code ReadOnlyTripLog} into this class for Jackson use.
     */
    public JsonSerializableTripLog(ReadOnlyTripLog source) {
        trips.addAll(source.getTripList().stream().map(JsonAdaptedTrip::new).collect(Collectors.toList()));
    }

    /**
     * Converts this trip log into the model's {@code TripLog} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public TripLog toModelType() throws IllegalValueException {
        TripLog tripLog = new TripLog();
        for (JsonAdaptedTrip jsonAdaptedTrip : trips) {
            Trip trip = jsonAdaptedTrip.toModelType();
            if (tripLog.hasTrip(trip)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_TRIP);
            }
            tripLog.addTrip(trip);
        }
        return tripLog;
    }

}
