package admin;
import Login.LoginController;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminPanel implements Initializable {
    private double xOffset = 0;
    private double yOffset = 0;
    @FXML
    private Circle profilepic;
    @FXML
    private Text name;
    @FXML
    private FontAwesomeIconView ico;
    @FXML
    private Pane dashboard_main;


    @FXML
    private void closeApp(MouseEvent event) {
        Platform.exit();
        System.exit(0);

    }

    @FXML
    private void minimizeApp(MouseEvent event) {
        Stage stage = (Stage) ico.getScene().getWindow();
        stage.setIconified(true);

    }

//    public void setData() {
//        Connection con = mySQLCon.getConnection();
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//
//        try {
//            String sql = "SELECT * FROM userdata WHERE AccountNo=?";
//            ps = con.prepareStatement(sql);
//            ps.setString(1, LoginController.acc);
//            rs = ps.executeQuery();
//            if (rs.next()) {
//                name.setText(rs.getString("Name"));
//                InputStream is = rs.getBinaryStream(("ProfilePic"));
//                OutputStream os = new FileOutputStream(new File("pic.jpg"));
//                byte[] content = new byte[1024];
//                int size = 0;
//                while ((size = is.read(content)) != -1) {
//                    os.write(content, 0, size);
//                }
//                os.close();
//                is.close();
//                profilepic.setStroke(Color.WHITE);
//
//                Image img = new Image("file:pic.jpg", false);
//                profilepic.setFill(new ImagePattern(img));
//
//
//            } else {
//                Alert a = new Alert(Alert.AlertType.ERROR);
//                a.setTitle("Error");
//                a.setHeaderText("Error in login");
//                a.setContentText("Your account number or pin is wrong. Enter again..!!!");
//                a.showAndWait();
//
//
//            }
//
//
//        } catch (Exception e) {
//            Alert a = new Alert(Alert.AlertType.ERROR);
//            a.setTitle("Error");
//            a.setHeaderText("Error in login .");
//            a.setContentText("There is some error. PLEASE TRY AGAIN..!!!");
//            a.showAndWait();
//
//        }
//
//    }

    @FXML
    public void click(MouseEvent mouseEvent) {
        xOffset = mouseEvent.getSceneX();
        yOffset = mouseEvent.getSceneY();

    }

    @FXML
    public void drag(MouseEvent mouseEvent) {
        LoginController.stage.setX(mouseEvent.getScreenX() - xOffset);
        LoginController.stage.setY(mouseEvent.getScreenY() - yOffset);

    }


    @FXML
    public void customerInfo(MouseEvent mouseEvent) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/CustomerInfo/CustomerInfo.fxml"));
        dashboard_main.getChildren().removeAll();
        dashboard_main.getChildren().addAll(fxml);

    }
    @FXML
    public void allowedBank(MouseEvent mouseEvent) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/allowedBank/AllowBank.fxml"));
        dashboard_main.getChildren().removeAll();
        dashboard_main.getChildren().addAll(fxml);

    }



    @FXML
    public void deposit(MouseEvent mouseEvent) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/deposit/DepositAmount.fxml"));
        dashboard_main.getChildren().removeAll();
        dashboard_main.getChildren().addAll(fxml);

    }
    public void transactionHistory(MouseEvent mouseEvent) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/transactionhistory/TransactionHistory.fxml"));
        dashboard_main.getChildren().removeAll();
        dashboard_main.getChildren().addAll(fxml);

    }

    @FXML
    public void mainScreen() throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        dashboard_main.getChildren().removeAll();
        dashboard_main.getChildren().addAll(fxml);

    }

    @FXML
    public void changePin() throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/changepin/ChangePin.fxml"));
        dashboard_main.getChildren().removeAll();
        dashboard_main.getChildren().addAll(fxml);

    }
    @FXML
    public void transferAmount() throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/transferamount/TransferAmount.fxml"));
        dashboard_main.getChildren().removeAll();
        dashboard_main.getChildren().addAll(fxml);

    }

    public void logout(MouseEvent event) throws Exception {
        ((Node) event.getSource()).getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("/Login/LoginScreen.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/design/design.css").toExternalForm());
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                xOffset = mouseEvent.getSceneX();
                yOffset = mouseEvent.getSceneY();
            }
        });
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                stage.setX(mouseEvent.getScreenX() - xOffset);
                stage.setY(mouseEvent.getScreenY() - yOffset);

            }
        });

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        setData();
//        try {
//            mainScreen();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

    }
}
