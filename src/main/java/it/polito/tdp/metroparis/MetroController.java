/**
 * Sample Skeleton for 'Metro.fxml' Controller Class
 */

package it.polito.tdp.metroparis;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.metroparis.model.Fermata;
import it.polito.tdp.metroparis.model.Model;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;

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
    
    @FXML
    private TableColumn<Fermata, String> colFermata;

    @FXML
    private TableView<Fermata> tablePercorso;


    @FXML // fx:id="txtReult"
    private TextArea txtReult; // Value injected by FXMLLoader

    @FXML
    void handleCerca(ActionEvent event) {

    	
    	Fermata partenza = cmbBoxPartenza.getValue();
    	Fermata arrivo = cmbBoxArrivo.getValue();
    	
    	if(partenza!=null && arrivo!=null && !partenza.equals(null)) {
    		List<Fermata> percorso = model.calcolaPercorso(partenza, arrivo);
    		
    		tablePercorso.setItems(FXCollections.observableArrayList(percorso));
    		
    		txtReult.setText("Percorso trovato con "+percorso.size()+" stazioni");
    	} else {
    		txtReult.setText("Devi selezionare due stazioni, diverse tra loro\n");
    	}
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

        
        colFermata.setCellValueFactory(new PropertyValueFactory<Fermata, String>("nome"));
    }

}

