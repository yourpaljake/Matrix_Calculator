import javax.swing.*;

public class CopyPaste {
    static private double[][][] matrices = new double[6][1][1];
    static private int num;

    public static JMenu createSaveMenu() {

        JMenu temp = new JMenu("Edit");

        for (int i = 1; i < num + 1; i++) {
            JMenu loopMenu = new JMenu("Matrix " + i);
            JMenuItem loopMenuItem = new JMenuItem("Save " + i);
            loopMenu.add(loopMenuItem);
            temp.add(loopMenu);
        }

        return temp;
    }

    public static JMenu createPasteMenu() {
        num = 6;
        
        JMenu temp = new JMenu("Edit");

        for (int i = 1; i < num + 1; i++) {
            JMenu loopMenu = new JMenu("Matrix " + i);
            JMenuItem loopMenuItem = new JMenuItem("Save " + i);
            JMenuItem loopMenuItem2 = new JMenuItem("Paste " + i);
            loopMenu.add(loopMenuItem);
            loopMenu.add(loopMenuItem2);
            temp.add(loopMenu);
        }

        return temp;
    }

    public static void saveMatrix(double[][] matrix, int pos) {
        try {
            matrices[pos] = new double[matrix.length][matrix[0].length];
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[0].length; j++) {
                    matrices[pos][i][j] = matrix[i][j];
                }
            }
            
            System.out.println("Matrix saved to position " + pos);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Matrix failed to save");
        }
    }

    public static JTextField[][] pasteMatrix(JTextField[][] field, int pos) {
        System.out.println("Attempting paste from position " + pos);
        JTextField[][] temp = field;
        try {
            for (int i = 0; i < temp.length; i++) {
                for (int j = 0; j < temp[0].length; j++) {
                    temp[i][j].setText(matrices[pos][j][j] + "");
                }
            }
            System.out.println("Successfully pasted from position " + pos);
            return temp;
        } catch (NullPointerException e) {
            JFrame error = new JFrame("Error");
            JOptionPane.showMessageDialog(error, "No Matrix Saved", "Error", JOptionPane.WARNING_MESSAGE);
            System.out.println("Matrix failed to paste. No matrix saved");
            return field;
        } catch (IndexOutOfBoundsException e) {
            JFrame error = new JFrame("Error");
            JOptionPane.showMessageDialog(error, "Incorrect Matrix Size", "Error", JOptionPane.WARNING_MESSAGE);
            System.out.printf("Matrix failed to paste. Was %dx%d and expected %dx%d%n", temp.length, temp[0].length, matrices[pos].length, matrices[pos][0].length);
            return field;
        }
    }
}
