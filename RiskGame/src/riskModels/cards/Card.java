
package riskModels.cards;

import java.io.Serializable;

import riskModels.country.Country;

/**
 * This class will handle operations on  cards
 *
 * @author Akshay Shah
 */
public final class Card implements Serializable{

    private final String type;
    private final Country country;

    /**
     * this setter method assigning the country and the card  type
     * @param type card type
     * @param country name of the country
     */
    public Card(String type, Country country) {
        this.type = type;
        this.country = country;
    }

    /**
     * getter method gives the country name and card type
     * @return name of country and type
     */
    public String getName() {
        return country.getCountryName() + ", " + type;
    }

    /**
     * getter method gives type of the card
     * @return string card object
     */
    public String getType() {
        return type;
    }

    /**
     * getter method gives name of the country
     * @return string country object
     */
    public Country getCountry() {
        return country;
    }
}

