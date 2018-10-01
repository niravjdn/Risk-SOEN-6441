package com.risk6441.controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

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
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * This class is a controller for the MapEditor layout.
 * @author Nirav
 *
 */
public class MapRedactorController  implements Initializable{

	/**
	 * The @map
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
    @FXML
    private TextField txtName;

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
    private TextField txtAreaMsg;

	
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

    }

    @FXML
    void deleteTerritiory(ActionEvent event) {

    }

    @FXML
    void exitBtnClick(ActionEvent event) {

    }


    @FXML
    void updateContinent(ActionEvent event) {
    	MapOperations.updateContinent(contList.getSelectionModel().getSelectedItem(), txtContControlVal.getText());
    	CommonMapUtil.enableControls(btnAddCont,txtContName);
    	CommonMapUtil.clearTextBox(txtContName,txtContControlVal);
    }

    @FXML
    void updateTerritiory(ActionEvent event) {

    }
    

    /**
     * This method validates the map. If it is valid then save it else show error.
     * @param event
     */
    @FXML
    void saveMap(ActionEvent event) {

    }
    

    
    private void showAdjTerritoryInList(Territory territory) {
    	
    }

	/* (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	public void initialize(URL location, ResourceBundle resources) {
		if (this.map == null) {
			map = new Map();
		} else {
			//for loading existing map and editing
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
		ShowTerritoryOfContInListView(contList.getSelectionModel().getSelectedItem());
	}
	
	public void ShowTerritoryOfContInListView(Continent continent) {
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
	
		
}

