import java.util.Scanner;
public class Main {
    static Scanner scanInput = new Scanner(System.in); //статический сканер перед main для однократного его написания, потом только вызывать
    public static void main(String[] args) throws Exception {
        System.out.println("Доступны операции сложения строк, вычитания строки из строки, умножения строки на число и деления строки на число: \n\"a\" + \"b\", \"a\" - \"b\", \"a\" * b, \"a\" / b. Данные передаются в одну строку.\nЗначения строк, передаваемых в выражении, выделяются двойными кавычками.");
        System.out.println("Введите строковое выражение, соответствующее условиям.");
        calcStr();
        scanInput.close();
    }
    public static String calcStr() throws Exception {
        String inputStr = scanInput.nextLine();
        String[] inputString = inputStr.split("[-+*/]");
        String str1 = inputString[0];
        String str2 = inputString[1];
        str1 = str1.replace("\"", "");
//        str1 = str1.trim();
        str2 = str2.replace("\"", "");
//        str2 = str2.trim();
        String result = "";

        String operSign;
        if (inputStr.contains("+")) {
            operSign = "+";
        } else if (inputStr.contains("-")) {
            operSign = "-";
        } else if (inputStr.contains("*")) {
            operSign = "*";
        } else if (inputStr.contains("/")) {
            operSign = "/";
        } else {
            throw new Exception("Ошибка - знаком операции могут быть только -, +, * или /");
        }
        if ((str1.length() > 10) || (str2.length() > 10)) {
            throw new Exception("Введённая строка должна быть длиной не более 10 символов");
        }
        switch (operSign) {
            case "+":
                result = str1 + str2;
                break;
            case "-":
                byte subIndex = (byte) (str1.indexOf(str2));
                if (subIndex == -1) {
                    result = str1;
                } else {
                    result = str1.substring(0, subIndex) + str1.substring(subIndex + str2.length());
                }
                break;
            case "*":
                try {
                    byte multiStr2 = Byte.parseByte(str2); // здесь String str2 преобразую в byte для дальнейшего умножения строки
                    if ((multiStr2 < 1) || (multiStr2 > 10)) { // проверяю byte на соответствие условию тз (от 1 до 10)
                        throw new Exception("Ошибка - калькулятор принимает на вход числа от 1 до 10 включительно, не более.");
                    } else {
                        for (int i = 1; i <= multiStr2; i++) {
                            result += str1;
                        }
                    }
                } catch (Exception e) {
                    throw new Exception("Ошибка - при умножении или делении вторым операндом может быть только число от 1 до 10 включительно, не более.");
                }
                break;
            case "/":
                try {
                    byte divStr2 = Byte.parseByte(str2); // здесь String str2 преобразую в byte для дальнейшего деления строки
                    if ((divStr2 < 1) || (divStr2 > 10)) { // проверяю byte на соответствие условию тз (от 1 до 10)
                        throw new Exception("Ошибка - калькулятор принимает на вход числа от 1 до 10 включительно, не более.");
                    } else {
                        byte div = (byte) (str1.length() / divStr2); //делю длину первой строки на число
                        result = str1.substring(0, div); //возвращаю новую строку, которая является подстрокой str1. Подстрока начинается с 0 символа str1, заканчивается на div символе
                    }
                } catch (Exception e) {
                    throw new Exception("Ошибка - при умножении или делении вторым операндом может быть только число");
                }
                break;
            default:
                throw new Exception("Что-то пошло не так");
        }
        if (result.length() > 40) {
            System.out.println("\"" + result.substring(0, 40) + "..." + "\"");
        } else {
            System.out.println("\"" + result + "\"");
        }
        return result;
    }
}