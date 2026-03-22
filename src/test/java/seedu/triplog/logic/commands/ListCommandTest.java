package seedu.triplog.logic.commands;

import static seedu.triplog.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.triplog.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.triplog.logic.commands.CommandTestUtil.showTripAtIndex;
import static seedu.triplog.testutil.TypicalIndexes.INDEX_FIRST_TRIP;
import static seedu.triplog.testutil.TypicalTrips.getTypicalTripLog;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.triplog.model.Model;
import seedu.triplog.model.ModelManager;
import seedu.triplog.model.UserPrefs;
import seedu.triplog.model.trip.Trip;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalTripLog(), new UserPrefs());
        expectedModel = new ModelManager(model.getTripLog(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        String expectedSummary = calculateExpectedSummary(expectedModel.getFilteredTripList());
        String expectedMessage = String.format(ListCommand.MESSAGE_SUCCESS, "start date", expectedSummary);
        assertCommandSuccess(new ListCommand(), model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showTripAtIndex(model, INDEX_FIRST_TRIP);
        String expectedSummary = calculateExpectedSummary(expectedModel.getFilteredTripList());
        String expectedMessage = String.format(ListCommand.MESSAGE_SUCCESS, "start date", expectedSummary);
        assertCommandSuccess(new ListCommand(), model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_sortBySortName_success() {
        String expectedSummary = calculateExpectedSummary(expectedModel.getFilteredTripList());
        String expectedMessage = String.format(ListCommand.MESSAGE_SUCCESS, "name (alphabetical)", expectedSummary);
        assertCommandSuccess(new ListCommand("name"), model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_sortBySortLen_success() {
        String expectedSummary = calculateExpectedSummary(expectedModel.getFilteredTripList());
        String expectedMessage = String.format(ListCommand.MESSAGE_SUCCESS,
                "duration (longest first)", expectedSummary);
        assertCommandSuccess(new ListCommand("len"), model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidSortKey_throwsCommandException() {
        assertCommandFailure(new ListCommand("price"), model, ListCommand.MESSAGE_INVALID_SORT_KEY);
    }

    /**
     * Helper method to calculate the expected summary based on the current system date.
     */
    private String calculateExpectedSummary(ObservableList<Trip> trips) {
        int upcoming = 0;
        int ongoing = 0;
        int completed = 0;
        int planning = 0;
        LocalDate today = LocalDate.now();

        for (Trip trip : trips) {
            if (trip.getStartDate() == null) {
                planning++;
                continue;
            }

            LocalDate start = trip.getStartDate().value;
            LocalDate end = (trip.getEndDate() == null) ? null : trip.getEndDate().value;

            if (today.isBefore(start)) {
                upcoming++;
            } else if (end != null && today.isAfter(end)) {
                completed++;
            } else {
                ongoing++;
            }
        }
        return String.format("Summary: %d Upcoming, %d Ongoing, %d Completed, %d Planning",
                upcoming, ongoing, completed, planning);
    }
}
