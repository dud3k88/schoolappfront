package com.schoolapp.child.gui;

import com.schoolapp.Menu;
import com.schoolapp.child.model.ChildDtoClient;
import com.schoolapp.child.service.ChildService;
import com.schoolapp.group.model.GroupDtoClient;
import com.schoolapp.group.service.GroupService;
import com.schoolapp.parent.model.ParentDtoClient;
import com.schoolapp.parent.service.ParentService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.NativeButtonRenderer;
import com.vaadin.flow.router.Route;

import java.util.List;
import java.util.stream.Collectors;

@Route(value = "Child", layout = Menu.class)
public class ChildGui extends HorizontalLayout {
    private ParentService parentService;
    private ChildService childService;
    private GroupService groupService;
    private List<ChildDtoClient> childDtoClients;
    private Grid<ChildDtoClient> grid = new Grid<>();
    private TextField textFieldName = new TextField("FIRST NAME");
    private TextField textFieldSecondName = new TextField("SECOND NAME");
    private ComboBox comboBoxYOB;
    private ComboBox comboBoxParent = new ComboBox("PARENT");
    private ComboBox comboBoxGroup = new ComboBox("GROUP");
    private Button send = new Button("SEND");

    public ChildGui(ChildService childService, ParentService parentService, GroupService groupService) {
        this.childService = childService;
        this.parentService = parentService;
        this.groupService = groupService;

        comboBoxYOB = setYOBComboBox();
        createGrid();
        createForm();
    }

    public void createGrid() {
        childDtoClients = childService.getAllChild();
        grid.setItems(childDtoClients);
        comboBoxParent.setItems(parentService.getParents().stream()
                .map(t -> t.getEmailAddress()));
        comboBoxGroup.setItems(groupService.getAllGroups().stream()
                .map(t -> t.getGroupName()));

        grid.addColumn(ChildDtoClient::getId).setHeader("IDENTITY").setWidth("50px");
        grid.addColumn(ChildDtoClient::getFirstName).setHeader("FIRST NAME");
        grid.addColumn(ChildDtoClient::getSecondName).setHeader("SECOND NAME");
        grid.addColumn(ChildDtoClient::getYearOfBirth).setHeader("YEAR OF BIRTH");
        grid.addColumn(ChildDtoClient::getParent).setHeader("PARENT");
        grid.addColumn(ChildDtoClient::getGroup).setHeader("GROUP");
        grid.addColumn(new NativeButtonRenderer<ChildDtoClient>("DELETE", event ->{
            deleteChild(event.getId());
        })).setHeader("DELETE");

        send.addClickListener(event -> {
            addChild();
            refresh();
        });
        add(grid);
    }

    public void createForm() {
        FormLayout form = new FormLayout(
                textFieldName, textFieldSecondName, comboBoxYOB, comboBoxParent, comboBoxGroup, send);
        form.setMaxWidth("300px");
        add(form);
    }

    public void addChild() {
        ChildDtoClient child = new ChildDtoClient();
        if (!textFieldName.getValue().isEmpty() && !textFieldSecondName.getValue().isEmpty()
        && !comboBoxYOB.isEmpty() && !comboBoxParent.isEmpty() && !comboBoxGroup.isEmpty()) {

            List<ParentDtoClient> collect = parentService.getParents().stream()
                    .filter(t -> t.getEmailAddress().equals(String.valueOf(comboBoxParent.getValue())))
                    .collect(Collectors.toList());
            ParentDtoClient parentDtoClient = collect.get(0);

            List<GroupDtoClient> collect2 = groupService.getAllGroups().stream()
                    .filter(t -> t.getGroupName().equals(String.valueOf(comboBoxGroup.getValue())))
                    .collect(Collectors.toList());
            GroupDtoClient groupDtoClient = collect2.get(0);

            child.setFirstName(textFieldName.getValue());
            child.setSecondName(textFieldSecondName.getValue());
            child.setYearOfBirth((int) comboBoxYOB.getValue());
            child.setParent(parentDtoClient);
            child.setGroup(groupDtoClient);

            childService.createChild(child);
            Span span = new Span("Add a new Child :)");
            Notification notification = new Notification(span);
            notification.setDuration(5000);
            notification.setPosition(Notification.Position.MIDDLE);
            notification.open();
            clearUpForm();
        } else {
            Notification notification = new Notification("Fields cannot be empty");
            notification.setDuration(3000);
            notification.setPosition(Notification.Position.MIDDLE);
            notification.open();
        }
    }

    public void deleteChild(Long childId) {
        childService.deleteChild(childId);
        refresh();
    }

    public void refresh() {
        childDtoClients = childService.getAllChild();
        grid.setItems(childDtoClients);
    }

    public ComboBox setYOBComboBox() {
        ComboBox comboBox = new ComboBox("YEAR OF BIRTH");
        comboBox.setItems(
                2000, 2001, 2002, 2003, 2004, 2005, 2006, 2007, 2008, 2009, 2010,
                2011, 2012, 2013, 2014, 2015, 2016, 2017, 2018, 2029, 2020);
        return comboBox;
    }

    public void clearUpForm() {
      textFieldName.clear();
      textFieldSecondName.clear();
      comboBoxYOB.clear();
      comboBoxParent.clear();
      comboBoxGroup.clear();
    }
}
