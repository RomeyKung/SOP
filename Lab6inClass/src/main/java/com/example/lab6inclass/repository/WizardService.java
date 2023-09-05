package com.example.lab6inclass.repository;

import com.example.lab6inclass.pojo.Wizard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WizardService  {
    @Autowired
    private WizardRepository repository;

    public WizardService(WizardRepository wizardRepository){
        this.repository = wizardRepository;

    }

    public List<Wizard> retrieveWizards(){
        return repository.findAll();
    }

    public Wizard findByName(String name){
        return  repository.getWizardByName(name);
    }

    public void addWizard(){}

}
