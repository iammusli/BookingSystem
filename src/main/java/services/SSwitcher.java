package services;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SSwitcher {

	private String resource;
    private Object controller;
    private ActionEvent event;

    public SSwitcher(String resource, Object controller, ActionEvent event) {
        this.setResource(resource);
        this.setController(controller);
        this.setEvent(event);
    }

	public void setResource(String resource) {
		this.resource = resource;
	}

	public void setController(Object controller) {
		this.controller = controller;
	}

	public void setEvent(ActionEvent event2) {
		this.event = event2;
	}
	
	public void load() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(this.resource));
        if (this.controller != null) {
            loader.setController(this.controller);
        }
        Stage stage = (Stage) ((Node) this.event.getSource()).getScene().getWindow();
        try {
            stage.setScene(
                    new Scene(loader.load())
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.show();
    }
}
