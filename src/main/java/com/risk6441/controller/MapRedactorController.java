package com.risk6441.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.risk6441.models.Continent;
import com.risk6441.models.Territory;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

/**
 * This class is a controller for the MapEditor layout.
 * @author Nirav
 *
 */
public class MapRedactorController implements Initializable {

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
    private Button btnContAdd;

    /**
     * @lblAuthor
     */
    @FXML
    private Button BtnContDelete;

    /**
     * @lblAuthor
     */
    @FXML
    private Button BtnContUpdate;

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
    private ComboBox<Territory> ComboTerrAdj;

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
    private Button BtnTerrDlt;

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
    private Button BtnTerrUpdate;

    /**
     * @lblAuthor
     */
    @FXML
    private ListView<Continent> lstCont;

    /**
     * @lblAuthor
     */
    @FXML
    private ListView<Territory> lstTerr;

    /**
     * @lblAuthor
     */
    @FXML
    private ListView<Territory> lstAdj;

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

	/* (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

}

