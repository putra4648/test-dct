package com.example.test_dct.mapper;

import com.example.test_dct.dto.GroupDTO;
import com.example.test_dct.entity.Group;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GroupMapper {

    public GroupDTO toDto(Group group) {
        if (group == null) {
            return null;
        }
        GroupDTO dto = new GroupDTO();
        dto.setId(group.getId());
        dto.setName(group.getName());
        return dto;
    }

    public Group toEntity(GroupDTO dto) {
        if (dto == null) {
            return null;
        }
        Group group = new Group();
        group.setName(dto.getName());
        return group;
    }

    public List<GroupDTO> toDtoList(List<Group> groups) {
        return groups.stream().map(this::toDto).collect(Collectors.toList());
    }
}
