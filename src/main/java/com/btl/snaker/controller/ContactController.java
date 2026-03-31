package com.btl.snaker.controller;

import com.btl.snaker.payload.request.ContactRequest;
import com.btl.snaker.service.imp.ContactServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/contact")
public class ContactController {

    @Autowired
    private ContactServiceImp contactServiceImp;

    @GetMapping("/admin/all")
    public ResponseEntity<?> getAllContacts() {
        return new ResponseEntity<>(contactServiceImp.getAllContacts(), HttpStatus.OK);
    }

    @GetMapping("/user/get")
    public ResponseEntity<?> getContactById(@RequestParam long id) {
        return new ResponseEntity<>(contactServiceImp.getContactById(id),HttpStatus.OK);
    }

    @PostMapping("/user/create")
    public ResponseEntity<?> createContact(@RequestBody ContactRequest contactRequest) {
        return new ResponseEntity<>(contactServiceImp.createContact(contactRequest), HttpStatus.OK);
    }

    @PutMapping("/admin/solve")
    public ResponseEntity<?> markAsRead(@RequestParam long contactId) {
        return new ResponseEntity<>(contactServiceImp.solveRead(contactId), HttpStatus.OK);
    }

}
