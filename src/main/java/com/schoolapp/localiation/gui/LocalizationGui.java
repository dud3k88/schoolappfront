package com.schoolapp.localiation.gui;

import com.schoolapp.Menu;
import com.schoolapp.group.service.GroupService;
import com.schoolapp.localiation.model.LocalizationDtoClient;
import com.schoolapp.localiation.service.LocalizationService;
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

@Route(value = "Localization", layout = Menu.class)
public class LocalizationGui extends HorizontalLayout {

    private LocalizationService localizationService;
    private GroupService groupService;
    private List<LocalizationDtoClient> localizationDtoClientList;
    private Grid<LocalizationDtoClient> grid = new Grid<>();
    private TextField localizationName = new TextField("LOCALIZATION NAME");
    private Button send = new Button("SEND");
    private Label label = new Label();

    public LocalizationGui(LocalizationService localizationService, GroupService groupService) {
        this.localizationService = localizationService;
        this.groupService = groupService;

        createMainGrid();
        createForm();
    }

    private void createForm() {
        FormLayout buttons = new FormLayout(localizationName, send);
        buttons.addClassName("buttons");
        buttons.setMinWidth("300px");
        send.addClickListener(event -> {
            addLocalization(localizationService);
            refresh();
        });
        add(buttons);
    }

    private void createMainGrid() {
        localizationDtoClientList = localizationService.getAllLocalization();
        grid.setItems(localizationDtoClientList);
        grid.addColumn(LocalizationDtoClient::getId).setHeader("IDENTITY");
        grid.addColumn(LocalizationDtoClient::getLocalizationName).setHeader("LOCALIZATION NAME");
        grid.addColumn(new NativeButtonRenderer<LocalizationDtoClient>("DELETE", e -> {
            deleteLocalization(e.getId());
            refresh();
        })).setHeader("DELETE LOCALIZATION");
        grid.addComponentColumn(event -> {
            Label label = new Label();
            label.setText(String.valueOf(countGroups(event.getId())));
            return  label;
        });
        add(grid);
    }

    public void addLocalization(LocalizationService localizationService) {
        if (!localizationName.getValue().isEmpty()) {
            LocalizationDtoClient localizationDtoClient = new LocalizationDtoClient();
            localizationDtoClient.setLocalizationName(localizationName.getValue());
            localizationService.createLocalization(localizationDtoClient);
            clearUpForm();
        } else {
            Notification notification = new Notification("Fields cannot be empty");
            notification.setDuration(3000);
            notification.setPosition(Notification.Position.MIDDLE);
            notification.open();
        }
    }

    public void deleteLocalization(Long localizationId) {
        final long count = groupService.getAllGroups().stream()
                .filter(t -> t.getLocalization().getId().equals(localizationId))
                .count();
        if (count > 0) {
            Span span = new Span("You can't delete this Localization, " +
                    "first delete all groups belonging to it");
            Notification notification = new Notification(span);
            notification.setDuration(5000);
            notification.setPosition(Notification.Position.MIDDLE);
            notification.open();
        } else {
            localizationService.deleteLocalization(localizationId);
        }
    }

    public void refresh(){
        localizationDtoClientList = localizationService.getAllLocalization();
        grid.setItems(localizationDtoClientList);
    }

    public int countGroups (Long localizationId) {
        final long count = groupService.getAllGroups().stream()
                .filter(t -> t.getLocalization().getId().equals(localizationId))
                .count();

        return (int) count;
    }

    private void clearUpForm() {
        localizationName.clear();
    }
}