package seedu.triplog.model.trip;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.triplog.testutil.Assert.assertThrows;
import static seedu.triplog.testutil.TypicalTrips.ALICE;

import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.triplog.testutil.TripBuilder;

public class TripTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Trip trip = new TripBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> trip.getTags().remove(0));
    }

    @Test
    public void isSameTrip() {
        assertTrue(ALICE.isSameTrip(ALICE));
        assertFalse(ALICE.isSameTrip(null));

        Trip editedAlice = new TripBuilder(ALICE).withPhone("91234567").build();
        assertTrue(ALICE.isSameTrip(editedAlice));

        editedAlice = new TripBuilder(ALICE).withName("Bob").build();
        assertFalse(ALICE.isSameTrip(editedAlice));
    }

    @Test
    public void equals() {
        Trip aliceCopy = new TripBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));
        assertTrue(ALICE.equals(ALICE));
        assertFalse(ALICE.equals(null));
        assertFalse(ALICE.equals(5));
    }

    @Test
    public void getDurationInDays() {
        Name name = new Name("Test");

        // Valid duration (10 days)
        TripDate start = new TripDate("2026-01-01");
        TripDate end = new TripDate("2026-01-11");
        Trip validTrip = new Trip(name, null, null, null, Collections.emptySet(), start, end);
        assertEquals(10, validTrip.getDurationInDays());

        // Coverage for null checks
        Trip nullStart = new Trip(name, null, null, null, Collections.emptySet(), null, end);
        assertEquals(-1, nullStart.getDurationInDays());

        Trip nullEnd = new Trip(name, null, null, null, Collections.emptySet(), start, null);
        assertEquals(-1, nullEnd.getDurationInDays());

        Trip bothNull = new Trip(name, null, null, null, Collections.emptySet(), null, null);
        assertEquals(-1, bothNull.getDurationInDays());
    }

    @Test
    public void constructor_startDateAfterEndDate_throwsIllegalArgumentException() {
        Name name = new Name("Test Trip");
        TripDate startDate = new TripDate("2026-12-31");
        TripDate endDate = new TripDate("2026-01-01");

        assertThrows(IllegalArgumentException.class, () ->
                new Trip(name, null, null, null, Collections.emptySet(), startDate, endDate)
        );
    }

    @Test
    public void chronologicalComparator_differentDatesAndNulls_sortedChronologically() {
        Trip janTrip = new Trip(new Name("A"), null, null, null, Collections.emptySet(),
                new TripDate("2026-01-01"), null);
        Trip febTrip = new Trip(new Name("B"), null, null, null, Collections.emptySet(),
                new TripDate("2026-02-01"), null);
        assertTrue(Trip.CHRONOLOGICAL_COMPARATOR.compare(janTrip, febTrip) < 0);

        Trip nullTrip = new Trip(new Name("C"), null, null, null, Collections.emptySet(), null, null);
        assertTrue(Trip.CHRONOLOGICAL_COMPARATOR.compare(janTrip, nullTrip) < 0);
        assertTrue(Trip.CHRONOLOGICAL_COMPARATOR.compare(nullTrip, janTrip) > 0);
    }
}
