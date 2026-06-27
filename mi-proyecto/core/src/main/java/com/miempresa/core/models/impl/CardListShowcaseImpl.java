package com.miempresa.core.models.impl;

import com.miempresa.core.models.CardListShowcase;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.Self;
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

    @Self
    private Resource resource;

    @ChildResource(name = "cards")
    private Resource cardsResource;

    private final List<CardItem> cardItems = new ArrayList<>();

    @PostConstruct
    private void init() {
        Resource cards = cardsResource != null
            ? cardsResource
            : (resource != null ? resource.getChild("cards") : null);

        if (cards != null) {
            for (Resource cardResource : cards.getChildren()) {
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

    public static class CardItemImpl implements CardItem {
        private final String cardTitle;
        private final String cardDescription;
        private final String cardImage;
        private final String cardLink;

        public CardItemImpl(Resource resource) {
            ValueMap vm = resource.getValueMap();
            this.cardTitle       = vm.get("cardTitle", String.class);
            this.cardDescription = vm.get("cardDescription", String.class);
            this.cardImage       = vm.get("cardImage", String.class);
            this.cardLink        = vm.get("cardLink", String.class);
        }

        @Override public String getCardTitle()       { return cardTitle; }
        @Override public String getCardDescription() { return cardDescription; }
        @Override public String getCardImage()       { return cardImage; }
        @Override public String getCardLink()        { return cardLink; }
    }
}
