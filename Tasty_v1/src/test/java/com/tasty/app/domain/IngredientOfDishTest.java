package com.tasty.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.tasty.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class IngredientOfDishTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IngredientOfDish.class);
        IngredientOfDish ingredientOfDish1 = new IngredientOfDish();
        ingredientOfDish1.setId(1L);
        IngredientOfDish ingredientOfDish2 = new IngredientOfDish();
        ingredientOfDish2.setId(ingredientOfDish1.getId());
        assertThat(ingredientOfDish1).isEqualTo(ingredientOfDish2);
        ingredientOfDish2.setId(2L);
        assertThat(ingredientOfDish1).isNotEqualTo(ingredientOfDish2);
        ingredientOfDish1.setId(null);
        assertThat(ingredientOfDish1).isNotEqualTo(ingredientOfDish2);
    }
}
