package com.example.lab6inclass.view;


import com.example.lab6inclass.pojo.Wizard;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;


@Route("mainPage.it")
public class MainWizardView extends VerticalLayout {
    private TextField fullName;
    private RadioButtonGroup gender;
    private ComboBox positions;
    private TextField dollars;
    private ComboBox schools;
    private ComboBox houses;
    private Button left, create, update, delete, right;
    private HorizontalLayout panel;
    private Notification notification;
    private List<Wizard> wizardList;
    private int index;

    public MainWizardView() {
        fullName = new TextField();
        gender = new RadioButtonGroup<>("Gender", "m", "f");
        positions = new ComboBox();
        dollars = new TextField();
        schools = new ComboBox();
        houses = new ComboBox();
        left = new Button("<<");
        create = new Button("Create");
        update = new Button("Update");
        delete = new Button("Delete");
        right = new Button(">>");
        panel = new HorizontalLayout();
        notification = new Notification();
        //setPrefix
        dollars.setPrefixComponent(new Span("$"));
        //setitems
        positions.setItems("","Student", "Teacher");
        schools.setItems("","Hogwarts", "Beauxbatons", "Durmstrang");
        houses.setItems("","Gryffindor", "Ravenclaw", "Hufflepuff", "Slyther");
        index = 0;


        //setPlaceholder
        fullName.setPlaceholder("Fullname");
        schools.setPlaceholder("School");
        houses.setPlaceholder("House");
        positions.setPlaceholder("Position");

        panel.add(left, create, update, delete, right);
        this.add(fullName, gender, positions, dollars, schools, houses, panel);

        loadWizards();

        this.right.addClickListener(event -> {

            int legthList = wizardList.toArray().length;
            if (index + 1 < legthList) {
                index++;
                setTextField(index);

            }
        });

        this.left.addClickListener(event -> {
            if (index - 1 >= 0) {
                index--;
                setTextField(index);
            }
        });

        this.create.addClickListener(event -> {
            MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
            String fullname = this.fullName.getValue();
            String sex = this.gender.getValue().toString();
            String position = this.positions.getValue().toString();
            String dollar = this.dollars.getValue();
            String school = this.schools.getValue().toString();
            String house = this.houses.getValue().toString();
            formData.add("fullname", fullname);
            formData.add("sex", sex);
            formData.add("position", position);
            formData.add("dollar", dollar);
            formData.add("school", school);
            formData.add("house", house);

            Wizard wizardReturn = WebClient.create().post()
                    .uri("http://localhost:8081/addWizard").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(BodyInserters.fromFormData(formData)).retrieve().bodyToMono(Wizard.class).block();
            index = wizardList.toArray().length;
//            index = wizardList.size();
            loadWizards();
            notification.setText( (wizardReturn != null ? "Created": "something went wrong") );
            notification.open();
        });


        this.update.addClickListener(event -> {
            MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
            String id = wizardList.get(index).get_id();
            String fullname = this.fullName.getValue();
            String sex = this.gender.getValue().toString();
            String position = this.positions.getValue().toString();
            String dollar = this.dollars.getValue();
            String school = this.schools.getValue().toString();
            String house = this.houses.getValue().toString();
            formData.add("id", id);
            formData.add("fullname", fullname);
            formData.add("sex", sex);
            formData.add("position", position);
            formData.add("dollar", dollar);
            formData.add("school", school);
            formData.add("house", house);

            Wizard wizardReturn = WebClient.create().post()
                    .uri("http://localhost:8081/updateWizard").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(BodyInserters.fromFormData(formData)).retrieve().bodyToMono(Wizard.class).block();

            loadWizards();
            notification.setText( (wizardReturn != null ? "Updated": "something went wrong") );
            notification.open();
        });

        this.delete.addClickListener(event ->{
            String status = WebClient.create().post().uri("http://localhost:8081/deleteWizard/"+wizardList.get(index).get_id()).retrieve().bodyToMono(String.class).block();
            notification.setText((status.equals("something went wrong")) ? "something went wrong": status);
            notification.open();
            if(index-1 >= 0) {
                index--;
            }
            loadWizards();
        });

    }

    public void loadWizards() {
        this.wizardList = WebClient.create().get()
                .uri("http://localhost:8081/wizards")
                .retrieve().bodyToFlux(Wizard.class).collectList().block();
//        System.out.println(wizardList);
        setTextField(index);
        
    }

    public void setTextField(int index) {
        if(wizardList.isEmpty()){
            this.fullName.clear();
            this.gender.clear();
            this.positions.clear();
            this.dollars.clear();
            this.schools.clear();
            this.houses.clear();
        }else{
            this.fullName.setValue(wizardList.get(index).getName());
            this.gender.setValue(wizardList.get(index).getSex());
            this.positions.setValue(wizardList.get(index).getPosition());
            this.dollars.setValue(wizardList.get(index).getMoney() + "");
            this.schools.setValue(wizardList.get(index).getSchool());
            this.houses.setValue(wizardList.get(index).getHouse());
        }
    }

}
