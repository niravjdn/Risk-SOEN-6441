package com.risk6441.controller;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

import com.risk6441.exception.InvalidMapException;
import com.risk6441.maputils.CommonMapUtil;
import com.risk6441.maputils.MapOperations;
import com.risk6441.maputils.MapVerifier;
import com.risk6441.maputils.MapWriter;
import com.risk6441.models.Continent;
import com.risk6441.models.Map;
import com.risk6441.models.Territory;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * This class is a controller for the MapEditor layout.
 * @author Karthik
 *
 */
public class MapRedactorController  implements Initializable{

	/**
	 * The @map map object
	 */
	private Map map;

	/**
	 * The @file
	 */
	private File file;
	
	
    /**
     * @lblAuthor1 label for author
     */
    @FXML
    private Label lblAuthor1;

    /**
     * @lblWarn for warn
     */
    @FXML
    private Label lblWarn;

    /**
     * @txtWarn for textbox Warn
     */
    @FXML
    private TextField txtWarn;

    /**
     * @lblAuthor for Label author
     */
    @FXML
    private Label lblAuthor;

    /**
     * @lblImage for label image
     */
    @FXML
    private Label lblImage;

    /**
     * @lblScroll for label scroll
     */
    @FXML
    private Label lblScroll;

    /**
     * @lblWrap for label wrap
     */
    @FXML
    private Label lblWrap;

    /**
     * @txtAuthor for textbox author
     */
    @FXML
    private TextField txtAuthor;

    /**
     * @txtImage for text image
     */
    @FXML
    private TextField txtImage;

    /**
     * @txtScroll for text scroll
     */
    @FXML
    private TextField txtScroll;

    /**
     * @txtWrap for text wrap
     */
    @FXML
    private TextField txtWrap;

    /**
     * @tctAuthorLabel12 for author
     */
    @FXML
    private Label tctAuthorLabel12;

    /**
     * @txtContName for continent name
     */
    @FXML
    private TextField txtContName;

    /**
     * @lblContDetail for continent details
     */
    @FXML
    private Label labelContDetail;

    /**
     * @txtContControlVal for continent control value
     */
    @FXML
    private TextField txtContControlVal;

    /**
     * @btnAddCont Button for adding Continent
     */
    @FXML
    private Button btnAddCont;

    /**
     * @btnDelCont Button for deleting Continent
     */
    @FXML
    private Button btnDelCont;

    /**
     * @btnUpdateCont Button for updating Continent details
     */
    @FXML
    private Button btnUpdateCont;

    /**
     * @tctAuthorLabel2 Label for Author
     */
    @FXML
    private Label tctAuthorLabel2;

    /**
     * @lblAuthor Label for author
     */
    @FXML
    private Label tctAuthorLabel11;

    /**
     * @txtXCo textfield for X Co-ordinate
     */
    @FXML
    private TextField txtXCo;

    /**
     * @txtYCo textfield for Y Co-ordinate
     */
    @FXML
    private TextField txtYCo;

    /**
     * @lblTerrname Label for Territory name
     */
    @FXML
    private Label lblTerrname;

    /**
     * @lblAdjTerr Label for adjacent territory
     */
    @FXML
    private Label lblAdjTerr;

    /**
     * @comboAdjTerr Combobox for adjacent terrritory
     */
    @FXML
    private ComboBox<Territory> comboAdjTerr;

    /**
     * @tctAuthorLabel1211 Label for author
     */
    @FXML
    private Label tctAuthorLabel1211;

    /**
     * @lblAuthor  Label for author
     */
    @FXML
    private Label tctAuthorLabel121;

    
    /**
     * @lblSelectedCont Label for selected continent
     */
    @FXML
    private Label lblSelectedCont;

    
    /**
     * @btnDelTerr Button for deleting territory
     */
    @FXML
    private Button btnDelTerr;

    /**
     * @btnAddTerr Button for adding territory
     */
    @FXML
    private Button btnAddTerr;

    /**
     * @btnExit for Exit
     */
    @FXML
    private Button btnExit;
    
    
    /**
     * @txtTerrName for Territory name
     */
    @FXML
    private TextField txtTerrName;

    /**
     * @btnUpdateTerr for updating Territory details
     */
    @FXML
    private Button btnUpdateTerr;

    /**
     * @contList List for continents
     */
    @FXML
    private ListView<Continent> contList;

    /**
     * @terrList List for Territories
     */
    @FXML
    private ListView<Territory> terrList;

    /**
     * @adjTerrList List for adjacent Territories
     */
    @FXML
    private ListView<Territory> adjTerrList;

    /**
     * @lblContList List for Continents
     */
    @FXML
    private Label lblContList;

    /**
     * @terrList List for Territories
     */
    @FXML
    private Label lblTerrList;

    /**
     * @btnDltAdjTerr Button for deleting adjacent territory
     */
    @FXML
    private Button btnDltAdjTerr;

    /**
     * @txtAreaMsg Textarea for message
     */
    @FXML
    private TextArea txtAreaMsg;

	
	/**
	 * This is parameterized constructor used in case of editing existing map.
	 * @param map
	 * @param file
	 */
	public MapRedactorController(Map map, File file) {
		this.map = map;
		this.file = file;
	}
	
	
	
	/**
	 * This is a default constructor which doesn't take any parameters.
	 */
	public MapRedactorController() {
		
	}
	
	/**
	 * This method adds the continent to the map
	 * @param event
	 * 		  event object containing details regarding origin of the event
	 */
	@FXML
    void addContinent(ActionEvent event) {
		//get details from the event object and proceed
		
		//call MapOperation.addContinent simillar for others
		Continent cnt;
		try {
			cnt = MapOperations.addContinent(map, txtContName.getText(), txtContControlVal.getText());
		}catch(InvalidMapException e) {
			CommonMapUtil.alertBox("Error", e.getMessage(), "Map is not valid.");
			return;
		}
		
		if (contList == null) {
			contList = new ListView<Continent>();
		}
		
		map.getContinents().add(cnt);
		contList.getItems().add(cnt);
		
		//clear the textboxes
		CommonMapUtil.clearTextBox(txtContName,txtContControlVal);
		
    }

    /**
     * This method adds a terrritory to the continent.
     * @param event
     */
    @FXML
    void addTerritiory(ActionEvent event) {

    	Territory adjTerr = comboAdjTerr.getSelectionModel().getSelectedItem();
    	Continent continent = contList.getSelectionModel().getSelectedItem();
    	
    	Territory territory = null;
    	try {
    		territory = MapOperations.addTerritory(map, txtTerrName.getText(), txtXCo.getText(), txtYCo.getText(),
    				adjTerr, continent);
    	}catch (InvalidMapException e) {
    		CommonMapUtil.alertBox("Error", e.getMessage(), "Map is not valid.");
    		return;
		}
    	
    	//add territories to continent
    	continent = MapOperations.mapTerritoriryToContinent(continent, territory);
    	comboAdjTerr.getItems().add(territory);
    	terrList.getItems().add(territory);
    	CommonMapUtil.clearTextBox(txtTerrName,txtXCo,txtYCo);
    }

    /**
     * This method deletes continent from the map.
     * @param event
     */
    @FXML
    void deleteContinent(ActionEvent event) {
    	Continent continent = contList.getSelectionModel().getSelectedItem();
    	
    	if(continent!=null && continent.getTerritories().size()>0) {
    		CommonMapUtil.putMessgae(txtAreaMsg, "Delete its territories first "+continent.getTerritories());
    	}else {
    		map.getContinents().remove(continent);
    		contList.getItems().remove(continent);
    		CommonMapUtil.putMessgae(txtAreaMsg, "Removed Successfully : Continent :"+continent);
    		CommonMapUtil.enableControls(txtContName,btnAddCont);
        	CommonMapUtil.clearTextBox(txtContName,txtContControlVal);
    	}
    }

    /**
     * This method deletes territory from the continent.
     * @param event
     */
    @FXML
    void deleteTerritiory(ActionEvent event) {
    	Continent continent = contList.getSelectionModel().getSelectedItem();
		Territory territory = terrList.getSelectionModel().getSelectedItem();
		
		if(continent!=null && continent.getTerritories().size() == 1) {
			CommonMapUtil.putMessgae(txtAreaMsg, "Continent has only one territory, hence it can't be removed.");
			return;
		}
		//now iterate and make a hash set
		Set<Territory> fromTerrToBeRemoved = new HashSet<>();
		
		if(territory!=null) {
			for(Territory adjTerr : territory.getAdjacentTerritories()) {
				if(adjTerr.getAdjacentTerritories().size()==1) {
					CommonMapUtil.putMessgae(txtAreaMsg, adjTerr.getName()+" has only one neighbour "+territory
							+ " , hence It can't be removed. OR add another territory as its neighbour and remove "+territory+".");
					return;
				}else {
					fromTerrToBeRemoved.add(adjTerr);
				}
			}
		}
		
		for(Territory t : fromTerrToBeRemoved) {
			t.getAdjacentTerritories().remove(territory);
		}
		
		terrList.getItems().remove(territory);
		continent.getTerritories().remove(territory);
		CommonMapUtil.disableControls(btnDelTerr,btnUpdateTerr);
		CommonMapUtil.putMessgae(txtAreaMsg, "Removed Successfully : Territory :"+territory);
    }

    /**
     * This method exits the program when the button is clicked.
     * @param event
     */
    @FXML
    void exitBtnClick(ActionEvent event) {
    	Stage stage = (Stage) btnExit.getScene().getWindow();
		stage.close();
    }


    /**
     * This method deletes adjacent territories
     * @param event
     */
    @FXML
    void deleteAdjTerritory(ActionEvent event) {
    	Territory terr = terrList.getSelectionModel().getSelectedItem();
    	Territory adjTerr = adjTerrList.getSelectionModel().getSelectedItem();
    	
    	if(terr!=null && adjTerr!=null) {
    		if(terr.getAdjacentTerritories().size() <= 1) {
    			CommonMapUtil.putMessgae(txtAreaMsg, "There should be at least one adjacent territory.");
    		}else {
    			//remove reference from both...adjacency relationship is mutual 
    			terr.getAdjacentTerritories().remove(adjTerr);
    			adjTerr.getAdjacentTerritories().remove(terr); 
    			
    			adjTerrList.getItems().remove(adjTerr);
    			CommonMapUtil.putMessgae(txtAreaMsg, "Removed Successfully : Adjacent Territory "+adjTerr.getName());
    		}
    	}
    }

    
    /**
     * This method updates continent details.
     * @param event
     */
    @FXML
    void updateContinent(ActionEvent event) {
    	MapOperations.updateContinent(contList.getSelectionModel().getSelectedItem(), txtContControlVal.getText());
    	CommonMapUtil.enableControls(btnAddCont,txtContName);
    	CommonMapUtil.clearTextBox(txtContName,txtContControlVal);
    }

    /**
     * This method updates territories.
     * @param event
     */
    @FXML
    void updateTerritiory(ActionEvent event) {
    	Territory territory = terrList.getSelectionModel().getSelectedItem();
    	Territory adjTerr = comboAdjTerr.getSelectionModel().getSelectedItem();
    	
    	if(territory.equals(adjTerr)) {
    		CommonMapUtil.putMessgae(txtAreaMsg, "Territory can'be its own neighbour.");
    		return;
    	}
    	
    	territory = MapOperations.updateTerritory(territory, txtXCo.getText(), txtYCo.getText(), adjTerr);
    	CommonMapUtil.enableControls(btnAddTerr,txtTerrName);
    	CommonMapUtil.clearTextBox(txtTerrName,txtXCo,txtYCo);
    	showAdjTerritoryOfTerrInList(territory);
    }
    

    /**
     * This method validates the map. If it is valid then save it else show error.
     * @param event
     */
    @FXML
    void saveMap(ActionEvent event) {
    	map.getMapData().put("image",txtImage.getText());
    	map.getMapData().put("author",txtAuthor.getText());
    	map.getMapData().put("scroll",txtScroll.getText());
    	map.getMapData().put("wrap",txtWrap.getText());
    	map.getMapData().put("wran",txtWarn.getText());
    	
    	try {
    		MapVerifier.verifyMap(map);
        }
    	catch(InvalidMapException e)
    	{
    		e.printStackTrace();
    		CommonMapUtil.alertBox("Error", e.getMessage(), "Map is not valid.");
    		return;
    	}

    	
    	MapWriter write = new MapWriter();
    	
    	if(file==null) {
    		file = CommonMapUtil.saveFileDialog();
    	}
    	write.writeMapFile(map, file);
    }
    
	/*
	 * This method intializes the Map Editor with default values.
	 */
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println("Intializer Called");
		
		CommonMapUtil.disableControls(btnAddTerr,btnUpdateTerr,btnDelTerr,txtTerrName,txtXCo,txtYCo,comboAdjTerr);
		comboAdjTerr.getItems().add(null);
		CommonMapUtil.disableControls(btnDltAdjTerr);
		
		if (this.map == null) {
			map = new Map();
			
			txtAuthor.setText("author");
			txtImage.setText("image");
			txtScroll.setText("scroll");
			txtWarn.setText("warn");
			txtWrap.setText("wrap");
			
		} else {
			//for loading existing map and editing
			parseMapData();
		}			
		
		contList.setCellFactory(param -> new ListCell<Continent>() {
		    @Override
		    protected void updateItem(Continent item, boolean empty) {
		        super.updateItem(item, empty);

		        if (empty || item == null || item.getName() == null) {
		            setText(null);
		        } else {
		            setText(item.getName());
		        }
		    }
		});
		
		terrList.setCellFactory(param -> new ListCell<Territory>() {
		    @Override
		    protected void updateItem(Territory item, boolean empty) {
		        super.updateItem(item, empty);

		        if (empty || item == null || item.getName() == null) {
		            setText(null);
		        } else {
		            setText(item.getName());
		        }
		    }
		});
		
		adjTerrList.setCellFactory(param -> new ListCell<Territory>() {
		    @Override
		    protected void updateItem(Territory item, boolean empty) {
		        super.updateItem(item, empty);

		        if (empty || item == null || item.getName() == null) {
		            setText(null);
		        } else {
		            setText(item.getName());
		        }
		    }
		});
		
		comboAdjTerr.setCellFactory(param -> new ListCell<Territory>() {
		    @Override
		    protected void updateItem(Territory item, boolean empty) {
		        super.updateItem(item, empty);

		        if (empty || item == null || item.getName() == null) {
		            setText(null);
		        } else {
		            setText(item.getName());
		        }
		    }
		});
		
		
		contList.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				onClickContList();
			}
		});
		
		
		terrList.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				onClickTerrList();
			}
		});
		
		adjTerrList.setOnMouseClicked(new EventHandler<MouseEvent>() {
			
			public void handle(MouseEvent event) {
				CommonMapUtil.enableControls(btnDltAdjTerr);
			}; 
		});
		
	}
	
	/**
	 * This method parses map data and stores in the map file
	 */
	public void parseMapData() {
	
		System.out.println("parseMapData Called");
		txtAuthor.setText(map.getMapData().get("author"));
		txtImage.setText(map.getMapData().get("image"));
		txtScroll.setText(map.getMapData().get("scroll"));
		txtWarn.setText(map.getMapData().get("warn"));
		txtWrap.setText(map.getMapData().get("wrap"));
		
		for(Continent continent : map.getContinents()) {
			contList.getItems().add(continent);
			for(Territory territory : continent.getTerritories()) {
				comboAdjTerr.getItems().add(territory);
			}
		}
			
	}
	
	/**
	 * This method is to perform operations on the selected continent like update, delete etc
	 */
	public void onClickContList() {
		Continent cnt = contList.getSelectionModel().getSelectedItem();
		txtContName.setText(cnt.getName());
		txtContControlVal.setText(String.valueOf(cnt.getValue()));
		lblSelectedCont.setText(cnt.getName());
		
		CommonMapUtil.disableControls(txtContName,btnAddCont,btnDltAdjTerr, txtTerrName, txtXCo, txtYCo, comboAdjTerr,btnAddTerr, btnUpdateTerr, btnDelTerr);
		CommonMapUtil.clearTextBox(txtTerrName, txtXCo, txtYCo);
		CommonMapUtil.enableControls(txtTerrName,btnAddTerr,txtXCo, txtYCo, comboAdjTerr);
		
		adjTerrList.getItems().clear();
		//show territories in the territory list
		showTerritoryOfContInList(contList.getSelectionModel().getSelectedItem());
	}
	
	/**
	 * This displays the territories in the selected Continent.
	 * @param continent
	 */
	public void showTerritoryOfContInList(Continent continent) {
		terrList.getItems().clear();
		try {
			for (Territory t : continent.getTerritories()) {
				terrList.getItems().add(t);
			}
		}catch (Exception e) {
			//CommonMapUtil.alertBox("Error", e.getMessage(), "Map is not valid.");
			//exception  will be thrown if continent doesn't have any territories
		}
	}
	
	/**
	 * This method is to perform operations on the selected territory like update, delete etc
	 */
	public void onClickTerrList() {
		Territory terr = terrList.getSelectionModel().getSelectedItem();
		txtTerrName.setText(terr.getName());
		txtXCo.setText(String.valueOf(terr.getxCoordinate()));
		txtYCo.setText(String.valueOf(terr.getyCoordinate()));
		
		CommonMapUtil.disableControls(txtTerrName,btnAddTerr,btnDltAdjTerr);
		CommonMapUtil.clearTextBox(txtContName, txtContControlVal);
		CommonMapUtil.enableControls(txtContName,btnAddCont, btnUpdateTerr, btnDelTerr);
		
		//show territories in the territory list
		showAdjTerritoryOfTerrInList(terrList.getSelectionModel().getSelectedItem());
	}
	
	/**
	 * This method is used to obtain the adjacent territories of a given territory
	 * @param terr Requiresd territory object whose adjacent territories have to be displayed
	 */
	public void showAdjTerritoryOfTerrInList(Territory terr) {
		adjTerrList.getItems().clear();
		try {
			for (Territory t : terr.getAdjacentTerritories()) {
				adjTerrList.getItems().add(t);
			}
		}catch (Exception e) {
			//CommonMapUtil.alertBox("Error", e.getMessage(), "Map is not valid.");
			//exception  will be thrown if continent doesn't have any territories
		}
	}
}

