package seedu.triplog.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.triplog.logic.parser.Prefix;
import seedu.triplog.model.trip.Trip;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_TRIP_DISPLAYED_INDEX = "The trip index provided is invalid";
    public static final String MESSAGE_TRIPS_LISTED_OVERVIEW = "%1$d trips listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
            "Multiple values specified for the following single-valued field(s): ";

    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Formats the {@code trip} for display to the user.
     */
    public static String format(Trip trip) {
        final StringBuilder builder = new StringBuilder();
        builder.append(trip.getName())
                .append("; Phone: ")
                .append(trip.getPhone())
                .append("; Email: ")
                .append(trip.getEmail())
                .append("; Address: ")
                .append(trip.getAddress())
                .append("; Tags: ");
        trip.getTags().forEach(builder::append);
        return builder.toString();
    }

}
