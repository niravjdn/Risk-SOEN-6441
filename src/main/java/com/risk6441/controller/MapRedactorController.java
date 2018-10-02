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
import com.risk6441.models.Continent;
import com.risk6441.models.Map;
import com.risk6441.models.Territory;

import javafx.event.ActionEvent;
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
     * @lblAuthor label for author
     */
    @FXML
    private Label lblAuthor1;

    /**
     * @lblAuthor
     */
    @FXML
    private Label lblWarn;

    /**
     * @lblAuthor
     */
    @FXML
    private TextField txtWarn;

    /**
     * @lblAuthor
     */
    @FXML
    private Label lblAuthor;

    /**
     * @lblAuthor
     */
    @FXML
    private Label lblImage;

    /**
     * @lblAuthor
     */
    @FXML
    private Label lblScroll;

    /**
     * @lblAuthor
     */
    @FXML
    private Label lblWrap;

    /**
     * @lblAuthor
     */
    @FXML
    private TextField txtAuthor;

    /**
     * @lblAuthor
     */
    @FXML
    private TextField txtImage;

    /**
     * @lblAuthor
     */
    @FXML
    private TextField txtScroll;

    /**
     * @lblAuthor
     */
    @FXML
    private TextField txtWrap;

    /**
     * @lblAuthor
     */
    @FXML
    private Label tctAuthorLabel12;

    /**
     * @lblAuthor
     */
    

    /**
     * @lblAuthor
     */
    @FXML
    private TextField txtContName;

    /**
     * @lblAuthor
     */
    @FXML
    private Label labelContDetail;

    /**
     * @lblAuthor
     */
    @FXML
    private TextField txtContControlVal;

    /**
     * @lblAuthor
     */
    @FXML
    private Button btnAddCont;

    /**
     * @lblAuthor
     */
    @FXML
    private Button btnContDelete;

    /**
     * @lblAuthor
     */
    @FXML
    private Button btnContUpdate;

    /**
     * @lblAuthor
     */
    @FXML
    private Label tctAuthorLabel2;

    /**
     * @lblAuthor
     */
    @FXML
    private Label tctAuthorLabel11;

    /**
     * @lblAuthor
     */
    @FXML
    private TextField txtXCo;

    /**
     * @lblAuthor
     */
    @FXML
    private TextField txtYCo;

    /**
     * @lblAuthor
     */
    @FXML
    private Label lblTerrname;

    /**
     * @lblAuthor
     */
    @FXML
    private Label lblAdjTerr;

    /**
     * @lblAuthor
     */
    @FXML
    private ComboBox<Territory> comboAdjTerr;

    /**
     * @lblAuthor
     */
    @FXML
    private Label tctAuthorLabel1211;

    /**
     * @lblAuthor
     */
    @FXML
    private Label tctAuthorLabel121;

    
    /**
     * @lblAuthor
     */
    @FXML
    private Label lblSelectedCont;

    
    /**
     * @lblAuthor
     */
    @FXML
    private Button btnTerrDlt;

    /**
     * @lblAuthor
     */
    @FXML
    private Button btnAddTerr;

    /**
     * @lblAuthor
     */
    @FXML
    private Button btnExit;
    
    
    /**
     * @lblAuthor
     */
    @FXML
    private TextField txtTerrName;

    /**
     * @lblAuthor
     */
    @FXML
    private Button btnTerrUpdate;

    /**
     * @lblAuthor
     */
    @FXML
    private ListView<Continent> contList;

    /**
     * @lblAuthor
     */
    @FXML
    private ListView<Territory> terrList;

    /**
     * @lblAuthor
     */
    @FXML
    private ListView<Territory> adjTerrList;

    /**
     * @lblAuthor
     */
    @FXML
    private Label lblContList;

    /**
     * @lblAuthor
     */
    @FXML
    private Label lblTerrList;

    /**
     * @lblAuthor
     */
    @FXML
    private Button btnDltAdjTerr;

    /**
     * @lblAuthor
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
	
	public MapRedactorController() {
		
	}
	
	/**
	 * This method adds the continent to the map
	 * @param event
	 * 		  event object containing detaisl regarding origin of the event
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
    	continent = MapOperations.assignTerritoriesToContinent(continent, territory);
    	comboAdjTerr.getItems().add(territory);
    	terrList.getItems().add(territory);
    	CommonMapUtil.clearTextBox(txtTerrName,txtXCo,txtYCo);
    }

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
		CommonMapUtil.putMessgae(txtAreaMsg, "Removed Successfully : Territory :"+territory);
    }

    @FXML
    void exitBtnClick(ActionEvent event) {
    	Stage stage = (Stage) btnExit.getScene().getWindow();
		stage.close();
    }


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

    
    @FXML
    void updateContinent(ActionEvent event) {
    	MapOperations.updateContinent(contList.getSelectionModel().getSelectedItem(), txtContControlVal.getText());
    	CommonMapUtil.enableControls(btnAddCont,txtContName);
    	CommonMapUtil.clearTextBox(txtContName,txtContControlVal);
    }

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

    }
    
	/* (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println("Intializer Called");
		if (this.map == null) {
			map = new Map();
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
		
	}
	
	public void parseMapData() {
	
		System.out.println("parseMapData Called");
		txtAuthor.setText(map.getMapData().get("author"));
		txtImage.setText(map.getMapData().get("iamge"));
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
	
	public void onClickContList() {
		Continent cnt = contList.getSelectionModel().getSelectedItem();
		txtContName.setText(cnt.getName());
		txtContControlVal.setText(String.valueOf(cnt.getValue()));
		lblSelectedCont.setText(cnt.getName());
		
		CommonMapUtil.disableControls(txtContName,btnAddCont);
		CommonMapUtil.clearTextBox(txtTerrName, txtXCo, txtYCo);
		CommonMapUtil.enableControls(txtTerrName,btnAddTerr);
		
		adjTerrList.getItems().clear();
		//show territories in the territory list
		showTerritoryOfContInList(contList.getSelectionModel().getSelectedItem());
	}
	
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
	
	public void onClickTerrList() {
		Territory terr = terrList.getSelectionModel().getSelectedItem();
		txtTerrName.setText(terr.getName());
		txtXCo.setText(String.valueOf(terr.getxCoordinate()));
		txtYCo.setText(String.valueOf(terr.getyCoordinate()));
		
		CommonMapUtil.disableControls(txtTerrName,btnAddTerr);
		CommonMapUtil.clearTextBox(txtContName, txtContControlVal);
		CommonMapUtil.enableControls(txtContName,btnAddCont);
		
		//show territories in the territory list
		showAdjTerritoryOfTerrInList(terrList.getSelectionModel().getSelectedItem());
	}
	
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

