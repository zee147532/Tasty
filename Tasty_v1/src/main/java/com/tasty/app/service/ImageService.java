package com.tasty.app.service;

import com.tasty.app.domain.enumeration.TypeOfImage;
import com.tasty.app.service.dto.ImageDTO;

public interface ImageService {
    String createImage(ImageDTO dto);
}
