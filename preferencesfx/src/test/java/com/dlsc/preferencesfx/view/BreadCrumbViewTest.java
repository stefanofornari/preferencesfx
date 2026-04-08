package com.dlsc.preferencesfx.view;

import com.dlsc.preferencesfx.model.Category;
import com.dlsc.preferencesfx.model.PreferencesFxModel;
import com.dlsc.preferencesfx.util.SearchHandler;
import com.dlsc.preferencesfx.util.StorageHandlerImpl;
import com.dlsc.preferencesfx.history.History;
import java.util.concurrent.TimeUnit;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.testfx.util.WaitForAsyncUtils.waitFor;
import static org.assertj.core.api.BDDAssertions.then;

/**
 * TestFX UI test for {@link BreadCrumbView} breadcrumb visibility.
 * Tests the crumbsVisibility feature at the UI level using actual rendering.
 *
 * @author François Martin
 * @author Marco Sanfratello
 */
@ExtendWith(ApplicationExtension.class)
@DisplayName("BreadCrumb View UI Tests")
class BreadCrumbViewTest {

  private PreferencesFxModel model;
  private BreadCrumbView breadCrumbView;
  private Category categoryOne;
  private Category categoryTwo;

  @Start
  void start(Stage stage) {
    // Create two categories for testing (one category layout hides breadcrumbs)
    categoryOne = Category.of("Category One");
    categoryTwo = Category.of("Category Two");
    
    model = new PreferencesFxModel(
        new StorageHandlerImpl(BreadCrumbViewTest.class),
        new SearchHandler(),
        new History(),
        new Category[]{categoryOne, categoryTwo}
    );

    UndoRedoBox undoRedoBox = new UndoRedoBox(model.getHistory());
    breadCrumbView = new BreadCrumbView(model, undoRedoBox);

    StackPane root = new StackPane(breadCrumbView);
    Scene scene = new Scene(root, 800, 600);
    stage.setScene(scene);
    stage.show();
  }

  @Test
  @DisplayName("breadcrumb bar should be visible by default")
  void breadcrumb_bar_should_be_visible_by_default(FxRobot robot) {
    robot.interact(() -> {
      then(breadCrumbView.breadCrumbBar.isVisible()).isTrue();
    });
  }

  @Test
  @DisplayName("breadcrumb bar should be hidden when crumbs visibility is set to false")
  void breadcrumb_bar_should_be_hidden_when_crumbs_visibility_is_false(FxRobot robot) {
    robot.interact(() -> {
      model.setCrumbsVisible(false);
      then(breadCrumbView.breadCrumbBar.isVisible()).isFalse();
    });
  }

  @Test
  @DisplayName("breadcrumb bar should be visible again when crumbs visibility is toggled to true")
  void breadcrumb_bar_should_be_visible_when_crumbs_visibility_is_toggled_back_to_true(FxRobot robot) {
    robot.interact(() -> {
      // Initially visible
      then(breadCrumbView.breadCrumbBar.isVisible()).isTrue();
      
      // Hide breadcrumbs
      model.setCrumbsVisible(false);
      then(breadCrumbView.breadCrumbBar.isVisible()).isFalse();
      
      // Show breadcrumbs again
      model.setCrumbsVisible(true);
      then(breadCrumbView.breadCrumbBar.isVisible()).isTrue();
    });
  }

  @Test
  @DisplayName("breadcrumb visibility should respond to property changes")
  void breadcrumb_visibility_should_respond_to_property_changes(FxRobot robot) {
    robot.interact(() -> {
      // Change through property
      model.crumbsVisibleProperty().set(false);
      then(breadCrumbView.breadCrumbBar.isVisible()).isFalse();
      
      // Change back through property
      model.crumbsVisibleProperty().set(true);
      then(breadCrumbView.breadCrumbBar.isVisible()).isTrue();
    });
  }

  @Test
  @DisplayName("breadcrumb bar should maintain visibility state through multiple toggles")
  void breadcrumb_bar_should_maintain_visibility_state_through_multiple_toggles(FxRobot robot) {
    robot.interact(() -> {
      // Toggle multiple times
      for (int i = 0; i < 5; i++) {
        model.setCrumbsVisible(false);
        then(breadCrumbView.breadCrumbBar.isVisible()).isFalse();
        
        model.setCrumbsVisible(true);
        then(breadCrumbView.breadCrumbBar.isVisible()).isTrue();
      }
    });
  }

  @Test
  @DisplayName("breadcrumb bar visibility property should be bound to model")
  void breadcrumb_bar_visibility_property_should_be_bound_to_model(FxRobot robot) {
    robot.interact(() -> {
      // Check that property is bound
      then(breadCrumbView.breadCrumbBar.visibleProperty().isBound()).isTrue();
      
      // Verify binding works by changing model
      model.setCrumbsVisible(false);
      then(breadCrumbView.breadCrumbBar.visibleProperty().get()).isFalse();
      
      model.setCrumbsVisible(true);
      then(breadCrumbView.breadCrumbBar.visibleProperty().get()).isTrue();
    });
  }
}
