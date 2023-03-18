import java.util.Scanner;

public class Main {
    public static Scanner in = new Scanner(System.in);
    private static int[] arabic = {0, 1, 4, 5, 9, 10, 40, 50, 90, 100, 400, 500, 900, 1000};
    private static String[] numerals = {"", "I", "IV", "V", "IX", "X", "XL", "L", "XC", "C", "CD", "D", "CM", "M"};

    public static int toArabic(String roman) {
        int result = 0;
        for (int i = arabic.length - 1; i >= 0; i--) {
            while (roman.indexOf(numerals[i]) == 0 && numerals[i].length() > 0) {
                result += arabic[i];
                roman = roman.substring(numerals[i].length());
            }
        }
        return result;
    }

    public final static int findFloor(final int number, final int firstIndex, final int lastIndex) {
        if (firstIndex == lastIndex)
            return firstIndex;
        if (arabic[firstIndex] == number)
            return firstIndex;
        if (arabic[lastIndex] == number)
            return lastIndex;
        final int median = (lastIndex + firstIndex) / 2;
        if (median == firstIndex)
            return firstIndex;
        if (number == arabic[median])
            return median;
        if (number > arabic[median])
            return findFloor(number, median, lastIndex);
        else
            return findFloor(number, firstIndex, median);

    }

    public final static String toRoman(final int number) {
        int floorIndex = findFloor(number, 0, arabic.length - 1);
        if (number == arabic[floorIndex])
            return numerals[floorIndex];
        return numerals[floorIndex] + toRoman(number - arabic[floorIndex]);
    }

    public static String calc(String input) {
        int num1 = 0, num2 = 0;
        String num_a1 = "", num_a2 = "";
        boolean operand = true;
        char operand_c = 0;
        for (char c : input.toCharArray()) {
            //System.out.println(c);
            if (c >= '0' && c <= '9' && operand) {
                num1*=10;
                num1 +=(Character.valueOf(c) - 48);
            }
            if ((c >= '0' && c <= '9') && !(operand)) {
                num2 = num2 * 10 + (Character.valueOf(c) - 48);
            }
            if (c == '*' || c == '+' || c == '-' || c == '/') {
                if (operand) {
                    operand = false;
                    operand_c = c;
                } else {
                    return "throws Exception";
                }
            }

            if ((c == 'I' || c == 'V' || c == 'X' ) && operand) {  //проверка только до 10 т.к. по условию можно вводить только от 1 до 10 включительно
                num_a1 += String.valueOf(c);
            }
            if ((c == 'I' || c == 'V' || c == 'X' ) && !operand) {
                num_a2 += String.valueOf(c);
            }
            //System.out.println(num1+" "+num2);

        }
        if(num1>10 || num2>10 || toArabic(num_a1)>10 || toArabic(num_a2)>10) //если числа больше 10
            return "throws Exception";
        if (num1 != 0 && num2 != 0) {
            switch (operand_c) {
                case ('+'):
                    return String.valueOf(num1 + num2);
                case ('*'):
                    return String.valueOf(num1 * num2);
                case ('-'):
                    return String.valueOf(num1 - num2);
                case ('/'):
                    return String.valueOf(num1 / num2);
                default:
                    return "throws Exception";
            }
        }
        if (!num_a1.equals("") && !num_a2.equals("")) {
            int n1 = toArabic(num_a1);
            int n2 = toArabic(num_a2);
            switch (operand_c) {
                case ('+'):
                    return toRoman(n1 + n2);
                case ('*'):
                    return toRoman(n1 * n2);
                case ('-'): {
                    if (n1 - n2 > 0)
                        return toRoman(n1 - n2);
                    else return "throws Exception";
                }
                case ('/'):
                    return toRoman(n1 / n2);
                default:
                    return "throws Exception";
            }
        }
        return "throws Exception";
    }

    public static void main(String[] args) {
        String string = in.nextLine();
        System.out.println(calc(string));
    }
}