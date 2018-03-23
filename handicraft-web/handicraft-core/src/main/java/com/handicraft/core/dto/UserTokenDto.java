package com.handicraft.core.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Setter
@Getter
public class UserTokenDto implements Serializable {
    private static final long serialVersionUID = 2759490395823610004L;
    private String accessToken;
    private UserDto userDto;
}
