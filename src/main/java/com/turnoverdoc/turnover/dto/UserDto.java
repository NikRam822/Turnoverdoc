package com.turnoverdoc.turnover.dto;

import com.turnoverdoc.turnover.model.Status;
import com.turnoverdoc.turnover.model.User;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;

@Data
public class UserDto {
    private Long id;
    private String username;
    private Status status;
    private List<RoleDto> roles;

    public static UserDto toUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setStatus(user.getStatus());
        userDto.setRoles(RoleDto.toRoleDto(user.getRoles()));
        return userDto;
    }
}
