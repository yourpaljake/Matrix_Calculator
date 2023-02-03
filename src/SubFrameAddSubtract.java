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
    public SubFrameAddSubtract(String o) {
        operation = o;
        subF = new JFrame("Matrix A");
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

    private void createFrame1() {
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

        subFrameA.setSize(200 + 10 * dimN,200 + 10 * dimN);
        subFrameA.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        subFrameA.setVisible(true);
    }

    private void createFrame2() {
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

        subFrameB.setSize(200 + 10 * dimN,200 + 10 * dimN);
        subFrameB.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        subFrameB.setVisible(true);
    }

    private void createResultFrame() {
        subFrameResult = new JFrame("Result");
        subFrameResult.setLayout(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();

        g.gridx = 0;
        g.gridy = 0;

        subMatrixResult = calculateResult(subMatrix1, subMatrix2);

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

    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();
        System.out.println(s);

        switch (s) {
            case "Go":
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
                break;
            case "OkA":
                try {
                    for (int i = 0; i < dimM; i++) {
                        for (int j = 0; j < dimN; j++) {
                            subMatrix1[i][j] = Double.parseDouble(textFieldA[i][j].getText());
                        }
                    }
                    subFrameA.dispose();
                    createFrame2();

                } catch (NumberFormatException i) {
                    JFrame errorFrame = new JFrame();
                    JOptionPane.showMessageDialog(errorFrame, "Invalid Input", "Alert", JOptionPane.WARNING_MESSAGE);
                }
                System.out.println("Frame A Success");
                break;
            case "OkB":
                try {
                    for (int i = 0; i < dimM; i++) {
                        for (int j = 0; j < dimN; j++) {
                            subMatrix2[i][j] = Double.parseDouble(textFieldB[i][j].getText());
                        }
                    }
                    System.out.println("Frame B Success");
                    subFrameB.dispose();
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
                System.out.println("File Saved: " + FileSaver.createSave(subMatrix1, subMatrix2, subMatrixResult, operation));
        }
    }

}
