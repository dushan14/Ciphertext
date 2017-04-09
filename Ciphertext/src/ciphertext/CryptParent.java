package ciphertext;

/*
 * @author dushan
 */

public abstract class CryptParent {

     public String getXor(String st1, String st2) {

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < st1.length(); i++) {
            sb.append((char) (st1.charAt(i) ^ st2.charAt(i % st2.length())));
        }

        String result = sb.toString();

        return result;
    }

    private int stringToBinaryCount(String text) {
        String bString = "";
        String temp = "";
        for (int i = 0; i < text.length(); i++) {
            temp = Integer.toBinaryString(text.charAt(i));
            bString += temp + " ";
        }
        int count = 0;
        for (int i = 0; i < bString.length(); i++) {

            if (bString.charAt(i) == "1".charAt(0)) {
                count += 1;

            }
        }
        return count;

    }

    public String pushForwardChars(String text, String key) {

        int binCount = stringToBinaryCount(key);

        char[] temp = text.toCharArray();

        StringBuilder sb = new StringBuilder();
        for (char x : temp) {
            int y = (int) x + binCount;

            sb.append((char) y);

        }

        return sb.toString();
    }
}
