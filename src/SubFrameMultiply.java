import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SubFrameMultiply extends JFrame implements ActionListener {
    static private JFrame subF, subFrameA, subFrameB, subFrameResult;
    static private JTextField dimMText1, dimNText1, dimMText2, dimNText2;
    static private JTextField[][] textFieldA, textFieldB;
    static private int dimM1, dimN1, dimM2, dimN2, dimMR, dimNR;
    static private double[][] subMatrix1, subMatrix2;
    static private String[][] inputs1, inputs2, resultsString;
    static private int currentFrame;
    static private boolean[] isShowing;
    static private boolean[] isFractionArray;

    public SubFrameMultiply() {
        isFractionArray = new boolean[2];
        isShowing = new boolean[4];
        currentFrame = 0;
        subF = new JFrame("Matrix A x B");
        subF.setLayout(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.gridx = 0;
        g.gridy = 0;

        JLabel subL = new JLabel("Input a the Dimensions of the Matrices:\t");
        subF.add(subL, g);
        g.gridy++;
        g.anchor = GridBagConstraints.FIRST_LINE_END;

        subF.add(new JLabel("Matrix A: "), g);

        g.gridx++;
        g.anchor = GridBagConstraints.FIRST_LINE_START;


        dimMText1 = new JTextField("");
        dimMText1.setPreferredSize(new Dimension(50, 20));
        subF.add(dimMText1, g);
        g.gridx++;

        subF.add(new JLabel("x"), g);

        g.gridx++;

        dimNText1 = new JTextField("");
        dimNText1.setPreferredSize(new Dimension(50, 20));
        subF.add(dimNText1, g);

        g.gridx = 0;
        g.gridy++;
        g.anchor = GridBagConstraints.FIRST_LINE_END;

        subF.add(new JLabel("Matrix B: "), g);

        g.gridx++;
        g.anchor = GridBagConstraints.FIRST_LINE_START;

        dimMText2 = new JTextField("");
        dimMText2.setPreferredSize(new Dimension(50, 20));
        subF.add(dimMText2, g);
        g.gridx++;

        subF.add(new JLabel("x"), g);
        g.gridx++;

        dimNText2 = new JTextField("");
        dimNText2.setPreferredSize(new Dimension(50, 20));
        subF.add(dimNText2, g);

        g.gridx++;
        g.gridy++;

        JButton subB = new JButton("Go");
        subB.addActionListener(this);
        subB.setActionCommand("Go");

        subF.add(subB, g);

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

        subMatrix1 = new double[dimM1][dimN1];

        textFieldA = new JTextField[dimM1][dimN1];

        for (int i = 0; i < dimM1; i++) {
            for (int j = 0; j < dimN1; j++) {
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

        subFrameA.setSize(200 + 30 * dimN1,200 + 30 * dimN1);
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

        subMatrix2 = new double[dimM2][dimN2];

        textFieldB = new JTextField[dimM2][dimN2];

        for (int i = 0; i < dimM2; i++) {
            for (int j = 0; j < dimN2; j++) {
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

        subFrameB.setSize(200 + 30 * dimN2,200 + 30 * dimN2);
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
                for (int i = 0; i < dimM2; i++) {
                    for (int j = 0; j < dimN2; j++) {
                        inputs2[i][j] = subMatrix2[i][j] + "/1";
                    }
                }
                System.out.println("Matrix A was Fractional");
            } else {
                for (int i = 0; i < dimM1; i++) {
                    for (int j = 0; j < dimN1; j++) {
                        inputs1[i][j] = subMatrix1[i][j] + "/1";
                    }
                }
                System.out.println("Matrix B was Fractional");
            }
            resultsString = MatrixHelper.multiplyFractionArray(inputs1, inputs2);
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

        subFrameResult.setSize(200 + 30 * dimNR,200 + 30 * dimNR);
        subFrameResult.setLocationRelativeTo(null);
        subFrameResult.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        subFrameResult.setVisible(true);
        isShowing[2] = false;
        isShowing[3] = true;
    }

    private double[][] calculateResult(double[][] tempM1, double[][] tempM2) {
        double[][] tempR = new double[tempM1.length][tempM2[0].length];

        for (int i = 0; i < dimMR; i++) {
            for (int j = 0; j < dimNR; j++) {
                for (int k = 0; k < dimM2; k++) {
                    tempR[i][j] += tempM1[i][k] * tempM2[k][j];
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

    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();

        System.out.printf("Current Action of %s on frame %d%n",s,currentFrame);

        if (s.equals("Go")) {
            boolean errorWasShown = false;
            try {
                if (Integer.parseInt(dimMText1.getText()) > 0 && Integer.parseInt(dimNText1.getText()) > 0) {
                    dimM1 = Integer.parseInt(dimMText1.getText());
                    dimN1 = Integer.parseInt(dimNText1.getText());

                    if (Integer.parseInt(dimMText2.getText()) == dimN1) {
                        if (Integer.parseInt(dimMText2.getText()) > 0 && Integer.parseInt(dimNText2.getText()) > 0) {
                            dimM2 = Integer.parseInt(dimMText2.getText());
                            dimN2 = Integer.parseInt(dimNText2.getText());
                            dimMR = dimM1;
                            dimNR = dimN2;
                            subF.dispose();
                            createFrame1();
                        } else {
                            String error = "Invalid input. Dimensions of matrices must be a positive integer.";
                            JOptionPane.showMessageDialog(new JFrame(), error, "Error", JOptionPane.WARNING_MESSAGE);
                            System.out.println("Not valid dimensions");
                            errorWasShown = true;
                            Integer.parseInt("hi");
                        }
                    } else {
                        String error = "Invalid input. Matrix of size %s x %s cannot be multiplied by Matrix of size %s x %s.";
                        JOptionPane.showMessageDialog(new JOptionPane(), String.format(error, dimMText2.getText(), dimNText2.getText(), dimM1, dimN1));
                        System.out.println("Not valid dimensions");
                        errorWasShown = true;
                        Integer.parseInt("hi");
                    }
                } else {
                    String error = "Invalid input. Dimensions of matrices must be a positive integer.";
                    JOptionPane.showMessageDialog(new JFrame(), error, "Error", JOptionPane.WARNING_MESSAGE);
                    System.out.println("Not valid dimensions");
                    errorWasShown = true;
                    Integer.parseInt("hi");
                }
            } catch (NumberFormatException i) {
                if (!errorWasShown) {
                    JOptionPane errorFrame = new JOptionPane();
                    JOptionPane.showMessageDialog(errorFrame, "Invalid Input", "Error", JOptionPane.WARNING_MESSAGE);
                }
                System.out.println("Failed to parse dimensions");
            }
            System.out.printf("Size of Matrix A: %d x %d%nSize of Matrix B: %d x %d%n", dimM1, dimN1, dimM2, dimN2);
        } else if (s.equals("OkA")) {
            try {
                inputs1 = new String[dimM1][dimN1];
                for (int i = 0; i < dimM1; i++) {
                    for (int j = 0; j < dimN1; j++) {
                        inputs1[i][j] = textFieldA[i][j].getText();
                    }
                }

                isFractionArray[0] = MatrixHelper.isFractionArray(inputs1);
                System.out.println("Is a fraction array: " + isFractionArray[0]);
                if (!isFractionArray[0]) {
                    for (int i = 0; i < dimM1; i++) {
                        for (int j = 0; j < dimN1; j++) {
                            subMatrix1[i][j] = Double.parseDouble(inputs1[i][j]);
                            System.out.println(inputs1[i][j] + " is already a double");
                        }
                    }
                } else {
                    for (int i = 0; i < dimM1; i++) {
                        for (int j = 0; j < dimN1; j++) {
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
                inputs2 = new String[dimM2][dimN2];
                for (int i = 0; i < dimM2; i++) {
                    for (int j = 0; j < dimN2; j++) {
                        inputs2[i][j] = textFieldB[i][j].getText();
                    }
                }

                isFractionArray[1] = MatrixHelper.isFractionArray(inputs2);
                System.out.println("Is a fraction array: " + isFractionArray[1]);
                if (!isFractionArray[1]) {
                    for (int i = 0; i < dimM2; i++) {
                        for (int j = 0; j < dimN2; j++) {
                            subMatrix2[i][j] = Double.parseDouble(inputs2[i][j]);
                            System.out.println(inputs2[i][j] + " is already a double");
                        }
                    }
                } else {
                    for (int i = 0; i < dimM2; i++) {
                        for (int j = 0; j < dimN2; j++) {
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
        }  else if (s.equals("OkResult")) {
            isShowing[3] = false;
            subFrameResult.dispose();
            MatrixCalculator.setOperation(null);
        } else if (s.equals("save")) {
            System.out.println("File Saved: " + FileSaver.createSaveTwoInput(inputs1, inputs2, resultsString, "multiply"));
        } else if (s.substring(0, s.length() - 2).equals("Save")) {
            int index = Integer.parseInt(s.substring(s.length() - 1));
            switch (currentFrame) {
                case 1 -> {
                    try {
                        inputs1 = new String[dimM1][dimN1];
                        for (int i = 0; i < dimM1; i++) {
                            for (int j = 0; j < dimN1; j++) {
                                inputs1[i][j] = textFieldA[i][j].getText();
                            }
                        }

                        isFractionArray[0] = MatrixHelper.isFractionArray(inputs1);
                        System.out.println("Is a fraction array: " + isFractionArray[0]);
                        if (!isFractionArray[0]) {
                            for (int i = 0; i < dimM1; i++) {
                                for (int j = 0; j < dimN1; j++) {
                                    subMatrix1[i][j] = Double.parseDouble(inputs1[i][j]);
                                    System.out.println(inputs1[i][j] + " is already a double");
                                }
                            }
                        } else {
                            for (int i = 0; i < dimM1; i++) {
                                for (int j = 0; j < dimN1; j++) {
                                    if (!MatrixHelper.isFraction(inputs1[i][j])) {
                                        inputs1[i][j] += "/1";
                                    }
                                }
                            }
                        }
                    } catch (NumberFormatException i) {
                        JFrame errorFrame = new JFrame();
                        JOptionPane.showMessageDialog(errorFrame, "Invalid Input", "Error", JOptionPane.WARNING_MESSAGE);
                    }
                    CopyPaste.saveMatrix(inputs1, index - 1);
                }
                case 2 -> {
                    try {
                        inputs2 = new String[dimM2][dimN2];
                        for (int i = 0; i < dimM2; i++) {
                            for (int j = 0; j < dimN2; j++) {
                                inputs2[i][j] = textFieldB[i][j].getText();
                            }
                        }

                        isFractionArray[1] = MatrixHelper.isFractionArray(inputs2);
                        System.out.println("Is a fraction array: " + isFractionArray[1]);
                        if (!isFractionArray[1]) {
                            for (int i = 0; i < dimM2; i++) {
                                for (int j = 0; j < dimN2; j++) {
                                    subMatrix2[i][j] = Double.parseDouble(inputs2[i][j]);
                                    System.out.println(inputs2[i][j] + " is already a double");
                                }
                            }
                        } else {
                            for (int i = 0; i < dimM2; i++) {
                                for (int j = 0; j < dimN2; j++) {
                                    if (!MatrixHelper.isFraction(inputs2[i][j])) {
                                        inputs2[i][j] += "/1";
                                    }
                                }
                            }
                        }
                    } catch (NumberFormatException i) {
                        JFrame errorFrame = new JFrame();
                        JOptionPane.showMessageDialog(errorFrame, "Invalid Input", "Error", JOptionPane.WARNING_MESSAGE);
                    }
                    CopyPaste.saveMatrix(inputs2, index - 1);
                }
                case 3 -> CopyPaste.saveMatrix(resultsString, index - 1);
            }
        } else if (s.substring(0, s.length() - 2).equals("Paste")) {
            int index = Integer.parseInt(s.substring(s.length() - 1));
            switch (currentFrame) {
                case 1 -> {
                    JTextField[][] temp = CopyPaste.pasteMatrix(textFieldA, index - 1);
                    for (int i = 0; i < textFieldA.length; i++) {
                        for (int j = 0; j < textFieldA[0].length; j++) {
                            textFieldA[i][j].setText(temp[i][j].getText());
                        }
                    }
                    subFrameA.setVisible(true);
                }
                case 2 -> {
                    JTextField[][] temp = CopyPaste.pasteMatrix(textFieldB, index - 1);
                    for (int i = 0; i < textFieldB.length; i++) {
                        for (int j = 0; j < textFieldB[0].length; j++) {
                            textFieldB[i][j].setText(temp[i][j].getText());
                        }
                    }
                    subFrameB.setVisible(true);
                }
            }
        }
    }
}