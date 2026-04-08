package com.dlsc.preferencesfx.util;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import com.dlsc.preferencesfx.model.Category;
import com.dlsc.preferencesfx.model.PreferencesFxModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for {@link SearchHandler}.
 *
 * @author François Martin
 * @author Marco Sanfratello
 */
class SearchHandlerTest {

  SearchHandler searchHandler;
  PreferencesFxModel mockModel;

  @BeforeEach
  void setUp() {
    mockModel = mock(PreferencesFxModel.class);
    searchHandler = new SearchHandler();
    // TODO: call init() on searchHandler
  }

  @Test
  void init() {
  }

  @Test
  void initializeSearchText() {
  }

  @Test
  void bindFilterPredicate() {
  }

  @Test
  void updateSearch() {
  }

  @Test
  void compareMatches() {
    Category setting = mock(Category.class);
    Category group = mock(Category.class);
    Category category = mock(Category.class);
    int[] matches1 = {0, 0, 0, 0, 0, 0, 2, 2, 2, 3, 3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3};
    int[] matches2 = {0, 2, 3, 0, 3, 0, 0, 2, 3, 0, 2, 3, 0, 3, 1, 2, 3, 1, 1, 2, 1, 2, 3, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 2, 0, 0, 1, 2, 3, 0, 1, 2, 3, 0, 1, 2, 3, 0, 1, 2, 3, 0, 2, 3, 0, 2, 3, 0, 2, 3, 0, 3, 0, 3};
    int[] matches3 = {1, 1, 1, 2, 2, 3, 1, 1, 1, 1, 1, 1, 2, 2, 0, 0, 0, 1, 2, 2, 3, 3, 3, 0, 1, 2, 3, 0, 0, 1, 2, 2, 3, 3, 0, 0, 0, 0, 0, 1, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3, 0, 0, 0, 2, 2, 2, 3, 3, 3, 0, 0, 3, 3};
    Object[] expected = {category, category, category, category, category, category, category, category, category, category, category, category, category, category, group, group, group, group, group, group, group, group, group, group, group, group, group, group, group, group, group, group, group, group, null, setting, setting, setting, setting, setting, setting, setting, setting, setting, setting, setting, setting, setting, setting, setting, setting, setting, setting, setting, setting, setting, setting, setting, setting, setting, setting, setting, setting, setting};
    for (int i = 0; i < matches1.length; i++) {
      assertSame(
          expected[i],
          searchHandler.compareMatches(setting, group, category, matches1[i], matches2[i], matches3[i]),
          "match1: " + matches1[i] + ", match2: " + matches2[i] + ", match3: " + matches3[i]
      );
    }
  }

  @Test
  void getCategoryMatch() {
  }

  @Test
  void categoryMatchProperty() {
  }

  @Test
  void init1() {
  }

  @Test
  void initializeSearchText1() {
  }

  @Test
  void bindFilterPredicate1() {
  }

  @Test
  void updateSearch1() {
  }

  @Test
  void compareMatches1() {
  }
}
