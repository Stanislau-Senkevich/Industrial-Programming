package Exam.MathExpressionWithRPN;

import java.util.*;

// Вычислить математическое выражение

public class Task5 {
    public static final HashMap<String, Integer> OPERATIONS = new HashMap<>();
    public static boolean isNumber(String num) {
        try {
            Double.parseDouble(num);
        } catch (NumberFormatException exc) {
            return false;
        }
        return true;
    }
    public static String toRPN(String expr) {
        Stack<String> stack = new Stack<>();
        StringTokenizer tokenizer = new StringTokenizer(expr);
        String result = "";
        String token;
        while (tokenizer.hasMoreTokens()) {
            token = tokenizer.nextToken();
            if (isNumber(token)) {
                result += token + " ";
            }
            if (token.equals("(")) {
                stack.push(token);
            }
            if (token.equals(")")) {
                try {
                    while (!stack.empty() && !stack.peek().equals("(")) {
                        result += stack.pop() + " ";
                    }
                    stack.pop();
                } catch (Exception e) {
                    System.err.println("?");
                    System.exit(1);
                }
            }

            if (OPERATIONS.containsKey(token)) {
                try {
                    if (!(token.equals("(") || token.equals(")"))) {
                        while (!stack.empty() &&
                                (OPERATIONS.get(stack.peek()) > OPERATIONS.get(token) ||
                                OPERATIONS.get(stack.peek()) == OPERATIONS.get(token))) {
                            result += stack.pop() + " ";
                        }
                        stack.push(token);
                    }
                } catch (Exception e) {
                    System.err.println("?");
                    System.exit(1);
                }
            }
        }
        while (!stack.empty()) {
            try {
                result += stack.pop() + " ";
            } catch (Exception e) {
                System.err.println("?");
                System.exit(1);
            }
        }
        return result;
    }

    public static void evalRPN(String cleanExpr) {
        Stack<Double> stack = new Stack<>();
        StringTokenizer tokenizer = new StringTokenizer(cleanExpr);
        String token;
        while (tokenizer.hasMoreTokens()) {
            token = tokenizer.nextToken();
            Double tokenNum = null;
            try {
                tokenNum = Double.parseDouble(token);
            } catch (NumberFormatException e) {
            }
            if (tokenNum != null) {
                stack.push(Double.parseDouble(token + ""));
            } else if (token.equals("*")) {
                double secondOperand = stack.pop();
                double firstOperand = stack.pop();
                stack.push(firstOperand * secondOperand);
            } else if (token.equals("/")) {
                double secondOperand = stack.pop();
                double firstOperand = stack.pop();
                stack.push(firstOperand / secondOperand);
            } else if (token.equals("-")) {
                double secondOperand = stack.pop();
                double firstOperand = stack.pop();
                stack.push(firstOperand - secondOperand);
            } else if (token.equals("+")) {
                double secondOperand = stack.pop();
                double firstOperand = stack.pop();
                stack.push(firstOperand + secondOperand);
            } else {
                System.out.println("?");
                return;
            }
        }
        try {
            System.out.println(stack.pop());
        } catch (Exception e) {
            System.err.println("?");
            System.exit(1);
        }
    }
    public static void main(String[] args) {
        OPERATIONS.put("(", 0);
        OPERATIONS.put(")", 0);
        OPERATIONS.put("-", 1);
        OPERATIONS.put("+", 1);
        OPERATIONS.put("*", 2);
        OPERATIONS.put("/", 2);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter an expression with spaces between each number or operation");
        String cleanExpr = scanner.nextLine();
        evalRPN(toRPN(cleanExpr));
    }
}

