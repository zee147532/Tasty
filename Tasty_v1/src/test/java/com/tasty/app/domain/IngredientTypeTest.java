package com.tasty.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.tasty.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class IngredientTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IngredientType.class);
        IngredientType ingredientType1 = new IngredientType();
        ingredientType1.setId(1L);
        IngredientType ingredientType2 = new IngredientType();
        ingredientType2.setId(ingredientType1.getId());
        assertThat(ingredientType1).isEqualTo(ingredientType2);
        ingredientType2.setId(2L);
        assertThat(ingredientType1).isNotEqualTo(ingredientType2);
        ingredientType1.setId(null);
        assertThat(ingredientType1).isNotEqualTo(ingredientType2);
    }
}
