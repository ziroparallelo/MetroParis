/**
 * Sample Skeleton for 'Metro.fxml' Controller Class
 */

package it.polito.tdp.metroparis;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.metroparis.model.Fermata;
import it.polito.tdp.metroparis.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class MetroController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="cmbBoxArrivo"
    private ComboBox<Fermata> cmbBoxArrivo; // Value injected by FXMLLoader

    @FXML // fx:id="cmbBoxPartenza"
    private ComboBox<Fermata> cmbBoxPartenza; // Value injected by FXMLLoader

    @FXML // fx:id="txtReult"
    private TextArea txtReult; // Value injected by FXMLLoader

    @FXML
    void handleCerca(ActionEvent event) {

    }

    public void setModel(Model model) {
    	this.model = model;
    	List<Fermata> fermate = model.getFermate();
    	
    	cmbBoxArrivo.getItems().addAll(fermate);
    	cmbBoxPartenza.getItems().addAll(fermate);

    }
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert cmbBoxArrivo != null : "fx:id=\"cmbBoxArrivo\" was not injected: check your FXML file 'Metro.fxml'.";
        assert cmbBoxPartenza != null : "fx:id=\"cmbBoxPartenza\" was not injected: check your FXML file 'Metro.fxml'.";
        assert txtReult != null : "fx:id=\"txtReult\" was not injected: check your FXML file 'Metro.fxml'.";

    }

}

