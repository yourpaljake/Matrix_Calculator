import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.Arrays;
/**
 * This houses the Matrix Scaling functionality of the program. It is called to
 * in the MatrixCalculator class to create an instance of this class.
 *
 * @author Jacob Larson
 * @version 0.3.3
 * @since 01/28/23
 */

@SuppressWarnings({"WriteOnlyObject", "EnhancedSwitchMigration"})
public class SubFrameScale extends JFrame implements ActionListener {
    static private JFrame subF, subFrameScale, subFrameA, subFrameResult;
    static private JTextField dimMText, dimNText, constantScale;
    static private JTextField[][] textField;
    static private double[][] subMatrix;
    static private String[][] inputs1, resultsString;
    static private int dimM, dimN;
    static private double scalar;
    static private String scalarString;
    static private int currentFrame;
    static private boolean[] isShowing;
    static private boolean[] isFractionArray;
    public SubFrameScale() {
        isFractionArray = new boolean[2];
        isShowing = new boolean[4];
        currentFrame = 0;
        subF = new JFrame("Matrix cA");
        subF.setLayout(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.gridx = 0;
        g.gridy = 0;

        JLabel subL = new JLabel("Input a the Dimensions of the Matrices:\t");
        subF.add(subL);
        g.gridx++;

        dimMText = new JTextField("");
        dimMText.setPreferredSize(new Dimension(50, 20));
        subF.add(dimMText);
        g.gridx++;

        subF.add(new JLabel("x"));

        dimNText = new JTextField("");
        dimNText.setPreferredSize(new Dimension(50, 20));
        subF.add(dimNText);

        g.gridy++;

        JButton subB = new JButton("Go");
        subB.addActionListener(this);
        subB.setActionCommand("Go");

        subF.add(subB);

        subF.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Arrays.fill(isShowing, false);
            }});

        subF.setSize(500,200);
        subF.setLocationRelativeTo(null);
        subF.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        subF.setVisible(true);
        isShowing[0] = true;
    }
    
    private void createFrameScale() {
        currentFrame = 1;
        subFrameScale = new JFrame("Scalar");
        subFrameScale.setLayout(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.gridx = 0;
        g.gridy = 0;

        JLabel subL = new JLabel("Input a the scalar:\t");
        subFrameScale.add(subL);
        g.gridx++;

        constantScale = new JTextField("");
        constantScale.setPreferredSize(new Dimension(50, 20));
        subFrameScale.add(constantScale);
        g.gridx++;
        g.gridy++;

        JButton subOKScale = new JButton("Ok");
        subOKScale.addActionListener(this);
        subOKScale.setActionCommand("OkScale");

        subFrameScale.add(subOKScale);

        subFrameScale.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Arrays.fill(isShowing, false);
            }});

        subFrameScale.setSize(500,200);
        subFrameScale.setLocationRelativeTo(null);
        subFrameScale.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        subFrameScale.setVisible(true);
        isShowing[0] = false;
        isShowing[1] = true;
    }
    
    private void createFrameMatrix() {
        currentFrame = 2;
        subFrameA = new JFrame("Matrix A");
        subFrameA.setLayout(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.gridx = 0;
        g.gridy = 0;

        subMatrix = new double[dimM][dimN];

        textField = new JTextField[dimM][dimN];

        for (int i = 0; i < dimM; i++) {
            for (int j = 0; j < dimN; j++) {
                g.gridx = j;
                g.gridy = i;
                textField[i][j] = new JTextField("");
                textField[i][j].setPreferredSize(new Dimension(50, 20));
                subFrameA.add(textField[i][j], g);
            }

        }

        JButton frameAOk = new JButton("Ok");
        frameAOk.addActionListener(this);
        frameAOk.setActionCommand("OkA");

        g.gridx++;
        g.gridy++;

        subFrameA.add(frameAOk, g);

        JMenuBar menuBar = new JMenuBar();
        JMenu tempMenu = CopyPaste.createPasteMenu();
        addActionToPasteMenu(tempMenu);
        menuBar.add(tempMenu);
        subFrameA.setJMenuBar(menuBar);

        subFrameA.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Arrays.fill(isShowing, false);
            }});

        subFrameA.setSize(200 + 30 * dimN,200 + 30 * dimN);
        subFrameA.setLocationRelativeTo(null);
        subFrameA.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        subFrameA.setVisible(true);
        isShowing[1] = false;
        isShowing[2] = true;
    }
    
    private void createResultFrame() {
        currentFrame = 3;
        subFrameResult = new JFrame("Result");
        subFrameResult.setLayout(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();

        g.gridx = 0;
        g.gridy = 0;

        JLabel[][] results;

        if (isFractionArray[0] && isFractionArray[1]) {
            resultsString = MatrixHelper.scaleFractionArray(scalarString, inputs1);
            results = MatrixHelper.toJLabel(resultsString);
        } else if (isFractionArray[0] ^ isFractionArray[1]) {
            if (isFractionArray[0]) {
                for (int i = 0; i < dimM; i++) {
                    for (int j = 0; j < dimN; j++) {
                        inputs1[i][j] = subMatrix[i][j] + "/1";
                    }
                }
                System.out.println("Scalar was Fractional");
            } else {
                scalarString = scalar + "/1";
                System.out.println("Matrix was Fractional");
            }
            resultsString = MatrixHelper.scaleFractionArray(scalarString, inputs1);
            results = MatrixHelper.toJLabel(resultsString);
        } else {
            double[][] subMatrixResult = calculateResult(subMatrix, scalar);
            resultsString = new String[subMatrixResult.length][subMatrixResult[0].length];
            for (int i = 0; i < subMatrixResult.length; i++) {
                for (int j = 0; j < subMatrixResult[0].length; j++) {
                    resultsString[i][j] = subMatrixResult[i][j] + "";
                }
            }

            results = MatrixHelper.toJLabel(subMatrixResult);
        }

        for (JLabel[] labels: results) {
            for (int i = 0; i < labels.length; i++) {
                g.gridx = i;
                subFrameResult.add(labels[i], g);
            }
            g.gridy++;
        }

        JButton frameResultOk = new JButton("Ok");
        frameResultOk.addActionListener(this);
        frameResultOk.setActionCommand("OkResult");

        JButton save = new JButton("Save");
        save.addActionListener(this);
        save.setActionCommand("save");

        g.gridy++;
        subFrameResult.add(save, g);

        g.gridx++;
        subFrameResult.add(frameResultOk, g);

        JMenuBar menuBar = new JMenuBar();
        JMenu tempMenu = CopyPaste.createSaveMenu();
        addActionToSaveMenu(tempMenu);
        menuBar.add(tempMenu);
        subFrameResult.setJMenuBar(menuBar);

        subFrameResult.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Arrays.fill(isShowing, false);
            }});

        subFrameResult.setSize(200 + 30 * dimN,200 + 30 * dimN);
        subFrameResult.setLocationRelativeTo(null);
        subFrameResult.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        subFrameResult.setVisible(true);
        isShowing[2] = false;
        isShowing[3] = true;
    }
    
    private double[][] calculateResult(double[][] matrix, double scale) {
        double[][] temp = new double[matrix.length][matrix[0].length];
        for (int i = 0; i < temp.length; i++) {
            for (int j = 0; j < temp[0].length; j++) {
                temp[i][j] = scale * matrix[i][j];
                if (temp[i][j] == -0) {
                    temp[i][j] = 0;
                }
            }
        }
        return temp;
    }

    private void addActionToPasteMenu(JMenu jMenu) {
        Component[] components = jMenu.getMenuComponents();
        JMenu[] menus = new JMenu[components.length];

        for (int i = 0; i < menus.length; i++) {
            menus[i] = (JMenu) components[i];

            JMenuItem item = (JMenuItem) menus[i].getMenuComponent(0);
            JMenuItem item2 = (JMenuItem) menus[i].getMenuComponent(1);
            item.addActionListener(this);
            item2.addActionListener(this);
        }
    }

    private void addActionToSaveMenu(JMenu jMenu) {
        Component[] components = jMenu.getMenuComponents();
        JMenu[] menus = new JMenu[components.length];

        for (int i = 0; i < menus.length; i++) {
            menus[i] = (JMenu) components[i];

            JMenuItem item = (JMenuItem) menus[i].getMenuComponent(0);
            item.addActionListener(this);
        }
    }

    public static boolean[] getIsShowing() {
        return isShowing;
    }

    @SuppressWarnings("DuplicateExpressions")
    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();

        if (s.equals("Go")) {
            try {
                if (Integer.parseInt(dimMText.getText()) > 0 && Integer.parseInt(dimNText.getText()) > 0) {
                    dimM = Integer.parseInt(dimMText.getText());
                    dimN = Integer.parseInt(dimNText.getText());
                }
                subF.dispose();
                createFrameScale();
            } catch (NumberFormatException i) {
                JFrame errorFrame = new JFrame();
                JOptionPane.showMessageDialog(errorFrame, "Invalid Input", "Error", JOptionPane.WARNING_MESSAGE);
                System.out.println("Failed to parse dimensions");
            }
            System.out.printf("Size of Matrix: %d x %d%n", dimM, dimN);
        } else if (s.equals("OkScale")) {
            try {
                scalarString = constantScale.getText();

                isFractionArray[0] = MatrixHelper.isFraction(scalarString);
                System.out.println("Scalar is fraction: " + isFractionArray[0]);
                if (!isFractionArray[0]) {
                    scalar = Double.parseDouble(scalarString);
                    System.out.println(scalarString + " is a double");
                }

                System.out.println("Frame Scalar Success");
                subFrameScale.dispose();
                createFrameMatrix();

            } catch (NumberFormatException i) {
                JFrame errorFrame = new JFrame();
                JOptionPane.showMessageDialog(errorFrame, "Invalid Input", "Error", JOptionPane.WARNING_MESSAGE);
                System.out.println("Failed ot parse Frame Scale");
            }
        } else if (s.equals("OkA")) {
            try {
                inputs1 = new String[dimM][dimN];
                for (int i = 0; i < dimM; i++) {
                    for (int j = 0; j < dimN; j++) {
                        inputs1[i][j] = textField[i][j].getText();
                    }
                }

                isFractionArray[1] = MatrixHelper.isFractionArray(inputs1);
                System.out.println("Is a fraction array: " + isFractionArray[1]);
                if (!isFractionArray[1]) {
                    for (int i = 0; i < dimM; i++) {
                        for (int j = 0; j < dimN; j++) {
                            subMatrix[i][j] = Double.parseDouble(inputs1[i][j]);
                            System.out.println(inputs1[i][j] + " is already a double");
                        }
                    }
                } else {
                    for (int i = 0; i < dimM; i++) {
                        for (int j = 0; j < dimN; j++) {
                            if (!MatrixHelper.isFraction(inputs1[i][j])) {
                                inputs1[i][j] += "/1";
                            }
                        }
                    }
                }

                System.out.println("Frame B Success");
                subFrameA.dispose();
                createResultFrame();

            } catch (NumberFormatException i) {
                JFrame errorFrame = new JFrame();
                JOptionPane.showMessageDialog(errorFrame, "Invalid Input", "Error", JOptionPane.WARNING_MESSAGE);
                System.out.println("Failed to parse Frame B");
            }
        } else if (s.equals("OkResult")) {
            isShowing[3] = false;
            subFrameResult.dispose();
            MatrixCalculator.setOperation(null);
        } else if (s.equals("save")) {
            System.out.println("File Saved: " + FileSaver.createSaveScalarInput(scalarString, inputs1, resultsString, "scale"));
        } else if (s.substring(0, s.length() - 2).equals("Save")) {
            int index = Integer.parseInt(s.substring(s.length() - 1));
            switch (currentFrame) {
                case 2: {
                    try {
                        inputs1 = new String[dimM][dimN];
                        for (int i = 0; i < dimM; i++) {
                            for (int j = 0; j < dimN; j++) {
                                inputs1[i][j] = textField[i][j].getText();
                            }
                        }

                        isFractionArray[1] = MatrixHelper.isFractionArray(inputs1);
                        System.out.println("Is a fraction array: " + isFractionArray[1]);
                        if (!isFractionArray[1]) {
                            for (int i = 0; i < dimM; i++) {
                                for (int j = 0; j < dimN; j++) {
                                    if (MatrixHelper.isFraction(inputs1[i][j])) {
                                        subMatrix[i][j] = MatrixHelper.fractionToDouble(inputs1[i][j]);
                                        System.out.println("Fraction converted to double");
                                    } else {
                                        subMatrix[i][j] = Double.parseDouble(inputs1[i][j]);
                                        System.out.println("Value already double");
                                    }
                                }
                            }
                        }
                    } catch (NumberFormatException i) {
                        JFrame errorFrame = new JFrame();
                        JOptionPane.showMessageDialog(errorFrame, "Invalid Input", "Error", JOptionPane.WARNING_MESSAGE);
                    }
                    CopyPaste.saveMatrix(inputs1, index - 1);
                    break;
                }
                case 3: {
                    CopyPaste.saveMatrix(resultsString, index - 1);
                    break;
                }
            }
        } else if (s.substring(0, s.length() - 2).equals("Paste")) {
            int index = Integer.parseInt(s.substring(s.length() - 1));
            if (currentFrame == 2) {
                JTextField[][] temp = CopyPaste.pasteMatrix(textField, index - 1);
                for (int i = 0; i < textField.length; i++) {
                    for (int j = 0; j < textField[0].length; j++) {
                        textField[i][j].setText(temp[i][j].getText());
                    }
                }
                subFrameA.setVisible(true);
            }
        }

    }
}
