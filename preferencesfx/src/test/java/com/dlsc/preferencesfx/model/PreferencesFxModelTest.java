package com.dlsc.preferencesfx.model;

import com.dlsc.preferencesfx.util.SearchHandler;
import com.dlsc.preferencesfx.util.StorageHandlerImpl;
import com.dlsc.preferencesfx.history.History;
import javafx.beans.property.BooleanProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.assertj.core.api.BDDAssertions.*;

/**
 * Test class for {@link PreferencesFxModel}.
 *
 * @author François Martin
 * @author Marco Sanfratello
 */
@DisplayName("PreferencesFxModel Tests")
class PreferencesFxModelTest {

  private PreferencesFxModel model;
  private Category testCategory;

  @BeforeEach
  void setUp() throws Exception {
    testCategory = Category.of("Test Category");
    model = new PreferencesFxModel(
        new StorageHandlerImpl(PreferencesFxModelTest.class),
        new SearchHandler(),
        new History(),
        new Category[]{testCategory}
    );
  }

  @Test
  @DisplayName("should load setting values")
  void load_setting_values() {
  }

  @Test
  @DisplayName("should save selected category")
  void save_selected_category() {
  }

  @Test
  @DisplayName("should load selected category")
  void load_selected_category() {
  }

  @Test
  @DisplayName("crumbs visibility should be true by default")
  void crumbs_visibility_should_be_true_by_default() {
    then(model.getCrumbsVisible()).isTrue();
  }

  @Test
  @DisplayName("crumbs visibility property should return boolean property")
  void crumbs_visibility_property_should_return_boolean_property() {
    BooleanProperty crumbsVisibleProperty = model.crumbsVisibleProperty();
    then(crumbsVisibleProperty).isNotNull();
    then(crumbsVisibleProperty.get()).isTrue();
  }

  @Test
  @DisplayName("setting crumbs visibility to false should update property")
  void setting_crumbs_visibility_to_false_should_update_property() {
    model.setCrumbsVisible(false);
    then(model.getCrumbsVisible()).isFalse();
  }

  @Test
  @DisplayName("setting crumbs visibility to true should update property")
  void setting_crumbs_visibility_to_true_should_update_property() {
    model.setCrumbsVisible(false);
    model.setCrumbsVisible(true);
    then(model.getCrumbsVisible()).isTrue();
  }

  @Test
  @DisplayName("crumbs visibility property should reflect changes")
  void crumbs_visibility_property_should_reflect_changes() {
    BooleanProperty crumbsVisibleProperty = model.crumbsVisibleProperty();
    model.setCrumbsVisible(false);
    then(crumbsVisibleProperty.get()).isFalse();
  }

  @Test
  @DisplayName("setting crumbs visibility through property should update getter")
  void setting_crumbs_visibility_through_property_should_update_getter() {
    BooleanProperty crumbsVisibleProperty = model.crumbsVisibleProperty();
    crumbsVisibleProperty.set(false);
    then(model.getCrumbsVisible()).isFalse();
  }
}


