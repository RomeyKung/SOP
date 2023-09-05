package com.example.lab6inclass.view;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;


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
    public MainWizardView(){
        fullName = new TextField();
        gender = new RadioButtonGroup<>("Gender", "Male","Female");
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

        dollars.setPrefixComponent(new Span("$"));
        //setPlaceholder
        fullName.setPlaceholder("Fullname");
        schools.setPlaceholder("School");
        houses.setPlaceholder("House");
        positions.setPlaceholder("Position");

        panel.add(left, create, update, delete, right);
        this.add(fullName, gender, positions, dollars,schools,houses, panel);
    }
}
