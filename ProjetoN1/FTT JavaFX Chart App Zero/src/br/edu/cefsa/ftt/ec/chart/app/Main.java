package br.edu.cefsa.ftt.ec.chart.app;

//TODO: Projeto N1

//TODO: Esta é a estratura básica do projeto
//TODO: Abrir um arquivo e ler os dados
//TODO: Mudar parametros do gráfico pegando dados com usuário utilizando dialog
//TODO: Gerar o gráfico no centro da interface (border layout)
//TODO: Utilizar valor dos sliders para modificar o gráfico dinamicamente...

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class Main extends Application {
	
    //TODO: Criar componente do gráfico...
	NumberAxis xAxis = new NumberAxis();
	NumberAxis yAxis =  new NumberAxis();
    LineChart<Number,Number> lineChart = new LineChart<Number,Number>(xAxis,yAxis);
	
	@Override
	public void start(Stage primaryStage) {
		
		try {
			
			BorderPane rootBorderPane = new BorderPane();

			//Reference: https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/BorderPane.html
			
			Scene scene = new Scene(rootBorderPane, 800, 600);
			
			primaryStage.setTitle("JavaFX - Chart Controll");
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			
			//Components declaration:
			//Reference: https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/package-summary.html
			
			Button buttonFileOpen = new Button("Open");
			
			Button buttonSize = new Button("Redefine Size");
			
			final ToggleGroup group = new ToggleGroup();
			
			//A radio button with the specified label
			RadioButton rby = new RadioButton("Eixo Y");
			rby.setToggleGroup(group);
			//A radio button with the specified label
			RadioButton rbx = new RadioButton("Eixo X");
			rbx.setToggleGroup(group);
			
		    rbx.setSelected(true);
			
			Slider slider = new Slider();
			//Reference for Slider: https://docs.oracle.com/javafx/2/ui_controls/slider.htm
			//                      https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/Slider.html
			
			slider.setMin(0);
			slider.setMax(100);
			slider.setValue(50);
			slider.setShowTickLabels(true);
			slider.setShowTickMarks(true);
			slider.setMajorTickUnit(50);
			slider.setMinorTickCount(5);
			slider.setBlockIncrement(10);
			
		    Label status = new Label("Status: ");


		    xAxis.setLabel("Eixo X");

			//Actions to the interactive components:
			
			buttonFileOpen.setOnAction(new EventHandler<ActionEvent>() {
			    @SuppressWarnings({ "unchecked", "rawtypes" })
				@Override 
			    public void handle(ActionEvent e) {
			    	//TODO: Read a file and get data to the graphic, using dialog
			    	xAxis = new NumberAxis();
			    	yAxis =  new NumberAxis();
			    	lineChart = new LineChart<Number,Number>(xAxis,yAxis);
				    //defining a series
				    XYChart.Series series = new XYChart.Series();
				    series.setName("Linguagem de programação 2");

					
			        FileChooser fileChooser = new FileChooser();
			        File selectedFile = fileChooser.showOpenDialog(null);

			  

			        if (selectedFile != null) {
				        lineChart.setTitle(selectedFile.getName());
				        lineChart.getData().clear();
			        	BufferedReader br;
			        	int x = 0;
			        	int y =0;

			        	//populating the series with data   
			        	try {
			        		br = new BufferedReader(new FileReader(selectedFile));
			        		while(br.ready())
			        		{			        			
			        			String[] coord = br.readLine().split(",");
			        	        series.getData().add(new XYChart.Data(Integer.valueOf(coord[0]), Integer.valueOf(coord[1])));
			        	        if(Integer.valueOf(coord[1]) > y)
			        	        	y = Integer.valueOf(coord[1]);
			        	        if(Integer.valueOf(coord[0]) > x)
			        	        	x = Integer.valueOf(coord[0]);
			        	    }

			        	    lineChart.getData().add(series);

			    		    //TODO: Criar o gráfico e carrega-lo no centro...
			    		    rootBorderPane.setCenter(lineChart);    		     
			    	       	    
			        	} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
			        }			       
		        }
			});
			
			buttonSize.setOnAction(new EventHandler<ActionEvent>() {
			    @Override 
			    public void handle(ActionEvent e) {
			    	//TODO: Change graphic parameters using dialog
			    	if(rbx.isSelected())
			    	{
			    		xAxis.setAutoRanging(false);
			        	xAxis.setUpperBound(((Math.floor(slider.getValue()))));
			        }
			    	else
			    	{
			    		yAxis.setAutoRanging(false);
			        	yAxis.setUpperBound(((Math.floor(slider.getValue()))));
			        }

			    }
			});	
			
			slider.valueProperty().addListener((observable, oldvalue, newvalue) -> {
			            float val = newvalue.floatValue();
			            System.out.println("Slider value: " + val);
			            status.setText(String.valueOf(val));
			});
		
			//Form layout...		
			
		    ToolBar toolbar = new ToolBar();
		     
		    toolbar.getItems().add(buttonFileOpen);
		    toolbar.getItems().add(slider);
		    toolbar.getItems().add(buttonSize);
		    toolbar.getItems().add(rbx);
		    toolbar.getItems().add(rby);
		     
		    HBox statusbar = new HBox();
		    //Reference: https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/HBox.html

		    statusbar.getChildren().add(status);

		    rootBorderPane.setTop(toolbar);
		   		    
		    rootBorderPane.setBottom(statusbar);
		      
			primaryStage.show();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
