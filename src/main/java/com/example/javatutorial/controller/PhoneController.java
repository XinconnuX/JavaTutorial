package com.example.javatutorial.controller;

import com.example.javatutorial.model.Contact;
import com.example.javatutorial.model.Phone;
import com.example.javatutorial.repository.ContactRepository;
import com.example.javatutorial.repository.PhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping({"/contacts/{contactId}/phones"})
public class PhoneController {

    @Autowired
    private PhoneRepository phoneRepository;

    @Autowired
    private ContactRepository contactRepository;

    //Buscar um Phone Especifico
    @GetMapping("/{phoneId}")
    public ResponseEntity findPhoneById (@PathVariable Long contactId, @PathVariable Long phoneId){
        return phoneRepository.findById(phoneId)
                .map(phone -> ResponseEntity.ok().body(phone))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity createPhone (@PathVariable Long contactId, @RequestBody List<Phone> phones){
        return contactRepository.findById(contactId)
                .map(contact -> {
                    phones.forEach( phone -> phone.setContactId(contactId));
                    List<Phone> savedPhones = phoneRepository.saveAll(phones);

                    return ResponseEntity.ok().body(savedPhones);
                })
                .orElse(ResponseEntity.notFound().build());
    }

}
