package com.dlsc.preferencesfx.view;

import com.dlsc.preferencesfx.PreferencesFx;
import com.dlsc.preferencesfx.model.Category;
import com.dlsc.preferencesfx.model.Group;
import com.dlsc.preferencesfx.model.Setting;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.controlsfx.control.BreadCrumbBar;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.assertj.core.api.BDDAssertions.then;

/**
 * TestFX UI integration test for crumbsVisibility feature. Tests that the
 * PreferencesFX UI correctly reflects crumbsVisibility changes.
 *
 * @author François Martin
 * @author Marco Sanfratello
 */
@ExtendWith(ApplicationExtension.class)
@DisplayName("PreferencesFx Crumbs Visibility Feature Tests")
class PreferencesFxCrumbsVisibilityTest {

    private PreferencesFx preferencesFx;

    @Start
    void start(Stage stage) {
        // Create multiple categories so breadcrumbs will be displayed
        Category generalCategory = Category.of("General",
            Group.of("Display",
                Setting.of("Theme", new SimpleBooleanProperty(false))
            )
        );

        Category advancedCategory = Category.of("Advanced",
            Group.of("Performance",
                Setting.of("Cache Enabled", new SimpleBooleanProperty(true)),
                Setting.of("Max Cache Size", new SimpleStringProperty("1024MB"))
            )
        );

        // Create PreferencesFx with multiple categories to ensure breadcrumbs are visible
        preferencesFx = PreferencesFx.of(
            PreferencesFxCrumbsVisibilityTest.class,
            generalCategory,
            advancedCategory
        );

        BorderPane root = new BorderPane();
        root.setCenter(preferencesFx.getView());

        Scene scene = new Scene(root, 1000, 700);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    @DisplayName("breadcrumb bar should be visible by default in preferences panel")
    void breadcrumb_bar_should_be_visible_by_default(FxRobot robot) {
        robot.interact(() -> {
            BreadCrumbBar<Category> breadCrumbBar = findBreadCrumbBar(robot);
            then(breadCrumbBar).isNotNull();
            then(breadCrumbBar.isVisible()).isTrue();
        });
    }

    @Test
    @DisplayName("breadcrumb bar should be hidden when crumbsVisibility is set to false")
    void breadcrumb_bar_should_be_hidden_when_crumbs_visibility_is_false(FxRobot robot) {
        robot.interact(() -> {
            BreadCrumbBar<Category> breadCrumbBar = findBreadCrumbBar(robot);

            // Set crumbsVisibility to false using fluent API
            preferencesFx.crumbsVisibility(false);

            // Verify breadcrumb bar is hidden
            then(breadCrumbBar.isVisible()).isFalse();
        });
    }

    @Test
    @DisplayName("breadcrumb bar should be visible again when crumbsVisibility is set back to true")
    void breadcrumb_bar_should_be_visible_again_when_set_to_true(FxRobot robot) {
        robot.interact(() -> {
            BreadCrumbBar<Category> breadCrumbBar = findBreadCrumbBar(robot);

            // Hide breadcrumbs
            preferencesFx.crumbsVisibility(false);
            then(breadCrumbBar.isVisible()).isFalse();

            // Show breadcrumbs again
            preferencesFx.crumbsVisibility(true);
            then(breadCrumbBar.isVisible()).isTrue();
        });
    }

    @Test
    @DisplayName("crumbsVisibility can be toggled multiple times and UI updates correctly")
    void crumbs_visibility_can_be_toggled_multiple_times(FxRobot robot) {
        robot.interact(() -> {
            BreadCrumbBar<Category> breadCrumbBar = findBreadCrumbBar(robot);

            // Toggle visibility multiple times
            for (int i = 0; i < 3; i++) {
                preferencesFx.crumbsVisibility(false);
                then(breadCrumbBar.isVisible()).isFalse();

                preferencesFx.crumbsVisibility(true);
                then(breadCrumbBar.isVisible()).isTrue();
            }
        });
    }

    @Test
    @DisplayName("crumbsVisibility in fluent API chain works correctly")
    void crumbs_visibility_in_fluent_chain_works(FxRobot robot) {
        robot.interact(() -> {
            // Create another instance and test fluent chaining
            Category cat1 = Category.of("Settings 1", Group.of("Group 1", Setting.of("Setting 1", new SimpleBooleanProperty(true))));
            Category cat2 = Category.of("Settings 2", Group.of("Group 2", Setting.of("Setting 2", new SimpleBooleanProperty(false))));

            PreferencesFx chainedPref = PreferencesFx.of(PreferencesFxCrumbsVisibilityTest.class, cat1, cat2)
                .crumbsVisibility(false)
                .instantPersistent(true)
                .crumbsVisibility(true);

            then(chainedPref).isNotNull();
        });
    }

    /**
     * Helper method to find the BreadCrumbBar using Node.lookup(). Uses a
     * generic lookup to find the BreadCrumbBar in the scene graph.
     */
    private BreadCrumbBar<Category> findBreadCrumbBar(FxRobot robot) {
        return robot.lookup(".bread-crumb-bar").query();
    }

}
