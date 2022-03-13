package com.phoenix.services.user;

import com.phoenix.data.dtos.AppUserRequestDto;
import com.phoenix.data.dtos.AppUserResponseDto;
import com.phoenix.data.models.AppUser;
import com.phoenix.data.repositories.AppUserRepository;
import com.phoenix.web.exceptions.BusinessLogicException;
import com.phoenix.web.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppUserServiceImpl implements AppUserService{

    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Override
    public AppUserResponseDto createUser(AppUserRequestDto appUserRequestDto) throws BusinessLogicException {
        //check that user does not exist
        Optional<AppUser> queryUser = appUserRepository.findByEmail(appUserRequestDto.getEmail());

        if(queryUser.isPresent()) throw new BusinessLogicException("user already exist");
        //create an app user object

        AppUser newUser = new AppUser();
        newUser.setFirstName(appUserRequestDto.getFirstName());
        newUser.setLastName(appUserRequestDto.getLastName());
        newUser.setPassword(passwordEncoder.encode(appUserRequestDto.getPassword()));
        newUser.setEmail(appUserRequestDto.getEmail());
        newUser.setAddress(appUserRequestDto.getAddress());

        //save object
        appUserRepository.save(newUser);

        //response

        return buildAppUserResponseDto(newUser);
    }

    private AppUserResponseDto buildAppUserResponseDto(AppUser appUser){
        return AppUserResponseDto.builder()
                .email(appUser.getEmail())
                .address(appUser.getAddress())
                .firstName(appUser.getFirstName())
                .lastName(appUser.getLastName())
                .build();
    }
}
