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
    public SubFrameScale() {
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
        subF.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        subF.setVisible(true);
    }
    
    private void createFrameScale() {
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
        subFrameScale.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        subFrameScale.setVisible(true);
    }
    
    private void createFrameMatrix() {
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

        subFrameA.setSize(200 + 10 * dimN,200 + 10 * dimN);
        subFrameA.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        subFrameA.setVisible(true);
    }
    
    private void createResultFrame() {
        subFrameResult = new JFrame("Result");
        subFrameResult.setLayout(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();

        g.gridx = 0;
        g.gridy = 0;

        subMatrixResult = calculateResult(subMatrix, scalar);

        JLabel[][] results = new JLabel[dimM][dimN];
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
        save.setActionCommand("Save");

        g.gridy++;
        subFrameResult.add(save, g);

        g.gridx++;
        subFrameResult.add(frameResultOk, g);

        subFrameResult.setSize(200 + 10 * dimN,200 + 10 * dimN);
        subFrameResult.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        subFrameResult.setVisible(true);
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
    
    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();
        System.out.println(s);

        switch (s) {
            case "Go":
                try {
                    dimM = Integer.parseInt(dimMText.getText());
                    dimN = Integer.parseInt(dimNText.getText());
                    subF.dispose();
                    createFrameScale();
                } catch (NumberFormatException i) {
                    JFrame errorFrame = new JFrame();
                    JOptionPane.showMessageDialog(errorFrame, "Invalid Input", "Alert", JOptionPane.WARNING_MESSAGE);
                }
                System.out.println(dimM + " x " + dimN);
                break;
            case "OkScale":
                try {
                    scalar = Double.parseDouble(constantScale.getText());
                    subFrameScale.dispose();
                    createFrameMatrix();

                } catch (NumberFormatException i) {
                    JFrame errorFrame = new JFrame();
                    JOptionPane.showMessageDialog(errorFrame, "Invalid Input", "Alert", JOptionPane.WARNING_MESSAGE);
                }
                System.out.println("Frame A Success");
                break;
            case "OkA":
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
                    JOptionPane.showMessageDialog(errorFrame, "Invalid Input", "Alert", JOptionPane.WARNING_MESSAGE);
                }
                break;
            case "OkResult":
                subFrameResult.dispose();
                MatrixCalculator.setOperation(null);
                break;
            case "Save":
                System.out.println(MatrixCalculator.getOperation());
                System.out.println("File Saved: " + FileSaver.createSaveScalarInput(scalar, subMatrix, subMatrixResult, "multiply"));
        }
    }
}
