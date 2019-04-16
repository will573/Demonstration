
package poker;
import java.util.*;

public class Poker {

    static class card{
    //create card template
        public card(int num, String sym) {
            number = num;
            suit = sym;
        }
        int number;    //A=1 K=13 Q=12 J=11
        String suit;      //Spade=S Heart=H Diamond=D Club=C

        int getNum(){
            return number;
        }

        String getSuit(){
            return suit;
        }
    }
    static int handRanker(card[] hand)
        {
            

            int[] numCheck = new int[5];//lists of just all numbers
            int[] ofAKind = new int[13];//used for multiple numbers(note: add 1 to offset difference) IE 0 is ACE, 1 is 2
            for (int i = 0; i < ofAKind.length; i++){
                ofAKind[i] = 0;
            }//sets up ofAkind
            

            for (int i = 0; i < hand.length; i++)
            {
                ofAKind[hand[i].getNum() - 1]++;// minus one is for offest
            }
            for (int i = 0; i < numCheck.length; i++)
            {
                numCheck[i] = hand[i].getNum();
            }
            Arrays.sort(numCheck);
            
            
            
            //royal flush
            Boolean royal = true;
            for (int i = 0; i < 5; i++)
            {
                if (!(hand[i].getNum() > 9 || hand[i].getNum() == 1)) {//checks for non royal flush nnumbers
                    royal = false;
                    break; }
                if (i != 0 && (!hand[i - 1].getSuit().equals(hand[i].getSuit()))) {//checks for different suits ie: not a royal flush 
                    royal = false;
                    break;
                }
            }
            String royalcards = "";
            if (royal)
            {
                for (int i = 0; i < 5; i++)
                {
                    royalcards += hand[i].getNum() + " ";
                }


                if (!(royalcards.contains("1") && royalcards.contains("10") && royalcards.contains("11")
                    && royalcards.contains("12") && royalcards.contains("13"))){
                    //checks if all the royal flush cards are present
                    royal = false;
                }
            }
            if (royal) {
                return 1; }

            //straight flush 
            //not to self cannot K A 2 3 4 only between A-5 and 10-A

            Boolean straightFlush = true;

            for (int i = 0; i < hand.length; i++)
            {
                if (numCheck[0] == 1) {//A 2 3 4 5
                    if (numCheck[i] != i + 1) {
                        straightFlush = false;
                        break;
                    }
                } else if (numCheck[4] == 1) {//10 J Q K A
                    if ((i != 4) && (numCheck[i] != i + 10)) {
                        straightFlush = false;
                        break;
                    }
                } else {
                    if (i != 0) {//skip frst to compare
                        if ((numCheck[i - 1] + 1) != numCheck[i])
                        {
                            straightFlush = false;
                            break;
                        }

                    }
                }
                if (i != 0)// compares suit
                {
                    if (!hand[i - 1].getSuit().equals(hand[i].getSuit()))
                    {
                        straightFlush = false;
                        break;
                    }
                }

            }
            if (straightFlush)
            {
                return 2;
            }

            //four of a kind
            for (int i = 0; i < ofAKind.length; i++) {
                if (ofAKind[i] == 4) {
                    return 3; }
            }

            //full house
            Boolean threeOfAKind = false, twoOfAKind = false;
            for (int i = 0; i < ofAKind.length; i++)
            {
                if (ofAKind[i] == 3) {
                    threeOfAKind = true;
                }
                if (ofAKind[i] == 2) {
                    twoOfAKind = true;
                }
            }
            if (threeOfAKind && twoOfAKind) {
                return 4;
            }

            //flush
            Boolean flush = true;
            for (int i = 1; i < hand.length; i++){
                if (!((hand[i - 1].getSuit()).equals(hand[i].getSuit())))
                {
                    flush = false;
                    break;
                }
            }
            if (flush) {
                return 5;
            }

            //straight 
            Boolean straight = true;
            for (int i = 0; i < hand.length; i++)
            {
                if (numCheck[0] == 1)
                {//A 2 3 4 5
                    if (numCheck[i] != i + 1)
                    {
                        straight = false;
                    }
                }
                else if (numCheck[4] == 1)
                {//10 J Q K A
                    if ((i != 4) && (numCheck[i] != i + 10))
                    {
                        straight = false;
                    }
                }
                else
                {
                    if (i != 0)
                    {//skip frst to compare
                        if (numCheck[i] != numCheck[i - 1] + 1)
                        {
                            straight = false;
                        }

                    }
                }
            }
            if (straight) {
                return 6;
            }
            //three of a kind
            if (threeOfAKind) { 
            return 7;
            }
            //two pair
            int numOfPairs=0;
            for(int i = 0; i < ofAKind.length; i++)
            {
                if (ofAKind[i] == 2) {
                    numOfPairs++;
                }
            }
            if (numOfPairs == 2) {
                return 8;
            }
            //pair 
            if (twoOfAKind)
            {
                return 9;
            }
            //high card

            return 10;
        }
    static String getHandRankName(int hand) {//return hand rank name
            switch(hand){
                case 1:
                    return "Royal Flush";
                case 2:
                    return "Straight Flush";
                case 3:
                    return "Four of a Kind";
                case 4:
                    return "Full House";
                case 5:
                    return "Flush";
                case 6:
                    return "Straight";
                case 7:
                    return "Three of a Kind";
                case 8:
                    return "Two Pair";
                case 9:
                    return "Pair";
                default:
                    return "Highest Card";
                    
            }
        }
        static int trueRank(card[] playerHand, card[] tableHand){

            card[] trueTable = tableHand.clone();
            card[] playHand = playerHand.clone();
            
            card[] pseudoHand = tableHand.clone();

            int rank= handRanker(pseudoHand);//replace no cards
            int newRank;
            for(int i = 0; i < 5; i++){//replace using first card

                pseudoHand[i] = playHand[0];//replace
                

                newRank = handRanker(pseudoHand);
                if (rank > newRank)
                {
                    rank = newRank;//update
                }
                pseudoHand[i] = trueTable[i];
            }
            for (int i = 0; i < 5; i++){//replace using second card
                pseudoHand[i] = playHand[1];
                newRank = handRanker(pseudoHand);
                if (rank > newRank)
                {
                    rank = newRank;//update
                }
                pseudoHand[i] = trueTable[i];
            }
            for (int i = 0; i < 5; i++){//replace two cards
                for(int j = 0; j < 5; j++){
                    if(i!=j){ //prevents only replacing one card which has aleardy been done
                        pseudoHand[i] = playHand[0];
                        pseudoHand[j] = playHand[1];
                        newRank = handRanker(pseudoHand);
                        if (rank > newRank){
                            
                            rank = newRank;//update
                        }

                        pseudoHand[i] = trueTable[i];
                        pseudoHand[j] = trueTable[j];
                    }

                }
            }
            return rank;
        }
        static void displayCard(card display)
        {
            if (display.getNum() == 1)
            {
                System.out.print("A"+display.getSuit());
            }
            else
            {
                System.out.print(display.getNum() + display.getSuit());
            }
            System.out.print("\t");
        }
    
    
    
    public static void main(String[] args) {
        System.out.println("Poker Program start");
            Scanner scan = new Scanner(System.in);
            int decksize = 52;

            //initate deck
            card[] deck =new card[decksize];
            
            int deckcreate = 0;
                for(int i=0; i<13; i++){
                    for(int j=0; j<4; j++){
                    String newsuit="X";//default to prevent and check errors
                    if (j == 0){
                        newsuit = "S";
                    }else if (j == 1){
                        newsuit = "H";
                    }else if (j == 2){
                        newsuit = "C";
                    }else if (j == 3){
                        newsuit = "D";
                    }
                    card card1 = new card(i+1, newsuit);//i+1 = card number
                    deck[deckcreate]= card1;
                    deckcreate++;
                }
            }



            int players = 0;
            while (players < 2 || players > 6){
                System.out.println("How many players? (2-6)");
                players = scan.nextInt();
            }

        //repeat loop start
        Boolean playAgain = true;
        while(playAgain){
                //shuffle deck
                System.out.println("Shuffling");
                for (int i = 0; i < 1000; i++)
                {
                    Random rnd = new Random();
                    int card1 = rnd.nextInt(52);
                    int card2 = rnd.nextInt(52);
                    card temp = deck[card1];
                    deck[card1] = deck[card2];
                    deck[card2] = temp;
                }
                //play
                //sets table cards
                card[] tableHand = new card[5];
                for(int i = 0; i < 5; i++)
                {
                    tableHand[i] = deck[i];
                }

                int[] playerRanks = {10,10,10,10,10,10};//defualt ranks (10 lowest, 1 highest)
                //sets players cards
                //players (0-4 is table 0,1,2,3,4) 5,6   7,8   9,10   11,12   13,14   15,16 
                card player1L = deck[5];
                card player1R = deck[6];
                
                card player2L = deck[7];
                card player2R = deck[8];
                
                card player3L = deck[9];
                card player3R = deck[10];
                    
                card player4L = deck[11];
                card player4R = deck[12];
                
                card player5L = deck[13];
                card player5R = deck[14];
                
                card player6L = deck[15];
                card player6R = deck[16];
                
                String betting="wait";//potentially used to bet
                //stage blind

                System.out.print("your hand is: ");
                displayCard(player1L);
                displayCard(player1R);
                System.out.println();
                
                System.out.println("Betting stage, press enter to continue:");
                
                betting = scan.nextLine();
                betting = scan.nextLine();
                //stage 3 cards
                System.out.println("The first 3 cards on the table are:");
                for (int i = 0; i < 3; i++)
                {
                    displayCard(tableHand[i]);
                }
                System.out.println();
                System.out.println("Betting stage, press enter to continue:");
                betting = scan.nextLine();

                //stage 4 cards
                System.out.println("The first 4 cards on the table are:");
                for (int i = 0; i < 4; i++)
                {
                    displayCard(tableHand[i]);
                }
                System.out.println();

                System.out.println("Betting stage, press enter to continue:");
                betting = scan.nextLine();

                //stage 5 cards
                System.out.println("All 5 cards on the table are:");
                for (int i = 0; i < 5; i++)
                {
                    displayCard(tableHand[i]);
                }
                System.out.println();
                int tableRank = handRanker(tableHand);
                System.out.println("Tha Table Hand is: "+getHandRankName(tableRank));


                System.out.println("Betting stage, press enter to continue:");
                betting = scan.nextLine();

                //final betting stage 

                System.out.println("Final betting stage, press enter to continue:");
                betting = scan.nextLine();

                //reveal stage



               System.out.println("player 1's cards are: ");
                displayCard(player1L);
                displayCard(player1R);
                System.out.println();
                card[] player1Hand = { player1L, player1R };
                playerRanks[0] = trueRank(player1Hand,tableHand);
                System.out.println("Your Hand Rank is: "+getHandRankName(playerRanks[0]));
                System.out.println();

                System.out.println("player 2's cards are: ");
                displayCard(player2L);
                displayCard(player2R);
                System.out.println();
                card[] player2Hand = { player2L, player2R };
                playerRanks[1] = trueRank(player2Hand,tableHand);
                System.out.println("Player 2's Hand Rank is: " + getHandRankName(playerRanks[1]));
                System.out.println();

                if (players > 2)
                {
                    System.out.println("player 3's cards are: ");
                    displayCard(player3L);
                    displayCard(player3R);
                    System.out.println();
                    card[] player3Hand = { player3L, player3R };
                    playerRanks[2] = trueRank(player3Hand, tableHand);
                    System.out.println("Player 3's Hand Rank is: " + getHandRankName(playerRanks[2]));
                    System.out.println();
                }
                
                if (players > 3)
                {
                    System.out.println("player 4's cards are: ");
                    displayCard(player4L);
                    displayCard(player4R);
                    System.out.println();
                    card[] player4Hand = { player4L, player4R };
                    playerRanks[3] = trueRank(player4Hand, tableHand);
                    System.out.println("Player 4's Hand Rank is: " + getHandRankName(playerRanks[3]));
                    System.out.println();
                }
                if (players > 4)
                {
                    System.out.println("player 5's cards are: ");
                    displayCard(player5L);
                    displayCard(player5R);
                    System.out.println();
                    card[] player5Hand = { player5L, player5R };
                    playerRanks[4] = trueRank(player5Hand, tableHand);
                    System.out.println("Player 5's Hand Rank is: " + getHandRankName(playerRanks[4]));
                    System.out.println();
                }
                if (players > 5)
                {//max
                    System.out.println("player 6's cards are: ");
                    displayCard(player6L);
                    displayCard(player6R);
                    System.out.println();
                    card[] player6Hand = { player6L, player6R };
                    playerRanks[5] = trueRank(player6Hand, tableHand);
                    System.out.println("Player 6's Hand Rank is: " + getHandRankName(playerRanks[5]));
                    System.out.println();
                }

                int highestRank = 10; ;
                for(int i=0;i<players; i++)
                {
                    if (highestRank > playerRanks[i])
                    {
                        highestRank = playerRanks[i];
                    }
                }
                System.out.println("The Highest Rank is: "+ getHandRankName(highestRank));
                
                System.out.println("Play Again? (type Yes or No)");
                String play = scan.next();

                if(play.equalsIgnoreCase("Yes")){
                    playAgain = true;
                }else
                { playAgain = false; }
                
            }
            System.out.println("Press any key to exit");
            System.out.println();
            //repeat loop end

        }

       
        
        
        
        
    }
    

