package seedu.triplog.logic.commands;

import static seedu.triplog.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.triplog.testutil.TypicalTrips.getTypicalTripLog;

import org.junit.jupiter.api.Test;

import seedu.triplog.model.Model;
import seedu.triplog.model.ModelManager;
import seedu.triplog.model.TripLog;
import seedu.triplog.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalTripLog(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalTripLog(), new UserPrefs());
        expectedModel.setTripLog(new TripLog());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
