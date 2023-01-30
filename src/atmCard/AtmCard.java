package atmCard;

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
import java.util.Random;
import java.util.ResourceBundle;

public class AtmCard implements Initializable {
    @FXML
    private Label name;
    @FXML
    private Label cardNumber;
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
                if (rs.getString("cardNo")!=null) cardNumber.setText(rs.getString("cardNo"));

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
    public void  generateCardNo(){
        Connection con= mySQLCon.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            String sql = "SELECT * FROM userdata WHERE AccountNo=?";
            ps = con.prepareStatement(sql);
            ps.setString(1, LoginController.acc);
            rs = ps.executeQuery();
            rs.next();
            System.out.println(rs.getString("cardNo"));
            if(rs.getString("cardNo")==null){
                Random random = new Random();
                String num = String.valueOf(random.nextLong(899999999)+100000000);
                cardNumber.setText(num);
                String sql1 = "UPDATE userdata SET cardNo='"+num+"'WHERE AccountNo='"+ LoginController.acc+"'";
                ps=con.prepareStatement(sql1);
                ps.execute();
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setTitle("Successful");
                a.setHeaderText("ATM card issued under your account");
                a.setContentText("Please collect the Physical Card from Nearby Branch." );
                a.showAndWait();

            }
            else
            {   Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Error");
                a.setHeaderText("ATM card Already Exists");
                a.setContentText("You have Already created an ATM card. You can't create anymore" );
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
setInfo();
    }
}
