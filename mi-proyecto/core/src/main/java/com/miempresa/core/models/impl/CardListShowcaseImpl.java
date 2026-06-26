package com.miempresa.core.models.impl;

import com.miempresa.core.models.CardListShowcase;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Model(
    adaptables = Resource.class,
    adapters = CardListShowcase.class,
    resourceType = "mi-proyecto/components/card-list-showcase",
    defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class CardListShowcaseImpl implements CardListShowcase {

    @ValueMapValue
    private String sectionTitle;

    @ValueMapValue
    private boolean toggleView;

    @SlingObject
    private Resource resource;

    private List<CardItem> cardItems = new ArrayList<>();

    @PostConstruct
    protected void init() {
        Resource cardsNode = resource.getChild("cards");
        if (cardsNode != null) {
            for (Resource cardResource : cardsNode.getChildren()) {
                cardItems.add(new CardItemImpl(cardResource));
            }
        }
    }

    @Override
    public String getSectionTitle() { return sectionTitle; }

    @Override
    public boolean isToggleView() { return toggleView; }

    @Override
    public List<CardItem> getCards() { return cardItems; }

    // Inner class POJO
    public static class CardItemImpl implements CardItem {
        private final String cardTitle;
        private final String cardDescription;
        private final String cardImage;
        private final String cardLink;

        public CardItemImpl(Resource resource) {
            this.cardTitle       = resource.getValueMap().get("cardTitle", String.class);
            this.cardDescription = resource.getValueMap().get("cardDescription", String.class);
            this.cardImage       = resource.getValueMap().get("cardImage", String.class);
            this.cardLink        = resource.getValueMap().get("cardLink", String.class);
        }

        @Override public String getCardTitle()       { return cardTitle; }
        @Override public String getCardDescription() { return cardDescription; }
        @Override public String getCardImage()       { return cardImage; }
        @Override public String getCardLink()        { return cardLink; }
    }
}