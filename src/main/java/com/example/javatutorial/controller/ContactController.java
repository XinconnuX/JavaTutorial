package com.example.javatutorial.controller;

import com.example.javatutorial.model.Contact;
import com.example.javatutorial.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
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

    @PostMapping
    public Contact create(@RequestBody Contact contact){
        return contactRepository.save(contact);
    }

    @PutMapping ("/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody Contact contact ){
        return contactRepository.findById(id)
                .map (record -> {
                            record.setName(contact.getName());
                            record.setPhone(contact.getPhone());
                            record.setEmail(contact.getEmail());

                            return ResponseEntity.ok().body(contactRepository.save(record));
                        }
                ).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        return contactRepository.findById(id)
                .map(record -> {
                    contactRepository.delete(record);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
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
    public ResponseEntity findByName( @PathVariable  String name){

    }
  */
}