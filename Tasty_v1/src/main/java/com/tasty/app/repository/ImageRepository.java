package com.tasty.app.repository;

import com.tasty.app.domain.Image;
import com.tasty.app.domain.enumeration.TypeOfImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ImageRepository extends JpaRepository<Image, Long> {
    @Modifying
    void deleteAllByPost_IdAndType(Long postsId, TypeOfImage type);
}
