/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ga.sat;

import ga.inc.Clauses;
import ga.inc.Result;
import ga.inc.Solution;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author user
 */
public class ResultController implements Initializable {

    @FXML BarChart resultChart;
    
    XYChart.Series<String,Integer> lineSeries = new XYChart.Series<>();
    ObservableList<Result> results = FXCollections.observableArrayList();
    
    private double round(double value, int places) {
        
	if (places < 0)
            throw new IllegalArgumentException();

	BigDecimal bd = new BigDecimal(Double.toString(value));
	bd = bd.setScale(places, RoundingMode.HALF_UP);
	return bd.doubleValue();
        
	}

	public double getSatisfiabilityRate() {
            
		float sumSatisfiedClausesPerAttempt = 0;

		for(int i=0; i<results.size(); i++)
			sumSatisfiedClausesPerAttempt += results.get(i).getSatisfiability();

		return round(100*sumSatisfiedClausesPerAttempt/results.size(), 7);
	}


	public double getAverageSearchTime() {
            
		long sumSearchTimePerAttempt = 0;

		for(int i=0; i<results.size(); i++)
			sumSearchTimePerAttempt += results.get(i).getTime();

		return round(sumSearchTimePerAttempt/results.size(), 9);
	}    
    
    public void addData(Clauses clset, Solution solution, long time, int numIter) {
		
        Result result = new Result(clset, solution, time);

	results.add(result);
        lineSeries.getData().add(new XYChart.Data<>("Iterration " + numIter + "\n(" + round(time/1000.0, 2) + "  sec)", solution.satisfiedClauses(clset)));
        
	}

        public void initBarChart(){
            
            if(!lineSeries.getData().isEmpty()){
                resultChart.getData().addAll(lineSeries);
		resultChart.setTitle("Satisfiability rate :  " + getSatisfiabilityRate()+" %  ("
									+getAverageSearchTime()/1000+"  sec)");
                lineSeries.getData().forEach((data) -> {
                    data.getNode().addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent event1) -> {
                        Tooltip.install(data.getNode(), new Tooltip(data.getYValue().toString()));
                    });
                    displayLabelForData(data);
                });
            }
            
        }
        
    private void displayLabelForData(XYChart.Data<String, Integer> data) {
        final Node node = data.getNode();
        final Text dataText = new Text(data.getYValue() + "");
        node.parentProperty().addListener((ObservableValue<? extends Parent> ov, Parent oldParent, Parent parent) -> {
            Group parentGroup = (Group) parent;
            parentGroup.getChildren().add(dataText);
        });

        node.boundsInParentProperty().addListener(new ChangeListener<Bounds>() {
          @Override public void changed(ObservableValue<? extends Bounds> ov, Bounds oldBounds, Bounds bounds) {
            dataText.setLayoutX(
              Math.round(
                bounds.getMinX() + bounds.getWidth() / 2 - dataText.prefWidth(-1) / 2
              )
            );
            dataText.setLayoutY(
              Math.round(
                bounds.getMinY() - dataText.prefHeight(-1) * 0.5
              )
            );
          }
        });
    }        
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
            
        
    }    
    
}
