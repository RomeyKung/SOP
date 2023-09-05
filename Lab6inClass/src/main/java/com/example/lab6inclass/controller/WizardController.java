package com.example.lab6inclass.controller;

import com.example.lab6inclass.pojo.Wizard;
import com.example.lab6inclass.pojo.Wizards;
import com.example.lab6inclass.repository.WizardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class WizardController {
    @Autowired
    private WizardService wizardService;

    @RequestMapping(value ="/wizards", method = RequestMethod.GET)
    public ArrayList<Wizard> getWizards(){
        List<Wizard> wizards =
    }
}
