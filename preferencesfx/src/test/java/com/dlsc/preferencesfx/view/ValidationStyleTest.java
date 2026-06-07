package com.dlsc.preferencesfx.view;

import com.dlsc.formsfx.model.validators.IntegerRangeValidator;
import com.dlsc.preferencesfx.PreferencesFx;
import com.dlsc.preferencesfx.model.Category;
import com.dlsc.preferencesfx.model.Group;
import com.dlsc.preferencesfx.model.Setting;
import ste.commons.javafx.property.IntegerProperty;
import javafx.css.PseudoClass;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.assertj.core.api.BDDAssertions.then;

@ExtendWith(ApplicationExtension.class)
class ValidationStyleTest {

    private PreferencesFx preferencesFx;
    private IntegerProperty ageProperty = new IntegerProperty(20);

    @Start
    void start(Stage stage) {
        preferencesFx = PreferencesFx.of(ValidationStyleTest.class,
            Category.of("Test Category",
                Group.of("Test Group",
                    Setting.of("Age", ageProperty)
                        .validate(IntegerRangeValidator.atLeast(18, "Must be 18+"))
                )
            )
        );

        BorderPane root = new BorderPane();
        root.setCenter(preferencesFx.getView());

        Scene scene = new Scene(root, 1000, 700);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    void invalid_pseudo_class_is_applied_when_field_is_invalid(FxRobot robot) {
        // Find the control node using the style class identified in the debug output
        Node controlNode = robot.lookup(".group-title-setting-last-node").query();
        then(controlNode).isNotNull();

        robot.interact(() -> {
            // Initially it should be valid
            then(controlNode.getPseudoClassStates().contains(PseudoClass.getPseudoClass("invalid"))).isFalse();

            // Make it invalid
            ageProperty.set(10);
        });

        // Wait for potential async UI updates
        robot.sleep(250);

        // Check if :invalid pseudo-class is applied
        then(controlNode.getPseudoClassStates().contains(PseudoClass.getPseudoClass("invalid")))
            .withFailMessage("Pseudo-class :invalid should be applied to control node when field is invalid")
            .isTrue();

        // Make it valid again
        robot.interact(() -> ageProperty.set(20));

        robot.sleep(250);

        then(controlNode.getPseudoClassStates().contains(PseudoClass.getPseudoClass("invalid"))).isFalse();
    }

    @Test
    void simple_control_style_class_is_applied(FxRobot robot) {
        Node controlNode = robot.lookup(".group-title-setting-last-node").query();
        then(controlNode.getStyleClass().contains("simple-control"))
            .withFailMessage("Style class 'simple-control' should be applied to control node")
            .isTrue();
    }
}
