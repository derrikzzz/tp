package seedu.triplog.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.triplog.commons.util.AppUtil.checkArgument;

public class TripDate {
    public static final String MESSAGE_CONSTRAINTS =
            "Dates should be in YYYY-MM-DD format";

    public static final String VALIDATION_REGEX = "\\d{4}-\\d{2}-\\d{2}";

    public final String value;

    /**
     * Constructs a {@code TripDate}.
     *
     * @param date A valid date.
     */
    public TripDate(String date) {
        requireNonNull(date);
        checkArgument(isValidDate(date), MESSAGE_CONSTRAINTS);
        this.value = date;
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidDate(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof TripDate)) {
            return false;
        }

        TripDate otherDate = (TripDate) other;
        return value.equals(otherDate.value);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
