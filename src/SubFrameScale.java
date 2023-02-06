import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SubFrameScale extends JFrame implements ActionListener {
    static private JFrame subF, subFrameScale, subFrameA, subFrameResult;
    static private JTextField dimMText, dimNText, constantScale;
    static private JTextField[][] textField;
    static private double[][] subMatrix, subMatrixResult;
    static private int dimM, dimN;
    static private double scalar;
    static private int currentFrame;
    static private boolean[] isShowing;
    public SubFrameScale() {
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

        subF.setSize(500,200);
        subF.setLocationRelativeTo(null);
        subF.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        subF.setVisible(true);
        isShowing[0] = true;
    }
    
    private void createFrameScale() {
        currentFrame = 1;
        subFrameScale = new JFrame("Scalar");
        subFrameScale.setBackground(Color.green);
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

        subMatrixResult = calculateResult(subMatrix, scalar);

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

    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();

        if (s.equals("Go")) {
            try {
                dimM = Integer.parseInt(dimMText.getText());
                dimN = Integer.parseInt(dimNText.getText());
                subF.dispose();
                createFrameScale();
            } catch (NumberFormatException i) {
                JFrame errorFrame = new JFrame();
                JOptionPane.showMessageDialog(errorFrame, "Invalid Input", "Error", JOptionPane.WARNING_MESSAGE);
            }
            System.out.printf("Size of Matrix: %d x %d", dimM, dimN);
        } else if (s.equals("OkScale")) {
            try {
                scalar = Double.parseDouble(constantScale.getText());
                subFrameScale.dispose();
                createFrameMatrix();

            } catch (NumberFormatException i) {
                JFrame errorFrame = new JFrame();
                JOptionPane.showMessageDialog(errorFrame, "Invalid Input", "Error", JOptionPane.WARNING_MESSAGE);
            }
            System.out.println("Frame A Success");
        } else if (s.equals("OkA")) {
            try {
                for (int i = 0; i < dimM; i++) {
                    for (int j = 0; j < dimN; j++) {
                        subMatrix[i][j] = Double.parseDouble(textField[i][j].getText());
                    }
                }
                System.out.println("Frame B Success");
                subFrameA.dispose();
                createResultFrame();

            } catch (NumberFormatException i) {
                JFrame errorFrame = new JFrame();
                JOptionPane.showMessageDialog(errorFrame, "Invalid Input", "Error", JOptionPane.WARNING_MESSAGE);
            }
        } else if (s.equals("OkResult")) {
            isShowing[3] = false;
            subFrameResult.dispose();
            MatrixCalculator.setOperation(null);
        } else if (s.equals("save")) {
            System.out.println("File Saved: " + FileSaver.createSaveScalarInput(scalar, subMatrix, subMatrixResult, "scale"));
        } else if (s.substring(0, s.length() - 2).equals("Save")) {
            int index = Integer.parseInt(s.substring(s.length() - 1));
            switch (currentFrame) {
                case 2: {
                    try {
                        for (int i = 0; i < dimM; i++) {
                            for (int j = 0; j < dimN; j++) {
                                subMatrix[i][j] = Double.parseDouble(textField[i][j].getText());
                            }
                        }
                    } catch (NumberFormatException i) {
                        JFrame errorFrame = new JFrame();
                        JOptionPane.showMessageDialog(errorFrame, "Invalid Input", "Error", JOptionPane.WARNING_MESSAGE);
                    }
                    CopyPaste.saveMatrix(subMatrix, index - 1);
                    break;
                }
                case 3: {
                    CopyPaste.saveMatrix(subMatrixResult, index - 1);
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
