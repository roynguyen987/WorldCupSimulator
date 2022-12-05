package edu.ucdenver.clientproject1;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminWindow extends Controller {
    public Button loadButton;
    public TextField loadFileText;
    public Button saveButton;
    public TextField saveFileText;
    public TextField createTournamentName;
    public DatePicker createTournamentStartDate;
    public DatePicker createTournamentEndDate;
    public TextField addCountryName;
    public Button createTournamentButton;
    public Button addCountryButton;
    public TextField addTeamName;
    public TextField addTeamCountry;
    public Button addTeamButton;
    public TextField addRefereeName;
    public TextField addRefereeCountry;
    public Button addRefereeButton;
    public TextField addMatchTeam1Name;
    public TextField addMatchTeam2Name;
    public DatePicker addMatchDate;
    public TextField addMatchTime;
    public Button addMatchButton;
    public TextField assignRefToMatchName;
    public TextField assignRefToMatchTime;
    public DatePicker assignRefToMatchDate;
    public TextField addPlayerToTeamName;
    public TextField addPlayerToTeamAge;
    public TextField addPlayerToTeamHeight;
    public TextField addPlayerToTeamSelectTeam;
    public TextField addPlayerToTeamWeight;
    public Button addPlayerToTeamButton;
    public DatePicker recordScoreDate;
    public TextField recordScoreTime;
    public TextField recordScoreTeamAScore;
    public TextField recordScoreTeamBScore;
    public Button assignRefToMatch;
    public Button recordScoreButton;
    public TextField addPlayerToTeamTeam;
    public TextField addPlayerToLineupName;
    public TextField addPlayerToLineupTeam;
    public Button addPlayerToLineupButton;
    public Button exitButton;

    public Client client;
    public TextField assignRefToMatchName1;
    public TextField assignRefToMatchName3;
    public TextField assignRefToMatchName2;
    public TextField assignRefToMatchName4;


    /**
     * AdminWindow
     */
    public AdminWindow() {
        client = new Client();
        client.connect();

    }

    /**
     * cleanLoadFile
     */
    public void cleanLoadFile() {
        this.loadFileText.setText("");
    }

    /**
     * cleanSaveFile
     */
    public void cleanSaveFile() {
        this.saveFileText.setText("");
    }

    /**
     * cleanCreateTournament
     */
    public void cleanCreateTournament() {
        this.createTournamentName.setText("");
        this.createTournamentStartDate.setValue(null);
        this.createTournamentEndDate.setValue(null);
    }

    /**
     * cleanAddCountry
     */
    public void cleanAddCountry() {
        this.addCountryName.setText("");
    }

    /**
     * cleanAddNationalTeam
     */
    public void cleanAddNationalTeam() {
        this.addTeamName.setText("");
        this.addTeamCountry.setText("");
    }

    /**
     * cleanAddRef
     */
    public void cleanAddRef() {
        this.addRefereeName.setText("");
        this.addRefereeCountry.setText("");
    }

    /**
     * cleanAddPlayer
     */
    public void cleanAddPlayer() {
        this.addPlayerToTeamName.setText("");
        this.addPlayerToTeamAge.setText("");
        this.addPlayerToTeamHeight.setText("");
        this.addPlayerToTeamWeight.setText("");
        this.addPlayerToTeamTeam.setText("");
    }

    /**
     * cleanAddMatch
     */
    public void cleanAddMatch() {
        this.addMatchDate.setValue(null);
        this.addMatchTime.setText("");
        this.addMatchTeam1Name.setText("");
        this.addMatchTeam2Name.setText("");
    }

    /**
     * cleanAssignRef
     */
    public void cleanAssignRef() {
        this.assignRefToMatchName1.setText("");
        this.assignRefToMatchName2.setText("");
        this.assignRefToMatchName3.setText("");
        this.assignRefToMatchName4.setText("");
        this.assignRefToMatchDate.setValue(null);
        this.assignRefToMatchTime.setText("");
    }

    /**
     * cleanAddPlayerToLineup
     */
    public void cleanAddPlayerToLineup() {
        this.addPlayerToLineupName.setText("");
        this.addPlayerToLineupTeam.setText("");
    }

    /**
     * cleanRecordScore
     */
    public void cleanRecordScore() {
        this.recordScoreDate.setValue(null);
        this.recordScoreTime.setText("");
        this.recordScoreTeamAScore.setText("");
        this.recordScoreTeamBScore.setText("");
    }


    /**
     * loadFromFile
     * Sends a load request to server
     * Response from server determines output
     *
     * @param actionEvent On clicking button
     * @throws IOException Throws IOException
     */
    public void loadFromFile(ActionEvent actionEvent) throws IOException {
        client.sendRequest("LOAD," + loadFileText.getText());
        String fromServer = client.getResponse();
        if (fromServer.startsWith("[ERROR]")) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "[Load From File] - Request Sent To Server\n" + fromServer);
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "[Load From File] - Request Sent To Server\n" + fromServer);
            alert.show();
        }
        cleanLoadFile();
    }

    /**
     * saveToFile
     * Sends a save request to server
     * Response from server determines output
     *
     * @param actionEvent On button press
     * @throws IOException Throws IOException
     */
    public void saveToFile(ActionEvent actionEvent) throws IOException {
        client.sendRequest("SAVE," + saveFileText.getText());
        String fromServer = client.getResponse();
        if (fromServer.startsWith("[ERROR]")) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "[Save To File] - Request Sent To Server\n" + fromServer);
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "[Save To File] - Request Sent To Server\n" + fromServer);
            alert.show();
        }
        cleanSaveFile();
    }

    /**
     * createTournament
     * Sends a create request to server
     * Response from server determines output
     *
     * @param actionEvent On Button press
     * @throws IOException Throws IOException
     */
    public void createTournament(ActionEvent actionEvent) throws IOException {
        client.sendRequest("CREATE," + createTournamentName.getText() + "," + createTournamentStartDate.getValue() + "," + createTournamentEndDate.getValue());
        String fromServer = client.getResponse();
        if (fromServer.startsWith("[ERROR]")) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "[Create Tournament] - Request Sent To Server\n" + fromServer);
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "[Create Tournament] - Request Sent To Server\n" + fromServer);
            alert.show();
        }
        cleanCreateTournament();
    }

    /**
     * addCountry
     * Sends an add country request to server
     * Response from server determines output
     *
     * @param actionEvent On button press
     * @throws IOException Throws IOException
     */
    public void addCountry(ActionEvent actionEvent) throws IOException {
        client.sendRequest("ADDCOUNTRY," + addCountryName.getText());
        String fromServer = client.getResponse();
        if (fromServer.startsWith("[ERROR]")) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "[Add Country] - Request Sent To Server\n" + fromServer);
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "[Add Country] - Request Sent To Server\n" + fromServer);
            alert.show();
        }
        cleanAddCountry();
    }

    /**
     * addTeam
     * Sends a add teamrequest to server
     * Response from server determines output
     *
     * @param actionEvent On button press
     * @throws IOException Throws IOException
     */
    public void addTeam(ActionEvent actionEvent) throws IOException {
        client.sendRequest("ADDTEAM," + addTeamName.getText() + "," + addTeamCountry.getText());
        String fromServer = client.getResponse();
        if (fromServer.startsWith("[ERROR]")) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "[Add Team] - Request Sent To Server\n" + fromServer);
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "[Add Team] - Request Sent To Server\n" + fromServer);
            alert.show();
        }
        cleanAddNationalTeam();
    }

    /**
     * addReferee
     * Sends an add referee request to server
     * Response from server determines output
     *
     * @param actionEvent On button press
     * @throws IOException Throws IOException
     */
    public void addReferee(ActionEvent actionEvent) throws IOException {
        client.sendRequest("ADDREFEREE," + addRefereeName.getText() + "," + addRefereeCountry.getText());
        String fromServer = client.getResponse();
        if (fromServer.startsWith("[ERROR]")) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "[Add Referee] - Request Sent To Server\n" + fromServer);
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "[Add Referee] - Request Sent To Server\n" + fromServer);
            alert.show();
        }
        cleanAddRef();
    }

    /**
     * addMatch
     * Sends an add match request to server
     * Response from server determines output
     *
     * @param actionEvent On button press
     * @throws IOException Throws IOException
     */
    public void addMatch(ActionEvent actionEvent) throws IOException {
        client.sendRequest("ADDMATCH," + addMatchDate.getValue() + ",T" + addMatchTime.getText() + ":00," + addMatchTeam1Name.getText() + "," + addMatchTeam2Name.getText());
        String fromServer = client.getResponse();
        if (fromServer.startsWith("[ERROR]")) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "[Add Match] - Request Sent To Server\n" + fromServer);
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "[Add Match] - Request Sent To Server\n" + fromServer);
            alert.show();
        }
        cleanAddMatch();
    }

    /**
     * assignRef
     * Sends an add referee request to server
     * Response from server determines output
     *
     * @param actionEvent On button press
     * @throws IOException Throws IOException
     */
    public void assignRef(ActionEvent actionEvent) throws IOException {
        client.sendRequest("ASSIGNREF," + assignRefToMatchName1.getText() + "," + assignRefToMatchName2.getText() + "," + assignRefToMatchName3.getText() + "," + assignRefToMatchName4.getText() + "," + assignRefToMatchDate.getValue() + ",T" + assignRefToMatchTime.getText() + ":00");
        String fromServer = client.getResponse();
        if (fromServer.startsWith("[ERROR]")) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "[Assign Referee] - Request Sent To Server\n" + fromServer);
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "[Assign Referee] - Request Sent To Server\n" + fromServer);
            alert.show();
        }
        cleanAssignRef();
    }

    /**
     * addPlayerToTeam
     * Sends an add player to team request to server
     * Response from server determines output
     *
     * @param actionEvent On button pressed
     * @throws IOException Throws IOException
     */
    public void addPlayerToTeam(ActionEvent actionEvent) throws IOException {
        client.sendRequest("ADDPLAYER," + addPlayerToTeamName.getText() + "," + addPlayerToTeamAge.getText() + "," + addPlayerToTeamHeight.getText() + "," + addPlayerToTeamWeight.getText() + "," + addPlayerToTeamTeam.getText());
        String fromServer = client.getResponse();
        if (fromServer.startsWith("[ERROR]")) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "[Add Player To Team] - Request Sent To Server\n" + fromServer);
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "[Add Player To Team] - Request Sent To Server\n" + fromServer);
            alert.show();
        }
        cleanAddPlayer();
    }

    /**
     * recordScore
     * Sends a record score request to server
     * Response from server determines output
     *
     * @param actionEvent On button press
     * @throws IOException throws IOException
     */
    public void recordScore(ActionEvent actionEvent) throws IOException {
        client.sendRequest("RECORDSCORE," + recordScoreDate.getValue() + ",T" + recordScoreTime.getText() + ":00," + recordScoreTeamAScore.getText() + "," + recordScoreTeamBScore.getText());
        String fromServer = client.getResponse();
        if (fromServer.startsWith("[ERROR]")) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "[Record Score] - Request Sent To Server\n" + fromServer);
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "[Record Score] - Request Sent To Server\n" + fromServer);
            alert.show();
        }
        cleanRecordScore();
    }

    /**
     * addPlayerToLineup
     * Sends an add player to lineup request to server
     * Response from server determines output
     *
     * @param actionEvent On button press
     * @throws IOException Throws IOException
     */
    public void addPlayerToLineup(ActionEvent actionEvent) throws IOException {
        client.sendRequest("ADDPLAYERTOLINEUP," + addPlayerToLineupName.getText() + "," + addPlayerToLineupTeam.getText());
        String fromServer = client.getResponse();
        if (fromServer.startsWith("[ERROR]")) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "[Add Player To Lineup] - Request Sent To Server\n" + fromServer);
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "[Add Player To Lineup] - Request Sent To Server\n" + fromServer);
            alert.show();
        }
        cleanAddPlayerToLineup();
    }

    /**
     * Exit
     *
     * @param actionEvent On button press
     */
    public void exit(ActionEvent actionEvent) {
        Stage stage = (Stage) this.exitButton.getScene().getWindow();
        stage.close();
    }
}
