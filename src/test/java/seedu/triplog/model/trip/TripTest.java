package seedu.triplog.model.trip;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.triplog.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.triplog.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.triplog.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.triplog.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.triplog.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.triplog.testutil.Assert.assertThrows;
import static seedu.triplog.testutil.TypicalTrips.ALICE;
import static seedu.triplog.testutil.TypicalTrips.BOB;

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

        Trip editedAlice = new TripBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameTrip(editedAlice));

        editedAlice = new TripBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSameTrip(editedAlice));
    }

    @Test
    public void equals() {
        Trip aliceCopy = new TripBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));
        assertTrue(ALICE.equals(ALICE));
        assertFalse(ALICE.equals(null));
        assertFalse(ALICE.equals(5));
        assertFalse(ALICE.equals(BOB));
    }

    @Test
    public void hashCode_test() {
        Trip aliceCopy = new TripBuilder(ALICE).build();
        assertEquals(ALICE.hashCode(), aliceCopy.hashCode());
        assertNotEquals(ALICE.hashCode(), BOB.hashCode());
    }

    @Test
    public void toStringMethod() {
        String expected = Trip.class.getCanonicalName() + "{name=" + ALICE.getName() + ", phone=" + ALICE.getPhone()
                + ", email=" + ALICE.getEmail() + ", address=" + ALICE.getAddress()
                + ", startDate=" + ALICE.getStartDate() + ", endDate=" + ALICE.getEndDate()
                + ", tags=" + ALICE.getTags() + "}";
        assertEquals(expected, ALICE.toString());
    }

    @Test
    public void getDurationInDays() {
        Name name = new Name("Test");

        // Valid duration
        TripDate start = new TripDate("2026-01-01");
        TripDate end = new TripDate("2026-01-11");
        Trip validTrip = new Trip(name, null, null, null, Collections.emptySet(), start, end);
        assertEquals(10, validTrip.getDurationInDays());

        // Null start date
        Trip nullStart = new Trip(name, null, null, null, Collections.emptySet(), null, end);
        assertEquals(-1, nullStart.getDurationInDays());

        // Null end date
        Trip nullEnd = new Trip(name, null, null, null, Collections.emptySet(), start, null);
        assertEquals(-1, nullEnd.getDurationInDays());

        // Both null
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
    public void constructor_startDateEqualsEndDate_noException() {
        Name name = new Name("Test Trip");
        TripDate startDate = new TripDate("2026-01-01");
        TripDate endDate = new TripDate("2026-01-01");
        new Trip(name, null, null, null, Collections.emptySet(), startDate, endDate);
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
