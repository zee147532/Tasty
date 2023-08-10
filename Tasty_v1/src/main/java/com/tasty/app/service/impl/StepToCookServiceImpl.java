package com.tasty.app.service.impl;

import com.tasty.app.domain.StepToCook;
import com.tasty.app.repository.StepToCookRepository;
import com.tasty.app.service.StepToCookService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link StepToCook}.
 */
@Service
@Transactional
public class StepToCookServiceImpl implements StepToCookService {

    private final Logger log = LoggerFactory.getLogger(StepToCookServiceImpl.class);

    private final StepToCookRepository stepToCookRepository;

    public StepToCookServiceImpl(StepToCookRepository stepToCookRepository) {
        this.stepToCookRepository = stepToCookRepository;
    }

    @Override
    public StepToCook save(StepToCook stepToCook) {
        log.debug("Request to save StepToCook : {}", stepToCook);
        return stepToCookRepository.save(stepToCook);
    }

    @Override
    public StepToCook update(StepToCook stepToCook) {
        log.debug("Request to update StepToCook : {}", stepToCook);
        return stepToCookRepository.save(stepToCook);
    }

    @Override
    public Optional<StepToCook> partialUpdate(StepToCook stepToCook) {
        log.debug("Request to partially update StepToCook : {}", stepToCook);

        return stepToCookRepository
            .findById(stepToCook.getId())
            .map(existingStepToCook -> {
                if (stepToCook.getContent() != null) {
                    existingStepToCook.setContent(stepToCook.getContent());
                }
                if (stepToCook.getIndex() != null) {
                    existingStepToCook.setIndex(stepToCook.getIndex());
                }

                return existingStepToCook;
            })
            .map(stepToCookRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StepToCook> findAll(Pageable pageable) {
        log.debug("Request to get all StepToCooks");
        return stepToCookRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StepToCook> findOne(Long id) {
        log.debug("Request to get StepToCook : {}", id);
        return stepToCookRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete StepToCook : {}", id);
        stepToCookRepository.deleteById(id);
    }
}
