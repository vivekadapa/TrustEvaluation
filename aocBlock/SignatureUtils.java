public class SignatureUtils {
    public static String sign(String secretKey, String... contents) {
        StringBuilder dataToSign = new StringBuilder(secretKey);
        for (String content : contents) {
            dataToSign.append(content);
        }
        int hashCode = dataToSign.toString().hashCode();
        return String.valueOf(hashCode);
    }
}
