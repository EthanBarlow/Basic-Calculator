/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package basiccalculator;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Ethan
 */
public class BasicCalculator extends Application {
        
    private double num1 = 0;
    private double num2 = 0;
    private char operator = ' ';
    private double result = 0;

    public double getNum1() {
        return num1;
    }

    public void setNum1(double num1) {
        this.num1 = num1;
    }

    public double getNum2() {
        return num2;
    }

    public void setNum2(double num2) {
        this.num2 = num2;
    }

    public char getOperator() {
        return operator;
    }

    public void setOperator(char operator) {
        this.operator = operator;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }
    
    
    @Override
    public void start(Stage primaryStage) {
       
        GridPane gridPane = new GridPane();
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        
        //number buttons
        ArrayList<Button> keys = new ArrayList<>();
        setKeypad(keys);
        
        //make the labels
        Label lblExpression = new Label("Expression: ");
        Label lblResult = new Label("Result: ");
        
        //create the text fields
        TextField tfExpression = new TextField();
        TextField tfResult = new TextField();
        
        //first row of calculator
        //gridPane.add(lblExpression, 0,0,2,1); //in column 1 - row 0, spanning 2 columns and 1 row
        gridPane.add(tfExpression, 2,0);
        //gridPane.add(lblResult, 0,1,2,1);
        gridPane.add(tfResult, 2,1);
        
        
        //set UI properties
        gridPane.setAlignment(Pos.CENTER);
        tfResult.setEditable(false);
        
        GridPane keypad = new GridPane();
        gridPane.add(keypad, 0,5,3,4); //set to start in col 0, row 5 --- spans all 3 cols and 4 rows
        keypad.setAlignment(Pos.CENTER);
        keypad.setHgap(3);
        keypad.setVgap(3);

        //set up the keypad grid using several for loops
        int buttonNum = 0;//index for keys array
        for(int row = 0; row<4; row++){
            for(int col=0; col<4; col++){
                keypad.add(keys.get(buttonNum), col, row);
                buttonNum++;
            }//end column loop
        }//end row loop
        
        keys.add(new Button("Clear"));
        
        gridPane.add(keys.get(16), 0,4,4,1);
        
        //managing the button presses
        keys.get(0).setOnAction(e->tfExpression.setText(tfExpression.getText()+"7"));
        keys.get(1).setOnAction(e->tfExpression.setText(tfExpression.getText()+"8"));        
        keys.get(2).setOnAction(e->tfExpression.setText(tfExpression.getText()+"9"));
        keys.get(4).setOnAction(e->tfExpression.setText(tfExpression.getText()+"4"));
        keys.get(5).setOnAction(e->tfExpression.setText(tfExpression.getText()+"5"));
        keys.get(6).setOnAction(e->tfExpression.setText(tfExpression.getText()+"6"));
        keys.get(8).setOnAction(e->tfExpression.setText(tfExpression.getText()+"1"));
        keys.get(9).setOnAction(e->tfExpression.setText(tfExpression.getText()+"2"));
        keys.get(10).setOnAction(e->tfExpression.setText(tfExpression.getText()+"3"));
        keys.get(12).setOnAction(e->tfExpression.setText(tfExpression.getText()+"0"));
        keys.get(13).setOnAction(e->tfExpression.setText(tfExpression.getText()+"."));
        keys.get(14).setOnAction(e-> tfResult.setText(answer(tfExpression.getText())));
        keys.get(3).setOnAction(e->tfExpression.setText(tfExpression.getText()+"+"));
        keys.get(7).setOnAction(e->tfExpression.setText(tfExpression.getText()+"-"));
        keys.get(11).setOnAction(e->tfExpression.setText(tfExpression.getText()+"/"));
        keys.get(15).setOnAction(e->tfExpression.setText(tfExpression.getText()+"*"));
        keys.get(16).setOnAction(e->reset(tfExpression, tfResult));
       
        
        Scene scene = new Scene(gridPane, 300, 400);
        
        primaryStage.setTitle("Calculator");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    public void reset(TextField exp, TextField result){
        exp.setText("");
        result.setText("");
    }
    
    public double add(double num1, double num2){
        return num1+num2;
    }
    
    public double subtract(double num1, double num2){
        return num1-num2;
    }
    
    public double multiply(double num1, double num2){
        return num1*num2;
    }
    
    public double divide(double num1, double num2){
        return num1/num2;
    }
    
    public String answer(String expression){
        calculate(expression);
        return(Double.toString(getResult()));
    }
    
    public void calculate(String expression){
        parseText(expression);
        
        switch(getOperator()){
            case '*':
            {
                setResult(multiply(getNum1(),getNum2()));
                break;
            }
            case '/':
            {
                setResult(divide(getNum1(),getNum2()));
                break;
            }
            
            case '+':
            {
                setResult(add(getNum1(),getNum2()));
                break;
            }
                
            case '-':
            {
                setResult(subtract(getNum1(),getNum2()));
                break;
            }
            
        }//end switch statement
    }
    
    /**
     * 
     * @param expression - the text grabbed from the box, all other arguments are currently empty
     * but will get values during this function
     */
    public void parseText(String expression){
        int stringPos;
        String firstNum;
        String secondNum;
        
        for(stringPos=0;stringPos<expression.length(); stringPos++){
            if(expression.charAt(stringPos) != '.' && !Character.isDigit(expression.charAt(stringPos))){
                break;
            }
        }//end for loop

        firstNum = expression.substring(0, stringPos); //stringPos is at the character for the operation, so get ever char before
        setOperator(expression.charAt(stringPos));
        secondNum = expression.substring(stringPos+1,expression.length());
        
        setNum1(new Double(firstNum));
        setNum2(new Double(secondNum));
        
        
    }
    
    public void setKeypad(ArrayList<Button>keys){
        
        //in order of the actual keypad layout
        keys.add(new Button("7"));
        keys.add(new Button("8"));
        keys.add(new Button("9"));
        keys.add(new Button("+"));
        
        keys.add(new Button("4"));
        keys.add(new Button("5"));
        keys.add(new Button("6"));
        keys.add(new Button("-"));
        
        keys.add(new Button("1"));
        keys.add(new Button("2"));
        keys.add(new Button("3"));
        keys.add(new Button("/"));
        
        keys.add(new Button("0"));
        keys.add(new Button("."));
        keys.add(new Button("="));
        keys.add(new Button("*"));
        
        for(int i=0; i<keys.size();i++)
        {      
            keys.get(i).setMinSize(30,30);
        }
        
    }
    
    
    
}//end BasicCalculator
