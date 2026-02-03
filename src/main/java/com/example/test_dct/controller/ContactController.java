package com.example.test_dct.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.test_dct.dto.ContactDTO;
import com.example.test_dct.entity.Contact;
import com.example.test_dct.mapper.ContactMapper;
import com.example.test_dct.repository.ContactRepository;

@RestController
@RequestMapping("/api/contacts")
@CrossOrigin(origins = "http://localhost:5173")
public class ContactController {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private ContactMapper contactMapper;

    // CREATE
    @PostMapping
    public ResponseEntity<ContactDTO> createContact(@RequestBody ContactDTO contactDTO) {
        Contact contact = contactMapper.toEntity(contactDTO);
        Contact savedContact = contactRepository.save(contact);
        return ResponseEntity.ok(contactMapper.toDto(savedContact));
    }

    // READ ALL (Optional: Pagination is usually better here)
    @GetMapping
    public List<ContactDTO> getAllContacts() {
        return contactMapper.toDtoList(contactRepository.findAll());
    }

    // SEARCH by Name
    @GetMapping("/search")
    public List<ContactDTO> searchContacts(@RequestParam String name) {
        return contactMapper.toDtoList(
                contactRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(name, name));
    }

    // GROUPING: Get contacts by Group Name
    @GetMapping("/group/{groupName}")
    public List<ContactDTO> getByGroup(@PathVariable String groupName) {
        return contactMapper.toDtoList(contactRepository.findByGroups_Name(groupName));
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<ContactDTO> updateContact(@PathVariable Long id, @RequestBody ContactDTO detailsDTO) {
        return contactRepository.findById(id)
                .map(contact -> {
                    contact.setFirstName(detailsDTO.getFirstname());
                    contact.setLastName(detailsDTO.getLastname());
                    contact.setEmail(detailsDTO.getEmail());
                    contact.setPhone(detailsDTO.getPhone());
                    Contact updatedContact = contactRepository.save(contact);
                    return ResponseEntity.ok(contactMapper.toDto(updatedContact));
                }).orElse(ResponseEntity.notFound().build());
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteContact(@PathVariable Long id) {
        return contactRepository.findById(id)
                .map(contact -> {
                    contactRepository.delete(contact);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }
}
