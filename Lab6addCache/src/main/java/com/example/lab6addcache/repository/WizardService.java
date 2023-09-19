package com.example.lab6addcache.repository;


import com.example.lab6addcache.pojo.Wizard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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

    @Cacheable(value = "Wizard", key = "'wizard'")
    public List<Wizard> retrieveWizards(){
        return repository.findAll();
    }

    @Cacheable(value = "Wizard", key = "'wizard' + #id")
    public Wizard findByID(String id){
        return  repository.getWizardByID(id);
    }
    @CacheEvict(value = "Wizard", key = "'wizard'")
    public Wizard addWizard(Wizard wizard){ return repository.save(wizard); }

    @CachePut(value = "Wizard", key = "'wizard'")
    public List<Wizard> updateWizard(Wizard wizard){ ;
        repository.save(wizard);
        return  repository.findAll();
    }

    @CacheEvict(value = "Wizard", key = "'wizard'")
    public boolean deleteWizard(String id){
        Wizard wizard = findByID(id);
        repository.delete(wizard);
        return true;
    }

}
