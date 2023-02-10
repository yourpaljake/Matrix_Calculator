import javax.swing.*;

public class MatrixHelper {
    /*
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
*/
    public static JLabel[][] toJLabel(double[][] matrix) {
        JLabel[][] temp = new JLabel[matrix.length][matrix[0].length];

        if (isIntArray(matrix)) {
            int[][] intArray = MatrixHelper.toIntArray(matrix);

            for (int i = 0; i < temp.length; i++) {
                for (int j = 0; j < temp[0].length; j++) {
                    temp[i][j] = new JLabel(Integer.toString(intArray[i][j]));
                }
            }
        } else {
            for (int i = 0; i < temp.length; i++) {
                for (int j = 0; j < temp[0].length; j++) {
                    temp[i][j] = new JLabel(Double.toString(matrix[i][j]));
                }
            }
        }

        return temp;
    }


    public static JLabel[][] toJLabel(String[][] matrix) {
        JLabel[][] temp = new JLabel[matrix.length][matrix[0].length];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                temp[i][j] = new JLabel(matrix[i][j] + "\t");
            }
        }

        return temp;
    }

    /*
    public static String[][] matrixToString(double[][] matrix) {
        String[][] matrixString = new String[matrix.length][matrix[0].length + 2];
        JLabel[][] tempLabels = MatrixHelper.toJLabel(matrix);

        for (int i = 0; i < tempLabels.length; i++) {
            for (int j = 0; j < tempLabels[0].length; j++) {
                String text = tempLabels[i][j].getText();
                if (MatrixHelper.isFraction(text)) {
                    matrixString[i][j] = text;
                } else {
                    matrixString[i][j] = text;
                }
            }
        }
        return matrixString;
    }
     */

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

    @SuppressWarnings("unused")
    public static boolean isFraction(String s) {
        try {
            int posOfSlash = s.indexOf("/");
            double numerator = Double.parseDouble(s.substring(0,posOfSlash));
            double denominator = Double.parseDouble(s.substring(posOfSlash + 1));

            return true;
        } catch (NumberFormatException e) {
            JFrame errorFrame = new JFrame();
            JOptionPane.showMessageDialog(errorFrame, "Invalid Input", "Error", JOptionPane.WARNING_MESSAGE);
            return false;
        } catch (IndexOutOfBoundsException e) {
            System.out.printf("%s does not contain a slash.%n", s);
            return false;
        }
    }

    public static boolean isWholeNumber(String value) {
        int indexOfSlash = value.indexOf("/");
        if (indexOfSlash == -1) {
            return false;
        }

        int denominator = Integer.parseInt(value.substring(indexOfSlash + 1));
        return denominator == 1;
    }

    public static boolean isFractionArray(String[][] matrix) {
        for (String[] columns: matrix) {
            for (String value: columns) {
                if (!isFraction(value)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static double fractionToDouble(String s) throws NumberFormatException{
        try {
            int posOfSlash = s.indexOf("/");
            double numerator = Double.parseDouble(s.substring(0,posOfSlash));
            double denominator = Double.parseDouble(s.substring(posOfSlash + 1));
            return (numerator / denominator);
        } catch (ArithmeticException e) {
            JOptionPane.showMessageDialog(new JFrame(), "Cannot divide by zero.", "Error", JOptionPane.WARNING_MESSAGE);
            throw new NumberFormatException();
        }
    }

    public static String addFractions(String num1, String num2) {
        String numResult;
        int posOfSlash1 = num1.indexOf("/");
        int posOfSlash2 = num2.indexOf("/");

        double denominator1 = Double.parseDouble(num1.substring(posOfSlash1  + 1));
        double denominator2 = Double.parseDouble(num2.substring(posOfSlash2 + 1));
        double denominatorResult = denominator1 * denominator2;

        double numerator1 = Double.parseDouble(num1.substring(0, posOfSlash1));
        double numerator2 = Double.parseDouble(num2.substring(0, posOfSlash2));
        double numeratorResult = (numerator1 * denominator2) + (numerator2 * denominator1);

        double gcd = gcd(numeratorResult, denominatorResult);

        if (Math.abs(gcd) > 1) {
            numeratorResult /= gcd;
            denominatorResult /= gcd;
        }

        if (denominatorResult < 0) {
            numeratorResult *= -1;
            denominatorResult *= -1;
        }

        int intDenominatorResult = (int) denominatorResult;
        int intNumeratorResult = (int) numeratorResult;

        if (intDenominatorResult == 1) {
            numResult = intNumeratorResult + "";
        } else {
            numResult = intNumeratorResult + "/" + intDenominatorResult;
        }

        return numResult;
    }

    public static String multiplyFractions(String num1, String num2) {
        String numResult;
        int posOfSlash1 = num1.indexOf("/");
        int posOfSlash2 = num2.indexOf("/");

        double denominator1 = Double.parseDouble(num1.substring(posOfSlash1  + 1));
        double denominator2 = Double.parseDouble(num2.substring(posOfSlash2 + 1));
        double denominatorResult = denominator1 * denominator2;

        double numerator1 = Double.parseDouble(num1.substring(0, posOfSlash1));
        double numerator2 = Double.parseDouble(num2.substring(0, posOfSlash2));
        double numeratorResult = numerator1 * numerator2;

        double gcd = gcd(numeratorResult, denominatorResult);

        if (Math.abs(gcd) > 1) {
            numeratorResult /= gcd;
            denominatorResult /= gcd;
        }

        if (denominatorResult < 0) {
            numeratorResult *= -1;
            denominatorResult *= -1;
        }

        int intDenominatorResult = (int) denominatorResult;
        int intNumeratorResult = (int) numeratorResult;

        if (intDenominatorResult == 1) {
            numResult = intNumeratorResult + "";
        } else {
            numResult = intNumeratorResult + "/" + intDenominatorResult;
        }

        return numResult;
    }

    public static String[][] addFractionArray(String[][] matrix1, String[][] matrix2) {
        String[][] temp = new String[matrix1.length][matrix1[0].length];

        for (int i = 0; i < temp.length; i++) {
            for (int j = 0; j < temp[0].length; j++) {
                String s = addFractions(matrix1[i][j], matrix2[i][j]);
                temp[i][j] = s;
            }
        }

        return temp;
    }

    public static String[][] scaleFractionArray(String scalar, String[][] matrix) {
        String[][] temp = new String[matrix.length][matrix[0].length];

        for (int i = 0; i < temp.length; i++) {
            for (int j = 0; j < temp[0].length; j++) {
                String s = multiplyFractions(scalar, matrix[i][j]);
                temp[i][j] = s;
            }
        }

        return temp;
    }

    public static double gcd(double num1, double num2) {
        if (num1 == 0) {
            return num2;
        } else if (num2 == 0) {
            return num1;
        } else if (Math.abs(num1) < Math.abs(num2)) {
            return gcd(num1, num2 % num1);
        } else {
            return gcd(num2, num1 % num2);
        }
    }
}
