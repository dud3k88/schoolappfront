package com.schoolapp;

import com.schoolapp.child.gui.ChildGui;
import com.schoolapp.group.gui.GroupGui;
import com.schoolapp.localiation.gui.LocalizationGui;
import com.schoolapp.parent.gui.ParentGui;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

import java.time.LocalDate;

@Route("Menu")
@CssImport("./menuStyle.css")

public class Menu extends AppLayout {

    public Menu() {
        createHeader();
        createDrawer();
    }

    private void createHeader() {
        H3 logo = new H3("School App");
        logo.addClassName("logo");
        Label time = new Label();
        LocalDate timeNow = LocalDate.now();
        time.setText(String.valueOf(timeNow));
        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo, time);
        header.addClassName("header");
        header.setWidth("100%");
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        addToNavbar(header);
    }

    public void createDrawer() {
        RouterLink dashboard = new RouterLink("Dashboard", Dashboard.class);
        dashboard.setHighlightCondition(HighlightConditions.sameLocation());
        addToDrawer(new VerticalLayout(dashboard,
                new RouterLink("Children", ChildGui.class),
                new RouterLink("Parents", ParentGui.class),
                new RouterLink("Groups", GroupGui.class),
                new RouterLink("Localization", LocalizationGui.class)
        ));
    }
}



