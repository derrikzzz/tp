package seedu.triplog.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.triplog.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.triplog.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.triplog.testutil.Assert.assertThrows;
import static seedu.triplog.testutil.TypicalTrips.ALICE;
import static seedu.triplog.testutil.TypicalTrips.getTypicalTripLog;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.triplog.model.trip.Trip;
import seedu.triplog.model.trip.exceptions.DuplicateTripException;
import seedu.triplog.testutil.TripBuilder;

public class TripLogTest {

    private final TripLog tripLog = new TripLog();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), tripLog.getTripList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> tripLog.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyTripLog_replacesData() {
        TripLog newData = getTypicalTripLog();
        tripLog.resetData(newData);
        assertEquals(newData, tripLog);
    }

    @Test
    public void resetData_withDuplicateTrips_throwsDuplicateTripException() {
        // Two trips with the same identity fields
        Trip editedAlice = new TripBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Trip> newTrips = Arrays.asList(ALICE, editedAlice);
        TripLogStub newData = new TripLogStub(newTrips);

        assertThrows(DuplicateTripException.class, () -> tripLog.resetData(newData));
    }

    @Test
    public void hasTrip_nullTrip_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> tripLog.hasTrip(null));
    }

    @Test
    public void hasTrip_tripNotInTripLog_returnsFalse() {
        assertFalse(tripLog.hasTrip(ALICE));
    }

    @Test
    public void hasTrip_tripInTripLog_returnsTrue() {
        tripLog.addTrip(ALICE);
        assertTrue(tripLog.hasTrip(ALICE));
    }

    @Test
    public void hasTrip_tripWithSameIdentityFieldsInTripLog_returnsTrue() {
        tripLog.addTrip(ALICE);
        Trip editedAlice = new TripBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(tripLog.hasTrip(editedAlice));
    }

    @Test
    public void getTripList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> tripLog.getTripList().remove(0));
    }

    @Test
    public void toStringMethod() {
        String expected = TripLog.class.getCanonicalName() + "{trips=" + tripLog.getTripList() + "}";
        assertEquals(expected, tripLog.toString());
    }

    /**
     * A stub ReadOnlyTripLog whose trips list can violate interface constraints.
     */
    private static class TripLogStub implements ReadOnlyTripLog {
        private final ObservableList<Trip> trips = FXCollections.observableArrayList();

        TripLogStub(Collection<Trip> trips) {
            this.trips.setAll(trips);
        }

        @Override
        public ObservableList<Trip> getTripList() {
            return trips;
        }
    }

}
