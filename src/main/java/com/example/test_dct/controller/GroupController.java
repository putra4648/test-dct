package com.example.test_dct.controller;

import com.example.test_dct.dto.GroupDTO;
import com.example.test_dct.entity.Group;
import com.example.test_dct.mapper.GroupMapper;
import com.example.test_dct.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
@CrossOrigin(origins = "http://localhost:5173")
public class GroupController {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private GroupMapper groupMapper;

    @PostMapping
    public ResponseEntity<GroupDTO> createGroup(@RequestBody GroupDTO groupDTO) {
        Group group = groupMapper.toEntity(groupDTO);
        Group savedGroup = groupRepository.save(group);
        return ResponseEntity.ok(groupMapper.toDto(savedGroup));
    }

    @GetMapping
    public List<GroupDTO> getAllGroups() {
        return groupMapper.toDtoList(groupRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupDTO> getGroupById(@PathVariable Long id) {
        return groupRepository.findById(id)
                .map(group -> ResponseEntity.ok(groupMapper.toDto(group)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<GroupDTO> updateGroup(@PathVariable Long id, @RequestBody GroupDTO groupDTO) {
        return groupRepository.findById(id)
                .map(group -> {
                    group.setName(groupDTO.getName());
                    Group updatedGroup = groupRepository.save(group);
                    return ResponseEntity.ok(groupMapper.toDto(updatedGroup));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGroup(@PathVariable Long id) {
        return groupRepository.findById(id)
                .map(group -> {
                    groupRepository.delete(group);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
