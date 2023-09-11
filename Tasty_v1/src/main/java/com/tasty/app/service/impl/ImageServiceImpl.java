package com.tasty.app.service.impl;

import com.tasty.app.domain.Customer;
import com.tasty.app.domain.Image;
import com.tasty.app.domain.Post;
import com.tasty.app.domain.enumeration.TypeOfImage;
import com.tasty.app.repository.CustomerRepository;
import com.tasty.app.repository.ImageRepository;
import com.tasty.app.repository.IngredientRepository;
import com.tasty.app.repository.PostRepository;
import com.tasty.app.service.ImageService;
import com.tasty.app.service.dto.ImageDTO;
import org.hibernate.type.ImageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.tasty.app.domain.enumeration.TypeOfImage.CUSTOMER;

@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private IngredientRepository ingredientRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ImageRepository imageRepository;

    private final Logger log = LoggerFactory.getLogger(ImageService.class);

    @Override
    public void createImage(ImageDTO dto) {
        Image image = new Image();
        switch (dto.getType()) {
            case DISH:
                Long postsId = dto.getPostsId();
                imageRepository.deleteAllByPost_IdAndType(postsId, TypeOfImage.DISH);
                Post post = postRepository.getReferenceById(postsId);
                if (Objects.isNull(post.getId())) {
                    log.warn(String.format("Không tìm thấy bài viết có id: %s", postsId));
                    return;
                }
                image.setPost(post);
                break;
            case CUSTOMER:
                String username = dto.getUsername();
                imageRepository.deleteAllByCustomer_UsernameAndType(username, CUSTOMER);
                Customer customer = customerRepository.findByUsername(username);
                if (Objects.isNull(customer)) {
                    log.warn(String.format("Không tìm thấy người dùng có username: %s", username));
                    return;
                }
                image.setCustomer(customer);
                break;
        }

        image.setType(dto.getType());
        image.setUri(dto.getUri());
        imageRepository.save(image);
    }
}
