package card;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Card {

    private final Rank rank;
    private final Suit suit;
    private static final String BASE_IMG_URL = "https://deckofcardsapi.com/static/img/";
    private static final String IMG_URL_SUFFIX = ".png";
    private String imgCode;
    private String imageUrl;

    private final static Map<String, Card> CARD_CACHE = initCache();

    private static Map<String, Card> initCache(){

        final Map<String, Card> cache = new HashMap<>();
        for (final Suit suit : Suit.values()){
            for(final Rank rank : Rank.values()){

                cache.put(cardKey(rank,suit), new Card(rank,suit));
            }
        }
        //Collection.unmodifiableMap -> makes the collection unchangeable!!
        return Collections.unmodifiableMap(cache);
    }

    public static Card getCard(final Rank rank, final Suit suit){

        final Card card = CARD_CACHE.get(cardKey(rank, suit));
        if(card != null){
            return card;
        }else{
            throw new RuntimeException("Invalid Card" + rank + " " + suit);
        }
    }

    private static String cardKey(final Rank rank, final Suit suit){

        return rank + " of " + suit;
    }

    private Card (final Rank rank, final Suit suit){
        this.rank = rank;
        this.suit = suit;
        this.imgCode = assessImgCode();

    }

    @Override
    public String toString() {
        return String.format("%s of %s", this.rank, this.suit);
    }

    public String assessImgCode(){
        char charCodeForImg;
        switch (this.rank.getRankValue()){
            case 14:
                charCodeForImg = 'A';
                return String.valueOf(charCodeForImg) + this.suit.getSuitValue();
            case 13:
                charCodeForImg = 'K';
                return String.valueOf(charCodeForImg) + this.suit.getSuitValue();
            case 12:
                charCodeForImg = 'Q';
                return String.valueOf(charCodeForImg)+this.suit.getSuitValue();
            case 11:
                charCodeForImg = 'J';
                return String.valueOf(charCodeForImg)+this.suit.getSuitValue();
            case 10:
                charCodeForImg = '0';
                return String.valueOf(charCodeForImg)+this.suit.getSuitValue();
            default:
                return Integer.toString(this.rank.getRankValue()) + this.suit.getSuitValue();
        }

    }


    public String getImageUrl(){
        return BASE_IMG_URL + imgCode + IMG_URL_SUFFIX;
    }

    enum Suit {
        DIAMONDS ('D'),
        CLUBS ('C'),
        HEARS ('H'),
        SPADES ('S') ;
        private final char suitCode;

        Suit(final char suitCode) {
            this.suitCode=suitCode;
        }

        public String getSuitValue(){
            return String.valueOf(this.suitCode);
        }
    }
    enum Rank {
        TWO (2),
        THREE (3),
        FOUR (4),
        FIVE (5),
        SIX (6),
        SEVEN (7),
        EIGHT (8),
        NINE (9),
        TEN (10),
        JACK (11),
        QUEEN (12),
        KING (13),
        ACE (14);

        private final int rankValue;

        Rank (final int rankValue){
            this.rankValue = rankValue;
        }

        public int getRankValue(){
            return this.rankValue;
        }

    }

}
