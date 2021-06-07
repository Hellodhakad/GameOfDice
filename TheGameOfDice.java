import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

class Dice {
    private final int min, max;
    Random random = new Random();

    Dice(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public int roll() {
        return random.nextInt(this.max - this.min + 1) + this.min;
    }
}

class Player {
    private final int id;
    private int preDiceNumber;
    private int accumulatePoints;
    private int rank;

    Player(int id) {
        this.id = id;
        this.accumulatePoints = 0;
        this.rank = -1;
    };

    public int getId() {
        return id;
    }

    public int getPreDiceNumber() {
        return preDiceNumber;
    }

    public void setPreDiceNumber(int preDiceNumber) {
        this.preDiceNumber = preDiceNumber;
    }

    public int getAccumulatePoints() {
        return accumulatePoints;
    }

    public void addPoints(int points) {
        this.accumulatePoints += points;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String toJson() {
        return "Player {\n" + "\tid: " + id + ",\n" + "\tpreDiceNumber: " + preDiceNumber + ",\n"
                + "\taccumulatePoints: " + accumulatePoints + ",\n" + "\trank: " + rank + "\n" + '}';
    }
}

public class TheGameOfDice {

    public static void displayDetails(ArrayList<Player> players) {
        System.out.println("ID\tPOINTS");
        for (Player player : players) {
            System.out.println(player.getId() + "\t" + player.getAccumulatePoints());
        }
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Dice dice = new Dice(1, 6);
        int n, m, rank = 1;

        System.out.println("The Game of Dice");
        System.out.println("How many players want to play?");

        n = scanner.nextInt();

        System.out.println("How many points need to win the game?");
        m = scanner.nextInt();

        ArrayList<Player> players = new ArrayList<>();

        for (int i = 1; i <= n; ++i) {
            players.add(new Player(i));
        }

        ArrayList<Player> clonedPlayers = new ArrayList<>(players);

        while (clonedPlayers.size() >= 1) {

            int diceNumber;
            for (int i = 0; i < clonedPlayers.size();) {
                Player player = clonedPlayers.get(i);

                System.out.println("****** Select option for player " + player.getId() + " ******");
                System.out.println("\t\t1. press `r` to roll the dice");
                System.out.println("\t\t2. press `d` to see the users details");

                String ch = scanner.next();
                switch (ch) {
                    case "r":
                        do {
                            diceNumber = dice.roll();
                            System.out.println("in dice roll number " + diceNumber + " occurred");
                            if (diceNumber == 1 && player.getPreDiceNumber() == 1) {
                                System.out.println("\tpenalty");
                                player.setPreDiceNumber(-1);
                            } else {
                                player.setPreDiceNumber(diceNumber);
                                player.addPoints(diceNumber);
                                if (player.getAccumulatePoints() >= m) {
                                    player.setRank(rank++);
                                    clonedPlayers.remove(i);
                                    System.out.println("\tPlayer" + player.getId() + "****rank: " + player.getRank());
                                    break;
                                }
                            }
                        } while (diceNumber == dice.getMax());
                        ++i;
                        break;
                    case "d":
                        displayDetails(players);
                        break;
                    default:
                        System.out.println("Invalid Input");
                        break;
                }
            }
        }

        System.out.println("\nGame Ended!!");

        System.out.println("\nResult with rank wise!!");

        for (Player player : players) {
            System.out.println(player.toJson());
        }
    }
}
