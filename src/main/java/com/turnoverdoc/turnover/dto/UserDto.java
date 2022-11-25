package com.turnoverdoc.turnover.dto;

import com.turnoverdoc.turnover.model.UserStatus;
import com.turnoverdoc.turnover.model.User;
import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    private Long id;
    private String username;
    private UserStatus userStatus;
    private List<RoleDto> roles;
    private String firstName;
    private String secondName;
    private String lastName;

    public static UserDto toUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setUserStatus(user.getUserStatus());
        userDto.setRoles(RoleDto.toRoleDto(user.getRoles()));
        userDto.setFirstName(user.getFirstName());
        userDto.setSecondName(user.getSecondName());
        userDto.setLastName(user.getSurname());
        return userDto;
    }
}
