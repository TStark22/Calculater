package com.starkov;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Main {
//  Метод по переводу римских цифр в арабские (сделал не сам нашёл в Инете)
    public static int romToArabic(String input) {
        String romanNum = input.toUpperCase();
        int result = 0;
        List<RomeNumTranslat> romeNumTransList = RomeNumTranslat.getReverseSortedValues();
        int i = 0;

        while ((romanNum.length() > -0) && (i < romeNumTransList.size())) {
            RomeNumTranslat symbol = romeNumTransList.get(i);
            if (romanNum.startsWith(symbol.name())) {
                result += symbol.getValue();
                romanNum = romanNum.substring(symbol.name().length());
            } else {
                i++;
            }
        }

        if (romanNum.length() > 0) {
            throw new IllegalArgumentException(input + "какая-то хрень с конвертацией!");
        }
        return result;
    }

//  Метод самой арифметической операции
    public static int mathMethod(int a, String operator, int b) {
        int result;

        switch (operator) {
            case "+":
                result = a + b;
                break;
            case "-":
                result = a - b;
                break;
            case "*":
                result = a * b;
                break;
            case "/":
                result = a / b;
                break;
            default:
                throw new IllegalStateException(operator + " - данный оператор не соответсвует условиям!!");
        }
        return result;
    }

    public static String calc(String input) throws ScannerException, IndexOutOfBoundsException {
        String operator, result;
        int a, b;
        String[] romeNumInput = new String[] {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};

        String denim = " ";
        StringTokenizer tokenizer = new StringTokenizer(input, denim);
        int tokenCount = tokenizer.countTokens();
        String[] stringArray = new String[tokenCount];


        for (int i = 0; i < tokenCount; i++) {
            stringArray[i] = tokenizer.nextToken();
            System.out.println(stringArray[i]);
        }

        operator = stringArray[1];
//  При работе с римскими цифрами
        if ((Arrays.binarySearch(romeNumInput, stringArray[0]) != -1) &&
                (Arrays.binarySearch(romeNumInput, stringArray[2]) != -1)) {
            System.out.println("Работаем с Римскими цифрами..");
            a = romToArabic(stringArray[0]);
            b = romToArabic(stringArray[2]);
            if (operator.equals("-") && a <= b) throw new ScannerException("В Римских числа меньше 1 быть не могут!");
            else {
                result = arabicToRome(mathMethod(a, operator, b));
            }
        }
//        При работе с арабскими цифрами
        else if ((Integer.parseInt(stringArray[0]) > 0) &&
                (Integer.parseInt(stringArray[0]) < 11) &&
                (Integer.parseInt(stringArray[2]) > 0) && (Integer.parseInt(stringArray[2]) < 11))
        {
            System.out.println("Выполняем метод с арабскими цифрами..");
            a = Integer.parseInt(stringArray[0]);
            b = Integer.parseInt(stringArray[2]);
            result = mathMethod(a, operator, b) + "";
        }
        else {
            throw new ScannerException("""
                    Повторите ввод без перечисленных ниже ошибок:\s
                    1. Данные должны быть в одной строке;
                    2. Цифры должны быть либо ТОЛЬКО арабские, либо ТОЛЬКО римские;
                    3. Ввели не целые числа;
                    4. Ввели числа не в пределах от 1 до 10;
                    5. Римские цифры могут быть только положительными.""");
        }
        return result;
    }

//  Метод перевода арабского результата в Римский
    public static String arabicToRome(int number) {
        List<RomeNumTranslat> romanNumerals = RomeNumTranslat.getReverseSortedValues();
        int i = 0;

        if (number <= 0) throw new IllegalArgumentException(number + "Чувак, отрицательных быть не должно!");

        StringBuilder sb = new StringBuilder();
        while ((number > 0) && (i < romanNumerals.size())) {
            RomeNumTranslat currentSymbol = romanNumerals.get(i);
            if (currentSymbol.getValue() <= number) {
                sb.append(currentSymbol.name());
                number -= currentSymbol.getValue();
            } else {
                i++;
            }
        }

        return sb.toString();
    }

    public static void main(String[] args) throws ScannerException, IndexOutOfBoundsException{
        String input, result;

        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите выражение в одну строку по примеру \"a + b\"" +
                " (между аргументами пробелы обязательны):");

        input = scanner.nextLine();
        result = calc(input);

        scanner.close();
        System.out.println(result);
    }
}
