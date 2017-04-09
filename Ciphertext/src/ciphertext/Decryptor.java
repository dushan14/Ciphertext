package ciphertext;

import java.io.File;
import java.io.IOException;

/*
 * @author dushan
 */
public class Decryptor extends CryptParent {

    private File inputFile;
    private String decryptionKey;
    private String encryptedMsg;

    public void decrypt(File file, String key) throws IOException {

        inputFile = file;
        decryptionKey = key;
        encryptedMsg = IOHandler.getTxtFromFile(file);

        String decryptedMsg = "";

        //decrypt logic here
        decryptedMsg = runDecryptLogic(encryptedMsg, key);

        IOHandler.writeIntoFile(decryptedMsg, inputFile, OutType.decrypted);

    }

    private String runDecryptLogic(String text, String key) {

        StringBuilder finalText = new StringBuilder();

        int keyLength = key.length();

        String tempFuncResult = pushForwardChars("ciphertext", key);

        for (int i = 0; i < text.length(); i = i + keyLength) {
            int lim = i + keyLength;
            if (lim >= text.length()) {
                lim = text.length();
            }

            String part = text.substring(i, lim);

            String secondXorBreak = getXor(part, key);

            String firstXorBreak = getXor(secondXorBreak, tempFuncResult);

            finalText.append(firstXorBreak);

            tempFuncResult = pushForwardChars(firstXorBreak, key);

        }
        return finalText.toString();
    }

}
