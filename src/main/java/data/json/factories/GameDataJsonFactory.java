package data.json.factories;

import data.model.metadata.GameData;

public class GameDataJsonFactory implements JsonFactory<GameData> {

    public String toJson(GameData metaData) {
        return '{' +
                "\"event\":" +
                metaData.event() +
                "," +
                "\"site\":" +
                metaData.site() +
                "," +
                "\"date\":" +
                metaData.date() +
                "," +
                "\"round\":" +
                metaData.round() +
                "," +
                "\"white\":" +
                metaData.white() +
                "," +
                "\"black\":" +
                metaData.black() +
                "," +
                "\"result\":" +
                metaData.result() +
                "," +
                "\"length\":" +
                metaData.length() +
                '}';
    }
}
