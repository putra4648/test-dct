package com.example.test_dct.mapper;

import com.example.test_dct.dto.ContactDTO;
import com.example.test_dct.dto.GroupDTO;
import com.example.test_dct.entity.Contact;
import com.example.test_dct.entity.Group;
import com.example.test_dct.entity.User;

import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class ContactMapper {

    public ContactDTO toDto(Contact contact) {
        if (contact == null) {
            return null;
        }
        ContactDTO dto = new ContactDTO();
        dto.setId(contact.getId());
        dto.setFirstname(contact.getFirstName());
        dto.setLastname(contact.getLastName());
        dto.setEmail(contact.getEmail());
        dto.setPhone(contact.getPhone());

        List<GroupDTO> groupDTOs = new ArrayList<>();
        Set<Group> groups = contact.getGroups();
        for (Group group : groups) {
            GroupDTO groupDTO = new GroupDTO();
            groupDTO.setId(group.getId());
            groupDTO.setName(group.getName());

            groupDTOs.add(groupDTO);

        }
        dto.setGroups(groupDTOs);

        return dto;
    }

    public Contact toEntity(ContactDTO dto) {
        if (dto == null) {
            return null;
        }
        Contact contact = new Contact();
        contact.setFirstName(dto.getFirstname());
        contact.setLastName(dto.getLastname());
        contact.setEmail(dto.getEmail());
        contact.setPhone(dto.getPhone());

        contact.getGroups().addAll(dto.getGroups().stream().map(group -> {
            Group groupEntity = new Group();
            groupEntity.setId(group.getId());
            return groupEntity;
        }).collect(Collectors.toSet()));
        User user = new User();
        user.setId(dto.getUserId());
        contact.setUser(user);

        return contact;
    }

    public List<ContactDTO> toDtoList(List<Contact> contacts) {
        return contacts.stream().map(this::toDto).collect(Collectors.toList());
    }
}
