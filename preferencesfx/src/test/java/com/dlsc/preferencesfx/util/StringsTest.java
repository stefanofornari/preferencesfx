package com.dlsc.preferencesfx.util;
import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;

/**
 * Test class for {@link Strings}.
 *
 * @author François Martin
 * @author Marco Sanfratello
 */
class StringsTest {
  @Test
  void testContainsIgnoreCase() {
    assertTrue(Strings.containsIgnoreCase("Favorites", ""));
    assertTrue(Strings.containsIgnoreCase("Favorites", "F"));
    assertTrue(Strings.containsIgnoreCase("Favorites", "Fa"));
    assertTrue(Strings.containsIgnoreCase("Font Size", "Fo"));
    assertTrue(Strings.containsIgnoreCase("Font Size", "fo"));
    assertTrue(Strings.containsIgnoreCase("Font Size", "fO"));
    assertTrue(Strings.containsIgnoreCase("Font Size", "nt si"));
    assertTrue(Strings.containsIgnoreCase("Scaling & Ordering", "ord"));

    assertFalse(Strings.containsIgnoreCase(null, "Fo"));
    assertFalse(Strings.containsIgnoreCase("Favorites", null));
    assertFalse(Strings.containsIgnoreCase("Favorites", "Fo"));
    assertFalse(Strings.containsIgnoreCase("Scaling & Ordering", "awdawdhwhd"));
  }

}
