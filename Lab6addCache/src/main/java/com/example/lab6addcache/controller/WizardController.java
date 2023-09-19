package com.example.lab6addcache.controller;


import com.example.lab6addcache.pojo.Wizard;
import com.example.lab6addcache.repository.WizardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

//จัดการ req-respond
@RestController
public class WizardController {
    @Autowired
    private WizardService wizardService;

    @RequestMapping(value = "/wizards", method = RequestMethod.GET)
    public ResponseEntity<List<Wizard>> getWizards() {
        List<Wizard> wizards = wizardService.retrieveWizards();
        return ResponseEntity.ok(wizards);
    }

    @RequestMapping(value = "/addWizard", method = RequestMethod.POST)
    public ResponseEntity<Wizard> addWizard(@RequestBody MultiValueMap<String, String> formdata) {
        Map<String, String> d = formdata.toSingleValueMap();
        int mon = Integer.parseInt(d.get("dollar"));
        Wizard wizard = wizardService.addWizard(
                new Wizard(null, d.get("fullname"), d.get("sex"), mon, d.get("position"), d.get("school"), d.get("house"))
        );
        return ResponseEntity.ok(wizard);

    }

    @RequestMapping(value = "/updateWizard", method = RequestMethod.POST)
    public List<Wizard> updateWizard(@RequestBody MultiValueMap<String, String> formdata) {
        //convert data
        Map<String, String> d = formdata.toSingleValueMap();
        int mon = Integer.parseInt(d.get("dollar"));

        List<Wizard> wizard = wizardService.updateWizard(
                new Wizard(d.get("id"), d.get("fullname"), d.get("sex"), mon, d.get("position"), d.get("school"), d.get("house"))
        );
        return wizard;

    }

    @RequestMapping(value = "/deleteWizard/{id}", method = RequestMethod.POST)
    public String deleteWizard(@PathVariable String id){
        boolean status =  wizardService.deleteWizard(id);
        return (status? "Deleted" : "Something went wrong");
    }

}
