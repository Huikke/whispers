package fi.utu.tech.telephonegame.ui;

import fi.utu.tech.telephonegame.GuiIO;
import fi.utu.tech.telephonegame.network.Resolver.NetworkType;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;

/**
 * Do not edit this file. Älä muokkaa tätä tiedostoa.
 * 
 * This class is the JavaFx controller class for the UI
 */

public class MainViewController {

	@FXML
	private Canvas signal;
	@FXML
	private Button SendNewButton;
	@FXML
	private ComboBox<String> ipAddresses;
	@FXML
	private Button connectButton;
	@FXML
	private TextArea receivedMessage;
	@FXML
	private TextArea refinedMessage;
	@FXML
	private TextArea newMessage;
	@FXML
	private RadioButton netSelectionLocal;
	@FXML
	private RadioButton netSelectionPublic;
	@FXML
	private CheckBox setRootNode;

	private GuiIO gui_io;
	private GraphicsContext signalContent;
	private ToggleGroup radioGroup;
	private NetworkType netType = NetworkType.LOCALHOST;
	private boolean rootNode = false;

	public void set_Gui_io(GuiIO gui_io) {
		this.gui_io = gui_io;
	}

	public void addIPs() {
		ipAddresses.getItems().addAll(gui_io.getIPs());
		ipAddresses.getSelectionModel().selectFirst();
	}

	@FXML
	public void initialize() {
		signalContent = signal.getGraphicsContext2D();
		signalContent.setFill(Color.BLUE);
		signalContent.fillRect(0, 0, signal.getWidth(), signal.getHeight());

		radioGroup = new ToggleGroup();
		netSelectionLocal.setToggleGroup(radioGroup);
		netSelectionPublic.setToggleGroup(radioGroup);
		netSelectionLocal.setOnAction(event -> netType = NetworkType.LOCALHOST);
		netSelectionPublic.setOnAction(event -> netType = NetworkType.PUBLIC);
		netSelectionLocal.setSelected(true);
		setRootNode.setOnAction(event -> rootNode = !rootNode);
		ipAddresses.disableProperty().bind(setRootNode.selectedProperty().not().or(netSelectionLocal.selectedProperty()));
		connectButton.textProperty().bind(Bindings.when(setRootNode.selectedProperty()).then("Start waiting for others").otherwise("Discover a peer and connect"));
		
	}

	@FXML
	private void sendNewButtonPressed(ActionEvent event) {
		gui_io.sendNewMessage(newMessage.getText());
		newMessage.clear();
		receivedMessage.clear();
		refinedMessage.clear();
		resetSignal();
	}

	@FXML
	private void connectButtonPressed(ActionEvent event) {
		gui_io.connect(netType, rootNode, ipAddresses.getValue());
		connectButton.setDisable(true);
	}

	private void resetSignal() {
		signalContent.setFill(Color.web("hsl(0,100%,100%)"));
		signalContent.fillRect(0, 0, signal.getWidth(), signal.getHeight());
	}

	public void enableConnect() {
		Platform.runLater(() -> connectButton.setDisable(false));
	}
	
	public void setReceivedMessage(String text) {
		Platform.runLater(() -> receivedMessage.setText(text));
	}

	public void setRefinedMessage(String text) {
		Platform.runLater(() -> refinedMessage.setText(text));
	}

	public void setSignal(Integer value) {
		Platform.runLater(() -> {
			signalContent.setFill(Color.web("hsl(" + value + ",100%,100%)"));
			signalContent.fillRect(0, 0, signal.getWidth(), signal.getHeight());
		});
	}

}
