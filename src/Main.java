import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.util.HashSet;

public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String str = in.nextLine();
        HashMap<String, Integer> coefficients = new HashMap<String, Integer>();
        String[] array = str.split(" ");
        for (int i = 0; i < array.length; i++) {
            int lastIndex = array[i].length() - 1;
            String add = "empty";
            int toAdd = 0;
            if ('x' == array[i].charAt(0)) { // x без коэффициента
                toAdd = 1;
                add = "x";
                if(lastIndex >= 1 && array[i].charAt(lastIndex - 1) == '^'){
                    add = "x^" + array[i].charAt(lastIndex);
                }
            } else if (array[i].charAt(lastIndex) == 'x') {
                toAdd = Integer.valueOf(array[i].substring(0, lastIndex));
                add = "x";
//            } else if (lastIndex >= 1 && array[i].charAt(lastIndex - 1) == '^'){ // TODO работает только для степеней < 10
//                toAdd = Integer.valueOf(array[i].substring(0,lastIndex));
//                add = "x^2";
            } else if (array[i].matches("^\\d+$")) {
                if (array[i - 1].equals("-") || search(array,"=") < i) {
                    toAdd = -Integer.valueOf(array[i]);
                } else {
                    toAdd = Integer.valueOf(array[i]);
                }
                add = "empty";
            }
            if (coefficients.containsKey(add)) {
                coefficients.put(add, coefficients.get(add) + toAdd);
            } else {
                coefficients.put(add, toAdd);
            }
        }
        HashSet<Integer> div = getDividers(coefficients.get("empty"));
        HashSet<Integer> answers = calculator(div,coefficients);
        System.out.println(answers);
    }

    //
//    public static float findX(HashMap<String, Integer> map){
//        float a = 0;
//        float b = 0;
//        for (HashMap.Entry<String, Integer> entry : map.entrySet()){ // складываем коэффициенты
//            String currKey = entry.getKey();
//            switch (currKey) {
//                case ("x"):
//                    a+=entry.getValue();
//                    break;
//                case ("empty"):
//                    b+=entry.getValue();
//                    break;
//            }
//        }
//        return -b/a;
//    }

    public static HashSet<Integer> calculator(HashSet<Integer> div, HashMap<String, Integer> coefficients) {
        HashSet<Integer> roots = new HashSet<>();
        for (Integer element : div) {
            int x = element;
            if (checkAnswer(x, coefficients)) {
                roots.add(x);
            }
        }
        return roots;
    }

    public static HashSet<Integer> getDividers(int n) {
        n = Math.abs(n);
        HashSet<Integer> div = new HashSet<>();
        for (int i = 1; i < n; i++) {
            if (n % i == 0){
                div.add(i);
                div.add(-i);
            }
        }
        return div;
    }

    public static boolean checkAnswer(int x, HashMap<String, Integer> coefficients) {
        int result = 0;
        for (HashMap.Entry<String, Integer> entry : coefficients.entrySet()) { // складываем коэффициенты
            String currKey = entry.getKey();
            switch (currKey) {
                case ("x"):
                    result += x * entry.getValue();
                    break;
                case ("empty"):
                    result += entry.getValue();
                    break;
                default:
                    if (currKey.matches("^x\\^\\d$")){ // 48 надо для перевода в целое
                        result += entry.getValue() * Math.pow(x, (int) currKey.charAt(currKey.length() - 1) - 48); // TODO Не работает с минусом
                    }
                    break;
            }
        }
        return result == 0;
    }


    public static int search(String[] array, String elementToSearch) { // TODO придумай как эту хуйню отсюда выпилить
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(elementToSearch)) {
                return i;
            }
        }
        return -1;
    }
}