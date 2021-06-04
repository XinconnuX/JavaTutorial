package com.example.javatutorial.controller;

import com.example.javatutorial.model.Contact;
import com.example.javatutorial.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping({"/contacts"})
public class ContactController {

    @Autowired
    private ContactRepository contactRepository;

    @GetMapping
    public List<Contact> findAllContacts(){
        return contactRepository.findAll();
    }

    @GetMapping("/{id}")// GET /contacts/11112
    public ResponseEntity findById(@PathVariable Long id){
        return contactRepository.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }




    //Exemplos de uso de Java Stream
    public List<Contact> findAllContactsNamedJonas(){
        return contactRepository.findAll().stream()
                .filter(contact -> contact.getName().equalsIgnoreCase("Jonas"))
                .collect(Collectors.toList());
    }

    public Contact findFirstContactsNamedJonas(){
        return contactRepository.findAll().stream()
                .filter(contact -> contact.getName().equalsIgnoreCase("Jonas"))
                .findFirst()
                .orElse(null);
    }

/*    @GetMapping("/{name}")// GET /contacts/asgfsadfg
    public ResponseEntity findByName( String name){

    }
  */
}