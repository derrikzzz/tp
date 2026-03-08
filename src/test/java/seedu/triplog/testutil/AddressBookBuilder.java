package seedu.triplog.testutil;

import seedu.triplog.model.TripLog;
import seedu.triplog.model.person.Trip;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code TripLog ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private TripLog tripLog;

    public AddressBookBuilder() {
        tripLog = new TripLog();
    }

    public AddressBookBuilder(TripLog tripLog) {
        this.tripLog = tripLog;
    }

    /**
     * Adds a new {@code Trip} to the {@code TripLog} that we are building.
     */
    public AddressBookBuilder withPerson(Trip trip) {
        tripLog.addTrip(trip);
        return this;
    }

    public TripLog build() {
        return tripLog;
    }
}
