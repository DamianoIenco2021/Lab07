/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.poweroutages;


import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import it.polito.tdp.poweroutages.model.Model;
import it.polito.tdp.poweroutages.model.Nerc;
import it.polito.tdp.poweroutages.model.PowerOutages;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="cmbNerc"
    private ComboBox<Nerc> cmbNerc; // Value injected by FXMLLoader

    @FXML // fx:id="txtYears"
    private TextField txtYears; // Value injected by FXMLLoader

    @FXML // fx:id="txtHours"
    private TextField txtHours; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    private Model model;
    
    @FXML
    void doRun(ActionEvent event) {
    	txtResult.clear();
    	
    	try {
    		Nerc selectedNerc= cmbNerc.getSelectionModel().getSelectedItem();
    		if(selectedNerc== null) {
    			txtResult.setText("Select a Nerc( area identifier)");
    			return;
    		}
    		
    		int maxY = Integer.parseInt(txtYears.getText());
    		if(maxY <= 0) {
    			txtResult.setText("Selezionare un numero di anni maggiore di 0!");
    			return;
    		}
    		
    		int maxH = Integer.parseInt(txtHours.getText());
    		if(maxH <= 0) {
    			txtResult.setText("Selezionare un numero di ore maggiore di 0");
    			return;
    		}
    		
    		txtResult.setText(
    				String.format("Analizzo il worst-case.. per %d ore e %d anni", maxH, maxY));
    		List<PowerOutages> worstCase = model.getWorstCase(maxY, maxH, selectedNerc);
    		
    		txtResult.clear();
    		txtResult.appendText("Numero di persone colpite: " + model.sumAffectedPeople(worstCase) + "\n");
    		txtResult.appendText("Numero di persone colpite: " + model.sumOutageHours(worstCase) + "\n");
    		
    		for(PowerOutages ee : worstCase) {
    			txtResult.appendText(String.format("%d %s %s %d %d", ee.getYear(), ee.getOutageStart(),
    					ee.getOutageEnd(), ee.getOutageDuration(), ee.getCustomerAffected()));
    			txtResult.appendText("\n");
    		}
    		
    		
    	}catch (NumberFormatException e) {
    		txtResult.setText("Inserire un numero valido di anni ed ore!");
    	}
    	
    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert cmbNerc != null : "fx:id=\"cmbNerc\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtYears != null : "fx:id=\"txtYears\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtHours != null : "fx:id=\"txtHours\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        
        // Utilizzare questo font per incolonnare correttamente i dati;
        txtResult.setStyle("-fx-font-family: monospace");
    }
    
    public void setModel(Model model) {
    	this.model = model;
    	List<Nerc> nercList= model.getNercList();
    	cmbNerc.getItems().addAll(nercList);
    }
}
