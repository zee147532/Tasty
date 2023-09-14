package com.tasty.app.repository;

import com.tasty.app.domain.Customer;
import com.tasty.app.domain.Image;
import com.tasty.app.domain.Post;
import com.tasty.app.domain.enumeration.TypeOfImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ImageRepository extends JpaRepository<Image, Long> {
    @Modifying
    void deleteAllByPost_IdAndType(Long postsId, TypeOfImage type);

    @Modifying
    void deleteAllByCustomer_UsernameAndType(String username, TypeOfImage type);

    Image findByTypeAndCustomer(TypeOfImage type, Customer customer);

    Image findByTypeAndCustomer_Username(TypeOfImage type, String username);

    Image findByTypeAndPost(TypeOfImage type, Post post);
}
