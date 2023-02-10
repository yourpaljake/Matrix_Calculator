import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileSaver {
    static private String locationDirectory;
    public static boolean createSaveTwoInput(String[][] matrix1, String[][] matrix2, String[][] result, String operation) {
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

                fileWriter.write("Operation: " + operation + "\n");

                fileWriter.write("Matrix A: \n");
                for (String[] columns: matrix1) {
                    for (String value: columns) {
                        if (MatrixHelper.isWholeNumber(value)) {
                            String numerator = value.substring(0, value.indexOf("/"));
                            fileWriter.write(numerator + "\t");
                        } else {
                            fileWriter.write(value + "\t");
                        }
                    }
                    fileWriter.write("\n");
                }

                fileWriter.write("Matrix B: \n");
                for (String[] columns: matrix2) {
                    for (String value: columns) {
                        if (MatrixHelper.isWholeNumber(value)) {
                            String numerator = value.substring(0, value.indexOf("/"));
                            fileWriter.write(numerator + "\t");
                        } else {
                            fileWriter.write(value + "\t");
                        }
                    }
                    fileWriter.write("\n");
                }

                fileWriter.write("Result: \n");
                for (String[] columns: result) {
                    for (String value: columns) {
                        if (MatrixHelper.isWholeNumber(value)) {
                            String numerator = value.substring(0, value.indexOf("/"));
                            fileWriter.write(numerator + "\t");
                        } else {
                            fileWriter.write(value + "\t");
                        }
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

    public static boolean createSaveScalarInput(String scalar, String[][] matrix, String[][] result, String operation) {
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

                fileWriter.write("Operation: " + operation + "\n");

                fileWriter.write(String.format("Scalar: %s%n", scalar));

                fileWriter.write("Matrix A: \n");
                for (String[] columns: matrix) {
                    for (String value: columns) {
                        if (MatrixHelper.isWholeNumber(value)) {
                            int temp = Double.valueOf(value.substring(0, value.indexOf("/"))).intValue();
                            String numerator = temp + "";
                            fileWriter.write(numerator + "\t");
                        } else {
                            fileWriter.write(value + "\t");
                        }
                    }
                    fileWriter.write("\n");
                }

                fileWriter.write("Result: \n");
                for (String[] columns: result) {
                    for (String value: columns) {
                        if (MatrixHelper.isWholeNumber(value)) {
                            String numerator = value.substring(0, value.indexOf("/"));
                            fileWriter.write(numerator + "\t");
                        } else {
                            fileWriter.write(value + "\t");
                        }
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
