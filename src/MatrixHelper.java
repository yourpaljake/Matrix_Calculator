import javax.swing.*;

public class MatrixHelper {
    public static JLabel[][] toJLabel(double[][] matrix) {
        JLabel[][] temp = new JLabel[matrix.length][matrix[0].length + 2];

        if (MatrixHelper.isIntArray(matrix)) {
            int[][] newMatrix = MatrixHelper.toIntArray(matrix);
            temp[0][0] = new JLabel("⸢\t");
            for (int i = 0; i < newMatrix[0].length; i++) {
                temp[0][i+1] = new JLabel(newMatrix[0][i] + "\t");
            }
            temp[0][temp[0].length - 1] = new JLabel("⸣");

            for (int i = 1; i < newMatrix.length; i++) {
                temp[i][0] = new JLabel("|\t");
                for (int j = 0; j < newMatrix[0].length; j++) {
                    temp[i][j+1] = new JLabel(newMatrix[i][j] + "\t");
                }
                temp[i][temp[0].length - 1] = new JLabel("|");
            }

            temp[temp.length - 1][0] = new JLabel("⸤\t");
            for (int i = 0; i < newMatrix[0].length; i++) {
                temp[temp.length - 1][i + 1] = new JLabel(newMatrix[newMatrix.length - 1][i] + "\t");
            }
            temp[temp.length - 1][temp[0].length - 1] = new JLabel("⸥");

        } else {
            temp[0][0] = new JLabel("⸢\t");
            for (int i = 0; i < matrix[0].length; i++) {
                temp[0][i+1] = new JLabel(matrix[0][i] + "\t");
            }
            temp[0][temp[0].length - 1] = new JLabel("⸣");

            for (int i = 1; i < matrix.length; i++) {
                temp[i][0] = new JLabel("|\t");
                for (int j = 0; j < matrix[0].length; j++) {
                    temp[i][j+1] = new JLabel(matrix[i][j] + "\t");
                }
                temp[i][temp[0].length - 1] = new JLabel("|");
            }

            temp[temp.length - 1][0] = new JLabel("⸤\t");
            for (int i = 0; i < matrix[0].length; i++) {
                temp[temp.length - 1][i + 1] = new JLabel(matrix[matrix.length - 1][i] + "\t");
            }
            temp[temp.length - 1][temp[0].length - 1] = new JLabel(matrix[matrix.length - 1][matrix[0].length - 1] + "⸥");

        }

        return temp;
    }

    public static String[][] matrixToString(double[][] matrix) {
        String[][] matrixString = new String[matrix.length][matrix[0].length + 2];
        JLabel[][] tempLabels = MatrixHelper.toJLabel(matrix);

        for (int i = 0; i < tempLabels.length; i++) {
            for (int j = 0; j < tempLabels[0].length; j++) {
                matrixString[i][j] = tempLabels[i][j].getText();
            }
        }
        return matrixString;
    }

    public static boolean isIntArray(double[][] matrix) {
        for (double[] doubles : matrix) {
            for (int j = 0; j < matrix[0].length; j++) {
                if ((int) doubles[j] != doubles[j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public static int[][] toIntArray(double[][] matrix) {
        int[][] temp = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < temp.length; i++) {
            for (int j = 0; j < temp[0].length; j++) {
                temp[i][j] = (int) matrix[i][j];
            }
        }
        return temp;
    }
}
