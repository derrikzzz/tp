package seedu.triplog.ui;

import java.util.Comparator;
import java.util.Objects;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.triplog.logic.parser.ParserUtil;
import seedu.triplog.model.person.Trip;

/**
 * An UI component that displays information of a {@code Trip}.
 */
public class TripCard extends UiPart<Region> {

    private static final String FXML = "TripListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on TripLog level 4</a>
     */

    public final Trip trip;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;
    @FXML
    private Label startDate;
    @FXML
    private Label endDate;

    /**
     * Creates a {@code PersonCode} with the given {@code Trip} and index to display.
     */
    public TripCard(Trip trip, int displayedIndex) {
        super(FXML);
        this.trip = trip;
        id.setText(displayedIndex + ". ");
        name.setText(trip.getName().fullName);

        if (!Objects.isNull(trip.getPhone())) {
            phone.setText(trip.getPhone().value);
        } else {
            phone.setManaged(false);
        }

        if (!Objects.isNull(trip.getAddress())) {
            address.setText(trip.getAddress().value);
        } else {
            address.setManaged(false);
        }

        if (!Objects.isNull(trip.getEmail())) {
            email.setText(trip.getEmail().value);
        } else {
            email.setManaged(false);
        }

        if (!Objects.isNull(trip.getStartDate())) {
            startDate.setText(trip.getStartDate().value);
        } else {
            startDate.setManaged(false);
        }

        if (!Objects.isNull(trip.getEndDate())) {
            endDate.setText(trip.getEndDate().value);
        } else {
            endDate.setManaged(false);
        }

        trip.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
