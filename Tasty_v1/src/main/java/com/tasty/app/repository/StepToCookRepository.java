package com.tasty.app.repository;

import com.tasty.app.domain.StepToCook;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the StepToCook entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StepToCookRepository extends JpaRepository<StepToCook, Long> {}
