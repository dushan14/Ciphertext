package ciphertext;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


/*
 * @author dushan
 */
public class Encryptor extends CryptParent {

    private File inputFile;
    private String encryptionKey;
    private String msg;

    public void encrypt(File file, String key) throws IOException {

        inputFile = file;
        encryptionKey = key;
        msg = IOHandler.getTxtFromFile(file);

        String encryptedMsg = "";

        //encrypt logic here
        encryptedMsg = runEncryptLogic(msg, key);

        IOHandler.writeIntoFile(encryptedMsg, inputFile, OutType.encrypted);

    }

    private String runEncryptLogic(String text, String key) {

        StringBuilder finalText = new StringBuilder();
        int keyLength = key.length();

        String tempFuncResult = pushForwardChars("ciphertext", key);

        for (int i = 0; i < text.length(); i = i + keyLength) {
            int lim = i + keyLength;
            if (lim >= text.length()) {
                lim = text.length();
            }

            String part = text.substring(i, lim);

            String firstXor = getXor(part, tempFuncResult);

            String secondXor = getXor(firstXor, key);

            finalText.append(secondXor);

            tempFuncResult = pushForwardChars(part, key);

        }
        return finalText.toString();
    }

}
