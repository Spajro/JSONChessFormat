package gui;

import chess.pieces.Piece;
import data.model.PromotionTypeProvider;
import gui.option.OptionPanel;

import javax.swing.*;

public class DialogPromotionTypeProvider implements PromotionTypeProvider {
    private final OptionPanel optionPanel;
    private final Object[] options = {
            "Knight", "Bishop", "Rook", "Queen"};

    public DialogPromotionTypeProvider(OptionPanel optionPanel) {
        this.optionPanel = optionPanel;
    }

    @Override
    public Piece.Type getPromotionType() {
        int n = JOptionPane.showOptionDialog(optionPanel,
                null,
                "Promotion",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[3]);
        return switch (n) {
            case 0 -> Piece.Type.KNIGHT;
            case 1 -> Piece.Type.BISHOP;
            case 2 -> Piece.Type.ROOK;
            case 3 -> Piece.Type.QUEEN;
            default -> throw new IllegalStateException("Unexpected value: " + n);
        };
    }
}
