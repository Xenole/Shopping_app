public class StringifyArguments {

    private StringifyArguments() {
    }

    public static String[] cleanArray(String[] array)
    {
        String validations = String.join("", array);
        String[] result = validations.split(",");
        return result;
    }
}
