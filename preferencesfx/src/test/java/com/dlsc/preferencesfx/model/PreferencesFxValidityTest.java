package com.dlsc.preferencesfx.model;

import com.dlsc.formsfx.model.validators.IntegerRangeValidator;
import com.dlsc.preferencesfx.PreferencesFx;
import ste.commons.javafx.property.IntegerProperty;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.assertj.core.api.BDDAssertions.then;

import org.testfx.util.WaitForAsyncUtils;

@ExtendWith(ApplicationExtension.class)
class PreferencesFxValidityTest {

  private PreferencesFx preferencesFx;
  private PreferencesFxModel model;
  private IntegerProperty ageProperty;

  @Start
  void start(Stage stage) {
    ageProperty = new IntegerProperty(20);
  }

  @Test
  void valid_property_exists() throws Exception {
    WaitForAsyncUtils.asyncFx(() -> {
      preferencesFx = PreferencesFx.of(PreferencesFxValidityTest.class,
          Category.of("Test Category",
              Setting.of("Age", ageProperty)
                  .validate(IntegerRangeValidator.atLeast(18, "Must be 18+"))
          )
      );
      model = preferencesFx.preferencesFxModel;
    }).get();
    then(model.validProperty()).isNotNull();
  }

  @Test
  void is_valid_is_true_by_default() throws Exception {
    WaitForAsyncUtils.asyncFx(() -> {
      preferencesFx = PreferencesFx.of(PreferencesFxValidityTest.class,
          Category.of("Test Category",
              Setting.of("Age", ageProperty)
                  .validate(IntegerRangeValidator.atLeast(18, "Must be 18+"))
          )
      );
      model = preferencesFx.preferencesFxModel;
    }).get();
    then(model.isValid()).isTrue();
  }

  @Test
  void model_is_invalid_if_any_setting_is_invalid() throws Exception {
    IntegerProperty anotherProperty = new IntegerProperty(30);
    WaitForAsyncUtils.asyncFx(() -> {
      preferencesFx = PreferencesFx.of(PreferencesFxValidityTest.class,
          Category.of("Category 1",
              Setting.of("Age", ageProperty)
                  .validate(IntegerRangeValidator.atLeast(18, "Must be 18+"))
          ),
          Category.of("Category 2",
              Setting.of("Another", anotherProperty)
                  .validate(IntegerRangeValidator.atLeast(10, "Must be 10+"))
          )
      );
      model = preferencesFx.preferencesFxModel;
    }).get();

    then(model.isValid()).isTrue();
    WaitForAsyncUtils.asyncFx(() -> ageProperty.set(10)).get();
    then(model.isValid()).isFalse();
    WaitForAsyncUtils.asyncFx(() -> ageProperty.set(20)).get();
    then(model.isValid()).isTrue();
    WaitForAsyncUtils.asyncFx(() -> anotherProperty.set(5)).get();
    then(model.isValid()).isFalse();
    WaitForAsyncUtils.asyncFx(() -> anotherProperty.set(15)).get();
    then(model.isValid()).isTrue();
  }
}

