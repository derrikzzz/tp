package seedu.triplog.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.triplog.testutil.TypicalTrips.getTypicalTripLog;

import org.junit.jupiter.api.Test;

import seedu.triplog.logic.commands.exceptions.CommandException;
import seedu.triplog.model.Model;
import seedu.triplog.model.ModelManager;
import seedu.triplog.model.UserPrefs;

public class PreviewDeleteCommandTest {

    private final Model model = new ModelManager(getTypicalTripLog(), new UserPrefs());

    @Test
    public void execute_validIndexPreview_success() throws Exception {
        PreviewDeleteCommand command = new PreviewDeleteCommand("1");
        CommandResult result = command.execute(model);

        assertEquals(
                "Preview: 1 trip will be deleted.\n"
                        + "1. Alice Pauline (2026-04-01 to 2026-04-10)\n"
                        + "\nPress Enter again to confirm deletion, or edit the command to cancel.",
                result.getFeedbackToUser());
    }

    @Test
    public void execute_validDateRangePreview_success() throws Exception {
        PreviewDeleteCommand command = new PreviewDeleteCommand("sd/2026-03-01 ed/2026-05-10");
        CommandResult result = command.execute(model);

        assertEquals(
                "Preview: 3 trips will be deleted.\n"
                        + "1. Alice Pauline (2026-04-01 to 2026-04-10)\n"
                        + "2. Elle Meyer (2026-05-01 to 2026-05-10)\n"
                        + "3. Fiona Kunz (2026-03-01 to 2026-03-10)\n"
                        + "\nPress Enter again to confirm deletion, or edit the command to cancel.",
                result.getFeedbackToUser());
    }

    @Test
    public void execute_invalidDeleteArgs_throwsCommandException() {
        PreviewDeleteCommand command = new PreviewDeleteCommand("abc");

        assertThrows(CommandException.class, () -> command.execute(model));
    }

    @Test
    public void execute_noMatchingTrips_throwsCommandException() {
        PreviewDeleteCommand command = new PreviewDeleteCommand("n/Nonexistent");

        CommandException e = assertThrows(CommandException.class, () -> command.execute(model));
        assertEquals(DeleteCommand.MESSAGE_NO_MATCHING_TRIPS, e.getMessage());
    }
}
