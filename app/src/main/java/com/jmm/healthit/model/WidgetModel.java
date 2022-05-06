package com.jmm.healthit.model;

import com.jmm.healthit.utils.CalculatorOption;

public class WidgetModel {

    String title;
    int icon;
    CalculatorOption type;

    public WidgetModel(String title, int icon, CalculatorOption type) {
        this.title = title;
        this.icon = icon;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public int getIcon() {
        return icon;
    }

    public CalculatorOption getType() {
        return type;
    }
}
