package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class Controller {
    @FXML
    public Label display;
    @FXML
    public Label finalExpression;

    private StringProperty resultText;
    private StringProperty expression;

    private boolean operatorClicked;
    private boolean expressionStart;
    private boolean isZero;

    @FXML
    public void initialize(){
        resultText = new SimpleStringProperty("0");
        expression = new SimpleStringProperty("0");

        display.textProperty().bindBidirectional(resultText);
        finalExpression.textProperty().bind(expression);

        operatorClicked = false;
        expressionStart = true;
        isZero = false;
    }

    public void operatorClicked(ActionEvent actionEvent){
        String operator = getButtonText(actionEvent);

        if (expressionStart && operator.equals("-")){
            //unarni minus
            expression.setValue(expression.get() + resultText.get());
        }else if(expressionStart && resultText.get().isBlank()){
            return;
        }else if(expressionStart && !resultText.get().isEmpty()){
            expression.setValue("");
            expressionStart = false;
        }

        if (operatorClicked){
            //minus pa plus
            expression.setValue(expression.get() + expression.get().substring(0, expression.get().length() - 2));
        }else{
            expression.setValue(expression.get() + resultText.get() + " ");
        }

        if (operator.equals("=")){
            evaluateExpression();
            return;
        }

        expression.setValue(expression.get() + operator + " ");
        operatorClicked = true;
    }

    public void operandClicked(ActionEvent actionEvent) {
        String operand = getButtonText(actionEvent);

        if (expressionStart && operand.equals(".")){
            return;
        }

        if (operatorClicked || expressionStart){
            resultText.setValue("");
            operatorClicked = false;
            isZero = false;
        }

        if (!(isZero && operand.equals("0"))){
            if(expressionStart) expression.setValue("");
            expressionStart = false;
            isZero = operand.equals("0");
            resultText.setValue(resultText.getValue() + operand);
        }
    }

    private String getButtonText(ActionEvent actionEvent) {
        return ((Button) actionEvent.getTarget()).getText();
    }

    private void evaluateExpression(){
        System.out.println(expression.get());
        try {
            Double result = ExpressionEvaluator.evaluate(expression.get());
            resultText.setValue(String.valueOf(result));
        } catch (InvalidExpression invalidExpression) {
            resultText.setValue(invalidExpression.getMessage());
        }
        expressionStart = true;
    }
}
