package seedu.triplog.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.triplog.model.Model;
import seedu.triplog.model.TripLog;

/**
 * Clears the trip log.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Trip log has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setTripLog(new TripLog());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
