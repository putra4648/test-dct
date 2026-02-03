package com.example.test_dct.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.test_dct.entity.Contact;

public interface ContactRepository extends JpaRepository<Contact, Long> {

    // SEARCH: Find by name containing a keyword (case-insensitive)
    List<Contact> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String firstName, String lastName);

    // GROUPING: Find all contacts assigned to a specific group name
    List<Contact> findByGroups_Name(String groupName);

    // Ownership: Ensure a user only sees their own contacts
    List<Contact> findByUserId(Long userId);
}
