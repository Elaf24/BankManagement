package dashboard;

import Login.LoginController;
import dbConnector.mySQLCon;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class MainScreen implements Initializable {
    @FXML
    private Label name;
    @FXML
    private Label body;


    public  void setInfo(){
        Connection con= mySQLCon.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;


        try{
            String sql = "SELECT * FROM userdata WHERE AccountNo=?";
            ps = con.prepareStatement(sql);
            ps.setString(1, LoginController.acc);
            rs = ps.executeQuery();
            if(rs.next()){
                name.setText(rs.getString("Name"));



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
            a.setHeaderText("Error in login .");
            a.setContentText("There is some error. PLEASE TRY AGAIN..!!!");
            a.showAndWait();

        }

    }










    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        body.setText("Bangladesh University of Professionals is the 31st public university of Bangladesh, located in Mirpur Cantonment, Dhaka.\nIt is the first public university of Bangladesh to be run by the Bangladesh Armed Forces.\nIt was established under the Bangladesh University of Professionals Act, 2009\nBUP Central Bank was created by the TEAM Hecker ");
        setInfo();
    }
}
