public class RabinKarpAlgorithm {


    /**
     * This algorithm returns the starting index of the matching substring.
     * This method will return -1 if no matching substring is found, or if the input is invalid.
     */
    public static int rabinKarp(String input, String pattern) {

        int patLen = pattern.length();
        RollingString pat = new RollingString(pattern, patLen);
        int patHash = pat.hashCode();

        RollingString rs = new RollingString(input.substring(0, patLen), patLen);
        if (rs.hashCode() == patHash) {
            return 0;
        }
        for (int i = patLen; i < input.length(); i++) {
            rs.addChar(input.charAt(i));
            if (rs.hashCode() == patHash) {
                return i - (patLen -1);
            }
        }
        return -1;
    }

}
