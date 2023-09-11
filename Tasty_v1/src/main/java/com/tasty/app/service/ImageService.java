package com.tasty.app.service;

import com.tasty.app.domain.Image;
import com.tasty.app.domain.enumeration.TypeOfImage;
import com.tasty.app.service.dto.ImageDTO;
import org.hibernate.type.ImageType;

public interface ImageService {
    void createImage(ImageDTO dto);
}
