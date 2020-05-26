/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.bar;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.bar.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class FXMLController {
	private Model model=new Model();
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void handleSimula(ActionEvent event) {
    	txtResult.clear();
    	model.simula();
    	Integer totale=model.getTotClienti();
    	Integer soddisfatti=model.getSoddisfatti();
    	Integer insoddisfatti=model.getInsoddisfatti();
    	this.txtResult.appendText("I clienti totali sono "+totale+" ma di questi i soddisfatti sono "+soddisfatti+" e gli insoddisfatti sono "+insoddisfatti);
    	Float percentualeInsoddisfatti=(float)((float)insoddisfatti/(float)totale)*100;
    	this.txtResult.appendText("\nLa percentuale degli insoddisfatti è "+percentualeInsoddisfatti+"%");
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
}
