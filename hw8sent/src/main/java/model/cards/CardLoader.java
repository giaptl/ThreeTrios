package model.cards;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Utility class for loading cards from a file.
 * The card data is parsed and stored as {@link Card} objects in a list.
 */
public class CardLoader {

  /**
   * Loads a list of {@link Card} objects from a specified file.
   *
   * @param filename the path to the card configuration file
   * @return a list of {@link Card} objects loaded from the file
   */
  public static List<Card> loadCards(String filename) {
    List<Card> cards = new ArrayList<>();

    try (Scanner scanner = new Scanner(new File(filename))) {

      while (scanner.hasNextLine()) {
        String[] data = scanner.nextLine().split(" ");
        String name = data[0];
        int north = parseValue(data[1]);
        int south = parseValue(data[2]);
        int east = parseValue(data[3]);
        int west = parseValue(data[4]);

        cards.add(new Card(name, north, south, east, west));
      }
    } catch (FileNotFoundException e) {
      System.err.println("Card file not found: " + filename);
    }

    return cards;
  }

  /**
   * Parses an attack value from a string. If the string is "A", the value
   * is treated as 10 (maximum attack). Otherwise, it is converted to an integer.
   *
   * @param value the string representation of the attack value
   * @return the integer attack value
   */
  private static int parseValue(String value) {
    return value.equals("A") ? 10 : Integer.parseInt(value);
  }
}
