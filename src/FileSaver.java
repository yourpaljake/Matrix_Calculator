import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileSaver {
    static private String locationDirectory;
    public static boolean createSaveTwoInput(double[][] matrix1, double[][] matrix2, double[][] result, String operation) {
        try {
            if (locationDirectory == null) {
                JFrame locationFrame = new JFrame("Save");
                String directoryMessage = "Paste the directory where you would like the files to be saved.";
                locationDirectory = JOptionPane.showInputDialog(locationFrame, directoryMessage);

            }

            if (locationDirectory == null || locationDirectory.equals("")) {
                return false;
            }

            File folder = new File(locationDirectory);
            File[] listOfFiles = folder.listFiles();

            int currentFile = 0;
            if (listOfFiles != null) {
                for (File tempFile : listOfFiles) {
                    if (tempFile.isFile()) {
                        String currentName = tempFile.getName();
                        currentName = currentName.substring(0, currentName.lastIndexOf("."));
                        int currentNum = 0;
                        if (!currentName.equals("")) {
                            currentNum = Integer.parseInt(currentName);
                        }
                        if (currentNum > currentFile) {
                            currentFile = currentNum;
                        }
                    }
                }
            }

            File newSave = new File(locationDirectory + "/" + ++currentFile + ".txt");
            //noinspection ResultOfMethodCallIgnored
            newSave.createNewFile();

            try {
                FileWriter fileWriter = new FileWriter(newSave);

                fileWriter.write("Matrix A: \n");
                String[][] matrixA = MatrixHelper.matrixToString(matrix1);
                for (String[] columns: matrixA) {
                    for (String value: columns) {
                        fileWriter.write(value);
                    }
                    fileWriter.write("\n");
                }

                fileWriter.write("Matrix B: \n");
                String[][] matrixB = MatrixHelper.matrixToString(matrix2);
                for (String[] columns: matrixB) {
                    for (String value: columns) {
                        fileWriter.write(value);
                    }
                    fileWriter.write("\n");
                }

                fileWriter.write("Operation: " + operation + "\n");

                fileWriter.write("Result: \n");
                String[][] resultString = MatrixHelper.matrixToString(result);
                for (String[] columns: resultString) {
                    for (String value: columns) {
                        fileWriter.write(value);
                    }
                    fileWriter.write("\n");
                }

                fileWriter.flush();
                fileWriter.close();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean createSaveScalarInput(double scalar, double[][] matrix, double[][] result, String operation) {
        try {
            if (locationDirectory == null) {
                JFrame locationFrame = new JFrame("Save");
                String directoryMessage = "Paste the directory where you would like the files to be saved.";
                locationDirectory = JOptionPane.showInputDialog(locationFrame, directoryMessage);

            }

            File folder = new File(locationDirectory);
            File[] listOfFiles = folder.listFiles();

            int currentFile = 0;
            if (listOfFiles != null) {
                for (File tempFile : listOfFiles) {
                    if (tempFile.isFile()) {
                        String currentName = tempFile.getName();
                        currentName = currentName.substring(0, currentName.lastIndexOf("."));
                        int currentNum = 0;
                        if (!currentName.equals("")) {
                            currentNum = Integer.parseInt(currentName);
                        }
                        if (currentNum > currentFile) {
                            currentFile = currentNum;
                        }
                    }
                }
            }

            File newSave = new File(locationDirectory + "/" + ++currentFile + ".txt");
            //noinspection ResultOfMethodCallIgnored
            newSave.createNewFile();

            try {
                FileWriter fileWriter = new FileWriter(newSave);

                fileWriter.write("Scalar: \n");
                String scalarC = scalar + "\n";
                fileWriter.write(scalarC);

                fileWriter.write("Matrix B: \n");
                String[][] matrixB = MatrixHelper.matrixToString(matrix);
                for (String[] columns: matrixB) {
                    for (String value: columns) {
                        fileWriter.write(value);
                    }
                    fileWriter.write("\n");
                }

                fileWriter.write("Operation: " + operation + "\n");

                fileWriter.write("Result: \n");
                String[][] resultString = MatrixHelper.matrixToString(result);
                for (String[] columns: resultString) {
                    for (String value: columns) {
                        fileWriter.write(value);
                    }
                    fileWriter.write("\n");
                }

                fileWriter.flush();
                fileWriter.close();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
