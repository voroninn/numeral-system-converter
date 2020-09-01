package converter;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int sourceRadix = 0;
        String sourceNumber = "";
        int targetRadix = 0;
        boolean hasError = false;

        try (Scanner scanner = new Scanner(System.in)) {
            sourceRadix = scanner.nextInt();
            sourceNumber = scanner.next();
            targetRadix = scanner.nextInt();
        } catch (Exception e) {
            hasError = true;
        }

        if (sourceRadix <= 0 || sourceRadix > 36 || targetRadix <= 0 || targetRadix > 36) {
            hasError = true;
        }

        if (!hasError) {
            String result = "";

            if (!sourceNumber.contains(".")) {
                result = convertInteger(sourceRadix, sourceNumber, targetRadix);
            } else {
                result = convertFractional(sourceRadix, sourceNumber, targetRadix);
            }

            System.out.println(result);
        } else {
            System.out.println("error");
        }
    }

    public static String convertInteger(int sourceRadix, String sourceNumber, int targetRadix) {
        int decimalNumber =
                sourceRadix == 1 ?
                        sourceNumber.length() : Integer.parseInt(sourceNumber, sourceRadix);

        StringBuilder result = new StringBuilder();
        if (targetRadix == 1) {
            result.append("1".repeat(Math.max(0, decimalNumber)));
        } else {
            result.append(Integer.toString(decimalNumber, targetRadix));
        }
        return result.toString();
    }

    public static String convertFractional(int sourceRadix, String sourceNumber, int targetRadix) {
        String integerPart = sourceNumber.substring(0, sourceNumber.indexOf('.'));
        String fractionalPart = sourceNumber.substring(sourceNumber.indexOf('.') + 1);
        String decimalInteger = convertInteger(sourceRadix, integerPart, 10);
        double decimalFraction = 0.0;

        for (int i = 0; i < fractionalPart.length(); i++) {
            decimalFraction += Character.getNumericValue(fractionalPart.charAt(i)) / Math.pow(sourceRadix, i + 1);
        }

        StringBuilder result = new StringBuilder(convertInteger(10, decimalInteger, targetRadix) + ".");
        for (int i = 0; i < 5; i++) {
            int appendable = (int) (decimalFraction * targetRadix);
            if (appendable > 9) {
                result.append((char) (appendable + 87));
            } else {
                result.append(appendable);
            }
            decimalFraction = decimalFraction * targetRadix - appendable;
        }
        return result.toString();
    }
}
