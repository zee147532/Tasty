package com.tasty.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.tasty.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StepToCookTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StepToCook.class);
        StepToCook stepToCook1 = new StepToCook();
        stepToCook1.setId(1L);
        StepToCook stepToCook2 = new StepToCook();
        stepToCook2.setId(stepToCook1.getId());
        assertThat(stepToCook1).isEqualTo(stepToCook2);
        stepToCook2.setId(2L);
        assertThat(stepToCook1).isNotEqualTo(stepToCook2);
        stepToCook1.setId(null);
        assertThat(stepToCook1).isNotEqualTo(stepToCook2);
    }
}
