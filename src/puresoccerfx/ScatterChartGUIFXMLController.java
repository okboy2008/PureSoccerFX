/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package puresoccerfx;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import org.gillius.jfxutils.chart.ChartZoomManager;
import puresoccerfx.model.PlayerItem;
import puresoccerfx.model.ScatterChartExtraData;

/**
 * FXML Controller class
 *
 * @author s145633
 */
public class ScatterChartGUIFXMLController implements Initializable {

    @FXML
    private ScatterChart<Number,Number> scatterChart;
    @FXML
    private StackPane chartPane;
    @FXML
    private Rectangle selectRect;
    
    private ObservableList<XYChart.Series<Number,Number>> data = FXCollections.observableArrayList();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
//        data.addAll(config.GlobalVariable.CLICKEDCHARTDATA);
//        scatterChart.getData().addAll(data);

        //XYChart.Series series1 = new XYChart.Series();
        for(XYChart.Series<Number,Number> s:config.GlobalVariable.CLICKEDCHARTDATA){
            XYChart.Series series = new XYChart.Series();
            for(XYChart.Data<Number,Number> d:s.getData()){
                XYChart.Data<Number,Number> new_data = new XYChart.Data<>(d.getXValue(),d.getYValue());
                new_data.setExtraValue(d.getExtraValue());
                series.getData().add(new_data);
            }
            data.add(series);
        }
        this.scatterChart.setData(data);
        this.addTooltipToChart(scatterChart);
//        series1.getData().add(new XYChart.Data<>(1,1));
//        
//        scatterChart.getData().add(series1);
        
        ChartZoomManager zoomManager = new ChartZoomManager( chartPane, selectRect, scatterChart );
        zoomManager.start();
    }    
    
     private void addTooltipToChart(ScatterChart<Number,Number> chart){
        for (XYChart.Series<Number, Number> s : chart.getData()) {
            for (XYChart.Data<Number, Number> d : s.getData()) {
                // System.out.println(d.getNode() == null);
                DecimalFormat f = new DecimalFormat("#.##");
//                Tooltip.install(d.getNode(), new Tooltip(d.getExtraValue()+ "\n"
//                        +"X: " + f.format(d.getXValue()) + "\n"
//                        +"Y: " + f.format(d.getYValue()) + "\n"));
                Tooltip.install(d.getNode(), new Tooltip(((ScatterChartExtraData)d.getExtraValue()).getName() + "\n"
                        + ((ScatterChartExtraData)d.getExtraValue()).getX()+ ": " + f.format(d.getXValue()) + "\n"
                        + ((ScatterChartExtraData)d.getExtraValue()).getY()+ ": " + f.format(d.getYValue()) + "\n"));
            }
        }
    }
    
}
