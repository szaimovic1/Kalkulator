package sample;

import java.util.ArrayList;
import java.util.Stack;

public class ExpressionEvaluator {

    private ExpressionEvaluator(){
    }

    public static Double evaluate(String infixExpression) {
        Stack<Double> operands = new Stack<>();
        Stack<String> operators = new Stack<>();

        populateStacks(infixExpression, operands, operators);



        return reduceToResult(operands, operators);
    }

    private static void populateStacks(String infixExpression, Stack<Double> operands, Stack<String> operators){
        String[] tokens = infixExpression.split(" ");

        for (String token : tokens){
            if (isOperator(token)){
                while (!operators.isEmpty() && precedence(operators.peek()) >= precedence(token)){
                    Double b = operands.pop();
                    Double a = operands.pop();
                    operands.add(applyOperator(a, b, operators.pop()));
                }
                operators.add(token);
            }else{
                operands.add(Double.parseDouble(token));
            }
        }
    }

    private static Double reduceToResult(Stack<Double> operands, Stack<String> operators) {
        while (!operators.isEmpty()){
            Double b = operands.pop();
            Double a = operands.pop();
            operands.add(applyOperator(a, b, operators.pop()));
        }

        return operands.pop();
    }

    private static int precedence(String operator){
        return switch (operator) {
            case "*", "/", "%" -> 2;
            case "+", "-" -> 1;
            default -> 0;
        };
    }

    private static Double applyOperator(Double a, Double b, String operator){
        return switch (operator) {
            case "+" -> a + b;
            case "-" -> a - b;
            case "*" -> a * b;
            case "/" -> a / b;
            case "%" -> a % b;
            default -> throw new IllegalStateException("Unexpected value: " + operator);
        };
    }

    private static boolean isOperator(String operator){
        return operator.equals("+") ||
                operator.equals("-") ||
                operator.equals("*") ||
                operator.equals("/") ||
                operator.equals("%");
    }
}
