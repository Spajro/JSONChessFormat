package gui.games;

import data.model.metadata.GameData;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class GameTableModel extends AbstractTableModel {
    private ArrayList<GameData> gameDataList;

    public GameTableModel(List<GameData> gameDataList) {
        this.gameDataList = new ArrayList<>(gameDataList);
    }

    @Override
    public int getRowCount() {
        return gameDataList.size();
    }

    @Override
    public int getColumnCount() {
        return 8;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return getFromGameData(gameDataList.get(rowIndex), columnIndex);
    }

    @Override
    public String getColumnName(int column) {
        return switch (column) {
            case 0 -> "Event";
            case 1 -> "Site";
            case 2 -> "Date";
            case 3 -> "Round";
            case 4 -> "White";
            case 5 -> "Black";
            case 6 -> "Result";
            case 7 -> "Length";
            default -> throw new IllegalArgumentException("Illegal index for column name: " + column);
        };
    }

    private String getFromGameData(GameData gameData, int index) {
        return switch (index) {
            case 0 -> gameData.event();
            case 1 -> gameData.site();
            case 2 -> gameData.date();
            case 3 -> gameData.round();
            case 4 -> gameData.white();
            case 5 -> gameData.black();
            case 6 -> gameData.result();
            case 7 -> Integer.toString(gameData.length());
            default -> throw new IllegalArgumentException("Illegal index from Gamedata: " + index);
        };
    }

    public void swapData(List<GameData> gameData) {
        this.gameDataList = new ArrayList<>(gameData);
    }

    public GameData getGameDataForRow(int index) {
        return gameDataList.get(index);
    }
}
