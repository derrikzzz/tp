package seedu.triplog.ui;

import java.util.Comparator;
import java.util.Objects;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.triplog.model.trip.Trip;

/**
 * An UI component that displays information of a {@code Trip}.
 */
public class TripCard extends UiPart<Region> {

    private static final String FXML = "TripListCard.fxml";

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
     * Creates a {@code TripCard} with the given {@code Trip} and index to display.
     */
    public TripCard(Trip trip, int displayedIndex) {
        super(FXML);
        this.trip = trip;
        id.setText(displayedIndex + ". ");
        name.setText(trip.getName().fullName);

        if (!Objects.isNull(trip.getPhone())) {
            phone.setText("Phone: " + trip.getPhone().value);
        } else {
            phone.setText("");
            phone.setManaged(false);
            phone.setVisible(false);
        }

        if (!Objects.isNull(trip.getAddress())) {
            address.setText("Address: " + trip.getAddress().value);
        } else {
            address.setText("");
            address.setManaged(false);
            address.setVisible(false);
        }

        if (!Objects.isNull(trip.getEmail())) {
            email.setText("Email: " + trip.getEmail().value);
        } else {
            email.setText("");
            email.setManaged(false);
            email.setVisible(false);
        }

        if (!Objects.isNull(trip.getStartDate())) {
            startDate.setText("Start: " + trip.getStartDate().toString());
        } else {
            startDate.setText("");
            startDate.setManaged(false);
            startDate.setVisible(false);
        }

        if (!Objects.isNull(trip.getEndDate())) {
            endDate.setText("End: " + trip.getEndDate().toString());
        } else {
            endDate.setText("");
            endDate.setManaged(false);
            endDate.setVisible(false);
        }

        if (trip.getTags().isEmpty()) {
            tags.setManaged(false);
            tags.setVisible(false);
        } else {
            trip.getTags().stream()
                    .sorted(Comparator.comparing(tag -> tag.tagName))
                    .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        }


    }

    // getters mainly used for testing
    public Label getPhoneLabel() {
        return phone;
    }

    public Label getAddressLabel() {
        return address;
    }

    public Label getEmailLabel() {
        return email;
    }

    public Label getStartDateLabel() {
        return startDate;
    }

    public Label getEndDateLabel() {
        return endDate;
    }

    public FlowPane getTags() {
        return tags;
    }
}
