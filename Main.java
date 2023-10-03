import java.util.Scanner;
public class Main {
    static Scanner inputCalcAgain = new Scanner(System.in); //сканер вынесен отдельно в static, чтобы каждый раз при вызове не прописывать его
    public static void main(String[] args) throws Exception {
        System.out.println("Введите арифметическое выражение без пробелов, одновременно состоящее из 2-х арабских или 2-х римских чисел от 1 до 10 включительно.");
        System.out.println(calc(inputCalcAgain.nextLine()));
        inputCalcAgain.close();
    }
    public static String calc(String input) throws Exception {
        byte a;
        byte b;
        char[] inputChar = new char[8];
        char operatorChar = '+';
        String operatorStr = "";
        for (byte i = 1; i < input.length(); i++) { //Прохожу циклом по массиву строк, нахожу знак операции и помещаю его в ячейку char operatorChar и элемент строки operatorStr
            inputChar[i] = input.charAt(i);
            if (inputChar[i] == '+') {
                operatorChar = '+';
                operatorStr = "\\+";
            }
            if (inputChar[i] == '-') {
                operatorChar = '-';
                operatorStr = "-";
            }
            if (inputChar[i] == '*') {
                operatorChar = '*';
                operatorStr = "\\*";
            }
            if (inputChar[i] == '/') {
                operatorChar = '/';
                operatorStr = "/";
            }
        }
        int result;
        String[] strings = input.split(operatorStr); //Принятую строку разделяю на символы по знаку операции с помощью элемента строки operatorStr
        if (strings.length != 2) {
            throw new Exception("Введено больше двух чисел и одного знака операции либо введён неверный знак операции");
        }
        try {
            a = romanNumbers(strings[0]);
            b = romanNumbers(strings[1]);
        } catch (ArrayIndexOutOfBoundsException e) { //Исключение+выход из калькулятора при неправильном вводе арифметического выражения
            throw new Exception("Ошибка - нужно ввести число, знак операции, число - без пробелов.");
        }
        if (a == 0 || b == 0) {
            try {
                a = Byte.parseByte(strings[0]);
                b = Byte.parseByte(strings[1]);
                if (a > 10 || a < 0 || b > 10 || b < 0) { //Исключение+выход из калькулятора при вводе чисел >10 или <0
                    throw new Exception("Ошибка - калькулятор умеет работать только с целыми арабскими или римскими цифрами от 1 до 10 включительно.");
                } else if (a == 0 || b == 0) {
                    throw new Exception("Ошибка - один из операндов равен нулю.");
                } else {
                    result = calcInt(a, b, operatorChar);
                    System.out.println("Ответ: " + result);
                }
            } catch (NumberFormatException e) { //Исключение+выход из калькулятора при одновременном вводе римских и арабских цифр
                throw new Exception("Ошибка - калькулятор умеет работать одновременно только с арабскими или только римскими целыми числами от 1 до 10 включительно.");
            } catch (ArithmeticException e) { //Исключение+выход из калькулятора при арифметической ошибке-делении на ноль. Не должна выпадать, т.к. есть предыдущее исключение - вводимые цифры больше 0
                throw new Exception("Ошибка - на ноль делить нельзя.");
            }
        } else {
            try {
                result = calcInt(a, b, operatorChar);
                if (result < 1) { //Исключение+выход из калькулятора при отрицательном ответе или 0 в вычислениях с римскими цифрами
                    throw new Exception("Ошибка - результатом работы калькулятора с римскими числами могут быть только положительные числа.");
                }
                String resultRoman = romanTrans(result-1);
                System.out.println("Ответ: " + resultRoman);
            } catch (Exception e) {
                throw new Exception("Ошибка - результатом работы калькулятора с римскими числами могут быть только положительные числа.");
            }
        }
        return "";
    }
    public static int calcInt(byte a, byte b, char operatorChar) { //собственно, вычисление результата в зависимости от введённого знака операции, помещённого в ячейку char operatorChar
        int result = 0;
        switch (operatorChar) {
            case '+':
                result = a + b;
                break;
            case '-':
                result = a - b;
                break;
            case '*':
                result = a * b;
                break;
            case '/':
                result = a / b;
                break;
            default:
                break;
        }
        return result;
    }
    public static String romanTrans(int arabicNumbers) { //Массив строк римских чисел, большая часть из которых нужна для вывода результатов вычисления, подтягивается в строку resultRoman при выводе результата в консоль
        String[] romanNumbers = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X",
                "XI", "XII", "XIII", "XIV", "XV", "XVI", "XVII", "XVIII", "XIX", "XX",
                "XXI", "XXII", "XXIII", "XXIV", "XXV", "XXVI", "XXVII", "XXVIII", "XXIX",
                "*****", "XXXI", "XXXII", "XXXIII", "XXXIV", "XXXV", "XXXVI", "XXXVII", "XXXVIII",
                "XXXIX", "XL", "XLI", "XLII", "XLIII", "XLIV", "XLV", "XLVI", "XLVII", "XLVIII",
                "XLIX", "L", "LI", "LII", "LIII", "LIV", "LV", "LVI", "LVII", "LVIII", "LIX", "LX",
                "LXI", "LXII", "LXIII", "LXIV", "LXV", "LXVI", "LXVII", "LXVIII", "LXIX", "LXX",
                "LXXI", "LXXII", "LXXIII", "LXXIV", "LXXV", "LXXVI", "LXXVII", "LXXVIII", "LXXIX",
                "LXXX", "LXXXI", "LXXXII", "LXXXIII", "LXXXIV", "LXXXV", "LXXXVI", "LXXXVII", "LXXXVIII",
                "LXXXIX", "XC", "XCI", "XCII", "XCIII", "XCIV", "XCV", "XCVI", "XCVII", "XCVIII", "XCIX", "C"};
        String rN = romanNumbers[arabicNumbers];
        return rN;
    }
    public static byte romanNumbers (String roman){ //метод перевода введённых римских цифр в арабские для дальнейших вычислений
        byte a = 0;
        switch (roman) {
            case "I": return 1;
            case "II": return 2;
            case "III": return 3;
            case "IV": return 4;
            case "V": return 5;
            case "VI": return 6;
            case "VII": return 7;
            case "VIII": return 8;
            case "IX": return 9;
            case "X": return 10;
        }
        return a;
    }
}