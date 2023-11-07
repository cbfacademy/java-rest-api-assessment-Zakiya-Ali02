package com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/creditpoints")
public class CreditPointsController {

    private final CreditPointsService creditPointsService;

    @Autowired
    public CreditPointsController(CreditPointsService creditPointsService) {
        this.creditPointsService = creditPointsService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        try {
            List<User> users = creditPointsService.getAllUsers();
            return ResponseEntity.ok(users);
        } catch (IOException e) {
            e.printStackTrace(); 
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/calculate")
    public ResponseEntity<String> calculateCreditPoints(@RequestBody User user) {
        int creditPoints = creditPointsService.calculateCreditPoints(user);

        
       

        try {
            creditPointsService.saveUserInput(user);
        } catch (Exception e) {
          
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving user input.");
        }

         return ResponseEntity.ok("Credit Points: " + creditPoints);
    }

    
}
