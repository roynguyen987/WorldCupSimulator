package edu.ucdenver.clientproject1;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class PublicWindow extends Controller {

    public Tab tabUpcomingMatches;
    public ListView listUpcomingMatches;
    public DatePicker matchesOnDateDate;
    public ListView listMatchesOnDate;
    public Button matchesOnDateButton;
    public TextField matchesForTeamName;
    public ListView listMatchesForTeam;
    public Button matchesForTeamButton;
    public TextField teamAName;
    public TextField teamBName;
    public TextField teamACountry;
    public TextField teamBCountry;
    public DatePicker dateOfMatch;
    public Button lineupsForMatchButton;
    public ListView teamALineup;
    public ListView teamBLineup;
    public Button updateButton;
    public Button exitButton;

    public Client client;
    public TextField timeOfMatch;


    /**
     * PublicWindow Constructor
     *
     * @throws IOException Throws IOException
     */
    public PublicWindow() throws IOException {
        client = new Client();
        client.connect();
        this.listUpcomingMatches = new ListView();
        this.listMatchesOnDate = new ListView<>();
        this.listMatchesForTeam = new ListView<>();
        this.teamALineup = new ListView<>();
        this.teamBLineup = new ListView<>();
    }


    /**
     * listUpcomingMatchesUpdate
     * Updates the upcoming matches
     *
     * @param event On button press
     * @throws IOException Throws IOException
     */
    public void listUpcomingMatchesUpdate(Event event) throws IOException {
        //set this list to this.listUpcomingMatches.setItems(FXCollections.observableArrayList(list of upcoming matches from server));
        client.sendRequest("UPDATEUPCOMING,");
        String fromServer = client.getResponse();
        String[] upcomingMatches = fromServer.split(",");
        this.listUpcomingMatches.setItems(FXCollections.observableArrayList(upcomingMatches));

    }

    /**
     * listMatchesOnDateUpdate
     * Updates the matches on a specific date
     *
     * @param actionEvent On button press
     * @throws IOException Throws IOException
     */
    public void listMatchesOnDateUpdate(ActionEvent actionEvent) throws IOException {
        //set this list to this.listMatchesOnDate.setItems(FXCollections.observableArrayList(list of matches from date from server));
        client.sendRequest("UPDATEDATE," + this.matchesOnDateDate.getValue());
        String fromServer = client.getResponse();
        String[] matchesOnDate = fromServer.split(",");
        this.listMatchesOnDate.setItems(FXCollections.observableArrayList(matchesOnDate));
    }

    /**
     * listMatchesForTeamUpdates
     * Updates matches for a team
     *
     * @param actionEvent On button press
     * @throws IOException Throws IOException
     */
    public void listMatchesForTeamUpdates(ActionEvent actionEvent) throws IOException {
        //set this list to this.listMatchesForTeam.setItems(FXCollections.observableArrayList(list of matches of a team));
        client.sendRequest("UPDATETEAM," + this.matchesForTeamName.getText());
        String fromServer = client.getResponse();
        String[] matchesForTeam = fromServer.split(",");
        this.listMatchesForTeam.setItems(FXCollections.observableArrayList(matchesForTeam));
    }

    /**
     * listLineupsForMatchUpdate
     * Updates lineups for a match
     *
     * @param actionEvent On button press
     * @throws IOException Throws IOException
     */
    public void listLineupsForMatchUpdate(ActionEvent actionEvent) throws IOException {
        client.sendRequest("LINEUPFORMATCH," + dateOfMatch.getValue() + ",T" + timeOfMatch.getText() + ":00,");
        String fromServer = client.getResponse();
        String[] teams = fromServer.split("-");
        String teamA = teams[0];
        String teamB = teams[1];
        String[] teamAList = teamA.split(",");
        String[] teamBList = teamB.split(",");
        this.teamALineup.setItems(FXCollections.observableArrayList(teamAList));
        this.teamBLineup.setItems(FXCollections.observableArrayList(teamBList));

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
