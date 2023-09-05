package com.example.lab6inclass.controller;

import com.example.lab6inclass.pojo.Wizard;
import com.example.lab6inclass.pojo.Wizards;
import com.example.lab6inclass.repository.WizardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class WizardController {
    @Autowired
    private WizardService wizardService;

    @RequestMapping(value ="/wizards", method = RequestMethod.GET)
    public ResponseEntity<List<Wizard>> getWizards(){
        List<Wizard> wizards = wizardService.retrieveWizards();
        return ResponseEntity.ok(wizards);
    }

    @RequestMapping(value ="/addWizard", method = RequestMethod.POST)
    public void addWizard(@RequestBody String fullname, @RequestBody String gender, @RequestBody String position, @RequestBody String money, @RequestBody String school, @RequestBody String house){
        wizardService.addWizard();
    }




}
