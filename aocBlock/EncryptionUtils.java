public class EncryptionUtils {
    public static String encrypt(String publicKey, String content) {
        StringBuilder encryptedText = new StringBuilder();
        for (int i = 0; i < content.length(); i++) {
            char encryptedChar = (char) (content.charAt(i) ^ publicKey.charAt(i % publicKey.length()));
            encryptedText.append(encryptedChar);
        }
        return encryptedText.toString();
    }

    public static String decrypt(String publicKey, String encryptedContent) {
        StringBuilder decryptedText = new StringBuilder();
        for (int i = 0; i < encryptedContent.length(); i++) {
            char decryptedChar = (char) (encryptedContent.charAt(i) ^ publicKey.charAt(i % publicKey.length()));
            decryptedText.append(decryptedChar);
        }
        return decryptedText.toString();
    }

    public static String hash(String input) {
        int hashCode = input.hashCode();
        return "hashed_" + hashCode;
    }
}

 