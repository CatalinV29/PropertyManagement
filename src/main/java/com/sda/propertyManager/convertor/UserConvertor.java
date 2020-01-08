package com.sda.propertyManager.convertor;


import com.sda.propertyManager.model.User;

public class UserConvertor {
    public static User convert(User user) {
        User userDto = new User();
        userDto.setUserName(user.getUserName());
        userDto.setPassword(user.getPassword());
        userDto.setEmail(user.getEmail());
        userDto.setPassword("******");
        return userDto;
    }

    public static User convertBack (User userDto) {
        User user = new User();
        user.setUserName(userDto.getUserName());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        return user;
    }
}