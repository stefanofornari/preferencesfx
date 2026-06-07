package com.dlsc.preferencesfx.model;

import static org.assertj.core.api.BDDAssertions.then;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ste.commons.javafx.property.IntegerProperty;

/**
 * Test class for {@link Setting}.
 *
 * @author François Martin
 * @author Marco Sanfratello
 */
class SettingTest {

    @BeforeEach
    void setUp() throws Exception {
    }

    @Test
    void of_integer_object_property() {
        IntegerProperty property = new IntegerProperty(1);
        Setting setting = Setting.of("Description", property);
        then(setting).isNotNull();
        then(setting.getDescription()).isEqualTo("Description");
        then(setting.valueProperty()).isEqualTo(property);
    }

    @Test
    void of_integer_object_property_with_null() {
        IntegerProperty property = new IntegerProperty(null);
        Setting setting = Setting.of("Description", property);
        then(setting).isNotNull();
        then(setting.valueProperty().getValue()).isNull();
    }
}
