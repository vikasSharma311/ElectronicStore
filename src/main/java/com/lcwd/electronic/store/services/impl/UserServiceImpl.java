package com.lcwd.electronic.store.services.impl;

import com.lcwd.electronic.store.dtos.PageableResponse;
import com.lcwd.electronic.store.dtos.UserDto;
import com.lcwd.electronic.store.entities.Role;
import com.lcwd.electronic.store.entities.User;
import com.lcwd.electronic.store.exceptions.ResourceNotFoundException;
import com.lcwd.electronic.store.helper.Helper;
import com.lcwd.electronic.store.repositories.RoleRepository;
import com.lcwd.electronic.store.repositories.UserRepository;
import com.lcwd.electronic.store.services.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    private Logger logger= LoggerFactory.getLogger(UserServiceImpl.class);
    @Value("${user.profile.image.path}")
    private String imageUploadPath;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private RoleRepository roleRepository;
    @Value("${normal.role.id}")
    private String normalRoleId;

    @Override
    public UserDto CreateUser(UserDto userDto) {
        //gernate unique id in string format
        String userId = UUID.randomUUID().toString();
        userDto.setUserId(userId);
        //encoding password
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User user = dtoToEntity(userDto);
        Role role = roleRepository.findById(normalRoleId).get();
        user.getRoles().add(role);
        User savedUser = userRepository.save(user);
        UserDto newDto = entityToDto(savedUser);
        return newDto;
    }

    @Override
    public UserDto updateUser(UserDto userDto, String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with given id !!"));
        user.setName(userDto.getName());
        //email update
        user.setAbout(userDto.getAbout());
        user.setGender(userDto.getGender());
        user.setPassword(userDto.getPassword());
        user.setImageName(userDto.getImageName());
        User updatedUser = userRepository.save(user);
        UserDto updatedDto = entityToDto(updatedUser);
        return updatedDto;

    }

    @Override
    public void deleteUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with given id !!"));
        String fullPath = imageUploadPath + user.getImageName();
        try {
                Path path = Paths.get(fullPath);
                if (Files.exists(path) && Files.size(path) > 0) {
                    Files.delete(path);
                } else {
                    logger.info("User image not found in folder or file size is zero");
                }
        }catch (NoSuchFileException ex){
            logger.info("User image not found in folder");
            ex.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        user.setRoles(null);
        userRepository.delete(user);
        
    }


    @Override
    public PageableResponse<UserDto> getAllUser(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize,sort);
        Page<User> page = userRepository.findAll(pageable);
        PageableResponse<UserDto> response = Helper.getPageableResponse(page, UserDto.class);
        return response;

    }

    @Override
    public UserDto getUserById(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with given id !!"));
        return entityToDto(user);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found with given Email !!"));
        return entityToDto(user);
    }

    @Override
    public List<UserDto> searchUser(String keyword) {
        List<User> users = userRepository.findByNameContaining(keyword);
        List<UserDto> dtoList = users.stream().map(user -> entityToDto(user)).collect(Collectors.toList());
        return dtoList;
    }

    private UserDto entityToDto(User savedUser) {
        UserDto userDto = modelMapper.map(savedUser, UserDto.class);
        return userDto;
    }

    private User dtoToEntity(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        return user;
    }
}
