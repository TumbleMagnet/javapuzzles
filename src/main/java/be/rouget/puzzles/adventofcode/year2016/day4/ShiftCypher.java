package be.rouget.puzzles.adventofcode.year2016.day4;

public class ShiftCypher {

    public static char decrypt(char encryptedChar, int shiftCount) {
        int actualShift = shiftCount % 26;
        int decrypted = encryptedChar + actualShift;
        if (decrypted > 122) {
            decrypted -= 26;
        }
        return (char) decrypted;
    }
}
