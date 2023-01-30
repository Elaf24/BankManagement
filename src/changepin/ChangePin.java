package changepin;

import Login.LoginController;
import dashboard.Dashboard;
import dbConnector.mySQLCon;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class ChangePin implements Initializable {
    @FXML
    private TextField oldpin;
    @FXML
    private TextField newpin;
    @FXML
    private TextField confirmdpin;
    Dashboard d = new Dashboard();


    public void changePin(MouseEvent event) {
        Connection con = mySQLCon.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT * FROM userdata WHERE AccountNo=? and PIN = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, LoginController.acc);
            ps.setString(2, oldpin.getText());
            rs = ps.executeQuery();
            if (rs.next()) {
                if (newpin.getText().equals(confirmdpin.getText())) {
                    String sql1 = "UPDATE userdata SET PIN='" + newpin.getText() + "'WHERE AccountNo='" + LoginController.acc + "'";
                    ps = con.prepareStatement(sql1);
                    ps.execute();
                    Alert a = new Alert(Alert.AlertType.INFORMATION);
                    a.setTitle("Change PIN");
                    a.setHeaderText("PIN Change Successful");
                    a.setContentText("Your Pin has been changed now you have to login again with new pin");
                    a.showAndWait();
                    oldpin.setText("");
                    newpin.setText("");
                    confirmdpin.setText("");
                    d.logout(event);
                }
            } else {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Error");
                a.setHeaderText("Error in login");
                a.setContentText("Your account number or pin is wrong. Enter again..!!!");
                a.showAndWait();

            }


        } catch (Exception e) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error");
            a.setHeaderText("Error in login.");
            a.setContentText("There is some error. PLEASE TRY AGAIN..!!!" + e.getMessage());
            a.showAndWait();


        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
