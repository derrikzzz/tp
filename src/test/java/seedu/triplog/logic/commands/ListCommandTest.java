package seedu.triplog.logic.commands;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static seedu.triplog.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.triplog.logic.commands.CommandTestUtil.showTripAtIndex;
import seedu.triplog.model.Model;
import seedu.triplog.model.ModelManager;
import seedu.triplog.model.UserPrefs;
import seedu.triplog.model.trip.Trip;
import static seedu.triplog.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.triplog.testutil.TypicalTrips.getTypicalTripLog;

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
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showTripAtIndex(model, INDEX_FIRST_PERSON);
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsSorted_showsTripsInAscendingOrder() throws Exception {
        new ListCommand().execute(model);

        List<Trip> sorted = model.getSortedTripList();
        for (int i = 0; i < sorted.size() - 1; i++) {
            assertTrue(
                !sorted.get(i).getStartDate().value.isAfter(sorted.get(i + 1).getStartDate().value),
                "Expected trips sorted by start date ascending"
            );
        }
    }
}
