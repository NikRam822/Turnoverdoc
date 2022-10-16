package com.turnoverdoc.turnover.dto;

import com.turnoverdoc.turnover.model.Role;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RoleDto {
    private Long id;
    private String name;

    public static List<RoleDto> toRoleDto(List<Role> roleList) {
        List<RoleDto> roleDtos = new ArrayList<>();

        for (int i = 0; i < roleList.size(); i++) {
            RoleDto roleDto = new RoleDto();
            roleDto.setId(roleList.get(i).getId());
            roleDto.setName(roleList.get(i).getName());
            roleDtos.add(roleDto);
        }

        return roleDtos;
    }
}
