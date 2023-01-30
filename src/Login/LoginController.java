package Login;

import dbConnector.mySQLCon;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;


public class LoginController implements Initializable {
    public static Stage stage = null;
    public static String acc;
    @FXML
    private TextField accountno;

    @FXML
    private PasswordField pin;
    @FXML
    private  Pane main_area;
    @FXML
    public Button Close;

    @FXML
    private void closeApp(MouseEvent event) {
        Platform.exit();
        System.exit(0);

    }

    @FXML
    public void createAccount(javafx.scene.input.MouseEvent mouseEvent) throws IOException {
        Parent fxml= FXMLLoader.load(getClass().getResource("/CreateAccount/CreateAccount.fxml"));
        main_area.getChildren().removeAll();
        main_area.getChildren().addAll(fxml);
    }
    @FXML
    public void forgotPassword(javafx.scene.input.MouseEvent mouseEvent) throws IOException {
        Parent fxml= FXMLLoader.load(getClass().getResource("/forgotpass/forgotpass.fxml"));
        main_area.getChildren().removeAll();
        main_area.getChildren().addAll(fxml);
    }
    public void loginAccount(MouseEvent event){
        Connection con=mySQLCon.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        System.out.println( accountno.getText());
        System.out.println(pin.getText());
        try{
            String sql = "SELECT * FROM userdata WHERE AccountNo=? and PIN=?";
            ps = con.prepareStatement(sql);
            ps.setString(1, accountno.getText());
            ps.setString(2, pin.getText());
            acc = accountno.getText();
            rs = ps.executeQuery();
             if(rs.next()){
                ((Node)event.getSource()).getScene().getWindow().hide();
                Parent root= FXMLLoader.load(getClass().getResource("/dashboard/Dashboard.fxml"));
                Scene scene =new Scene(root);
                scene.getStylesheets().add(getClass().getResource("/design/design.css").toExternalForm());
                Stage stage = new Stage();
                stage.initStyle(StageStyle.UNDECORATED);
                stage.setScene(scene);
                stage.show();
                this.stage = stage;

            }else if (acc.equals("admin")&&pin.getText().equals("1234")){
                 System.out.println("1");
                 ((Node)event.getSource()).getScene().getWindow().hide();
                 System.out.println("2");
                 Parent root= FXMLLoader.load(getClass().getResource("/admin/AdminPanel.fxml"));
                 System.out.println("3");
                 Scene scene =new Scene(root);
                 scene.getStylesheets().add(getClass().getResource("/design/design.css").toExternalForm());
                 Stage stage = new Stage();
                 stage.initStyle(StageStyle.UNDECORATED);
                 stage.setScene(scene);
                 stage.show();
                 this.stage = stage;

             }
            else
            {   Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Error");
                a.setHeaderText("Error in login");
                a.setContentText("Your account number or pin is wrong. Enter again..!!!" );
                a.showAndWait();


            }


        }catch(Exception e){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error");
            a.setHeaderText("Error in login");
            a.setContentText("There is some error. PLEASE TRY AGAIN..!!!");
            a.showAndWait();


        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}






