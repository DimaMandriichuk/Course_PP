package com.example.dimakurs.customCells;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.ListCell;

public class PromptCell<T> extends ListCell<T> {
    private final StringProperty promptText = new SimpleStringProperty();

    public PromptCell(String promptText) {
        this.promptText.addListener((obs, oldText, newText) -> {
            if (isEmpty() || getItem() == null) {
                setText(newText);
            }
        });
        setPromptText(promptText);
    }

    public StringProperty promptTextProperty() {
        return promptText ;
    }

    public final String getPromptText() {
        return promptTextProperty().get();
    }

    public final void setPromptText(String promptText) {
        promptTextProperty().set(promptText);
    }

    @Override
    protected void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setText(getPromptText());
        } else {
            setText(item.toString());
        }
    }
}
