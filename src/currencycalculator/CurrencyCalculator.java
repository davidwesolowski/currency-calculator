/*
 * Currency calculator
 * Atos internship excercise
 */
package currencycalculator;

import java.io.File;
import java.util.Map;
import java.util.TreeMap;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Dawid
 */
public class CurrencyCalculator extends Application implements CalculatorInterface {
    
    private Map<String, Double> currencyMap = new TreeMap<>();
    
    public Map getCurrencyMap() {
        return this.currencyMap;
    }
    
    @Override
    public void parseDocument() {
        
        String fileName = "currencyStats.xml";
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
     
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(fileName));
            document.getDocumentElement().normalize();
            NodeList currencyList = document.getElementsByTagName("Cube");
            
            for (int i = 2; i < currencyList.getLength(); i++) {
                Node currencyItem = currencyList.item(i);
                if (currencyItem.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element currencyElement = (Element) currencyItem;
                    String currency = currencyElement.getAttribute("currency");
                    double rate = Double.parseDouble(currencyElement.getAttribute("rate"));
                    this.currencyMap.put(currency, rate);
                }
            }
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    @Override 
    public double calcCurrency(double money, String currency) {
        if (this.currencyMap.get(currency) == null)
            return 0;
        double relatedRate = this.currencyMap.get(currency);
        double result = money * relatedRate;
        result = (double) Math.round(result * 100000) / 100000;
        return result;
    }
    
    @Override
    public void start(Stage primaryStage) {
        
        double textFieldWidth = 130;
        double textFieldHeight = 20;

        parseDocument();
        
        Label inputLabel = new Label("Enter amount of money");
        inputLabel.setLayoutX(20);
        inputLabel.setLayoutY(10);
       
        TextField inputField = new TextField();
        inputField.setMaxWidth(textFieldWidth);
        inputField.setMaxHeight(textFieldHeight);
        inputField.setLayoutX(20);
        inputField.setLayoutY(40);
        
        Label outputLabel = new Label("Result:");
        outputLabel.setLayoutX(190);
        outputLabel.setLayoutY(85);
        
        TextField outputField = new TextField();
        outputField.setMaxWidth(textFieldWidth);
        outputField.setMaxHeight(textFieldHeight);
        outputField.setLayoutX(190);
        outputField.setLayoutY(105);
   
        Button calcBtn = new Button("Calculate");
        calcBtn.setLayoutX(20);
        calcBtn.setLayoutY(105);
        
        Label currencyLabel = new Label("Currency:");
        currencyLabel.setLayoutX(190);
        currencyLabel.setLayoutY(10);
        
        ComboBox currencyBox = new ComboBox();
        
        currencyBox.setMaxWidth(140);
        currencyBox.setMaxHeight(50);
        currencyBox.setLayoutX(190);
        currencyBox.setLayoutY(40);
        
        for(String currency: this.currencyMap.keySet()) {
            String comboItem = currency + ": " + this.currencyMap.get(currency);
            currencyBox.getItems().add(comboItem);
        }
        
        calcBtn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                try {
                    double money = Double.parseDouble(inputField.getText());
                    String currency = String.valueOf(currencyBox.getValue()).split(":")[0];
                    double calculatedMoney = calcCurrency(money, currency);
                    outputField.setText(Double.toString(calculatedMoney));
                }
                catch (Exception ex) {
                    Alert alertWrongInput = new Alert(AlertType.ERROR);
                    alertWrongInput.setTitle("Error");
                    alertWrongInput.setContentText("Input data are incorrect!");
                    alertWrongInput.show();
                }
            }
        });
        
        Pane root = new Pane();
        root.getChildren().add(inputLabel);
        root.getChildren().add(inputField);
        root.getChildren().add(currencyLabel);
        root.getChildren().add(currencyBox);
        root.getChildren().add(calcBtn);
        root.getChildren().add(outputLabel);
        root.getChildren().add(outputField);
        
        Scene scene = new Scene(root, 400, 200);
        primaryStage.setTitle("Currency calculator");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
