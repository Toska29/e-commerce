package com.phoenix.services.user;

import com.phoenix.data.dtos.AppUserRequestDto;
import com.phoenix.data.dtos.AppUserResponseDto;
import com.phoenix.web.exceptions.BusinessLogicException;
import com.phoenix.web.exceptions.UserNotFoundException;

public interface AppUserService {
    AppUserResponseDto createUser(AppUserRequestDto appUserRequestDto) throws BusinessLogicException;
}
