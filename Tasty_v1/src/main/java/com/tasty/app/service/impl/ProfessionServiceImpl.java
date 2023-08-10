package com.tasty.app.service.impl;

import com.tasty.app.domain.Profession;
import com.tasty.app.repository.ProfessionRepository;
import com.tasty.app.service.ProfessionService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Profession}.
 */
@Service
@Transactional
public class ProfessionServiceImpl implements ProfessionService {

    private final Logger log = LoggerFactory.getLogger(ProfessionServiceImpl.class);

    private final ProfessionRepository professionRepository;

    public ProfessionServiceImpl(ProfessionRepository professionRepository) {
        this.professionRepository = professionRepository;
    }

    @Override
    public Profession save(Profession profession) {
        log.debug("Request to save Profession : {}", profession);
        return professionRepository.save(profession);
    }

    @Override
    public Profession update(Profession profession) {
        log.debug("Request to update Profession : {}", profession);
        return professionRepository.save(profession);
    }

    @Override
    public Optional<Profession> partialUpdate(Profession profession) {
        log.debug("Request to partially update Profession : {}", profession);

        return professionRepository
            .findById(profession.getId())
            .map(existingProfession -> {
                if (profession.getName() != null) {
                    existingProfession.setName(profession.getName());
                }

                return existingProfession;
            })
            .map(professionRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Profession> findAll(Pageable pageable) {
        log.debug("Request to get all Professions");
        return professionRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Profession> findOne(Long id) {
        log.debug("Request to get Profession : {}", id);
        return professionRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Profession : {}", id);
        professionRepository.deleteById(id);
    }
}
