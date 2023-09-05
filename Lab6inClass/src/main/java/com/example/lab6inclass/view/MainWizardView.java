package com.example.lab6inclass.view;


import com.example.lab6inclass.pojo.Wizard;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
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
        //setPrefix
        dollars.setPrefixComponent(new Span("$"));
        //setitems
        positions.setItems("Student", "Teacher");
        schools.setItems("Hogwarts", "Beauxbatons", "Durmstrang");
        houses.setItems("Gryffindor", "Ravenclaw", "Hufflepuff", "Slyther");
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

            if (index - 1 > 0) {
                index--;
                setTextField(index);
            }
        });
    }

    public void loadWizards() {
        this.wizardList = WebClient.create().get()
                .uri("http://localhost:8081/wizards")
                .retrieve().bodyToFlux(Wizard.class).collectList().block();
        System.out.println(wizardList);
        this.fullName.setValue(wizardList.get(0).getName());
        this.gender.setValue(wizardList.get(0).getSex());
        this.positions.setValue(wizardList.get(0).getPosition());
        this.dollars.setValue(wizardList.get(0).getMoney() + "");
        this.schools.setValue(wizardList.get(0).getSchool());
        this.houses.setValue(wizardList.get(0).getHouse());

    }
    public void setTextField(int index){
        this.fullName.setValue(wizardList.get(index).getName());
        this.gender.setValue(wizardList.get(index).getSex());
        this.positions.setValue(wizardList.get(index).getPosition());
        this.dollars.setValue(wizardList.get(index).getMoney() + "");
        this.schools.setValue(wizardList.get(index).getSchool());
        this.houses.setValue(wizardList.get(index).getHouse());
    }

}
