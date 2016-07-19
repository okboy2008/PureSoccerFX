/*
 * Copyright 2013 Jason Winnebeck
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gillius.jfxutils.chart;

import datatype.Player;
import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.chart.ValueAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import org.gillius.jfxutils.EventHandlerManager;
import puresoccerfx.model.Filter;
import puresoccerfx.model.PlayerItem;
import puresoccerfx.model.Range;
import puresoccerfx.model.ScatterChartExtraData;

/**
 * ChartZoomManager manages a zooming selection rectangle and the bounds of the graph. It can be
 * enabled via {@link #start()} and disabled via {@link #stop()}. The normal usage is to create a
 * StackPane with two children, an XYChart type and a Rectangle. The Rectangle should start out
 * invisible and have mouseTransparent set to true. If it has a stroke, it should be of INSIDE
 * type to be pixel perfect.
 * <p>
 * You can also use {@link JFXChartUtil#setupZooming(XYChart)} for a default solution.
 * <p>
 * Six types of zooming are supported. All are enabled by default. The drag zooming can be disabled
 * with the {@link #setMouseFilter} set to a mouse filter that allows nothing. Mouse wheel zooming
 * can be disabled via the {@link #setMouseWheelZoomAllowed} method.
 * <ol>
 *   <li>Free-form zooming in the plot area on both axes</li>
 *   <li>X-axis only zooming by dragging in the x-axis</li>
 *   <li>Y-axis only zooming by dragging in the y-axis</li>
 *   <li>Free-form zooming by the mouse wheel. The location of the cursor is taken as the zoom
 *       focus point</li>
 *   <li>X-axis only zooming by mouse wheel; cursor used as focus point</li>
 *   <li>Y-axis only zooming by mouse wheel; cursor used as focus point</li>
 * </ol>
 * <p>
 * A lot of code in ChartZoomManager currently assumes there are no scale or rotate
 * transforms between the chartPane and the axes and plot area. However, all translation transforms,
 * layoutX/Y changes, padding, margin, and setTranslate issues should be OK. This might be improved
 * later, for example JavaFX 8 is rumored to allow transform multiplication, which could solve this.
 * <p>
 * Example FXML to create the components used by this class:
 * <pre>
&lt;StackPane fx:id="chartPane" alignment="CENTER"&gt;
  &lt;LineChart fx:id="chart" animated="false" legendVisible="false"&gt;
    &lt;xAxis&gt;
      &lt;NumberAxis animated="false" side="BOTTOM" /&gt;
    &lt;/xAxis&gt;
    &lt;yAxis&gt;
      &lt;NumberAxis animated="false" side="LEFT" /&gt;
    &lt;/yAxis&gt;
  &lt;/LineChart&gt;
  &lt;Rectangle fx:id="selectRect" fill="DODGERBLUE" height="0.0" mouseTransparent="true"
             opacity="0.3" stroke="#002966" strokeType="INSIDE" strokeWidth="3.0" width="0.0"
             x="0.0" y="0.0" StackPane.alignment="TOP_LEFT" /&gt;
&lt;/StackPane&gt;</pre>
 *
 * Example Java code in bound controller class:
 * <pre>
ChartZoomManager zoomManager = new ChartZoomManager( chartPane, selectRect, chart );
zoomManager.start();</pre>
 *
 * @author Jason Winnebeck
 */
public class ChartZoomManager {
	/**
	 * The default mouse filter for the {@link ChartZoomManager} filters events unless only primary
	 * mouse button (usually left) is depressed.
	 */
	public static final EventHandler<MouseEvent> DEFAULT_FILTER = new EventHandler<MouseEvent>() {
		@Override
		public void handle( MouseEvent mouseEvent ) {
			//The ChartPanManager uses this reference, so if behavior changes, copy to users first.
			if ( mouseEvent.getButton() != MouseButton.PRIMARY )
				mouseEvent.consume();
		}
	};

	private final SimpleDoubleProperty rectX = new SimpleDoubleProperty();
	private final SimpleDoubleProperty rectY = new SimpleDoubleProperty();
	private final SimpleBooleanProperty selecting = new SimpleBooleanProperty( false );

	private final DoubleProperty zoomDurationMillis = new SimpleDoubleProperty( 750.0 );
	private final BooleanProperty zoomAnimated = new SimpleBooleanProperty( true );
	private final BooleanProperty mouseWheelZoomAllowed = new SimpleBooleanProperty( true );

	private AxisConstraint zoomMode = AxisConstraint.None;
	private AxisConstraintStrategy axisConstraintStrategy = AxisConstraintStrategies.getIgnoreOutsideChart();
	private AxisConstraintStrategy mouseWheelAxisConstraintStrategy = AxisConstraintStrategies.getDefault();

	private EventHandler<? super MouseEvent> mouseFilter = DEFAULT_FILTER;

	private final EventHandlerManager handlerManager;

	private final Rectangle selectRect;
	private final ValueAxis<?> xAxis;
	private final ValueAxis<?> yAxis;
	private final XYChartInfo chartInfo;

	private final Timeline zoomAnimation = new Timeline();
        
        private String X;
        private String Y;
        private ObservableList<XYChart.Series<Number,Number>> data;

    public ObservableList<XYChart.Series<Number, Number>> getData() {
        return data;
    }

    public void setData(ObservableList<XYChart.Series<Number, Number>> data) {
        this.data = data;
    }
        
        

    public String getX() {
        return X;
    }

    public void setX(String X) {
        this.X = X;
    }

    public String getY() {
        return Y;
    }

    public void setY(String Y) {
        this.Y = Y;
    }

        
        
	/**
	 * Construct a new ChartZoomManager. See {@link ChartZoomManager} documentation for normal usage.
	 *
	 * @param chartPane  A Pane which is the ancestor of all arguments
	 * @param selectRect A Rectangle whose layoutX/Y makes it line up with the chart
	 * @param chart      Chart to manage, where both X and Y axis are a {@link ValueAxis}.
	 */
	public ChartZoomManager( Pane chartPane, Rectangle selectRect, XYChart<?,?> chart ) {
		this.selectRect = selectRect;
		this.xAxis = (ValueAxis<?>) chart.getXAxis();
		this.yAxis = (ValueAxis<?>) chart.getYAxis();
		chartInfo = new XYChartInfo( chart, chartPane );

		handlerManager = new EventHandlerManager( chartPane );

		handlerManager.addEventHandler( false, MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			@Override
			public void handle( MouseEvent mouseEvent ) {
				if ( passesFilter( mouseEvent ) )
					onMousePressed( mouseEvent );
			}
		} );

		handlerManager.addEventHandler( false, MouseEvent.DRAG_DETECTED, new EventHandler<MouseEvent>() {
			@Override
			public void handle( MouseEvent mouseEvent ) {
				if ( passesFilter( mouseEvent ) )
					onDragStart();
			}
		} );

		handlerManager.addEventHandler( false, MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
			@Override
			public void handle( MouseEvent mouseEvent ) {
				//Don't check filter here, we're either already started, or not
				onMouseDragged( mouseEvent );
			}
		} );

		handlerManager.addEventHandler( false, MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
			@Override
			public void handle( MouseEvent mouseEvent ) {
				//Don't check filter here, we're either already started, or not
				onMouseReleased();
			}
		} );

		handlerManager.addEventHandler( false, ScrollEvent.ANY, new MouseWheelZoomHandler() );
	}

	/**
	 * Returns the current strategy in use for mouse drag events.
	 *
	 * @see #setAxisConstraintStrategy(AxisConstraintStrategy)
	 */
	public AxisConstraintStrategy getAxisConstraintStrategy() {
		return axisConstraintStrategy;
	}

        public void getFilteredPlayers(){
            
            ArrayList<PlayerItem> tmp = new ArrayList<>();
            for(PlayerItem p: config.GlobalVariable.FILTERED_PLAYER_ITEM){
                boolean flag = true;
                for(String s:config.GlobalVariable.MAPSCATTERTOFILTER.keySet()){
                    Filter f = config.GlobalVariable.MAPSCATTERTOFILTER.get(s);
                    if (s.contains(config.DataSetConfig.AVG_PREFIX)) {
                        if (!f.isPlayerSatisfiedAVG(p.getPlayer())){
                            flag = false;
                            break;
                        }
                    } else if (!f.isPlayerSatisfied(p.getPlayer())) {
                        flag = false;
                        break;
                    }
                }
                if(flag){
                    tmp.add(p);
                }
            }
            if(tmp.isEmpty()){
                config.GlobalVariable.NEED_UPDATE = true;
                config.GlobalVariable.SELECTED_PLAYER_ITEM.clear();
                return;
            }
            config.GlobalVariable.SELECTED_PLAYER_ITEM.clear();
            for(PlayerItem p : tmp){
                if(tmp.indexOf(p)==tmp.size()-1){
                    config.GlobalVariable.NEED_UPDATE = true;
                }
                config.GlobalVariable.SELECTED_PLAYER_ITEM.add(p);
            }
        }
        
	/**
	 * Sets the {@link AxisConstraintStrategy} to use for mouse drag events, which determines which axis is allowed for
	 * zooming. The default implementation is {@link AxisConstraintStrategies#getIgnoreOutsideChart()}.
	 *
	 * @see AxisConstraintStrategies
	 */
	public void setAxisConstraintStrategy( AxisConstraintStrategy axisConstraintStrategy ) {
		this.axisConstraintStrategy = axisConstraintStrategy;
	}

	/**
	 * Returns the current strategy in use for mouse wheel events.
	 *
	 * @see #setMouseWheelAxisConstraintStrategy(AxisConstraintStrategy)
	 */
	public AxisConstraintStrategy getMouseWheelAxisConstraintStrategy() {
		return mouseWheelAxisConstraintStrategy;
	}

	/**
	 * Sets the {@link AxisConstraintStrategy} to use for mouse wheel events, which determines which axis is allowed for
	 * zooming. The default implementation is {@link AxisConstraintStrategies#getDefault()}.
	 *
	 * @see AxisConstraintStrategies
	 */
	public void setMouseWheelAxisConstraintStrategy( AxisConstraintStrategy mouseWheelAxisConstraintStrategy ) {
		this.mouseWheelAxisConstraintStrategy = mouseWheelAxisConstraintStrategy;
	}

	/**
	 * If true, animates the zoom.
	 */
	public boolean isZoomAnimated() {
		return zoomAnimated.get();
	}

	/**
	 * If true, animates the zoom.
	 */
	public BooleanProperty zoomAnimatedProperty() {
		return zoomAnimated;
	}

	/**
	 * If true, animates the zoom.
	 */
	public void setZoomAnimated( boolean zoomAnimated ) {
		this.zoomAnimated.set( zoomAnimated );
	}

	/**
	 * Returns the number of milliseconds the zoom animation takes.
	 */
	public double getZoomDurationMillis() {
		return zoomDurationMillis.get();
	}

	/**
	 * Returns the number of milliseconds the zoom animation takes.
	 */
	public DoubleProperty zoomDurationMillisProperty() {
		return zoomDurationMillis;
	}

	/**
	 * Sets the number of milliseconds the zoom animation takes.
	 */
	public void setZoomDurationMillis( double zoomDurationMillis ) {
		this.zoomDurationMillis.set( zoomDurationMillis );
	}

	/**
	 * If true, allow zooming via mouse wheel.
	 */
	public boolean isMouseWheelZoomAllowed() {
		return mouseWheelZoomAllowed.get();
	}

	/**
	 * If true, allow zooming via mouse wheel.
	 */
	public BooleanProperty mouseWheelZoomAllowedProperty() {
		return mouseWheelZoomAllowed;
	}

	/**
	 * If true, allow zooming via mouse wheel.
	 */
	public void setMouseWheelZoomAllowed( boolean allowed ) {
		mouseWheelZoomAllowed.set( allowed );
	}

	/**
	 * Returns the mouse filter.
	 *
	 * @see #setMouseFilter(EventHandler)
	 */
	public EventHandler<? super MouseEvent> getMouseFilter() {
		return mouseFilter;
	}

	/**
	 * Sets the mouse filter for starting the zoom action. If the filter consumes the event with
	 * {@link Event#consume()}, then the event is ignored. If the filter is null, all events are
	 * passed through. The default filter is {@link #DEFAULT_FILTER}.
	 */
	public void setMouseFilter( EventHandler<? super MouseEvent> mouseFilter ) {
		this.mouseFilter = mouseFilter;
	}

	/**
	 * Start managing zoom management by adding event handlers and bindings as appropriate.
	 */
	public void start() {
		handlerManager.addAllHandlers();

		selectRect.widthProperty().bind( rectX.subtract( selectRect.translateXProperty() ) );
		selectRect.heightProperty().bind( rectY.subtract( selectRect.translateYProperty() ) );
		selectRect.visibleProperty().bind( selecting );
	}

	/**
	 * Stop managing zoom management by removing all event handlers and bindings, and hiding the
	 * rectangle.
	 */
	public void stop() {
		handlerManager.removeAllHandlers();
		selecting.set( false );
		selectRect.widthProperty().unbind();
		selectRect.heightProperty().unbind();
		selectRect.visibleProperty().unbind();
	}

	private boolean passesFilter( MouseEvent event ) {
		if ( mouseFilter != null ) {
			MouseEvent cloned = (MouseEvent) event.clone();
			mouseFilter.handle( cloned );
			if ( cloned.isConsumed() )
				return false;
		}

		return true;
	}

	private void onMousePressed( MouseEvent mouseEvent ) {
		double x = mouseEvent.getX();
		double y = mouseEvent.getY();

		Rectangle2D plotArea = chartInfo.getPlotArea();
		DefaultChartInputContext context = new DefaultChartInputContext( chartInfo, x, y );
		zoomMode = axisConstraintStrategy.getConstraint(context);
                System.out.println("zoomMode: "+zoomMode);
		if ( zoomMode == AxisConstraint.Both ) {
			selectRect.setTranslateX( x );
			selectRect.setTranslateY( y );
			rectX.set( x );
			rectY.set( y );

		} else if ( zoomMode == AxisConstraint.Horizontal ) {
			selectRect.setTranslateX( x );
			selectRect.setTranslateY( plotArea.getMinY() );
			rectX.set( x );
			rectY.set( plotArea.getMaxY() );

		} else if ( zoomMode == AxisConstraint.Vertical ) {
			selectRect.setTranslateX( plotArea.getMinX() );
			selectRect.setTranslateY( y );
			rectX.set( plotArea.getMaxX() );
			rectY.set( y );
		}
	}

	private void onDragStart() {
		//Don't actually start the selecting process until it's officially a drag
		//But, we saved the original coordinates from where we started.
		if ( zoomMode != AxisConstraint.None )
			selecting.set( true );
	}

	private void onMouseDragged( MouseEvent mouseEvent ) {
		if ( !selecting.get() )
			return;

		Rectangle2D plotArea = chartInfo.getPlotArea();

		if ( zoomMode == AxisConstraint.Both || zoomMode == AxisConstraint.Horizontal ) {
			double x = mouseEvent.getX();
			//Clamp to the selection start
			x = Math.max( x, selectRect.getTranslateX() );
			//Clamp to plot area
			x = Math.min( x, plotArea.getMaxX() );
			rectX.set( x );
		}

		if ( zoomMode == AxisConstraint.Both || zoomMode == AxisConstraint.Vertical ) {
			double y = mouseEvent.getY();
			//Clamp to the selection start
			y = Math.max( y, selectRect.getTranslateY() );
			//Clamp to plot area
			y = Math.min( y, plotArea.getMaxY() );
			rectY.set( y );
		}
	}

        private void printSelectedZone(){
            System.out.println("x: "+this.selectRect.getX());
            System.out.println("y: "+this.selectRect.getY());
            System.out.println("width: "+ this.selectRect.getWidth());
            System.out.println("height: "+ this.selectRect.getHeight());
        }
        
        private void updateFilter(String X, String Y, double minx, double maxx, double miny, double maxy){
            Filter f = config.GlobalVariable.MAPSCATTERTOFILTER.get(X+Y);
            // new filter
            if(f == null){
                f = new Filter();
                
                // per appearance
                if(X.startsWith(config.DataSetConfig.AVG_PREFIX)){
                    String name = X.substring(1);
                    f.setX(name);
                    name = Y.substring(1);
                    f.setY(name);
                    
                }else{
                    f.setX(X);
                    f.setY(Y);
                }
                f.getFilters().add(new Range(minx,maxx,miny,maxy));
                config.GlobalVariable.MAPSCATTERTOFILTER.put(X+Y, f);
                return;
            }
            f.getFilters().add(new Range(minx,maxx,miny,maxy));
        }
        
	private void onMouseReleased() {
		if ( !selecting.get() ){
                    if(config.GlobalVariable.MAPSCATTERTOFILTER.get(X+Y) == null)
                        return;
                    // reset selection
                    config.GlobalVariable.MAPSCATTERTOFILTER.get(X+Y).getFilters().clear();
//                    config.GlobalVariable.MAPSCATTERTOFILTER.get(Y).getFilters().clear();
                    this.getFilteredPlayers();
//                    if(config.GlobalVariable.SELECTED_PLAYER_ITEM.size()!=config.GlobalVariable.FILTERED_PLAYER_ITEM.size()){
//                        config.GlobalVariable.SELECTED_PLAYER_ITEM.clear();
//                        for(PlayerItem p:config.GlobalVariable.FILTERED_PLAYER_ITEM){
//                            if(config.GlobalVariable.FILTERED_PLAYER_ITEM.indexOf(p)== config.GlobalVariable.FILTERED_PLAYER_ITEM.size()-1){
//                                config.GlobalVariable.NEED_UPDATE = true;
//                                System.out.println("set update true in 1");
//                            }
//                            config.GlobalVariable.SELECTED_PLAYER_ITEM.add(p);
//                        }
//                    }
			return;
                }

		//Prevent a silly zoom... I'm still undecided about && vs ||
		if ( selectRect.getWidth() == 0.0 ||
				 selectRect.getHeight() == 0.0 ) {
			selecting.set( false );
                        // reset here?
                        if(config.GlobalVariable.MAPSCATTERTOFILTER.get(X+Y) == null)
                            return;
                        config.GlobalVariable.MAPSCATTERTOFILTER.get(X+Y).getFilters().clear();
//                        config.GlobalVariable.MAPSCATTERTOFILTER.get(Y).getFilters().clear();
                        this.getFilteredPlayers();
			return;
		}

		Rectangle2D zoomWindow = chartInfo.getDataCoordinates(
				selectRect.getTranslateX(), selectRect.getTranslateY(),
				rectX.get(), rectY.get()
		);
                
                System.out.println("upper-left: "+ zoomWindow.getMinX()+zoomWindow.getMinY());
                System.out.println("bottom-right: "+ zoomWindow.getMaxX()+zoomWindow.getMaxY());
                
                config.GlobalVariable.MIN_X = zoomWindow.getMinX();
                config.GlobalVariable.MIN_Y = zoomWindow.getMinY();
                config.GlobalVariable.MAX_X = zoomWindow.getMaxX();
                config.GlobalVariable.MAX_Y = zoomWindow.getMaxY();
                
                System.out.println("X attribute: "+ X);
                System.out.println("Y attribute: "+ Y);
                
                // selection
//                ObservableList<Player> copy_list = FXCollections.observableArrayList(config.GlobalVariable.SELECTED_PLAYER);
//                for(Player p:copy_list){
//                    int x_count = p.getStatisticByName(X);
//                    int y_count = p.getStatisticByName(Y);
//                    
//                    
//                    if(x_count<config.GlobalVariable.MIN_X || x_count > config.GlobalVariable.MAX_X || y_count<config.GlobalVariable.MIN_Y || y_count>config.GlobalVariable.MAX_Y){
//                        config.GlobalVariable.SELECTED_PLAYER.remove(p);
//                    }
//                }

                XYChart.Series<Number,Number> series1 = new XYChart.Series(); // unselected players
                
                XYChart.Series<Number,Number> series2 = new XYChart.Series(); // selected players
                
                // update new filter , check for average
                this.updateFilter(X, Y, zoomWindow.getMinX(), zoomWindow.getMaxX(), zoomWindow.getMinY(), zoomWindow.getMaxY());
//                this.updateFilter(Y, zoomWindow.getMinY(), zoomWindow.getMaxY());
                this.getFilteredPlayers();
////////////////////////// old version ///////////////////////////////////////////////////////////////                
//                for(XYChart.Data<Number,Number> d:data.get(0).getData()){
//                    double x_count = d.getXValue().doubleValue();
//                    double y_count = d.getYValue().doubleValue();
//                    ScatterChartExtraData data = (ScatterChartExtraData)d.getExtraValue();
//                    if(x_count<config.GlobalVariable.MIN_X || x_count > config.GlobalVariable.MAX_X || y_count<config.GlobalVariable.MIN_Y || y_count>config.GlobalVariable.MAX_Y){
//                        series1.getData().add(d);
//                    }else{
//                        boolean found = false;
//                        for(PlayerItem p:config.GlobalVariable.SELECTED_PLAYER_ITEM){
//                            if(p.getPlayer().equals(data.getPlayer())){
//                                found = true;
//                                break;
//                            }
//                        }
//                        if(found)
//                            series2.getData().add(d);
//                        else
//                            series1.getData().add(d);
//                    }
//                }
//                
//                for(XYChart.Data<Number,Number> d:data.get(1).getData()){
//                    double x_count = d.getXValue().doubleValue();
//                    double y_count = d.getYValue().doubleValue();
//                    ScatterChartExtraData data = (ScatterChartExtraData)d.getExtraValue();
//                    if(x_count<config.GlobalVariable.MIN_X || x_count > config.GlobalVariable.MAX_X || y_count<config.GlobalVariable.MIN_Y || y_count>config.GlobalVariable.MAX_Y){
//                        series1.getData().add(d);
//                    }else{
//                        boolean found = false;
////                        for(Player p:config.GlobalVariable.SELECTED_PLAYER){
////                            if(p.isEqual(data.getPlayer())){
////                                found = true;
////                                break;
////                            }
////                        }
//                        for(PlayerItem p : config.GlobalVariable.SELECTED_PLAYER_ITEM){
//                            if(p.getPlayer().equals(data.getPlayer())){
//                                found = true;
//                                break;
//                            }
//                        }
//                        if(found)
//                            series2.getData().add(d);
//                        else
//                            series1.getData().add(d);
//                    }
//                       
//                }
//                
//                // update unselected players to scatter chart
//                
//                data.get(0).getData().clear();
//                for(XYChart.Data<Number,Number> d: series1.getData()){
//                    XYChart.Data<Number, Number> new_data = new XYChart.Data<Number, Number>(d.getXValue(),d.getYValue());
//                    new_data.setExtraValue(d.getExtraValue());
//                    data.get(0).getData().add(new_data);
//                }
//                
//                // update selected players to scatter chart and observable list
//                data.get(1).getData().clear();
////                config.GlobalVariable.SELECTED_PLAYER.clear();
//                config.GlobalVariable.SELECTED_PLAYER_ITEM.clear();
//                for(XYChart.Data<Number,Number> d: series2.getData()){
//                    XYChart.Data<Number, Number> new_data = new XYChart.Data<Number, Number>(d.getXValue(),d.getYValue());
//                    new_data.setExtraValue(d.getExtraValue());
//                    data.get(1).getData().add(new_data);
//                    System.out.println(((ScatterChartExtraData)d.getExtraValue()).getName());
//                    System.out.println(((ScatterChartExtraData)d.getExtraValue()).getTeam_id());
//                    System.out.println(((ScatterChartExtraData)d.getExtraValue()).getPlayer_id());
//                    if(series2.getData().indexOf(d) == series2.getData().size()-1)
//                        config.GlobalVariable.NEED_UPDATE = true;
////                    config.GlobalVariable.SELECTED_PLAYER.add(((ScatterChartExtraData)d.getExtraValue()).getPlayer());
//                    config.GlobalVariable.SELECTED_PLAYER_ITEM.add(((ScatterChartExtraData)d.getExtraValue()).getPlayerItem());
//                }
//////////////////////////////////////////// end of old version ///////////////////////////////////////////                

                for(PlayerItem p : config.GlobalVariable.SELECTED_PLAYER_ITEM){
                    System.out.println("selectd players: " + p.getPlayer_name());
                }
                
                
//                System.out.println("selected size: " + config.GlobalVariable.SELECTED_PLAYER.size());
//                
//                for(Player p:config.GlobalVariable.SELECTED_PLAYER){
//                    System.out.println(p.getName());
//                }
                
//		xAxis.setAutoRanging( false );
//		yAxis.setAutoRanging( false );
//		if ( zoomAnimated.get() ) {
//			zoomAnimation.stop();
//			zoomAnimation.getKeyFrames().setAll(
//					new KeyFrame( Duration.ZERO,
//					              new KeyValue( xAxis.lowerBoundProperty(), xAxis.getLowerBound() ),
//					              new KeyValue( xAxis.upperBoundProperty(), xAxis.getUpperBound() ),
//					              new KeyValue( yAxis.lowerBoundProperty(), yAxis.getLowerBound() ),
//					              new KeyValue( yAxis.upperBoundProperty(), yAxis.getUpperBound() )
//					),
//			    new KeyFrame( Duration.millis( zoomDurationMillis.get() ),
//			                  new KeyValue( xAxis.lowerBoundProperty(), zoomWindow.getMinX() ),
//			                  new KeyValue( xAxis.upperBoundProperty(), zoomWindow.getMaxX() ),
//			                  new KeyValue( yAxis.lowerBoundProperty(), zoomWindow.getMinY() ),
//			                  new KeyValue( yAxis.upperBoundProperty(), zoomWindow.getMaxY() )
//			    )
//			);
//			zoomAnimation.play();
//		} else {
//			zoomAnimation.stop();
//			xAxis.setLowerBound( zoomWindow.getMinX() );
//			xAxis.setUpperBound( zoomWindow.getMaxX() );
//			yAxis.setLowerBound( zoomWindow.getMinY() );
//			yAxis.setUpperBound( zoomWindow.getMaxY() );
//		}
//                this.printSelectedZone();
//		selecting.set( false );
	}

	private static double getBalance( double val, double min, double max ) {
		if ( val <= min )
			return 0.0;
		else if ( val >= max )
			return 1.0;

		return (val - min) / (max - min);
	}

	private class MouseWheelZoomHandler implements EventHandler<ScrollEvent> {
		private boolean ignoring = false;

		@Override
		public void handle( ScrollEvent event ) {
			EventType<? extends Event> eventType = event.getEventType();
			if ( eventType == ScrollEvent.SCROLL_STARTED ) {
				//mouse wheel events never send SCROLL_STARTED
				ignoring = true;
			} else if ( eventType == ScrollEvent.SCROLL_FINISHED ) {
				//end non-mouse wheel event
				ignoring = false;

			} else if ( eventType == ScrollEvent.SCROLL &&
			            //If we are allowing mouse wheel zooming
			            mouseWheelZoomAllowed.get() &&
			            //If we aren't between SCROLL_STARTED and SCROLL_FINISHED
			            !ignoring &&
			            //inertia from non-wheel gestures might have touch count of 0
			            !event.isInertia() &&
			            //Only care about vertical wheel events
			            event.getDeltaY() != 0 &&
			            //mouse wheel always has touch count of 0
			            event.getTouchCount() == 0 ) {

				//Find out which axes to zoom based on the strategy
				double eventX = event.getX();
				double eventY = event.getY();
				DefaultChartInputContext context = new DefaultChartInputContext( chartInfo, eventX, eventY );
				AxisConstraint zoomMode = mouseWheelAxisConstraintStrategy.getConstraint( context );

				if ( zoomMode == AxisConstraint.None )
					return;

				//If we are are doing a zoom animation, stop it. Also of note is that we don't zoom the
				//mouse wheel zooming. Because the mouse wheel can "fly" and generate a lot of events,
				//animation doesn't work well. Plus, as the mouse wheel changes the view a small amount in
				//a predictable way, it "looks like" an animation when you roll it.
				//We might experiment with mouse wheel zoom animation in the future, though.
				zoomAnimation.stop();

				//At this point we are a mouse wheel event, based on everything I've read
				Point2D dataCoords = chartInfo.getDataCoordinates( eventX, eventY );

				//Determine the proportion of change to the lower and upper bounds based on how far the
				//cursor is along the axis.
				double xZoomBalance = getBalance( dataCoords.getX(),
				                                  xAxis.getLowerBound(), xAxis.getUpperBound() );
				double yZoomBalance = getBalance( dataCoords.getY(),
				                                  yAxis.getLowerBound(), yAxis.getUpperBound() );

				//Are we zooming in or out, based on the direction of the roll
				double direction = -Math.signum( event.getDeltaY() );

				//TODO: Do we need to handle "continuous" scroll wheels that don't work based on ticks?
				//If so, the 0.2 needs to be modified
				double zoomAmount = 0.2 * direction;

				if ( zoomMode == AxisConstraint.Both || zoomMode == AxisConstraint.Horizontal ) {
					double xZoomDelta = ( xAxis.getUpperBound() - xAxis.getLowerBound() ) * zoomAmount;
					xAxis.setAutoRanging( false );
					xAxis.setLowerBound( xAxis.getLowerBound() - xZoomDelta * xZoomBalance );
					xAxis.setUpperBound( xAxis.getUpperBound() + xZoomDelta * ( 1 - xZoomBalance ) );
				}

				if ( zoomMode == AxisConstraint.Both || zoomMode == AxisConstraint.Vertical ) {
					double yZoomDelta = ( yAxis.getUpperBound() - yAxis.getLowerBound() ) * zoomAmount;
					yAxis.setAutoRanging( false );
					yAxis.setLowerBound( yAxis.getLowerBound() - yZoomDelta * yZoomBalance );
					yAxis.setUpperBound( yAxis.getUpperBound() + yZoomDelta * ( 1 - yZoomBalance ) );
				}
			}
		}
	}
}
