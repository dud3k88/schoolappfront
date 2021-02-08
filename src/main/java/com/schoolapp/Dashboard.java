package com.schoolapp;

import com.schoolapp.child.service.ChildService;
import com.schoolapp.euro.service.EuroService;
import com.schoolapp.group.service.GroupService;
import com.schoolapp.localiation.service.LocalizationService;
import com.schoolapp.parent.service.ParentService;
import com.schoolapp.weather.service.WeatherService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "", layout = Menu.class)
@CssImport("./menuStyle.css")
public class Dashboard extends VerticalLayout {

   private final ChildService childService;
   private final ParentService parentService;
   private final WeatherService weatherService;
   private final GroupService groupService;
   private final LocalizationService localizationService;
   private final EuroService euroService;
   private Label euroValueLabel = new Label("Euro Value");
   private Div weather = new Div();
   private Div buttonsDiv = new Div();

    public Dashboard(WeatherService weatherService, ChildService childService, ParentService parentService,
                     GroupService groupService, LocalizationService localizationService, EuroService euroService) {
        this.weatherService = weatherService;
        this.childService = childService;
        this.parentService = parentService;
        this.groupService = groupService;
        this.localizationService = localizationService;
        this.euroService = euroService;

        createMainButtonNavigation();
        createWeatherAndEuroView();

    }

    public void createMainButtonNavigation() {
        Button childrenButton = new Button("CHILDREN (" + childrenStats() + ")");
        childrenButton.addClassName("childrenButton");
        childrenButton.addClickListener(event -> {
            childrenButton.getUI().ifPresent(ui -> {ui.navigate("Child");});
        });

        Button parentsButton = new Button("PARENTS (" + parentStats() + ")");
        parentsButton.addClassName("parentsButton");
        parentsButton.addClickListener(event -> {
            parentsButton.getUI().ifPresent(ui -> {ui.navigate("Parent");});
        });

        Button groupButton = new Button("GROUPS (" + (groupStats()) + ")");
        groupButton.addClassName("groupButton");
        groupButton.addClickListener(event -> {
            groupButton.getUI().ifPresent(ui -> {ui.navigate("Group");});
        });

        Button localizationButton = new Button("LOCALIZATIONS (" + localizationStats() + ")");
        localizationButton.addClassName("localizationButton");
        localizationButton.addClickListener(event -> {
            localizationButton.getUI().ifPresent(ui -> {ui.navigate("Localization");});
        });

        buttonsDiv.add(childrenButton, parentsButton, groupButton, localizationButton);
        buttonsDiv.setClassName("buttonsDiv");
        add(buttonsDiv);
    }

    private void createWeatherAndEuroView() {
        euroValueLabel.setText("EURO VALUE\n: " + String.valueOf(euroService.getEuroValue().getEuroValue()));
        euroValueLabel.addClassName("euroValueLabel");
        weather = weatherInformation();
        HorizontalLayout weatherAndEuro = new HorizontalLayout(weather, euroValueLabel);
        weatherAndEuro.addClassName("weatherAndEuro");
        add(weatherAndEuro);
    }

    private int childrenStats() {
        int size = childService.getAllChild().size();
        return size;
    }

    private int parentStats() {
        int size = parentService.getParents().size();
        return size;
    }
    private int groupStats() {
        int size = groupService.getAllGroups().size();
        return size;
    }
    private int localizationStats() {
        int size = localizationService.getAllLocalization().size();
        return size;
    }

    public Div weatherInformation() {
        Span tempSpan = new Span("Temperature: ");
        tempSpan.setClassName("tempSpan");
        Label tempLabel = new Label();
        tempLabel.setText(String.valueOf(weatherService.getWeather().getTemperature()));
        Div tempDiv = new Div(tempSpan, tempLabel);

        Span pressureSpan = new Span("Pressure: ");
        Label pressureLabel = new Label();
        pressureLabel.setText(String.valueOf(weatherService.getWeather().getPressure()));
        Div pressureDiv = new Div(pressureSpan, pressureLabel);

        Span humiditySpan = new Span("Humidity: ");
        Label humidityLabel = new Label();
        humidityLabel.setText(String.valueOf(weatherService.getWeather().getHumidity()));
        Div humidityDiv = new Div(humiditySpan, humidityLabel);

        Span windSpeedSpan = new Span("Wind Speed: ");
        Label windSpeedLabel = new Label();
        windSpeedLabel.setText(String.valueOf(weatherService.getWeather().getWindSpeed()));
        Div windSpeedDiv = new Div(windSpeedSpan, windSpeedLabel);

        Label city = new Label();
        city.setText("WROCŁAW FORECAST");

        weather.addClassName("weather");
        weather.add(city, tempDiv, pressureDiv, humidityDiv, windSpeedDiv);

        return weather;
    }
}
