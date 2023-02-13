import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.Arrays;
/**
 * This houses the Matrix Addition and Subtraction functionality of the program. It is called to
 * in the MatrixCalculator class with a parameter containing the operation to create an instance
 * of this class.
 *
 * @author Jacob Larson
 * @version 0.3.3
 * @since 01/28/23
 */

public class SubFrameAddSubtract extends JFrame implements ActionListener {
    static private JFrame subF, subFrameA, subFrameB, subFrameResult;
    static private JTextField dimMText, dimNText;
    static private JTextField[][] textFieldA, textFieldB;
    static private int dimM, dimN;
    static private double[][] subMatrix1, subMatrix2;
    static private String[][] inputs1, inputs2, resultsString;
    static private String operation;
    static private int currentFrame;
    static private boolean[] isShowing;
    static private boolean[] isFractionArray;
    public SubFrameAddSubtract(String o) {
        isFractionArray = new boolean[2];
        isShowing = new boolean[4];
        currentFrame = 0;
        operation = o;
        String tempOp = (operation.equals("add"))? "+":"-";
        subF = new JFrame("Matrix A " + tempOp + " B");
        subF.setLayout(new GridBagLayout());
        @SuppressWarnings("WriteOnlyObject")
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

    private void createFrame1() {
        currentFrame = 1;
        subFrameA = new JFrame("Matrix A");
        subFrameA.setLayout(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.gridx = 0;
        g.gridy = 0;

        subMatrix1 = new double[dimM][dimN];

        textFieldA = new JTextField[dimM][dimN];

        for (int i = 0; i < dimM; i++) {
            for (int j = 0; j < dimN; j++) {
                g.gridx = j;
                g.gridy = i;
                textFieldA[i][j] = new JTextField("");
                textFieldA[i][j].setPreferredSize(new Dimension(50, 20));
                subFrameA.add(textFieldA[i][j], g);
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
        isShowing[0] = false;
        isShowing[1] = true;
    }

    private void createFrame2() {
        currentFrame = 2;
        subFrameB = new JFrame("Matrix B");
        subFrameB.setLayout(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.gridx = 0;
        g.gridy = 0;

        subMatrix2 = new double[dimM][dimN];

        textFieldB = new JTextField[dimM][dimN];

        for (int i = 0; i < dimM; i++) {
            for (int j = 0; j < dimN; j++) {
                g.gridx = j;
                g.gridy = i;
                textFieldB[i][j] = new JTextField("");
                textFieldB[i][j].setPreferredSize(new Dimension(50, 20));
                subFrameB.add(textFieldB[i][j], g);
            }

        }

        JButton frameBOk = new JButton("Ok");
        frameBOk.addActionListener(this);
        frameBOk.setActionCommand("OkB");

        g.gridx++;
        g.gridy++;

        subFrameB.add(frameBOk, g);

        JMenuBar menuBar = new JMenuBar();
        JMenu tempMenu = CopyPaste.createPasteMenu();
        addActionToPasteMenu(tempMenu);
        menuBar.add(tempMenu);
        subFrameB.setJMenuBar(menuBar);

        subFrameB.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Arrays.fill(isShowing, false);
            }});

        subFrameB.setSize(200 + 30 * dimN,200 + 30 * dimN);
        subFrameB.setLocationRelativeTo(null);
        subFrameB.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        subFrameB.setVisible(true);
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
            resultsString = MatrixHelper.addFractionArray(inputs1, inputs2);
            results = MatrixHelper.toJLabel(resultsString);
        } else if (isFractionArray[0] ^ isFractionArray[1]) {
            if (isFractionArray[0]) {
                for (int i = 0; i < dimM; i++) {
                    for (int j = 0; j < dimN; j++) {
                        inputs2[i][j] = subMatrix2[i][j] + "/1";
                    }
                }
                System.out.println("Matrix A was Fractional");
            } else {
                for (int i = 0; i < dimM; i++) {
                    for (int j = 0; j < dimN; j++) {
                        inputs1[i][j] = subMatrix1[i][j] + "/1";
                    }
                }
                System.out.println("Matrix B was Fractional");
            }
            resultsString = MatrixHelper.addFractionArray(inputs1, inputs2);
            results = MatrixHelper.toJLabel(resultsString);
        } else {
            double[][] subMatrixResult = calculateResult(subMatrix1, subMatrix2);
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

    private double[][] calculateResult(double[][] tempM1, double[][] tempM2) {
        double[][] tempR = new double[tempM1.length][tempM1[0].length];
        for (int i = 0; i < dimM; i++) {
            for (int j = 0; j <dimN; j++) {
                if (operation.equals("add")) {
                    tempR[i][j] = tempM1[i][j] + tempM2[i][j];
                } else if (operation.equals("subtract")) {
                    tempR[i][j] = tempM1[i][j] - tempM2[i][j];
                }
            }
        }
        return tempR;
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

    @SuppressWarnings({"EnhancedSwitchMigration"})
    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();

        System.out.printf("Current Action of %s on frame %d%n",s,currentFrame);

        if (s.equals("Go")) {
            try {
                if (Integer.parseInt(dimMText.getText()) > 0 && Integer.parseInt(dimNText.getText()) > 0) {
                    dimM = Integer.parseInt(dimMText.getText());
                    dimN = Integer.parseInt(dimNText.getText());
                    subF.dispose();
                    createFrame1();
                } else {
                    JOptionPane.showMessageDialog(new JFrame(), "Invalid Input", "Error", JOptionPane.WARNING_MESSAGE);
                    System.out.println("Not valid dimensions");
                }
            } catch (NumberFormatException i) {
                JFrame errorFrame = new JFrame();
                JOptionPane.showMessageDialog(errorFrame, "Invalid Input", "Error", JOptionPane.WARNING_MESSAGE);
                System.out.println("Failed to parse dimensions");
            }
            System.out.printf("Size of Matrix: %d x %d%n", dimM, dimN);
        } else if (s.equals("OkA")) {
            try {
                inputs1 = new String[dimM][dimN];
                for (int i = 0; i < dimM; i++) {
                    for (int j = 0; j < dimN; j++) {
                        inputs1[i][j] = textFieldA[i][j].getText();
                    }
                }

                isFractionArray[0] = MatrixHelper.isFractionArray(inputs1);
                System.out.println("Is a fraction array: " + isFractionArray[0]);
                if (!isFractionArray[0]) {
                    for (int i = 0; i < dimM; i++) {
                        for (int j = 0; j < dimN; j++) {
                            subMatrix1[i][j] = Double.parseDouble(inputs1[i][j]);
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

                System.out.println("Frame A Success");
                subFrameA.dispose();
                createFrame2();

            } catch (NumberFormatException i) {
                JFrame errorFrame = new JFrame();
                JOptionPane.showMessageDialog(errorFrame, "Invalid Input", "Error", JOptionPane.WARNING_MESSAGE);
                System.out.println("Failed to parse Frame A");
            }
        } else if (s.equals("OkB")) {
            try {
                inputs2 = new String[dimM][dimN];
                for (int i = 0; i < dimM; i++) {
                    for (int j = 0; j < dimN; j++) {
                        inputs2[i][j] = textFieldB[i][j].getText();
                    }
                }

                isFractionArray[1] = MatrixHelper.isFractionArray(inputs2);
                System.out.println("Is a fraction array: " + isFractionArray[1]);
                if (!isFractionArray[1]) {
                    for (int i = 0; i < dimM; i++) {
                        for (int j = 0; j < dimN; j++) {
                            subMatrix2[i][j] = Double.parseDouble(inputs2[i][j]);
                            System.out.println(inputs2[i][j] + " is already a double");
                        }
                    }
                } else {
                    for (int i = 0; i < dimM; i++) {
                        for (int j = 0; j < dimN; j++) {
                            if (!MatrixHelper.isFraction(inputs2[i][j])) {
                                inputs2[i][j] += "/1";
                            }
                        }
                    }
                }

                System.out.println("Frame B Success");
                subFrameB.dispose();
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
            System.out.println("File Saved: " + FileSaver.createSaveTwoInput(inputs1, inputs2, resultsString, operation));
        } else if (s.substring(0, s.length() - 2).equals("Save")) {
            int index = Integer.parseInt(s.substring(s.length() - 1));
            switch (currentFrame) {
                case 1: {
                    try {
                        inputs1 = new String[dimM][dimN];
                        for (int i = 0; i < dimM; i++) {
                            for (int j = 0; j < dimN; j++) {
                                inputs1[i][j] = textFieldA[i][j].getText();
                            }
                        }

                        isFractionArray[0] = MatrixHelper.isFractionArray(inputs1);
                        System.out.println("Is a fraction array: " + isFractionArray[0]);
                        if (!isFractionArray[0]) {
                            for (int i = 0; i < dimM; i++) {
                                for (int j = 0; j < dimN; j++) {
                                    if (MatrixHelper.isFraction(inputs1[i][j])) {
                                        subMatrix1[i][j] = MatrixHelper.fractionToDouble(inputs1[i][j]);
                                        System.out.printf("%s converted to %f%n", inputs1[i][j], subMatrix1[i][j]);
                                    } else {
                                        subMatrix1[i][j] = Double.parseDouble(inputs1[i][j]);
                                        System.out.println(inputs1[i][j] + " is already a double");
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
                case 2: {
                    try {
                        inputs2 = new String[dimM][dimN];
                        for (int i = 0; i < dimM; i++) {
                            for (int j = 0; j < dimN; j++) {
                                inputs2[i][j] = textFieldB[i][j].getText();
                            }
                        }

                        isFractionArray[1] = MatrixHelper.isFractionArray(inputs2);
                        System.out.println("Is a fraction array: " + isFractionArray[1]);
                        if (!isFractionArray[1]) {
                            for (int i = 0; i < dimM; i++) {
                                for (int j = 0; j < dimN; j++) {
                                    if (MatrixHelper.isFraction(inputs2[i][j])) {
                                        subMatrix2[i][j] = MatrixHelper.fractionToDouble(inputs2[i][j]);
                                        System.out.println("Fraction converted to double");
                                    } else {
                                        subMatrix2[i][j] = Double.parseDouble(inputs2[i][j]);
                                        System.out.println("Value already double");
                                    }
                                }
                            }
                        }
                    } catch (NumberFormatException i) {
                        JFrame errorFrame = new JFrame();
                        JOptionPane.showMessageDialog(errorFrame, "Invalid Input", "Error", JOptionPane.WARNING_MESSAGE);
                    }
                    CopyPaste.saveMatrix(inputs2, index - 1);
                    break;
                }
                case 3: {
                    CopyPaste.saveMatrix(resultsString, index - 1);
                    break;
                }
            }
        } else if (s.substring(0, s.length() - 2).equals("Paste")) {
            int index = Integer.parseInt(s.substring(s.length() - 1));
            switch (currentFrame) {
                case 1: {
                    JTextField[][] temp = CopyPaste.pasteMatrix(textFieldA, index - 1);
                    for (int i = 0; i < textFieldA.length; i++) {
                        for (int j = 0; j < textFieldA[0].length; j++) {
                            textFieldA[i][j].setText(temp[i][j].getText());
                        }
                    }
                    subFrameA.setVisible(true);
                    break;
                }
                case 2: {
                    JTextField[][] temp = CopyPaste.pasteMatrix(textFieldB, index - 1);
                    for (int i = 0; i < textFieldB.length; i++) {
                        for (int j = 0; j < textFieldB[0].length; j++) {
                            textFieldB[i][j].setText(temp[i][j].getText());
                        }
                    }
                    subFrameB.setVisible(true);
                    break;
                }
            }
        }
    }

}
