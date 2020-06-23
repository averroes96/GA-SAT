/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ga.sat;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXSlider;
import ga.inc.Algorithm;
import ga.inc.Clauses;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author user
 */
public class MainController implements Initializable {
    
    @FXML JFXSlider popSize, maxIterations;
    @FXML JFXComboBox<String> benchmarks;
    @FXML JFXButton startBtn;
    @FXML Label popLabel,iterLabel;

    Clauses clauses;
    private Instant t1,t2;
    private long elapsedTime;
    XYChart.Series<String,Integer> data = new XYChart.Series<>();
    
    public String loadClausesSet(String cnf_filePath) {
            
        try {
            clauses = new Clauses(cnf_filePath);
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
                
		return "SAT instance loaded :  " + clauses.getNumberClause() + "  clauses,  " + clauses.getNumberVariables() + "  variables,  " + clauses.getClauseSize() + "  variables/clause";
	}    
    
    public void initSlider(JFXSlider slider, Label sliderLabel){
            
        slider.valueProperty().addListener((obs,oldVal,newVal)->{
            slider.setValue(newVal.intValue());
        });

        sliderLabel.textProperty().bindBidirectional(slider.valueProperty(), NumberFormat.getIntegerInstance());            
    }

    public static ObservableList<String> getAllFileNames(final File folder) throws IOException{
            
            ObservableList<String> filesList = FXCollections.observableArrayList();
            
            for (final File fileEntry : folder.listFiles()) {
                if (fileEntry.isDirectory()) {
                    getAllFileNames(fileEntry);
                } else {
                    filesList.add(fileEntry.getName());
                }
            }
            
            return filesList ;
            
        }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        initSlider(popSize, popLabel);
        initSlider(maxIterations, iterLabel);
        
        popSize.setMax(100);
        maxIterations.setMax(5000);
        
        popSize.setValue(10);
        maxIterations.setValue(100);
        
        benchmarks.setOnAction(Action -> {
        
            loadClausesSet("C:\\Users\\user\\Documents\\NetBeansProjects\\GA-SAT\\src\\Benchmarks\\" + benchmarks.getValue());
        
        });     

        try {
            benchmarks.setItems(getAllFileNames(new File("C:\\Users\\user\\Documents\\NetBeansProjects\\GA-SAT\\src\\BenchMarks")));
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        startBtn.setOnAction(Action -> {

            try {
                //((javafx.scene.Node)Action.getSource()).getScene().getWindow().hide();
                Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Result.fxml"));
                AnchorPane root = (AnchorPane)loader.load();
                ResultController rControl = (ResultController)loader.getController();
                
                for(int i=0; i< maxIterations.getValue(); i++) {
                    System.out.println("> " + i);
                    t1 = Instant.now();
                    rControl.addData(clauses, Algorithm.searchGA(clauses, (int)popSize.getValue(), 100, 20, (int)maxIterations.getValue(), 5000),
                    Duration.between(Instant.now(), t1).toMillis() > 5000 ? 5000 : Duration.between(Instant.now(), t1).toMillis(), i+1);
                }
                
                rControl.initBarChart();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();          
            } catch (IOException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        });
    }    
   
}
