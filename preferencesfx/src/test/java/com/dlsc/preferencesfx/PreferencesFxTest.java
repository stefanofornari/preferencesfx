package com.dlsc.preferencesfx;

import com.dlsc.preferencesfx.model.Category;
import com.dlsc.preferencesfx.model.Group;
import com.dlsc.preferencesfx.model.Setting;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.assertj.core.api.BDDAssertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

/**
 *
 */
@ExtendWith(ApplicationExtension.class)
public class PreferencesFxTest {

    private PreferencesFx preferencesFx;

    @Test
    @DisplayName("model is initialized at creation")
    void initialize_model_at_creation() {
        then(preferencesFx.preferencesFxModel).isNotNull();
        then(preferencesFx.preferencesFxModel.getCategories()).hasSize(2);
    }

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

        preferencesFx = PreferencesFx.of(
            PreferencesFxTest.class,
            generalCategory,
            advancedCategory
        );

        BorderPane root = new BorderPane();
        root.setCenter(preferencesFx.getView());

        Scene scene = new Scene(root, 1000, 700);
        stage.setScene(scene);
        stage.show();
    }

}
