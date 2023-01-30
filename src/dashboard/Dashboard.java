package dashboard;

import Login.LoginController;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import dbConnector.mySQLCon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class Dashboard implements Initializable {
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

    public void setData() {
        Connection con = mySQLCon.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT * FROM userdata WHERE AccountNo=?";
            ps = con.prepareStatement(sql);
            ps.setString(1, LoginController.acc);
            rs = ps.executeQuery();
            if (rs.next()) {
                name.setText(rs.getString("Name"));
                InputStream is = rs.getBinaryStream(("ProfilePic"));
                OutputStream os = new FileOutputStream(new File("pic.jpg"));
                byte[] content = new byte[1024];
                int size = 0;
                while ((size = is.read(content)) != -1) {
                    os.write(content, 0, size);
                }
                os.close();
                is.close();
                profilepic.setStroke(Color.WHITE);

                Image img = new Image("file:pic.jpg", false);
                profilepic.setFill(new ImagePattern(img));


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
            a.setHeaderText("Error in login .");
            a.setContentText("There is some error. PLEASE TRY AGAIN..!!!");
            a.showAndWait();

        }

    }

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
    public void accountInformation(MouseEvent mouseEvent) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/accountinfo/AccountInformation.fxml"));
        dashboard_main.getChildren().removeAll();
        dashboard_main.getChildren().addAll(fxml);

    }

    @FXML
    public void withdraw(MouseEvent mouseEvent) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/withdraw/WithdrawAmount.fxml"));
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

    @FXML
    public void atmCard() throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/atmCard/AtmCard.fxml"));
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

    @FXML
    public void printPDF(MouseEvent event) throws IOException {
        Connection con = mySQLCon.getConnection();
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {

            String sql = "SELECT * FROM withdraw WHERE AccountNo=?";
            ps = con.prepareStatement(sql);
            ps.setString(1, LoginController.acc);
            rs = ps.executeQuery();

            Document my_pdf_report = new Document();
            PdfWriter.getInstance(my_pdf_report, new FileOutputStream("C:\\Users\\Ismail Ariyan\\Desktop\\invoice.pdf"));
            my_pdf_report.open();
            PdfPTable table1 = new PdfPTable(4);
            PdfPCell table_cell;
            table_cell = new PdfPCell(new Paragraph("WITHDRAW HISTORY"));
            table_cell.setColspan(4);
            table_cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table1.addCell(table_cell);
            table1.addCell("WithDrawn");
            table1.addCell("Remaining");
            table1.addCell("Date");
            table1.addCell("Time");
            while (rs.next()) {

                String withdrawAmount = rs.getString(2);
                table_cell = new PdfPCell(new Phrase(withdrawAmount));
                table1.addCell(table_cell);
                String remainingAmount = rs.getString(3);
                table_cell = new PdfPCell(new Phrase(remainingAmount));
                table1.addCell(table_cell);
                String Date = rs.getString(4);
                table_cell = new PdfPCell(new Phrase(Date));
                table1.addCell(table_cell);
                String Time = rs.getString(5);
                table_cell = new PdfPCell(new Phrase(Time));
                table1.addCell(table_cell);

            }

            PdfPTable table2 = new PdfPTable(4);
            PdfPCell table_cell2;
            table_cell2 = new PdfPCell(new Paragraph("DEPOSIT HISTORY"));
            table_cell2.setColspan(4);
            table_cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
            table1.addCell(table_cell2);
            table1.addCell("Deposit");
            table1.addCell("NewAmount");
            table1.addCell("Date");
            table1.addCell("Time");
            String sql2 = "SELECT * FROM deposit WHERE AccountNo=?";
            ps = con.prepareStatement(sql2);
            ps.setString(1, LoginController.acc);
            rs = ps.executeQuery();
            while (rs.next()) {

                String withdrawAmount = rs.getString(2);
                table_cell = new PdfPCell(new Phrase(withdrawAmount));
                table1.addCell(table_cell);
                String remainingAmount = rs.getString(3);
                table_cell = new PdfPCell(new Phrase(remainingAmount));
                table1.addCell(table_cell);
                String Date = rs.getString(4);
                table_cell = new PdfPCell(new Phrase(Date));
                table1.addCell(table_cell);
                String Time = rs.getString(5);
                table_cell = new PdfPCell(new Phrase(Time));
                table1.addCell(table_cell);

            }
            PdfPTable table3 = new PdfPTable(4);
            PdfPCell table_cell3;
            table_cell3 = new PdfPCell(new Paragraph("TRANSFER HISTORY"));
            table_cell3.setColspan(4);
            table_cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
            table1.addCell(table_cell3);
            table1.addCell("Amount");
            table1.addCell("SendTo");
            table1.addCell("Date");
            table1.addCell("Time");
            String sql3 = "SELECT * FROM transferamount WHERE AccountNo=?";
            ps = con.prepareStatement(sql3);
            ps.setString(1, LoginController.acc);
            rs = ps.executeQuery();
            while (rs.next()) {

                String withdrawAmount = rs.getString(2);
                table_cell = new PdfPCell(new Phrase(withdrawAmount));
                table1.addCell(table_cell);
                String remainingAmount = rs.getString(3);
                table_cell = new PdfPCell(new Phrase(remainingAmount));
                table1.addCell(table_cell);
                String Date = rs.getString(4);
                table_cell = new PdfPCell(new Phrase(Date));
                table1.addCell(table_cell);
                String Time = rs.getString(5);
                table_cell = new PdfPCell(new Phrase(Time));
                table1.addCell(table_cell);

            }

            my_pdf_report.add(table1);
            my_pdf_report.add(table2);
            my_pdf_report.add(table3);
            my_pdf_report.close();

        } catch (Exception e) {
            System.out.println(e);

        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setData();
        try {
            mainScreen();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}