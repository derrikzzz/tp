package seedu.triplog.ui;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import seedu.triplog.model.trip.Name;
import seedu.triplog.model.trip.Trip;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(ApplicationExtension.class)
public class TripCardTest {
    @Test
    public void tripCard_optionalFieldsNull_noExceptionsAndCorrectManaged() {
        Trip tripWithNulls = new Trip(
                new Name("Bali Trip"),
                null,           // phone
                null,           // email
                null,           // address
                Collections.emptySet(),
                null,           // startDate
                null            // endDate
        );

        TripCard tripCard = new TripCard(tripWithNulls, 1);

        // check that the text for optional fields is not set
        assertEquals("", tripCard.getPhoneLabel().getText());
        assertEquals("", tripCard.getAddressLabel().getText());
        assertEquals("", tripCard.getEmailLabel().getText());
        assertEquals("", tripCard.getStartDateLabel().getText());
        assertEquals("", tripCard.getEndDateLabel().getText());

        // check that the nodes are unmanaged
        assertFalse(tripCard.getPhoneLabel().isManaged());
        assertFalse(tripCard.getAddressLabel().isManaged());
        assertFalse(tripCard.getEmailLabel().isManaged());
        assertFalse(tripCard.getStartDateLabel().isManaged());
        assertFalse(tripCard.getEndDateLabel().isManaged());
    }
}
