package com.miempresa.core.models;

import java.util.List;

public interface CardListShowcase {
    String getSectionTitle();
    boolean isToggleView();
    List<CardItem> getCards();

    interface CardItem {
        String getCardTitle();
        String getCardDescription();
        String getCardImage();
        String getCardLink();
    }
}