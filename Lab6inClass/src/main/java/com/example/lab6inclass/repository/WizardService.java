package com.example.lab6inclass.repository;

import com.example.lab6inclass.pojo.Wizard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import java.util.List;


//จัดการ redis here
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

    public Wizard findByID(String id){
        return  repository.getWizardByID(id);
    }
    @CacheEvict(value="Wizard", allEntries = true)
    public Wizard addWizard(Wizard wizard){ return repository.save(wizard); }

    @CachePut(value = "Wizard")
    public Wizard updateWizard(Wizard wizard){ return repository.save(wizard); }

    @CacheEvict(value="Wizard", allEntries = true)
    public boolean deleteWizard(String id){
        Wizard wizard = findByID(id);
        repository.delete(wizard);
        return true;
    }

}
