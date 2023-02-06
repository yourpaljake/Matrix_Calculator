import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SubFrameAddSubtract extends JFrame implements ActionListener {
    static private JFrame subF, subFrameA, subFrameB, subFrameResult;
    static private JTextField dimMText, dimNText;
    static private JTextField[][] textFieldA, textFieldB;
    static private int dimM, dimN;
    static private double[][] subMatrix1, subMatrix2, subMatrixResult;
    static private String operation;
    static private int currentFrame;
    public SubFrameAddSubtract(String o) {
        currentFrame = 0;
        operation = o;
        subF = new JFrame("Matrix A" + operation + "B");
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

        subF.setSize(500,200);
        subF.setLocationRelativeTo(null);
        subF.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        subF.addWindowListener(new WindowClose());
        subF.setVisible(true);
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

        subFrameA.setSize(200 + 10 * dimN,200 + 10 * dimN);
        subFrameA.setLocationRelativeTo(null);
        subFrameA.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        subFrameA.addWindowListener(new WindowClose());
        subFrameA.setVisible(true);
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

        subFrameB.setSize(200 + 10 * dimN,200 + 10 * dimN);
        subFrameB.setLocationRelativeTo(null);
        subFrameB.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        subFrameB.addWindowListener(new WindowClose());
        subFrameB.setVisible(true);
    }

    private void createResultFrame() {
        currentFrame = 3;
        subFrameResult = new JFrame("Result");
        subFrameResult.setLayout(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();

        g.gridx = 0;
        g.gridy = 0;

        subMatrixResult = calculateResult(subMatrix1, subMatrix2);

        JLabel[][] results;
        results = MatrixHelper.toJLabel(subMatrixResult);

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

        subFrameResult.setSize(200 + 10 * dimN,200 + 10 * dimN);
        subFrameResult.setLocationRelativeTo(null);
        subFrameResult.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        subFrameResult.addWindowListener(new WindowClose());
        subFrameResult.setVisible(true);
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

    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();

        if (s.equals("Go")) {
            try {
                dimM = Integer.parseInt(dimMText.getText());
                dimN = Integer.parseInt(dimNText.getText());
                subF.dispose();
                createFrame1();
            } catch (NumberFormatException i) {
                JFrame errorFrame = new JFrame();
                JOptionPane.showMessageDialog(errorFrame, "Invalid Input", "Alert", JOptionPane.WARNING_MESSAGE);
            }
            System.out.println(dimM + " x " + dimN);
        } else if (s.equals("OkA")) {
            try {
                for (int i = 0; i < dimM; i++) {
                    for (int j = 0; j < dimN; j++) {
                        subMatrix1[i][j] = Double.parseDouble(textFieldA[i][j].getText());
                    }
                }
                System.out.println("Frame A Success");
                subFrameA.dispose();
                createFrame2();

            } catch (NumberFormatException i) {
                JFrame errorFrame = new JFrame();
                JOptionPane.showMessageDialog(errorFrame, "Invalid Input", "Alert", JOptionPane.WARNING_MESSAGE);
            }
        } else if (s.equals("OkB")) {
            try {
                for (int i = 0; i < dimM; i++) {
                    for (int j = 0; j < dimN; j++) {
                        subMatrix2[i][j] = Double.parseDouble(textFieldB[i][j].getText());
                        System.out.println(subMatrix2[i][j]);
                    }
                }
                System.out.println("Frame B Success");
                subFrameB.dispose();
                createResultFrame();

            } catch (NumberFormatException i) {
                JFrame errorFrame = new JFrame();
                JOptionPane.showMessageDialog(errorFrame, "Invalid Input", "Alert", JOptionPane.WARNING_MESSAGE);
            }
        } else if (s.equals("OkResult")) {
            subFrameResult.dispose();
            MatrixCalculator.setOperation(null);
            MatrixCalculator.getFrame().setVisible(true);
        } else if (s.equals("save")) {
            System.out.println("File Saved: " + FileSaver.createSaveTwoInput(subMatrix1, subMatrix2, subMatrixResult, operation));
        } else if (s.substring(0, s.length() - 2).equals("Save")) {
            int index = Integer.parseInt(s.substring(s.length() - 1));
            switch (currentFrame) {
                case 1: {
                    try {
                        for (int i = 0; i < dimM; i++) {
                            for (int j = 0; j < dimN; j++) {
                                subMatrix1[i][j] = Double.parseDouble(textFieldA[i][j].getText());
                            }
                        }
                    } catch (NumberFormatException i) {
                        JFrame errorFrame = new JFrame();
                        JOptionPane.showMessageDialog(errorFrame, "Invalid Input", "Alert", JOptionPane.WARNING_MESSAGE);
                    }
                    CopyPaste.saveMatrix(subMatrix1, index - 1);
                    break;
                }
                case 2: {
                    try {
                        for (int i = 0; i < dimM; i++) {
                            for (int j = 0; j < dimN; j++) {
                                subMatrix2[i][j] = Double.parseDouble(textFieldB[i][j].getText());
                            }
                        }
                        System.out.println("Frame B Success");
                    } catch (NumberFormatException i) {
                        JFrame errorFrame = new JFrame();
                        JOptionPane.showMessageDialog(errorFrame, "Invalid Input", "Alert", JOptionPane.WARNING_MESSAGE);
                    }
                    CopyPaste.saveMatrix(subMatrix2, index - 1);
                    break;
                }
                case 3: {
                    CopyPaste.saveMatrix(subMatrixResult, index - 1);
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
