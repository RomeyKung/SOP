package com.example.lab6addcache.repository;

import com.example.lab6addcache.pojo.Wizard;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public  interface WizardRepository extends MongoRepository<Wizard, String> {
    @Query(value = "{_id: '?0'}")
    public Wizard getWizardByID(String id);

}
