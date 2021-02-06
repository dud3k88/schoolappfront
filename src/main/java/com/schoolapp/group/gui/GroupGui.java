package com.schoolapp.group.gui;

import com.schoolapp.Menu;
import com.schoolapp.child.service.ChildService;
import com.schoolapp.group.model.GroupDtoClient;
import com.schoolapp.group.service.GroupService;
import com.schoolapp.localiation.model.LocalizationDtoClient;
import com.schoolapp.localiation.service.LocalizationService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
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
import java.util.stream.Collectors;

@Route(value = "Group",  layout = Menu.class)
public class GroupGui extends HorizontalLayout {

    private GroupService groupService;
    private LocalizationService localizationService;
    private ChildService childService;
    private List<GroupDtoClient> groupDtoClients;
    private Grid<GroupDtoClient> grid = new Grid<>();
    private TextField textFieldGroupName = new TextField("GROUP NAME");
    private ComboBox comboBoxMinYear;
    private ComboBox combBoxMaxYear;
    private ComboBox localizationBox = new ComboBox("LOCALIZATION");
    private Button send = new Button("SEND");

    public GroupGui(GroupService groupService, LocalizationService localizationService, ChildService childService) {
        this.groupService = groupService;
        this.localizationService = localizationService;
        this.childService = childService;

        localizationBox.setItems(localizationService.getAllLocalization());
        comboBoxMinYear = setMinValueInComboBox();
        combBoxMaxYear = setMaxValueInComboBox();
        createMainGrid();
        createFrom();
    }

    public void createMainGrid() {
        groupDtoClients = groupService.getAllGroups();

        grid.setItems(groupDtoClients);
        grid.addColumn(GroupDtoClient::getId).setHeader("IDENTITY");
        grid.addColumn(GroupDtoClient::getGroupName).setHeader("GROUP NAME");
        grid.addColumn(GroupDtoClient::getMinYearOfBirth).setHeader("MIN YEAR");
        grid.addColumn(GroupDtoClient::getMaxYearOfBirth).setHeader("MAX YEAR");
        grid.addColumn(GroupDtoClient::getLocalization).setHeader("LOCALIZATION");
        grid.addColumn(new NativeButtonRenderer<GroupDtoClient>("DELETE", e -> {
            deleteGroup(e.getId());
            refresh();
        })).setHeader("DELETE GROUP");
        grid.addComponentColumn(event -> {
            Label label = new Label();
            label.setText(String.valueOf(countChildren(event.getId())));
            return  label;
        }).setHeader("CHILDREN");

        add(grid);
    }

    public void createFrom() {
        FormLayout form = new FormLayout(textFieldGroupName, comboBoxMinYear, combBoxMaxYear, localizationBox, send);
        form.setMaxWidth("300px");
        send.addClickListener(event -> {
            addGroup();
        });
        add(form);
    }

    public void addGroup() {
        GroupDtoClient groupDtoClient = new GroupDtoClient();
        if (!textFieldGroupName.getValue().isEmpty() && !comboBoxMinYear.isEmpty() && !combBoxMaxYear.isEmpty() &&
            !localizationBox.isEmpty()) {
            List<LocalizationDtoClient> collect = localizationService.getAllLocalization().stream()
                    .filter(t -> t.getLocalizationName().equals(String.valueOf(localizationBox.getValue())))
                    .collect(Collectors.toList());
            LocalizationDtoClient localizationDtoClient = collect.get(0);
            groupDtoClient.setGroupName(textFieldGroupName.getValue());
            groupDtoClient.setMinYearOfBirth((int) comboBoxMinYear.getValue());
            groupDtoClient.setMaxYearOfBirth((int) combBoxMaxYear.getValue());
            groupDtoClient.setLocalization(localizationDtoClient);
            groupService.createGroup(groupDtoClient);
            refresh();
        } else {
            Notification notification = new Notification("Fields cannot be empty");
            notification.setDuration(3000);
            notification.setPosition(Notification.Position.MIDDLE);
            notification.open();
        }
    }

    public void deleteGroup(Long groupId) {
        final long count = childService.getAllChild().stream()
                .filter(t -> t.getGroup().getId().equals(groupId))
                .count();
        if (count > 0) {
            Span span = new Span("You can't delete this Group, " +
                    "first delete all child belonging to it");
            Notification notification = new Notification(span);
            notification.setDuration(5000);
            notification.setPosition(Notification.Position.MIDDLE);
            notification.open();
        } else {
            groupService.deleteGroup(groupId);
        }
        refresh();
    }

    public void refresh() {
        groupDtoClients = groupService.getAllGroups();
        grid.setItems(groupDtoClients);
    }

    public int countChildren (Long childrenId) {
        final long count = childService.getAllChild().stream()
                .filter(t -> t.getGroup().getId().equals(childrenId))
                .count();
        return (int) count;
    }
    public ComboBox setMinValueInComboBox() {
        ComboBox comboBoxMin = new ComboBox("MIN YEAR OF GROUP");
        comboBoxMin.setItems(
                2000, 2001, 2002, 2003, 2004, 2005, 2006, 2007, 2008, 2009, 2010,
                2011, 2012, 2013, 2014, 2015, 2016, 2017, 2018, 2029, 2020);
        return comboBoxMin;
    }
    public ComboBox setMaxValueInComboBox() {
        ComboBox comboBoxMax = new ComboBox("MAX YEAR OF GROUP");
        comboBoxMax.setItems(
                2000, 2001, 2002, 2003, 2004, 2005, 2006, 2007, 2008, 2009, 2010,
                2011, 2012, 2013, 2014, 2015, 2016, 2017, 2018, 2029, 2020);
        return comboBoxMax;
    }
}
