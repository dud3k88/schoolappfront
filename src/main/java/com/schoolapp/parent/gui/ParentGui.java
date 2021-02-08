package com.schoolapp.parent.gui;

import com.schoolapp.Menu;
import com.schoolapp.child.service.ChildService;
import com.schoolapp.parent.model.ParentDtoClient;
import com.schoolapp.parent.service.ParentService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.NativeButtonRenderer;
import com.vaadin.flow.router.Route;
import java.util.List;

@Route(value = "Parent", layout = Menu.class)
public class ParentGui extends HorizontalLayout {
    private ParentService parentService;
    private ChildService childService;
    private List<ParentDtoClient> parentDtoClients;
    private Grid<ParentDtoClient> grid = new Grid<>();
    private TextField textFieldFirstName = new TextField("First Name");
    private TextField textFieldLastName = new TextField("Last Name");
    private TextField textFieldEmail = new TextField("Email");
    private Button send = new Button("SEND");

    public ParentGui(ParentService parentService, ChildService childService) {
        this.parentService = parentService;
        this.childService = childService;

        createMainGrid();
        createForm();
    }

    private void createForm() {
        FormLayout form = new FormLayout(textFieldFirstName, textFieldLastName, textFieldEmail, send);
        form.setMaxWidth("300px");
        send.addClickListener(event -> {
            addParent();
            refresh();
        });
        add(form);
    }

    private void createMainGrid() {
        parentDtoClients = parentService.getParents();
        grid.setItems(parentDtoClients);
        grid.addColumn(ParentDtoClient::getId).setHeader("IDENTITY");
        grid.addColumn(ParentDtoClient::getFirstName).setHeader("FIRST NAME");
        grid.addColumn(ParentDtoClient::getSecondName).setHeader("SECOND NAME");
        grid.addColumn(ParentDtoClient::getEmailAddress).setHeader("EMAIL");
        grid.addColumn(new NativeButtonRenderer<ParentDtoClient>("DELETE", event -> {
            deleteParent(Long.parseLong(event.getId()));
        })).setHeader("DELETE");
        grid.addComponentColumn(event -> {
            Label label = new Label();
            label.setText(String.valueOf(countChildren((event.getEmailAddress()))));
            return  label;
        }).setHeader("Children").setKey("Chil");
        add(grid);
    }

    public void refresh() {
        parentDtoClients = parentService.getParents();
        grid.setItems(parentDtoClients);
    }

    public void deleteParent(Long parentId) {
       long count = childService.getAllChild().stream()
               .filter(t -> t.getParent().getId().equals(parentId))
               .count();
       if (count > 0) {
            Span span = new Span("You can't delete this Parent, " +
                    "first delete all children belonging to it");
            Notification notification = new Notification(span);
            notification.setDuration(5000);
            notification.setPosition(Notification.Position.MIDDLE);
            notification.open();
        } else {
            parentService.deleteParent(parentId);
        }
        refresh();
    }

    public void addParent() {
        if (!textFieldFirstName.getValue().isEmpty() && !textFieldLastName.getValue().isEmpty() &&
            !textFieldEmail.getValue().isEmpty()) {
            ParentDtoClient parent = new ParentDtoClient();
            parent.setFirstName(textFieldFirstName.getValue());
            parent.setSecondName(textFieldLastName.getValue());
            parent.setEmailAddress(textFieldEmail.getValue());
            parentService.createParent(parent);
            clearUpForm();
            refresh();

            Notification notification = new Notification("Parent is Added");
            notification.setDuration(3000);
            notification.setPosition(Notification.Position.MIDDLE);
            notification.open();
        } else {
            Notification notification = new Notification("Fields cannot be empty");
            notification.setDuration(3000);
            notification.setPosition(Notification.Position.MIDDLE);
            notification.open();
        }
    }

    public int countChildren (String email) {
        final long count = childService.getAllChild().stream()
                .filter(t -> t.getParent().getEmailAddress().equals(email))
                .count();
        return (int) count;
    }

    public void clearUpForm() {
        textFieldFirstName.clear();
        textFieldLastName.clear();
        textFieldEmail.clear();
    }
}
