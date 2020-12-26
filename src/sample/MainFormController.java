package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.*;

import java.net.URL;
import java.util.ResourceBundle;

public class MainFormController implements Initializable {

    @FXML
    public ListView <String > BlackList;
    @FXML
    public ListView <String > WhiteList;
    @FXML
    public Label BlackLbl;
    @FXML
    public Label WhiteLbl;

    ObservableList<String> BlackListItems = FXCollections.observableArrayList();
    ObservableList<String> WhiteListItems = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        BlackList.setItems(BlackListItems);
        WhiteList.setItems(WhiteListItems);
    }

    public void onDragDetected(MouseEvent mouseEvent) {

        Node sourceNode = (Node) mouseEvent.getSource();
        Dragboard db = sourceNode.startDragAndDrop(TransferMode.ANY);

        ClipboardContent content = new ClipboardContent();
        content.putString("");
        db.setContent(content);

        mouseEvent.consume();
    }

    public void onListViewDragOver(DragEvent dragEvent)
    {
        if (dragEvent.getGestureSource() == BlackLbl && dragEvent.getSource() == BlackList
                || dragEvent.getGestureSource() == WhiteLbl && dragEvent.getSource() == WhiteList) {
            dragEvent.acceptTransferModes(TransferMode.ANY);
        }

        if (dragEvent.getGestureSource() instanceof ListView) {
            dragEvent.acceptTransferModes(TransferMode.ANY);
        }
    }

    public void onListViewDragDropped(DragEvent dragEvent)
    {
        ListView<String> targetListView = (ListView) dragEvent.getGestureTarget();

        if (dragEvent.getGestureSource() instanceof Label) {
            Label sourceLabel = (Label) dragEvent.getGestureSource();
            targetListView.getItems().add(sourceLabel.getText());
        } else if (dragEvent.getGestureSource() instanceof ListView) {
            ListView sourceListView = (ListView) dragEvent.getGestureSource();

            String lastItem = (String)sourceListView.getItems().get(sourceListView.getItems().size() - 1);

            sourceListView.getItems().remove(sourceListView.getItems().size() - 1);

            targetListView.getItems().add(lastItem);
        }
    }

}
