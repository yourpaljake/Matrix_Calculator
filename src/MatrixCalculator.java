import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MatrixCalculator extends JFrame implements ActionListener {
    static private JLabel label;

    static private String operation;

    public MatrixCalculator() {
        JFrame frame = new JFrame("Matrix Calculator");
        frame.setLayout(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();

        label = new JLabel("Nothing Selected");

        JMenuBar mb = new JMenuBar();

        JMenu elementary = new JMenu("Elementary Operations");

        JMenuItem add = new JMenuItem("Add (A+B)");
        JMenuItem subtract = new JMenuItem("Subtract (A-B)");
        JMenuItem scale = new JMenuItem("Scale (cA)");

        add.addActionListener(this);
        add.setActionCommand("add");
        subtract.addActionListener(this);
        subtract.setActionCommand("subtract");
        scale.addActionListener(this);
        scale.setActionCommand("scale");

        elementary.add(add);
        elementary.add(subtract);
        elementary.add(scale);

        mb.add(elementary);

        frame.setJMenuBar(mb);

        g.gridy = 3;

        frame.add(label, g);

        JButton go = new JButton("Go");
        go.addActionListener(this);
        go.setActionCommand("Go");
        go.setPreferredSize(new Dimension(90,30));
        JPanel p = new JPanel();
        p.add(go);

        g.gridy = 5;

        frame.add(p, g);

        frame.setSize(600,400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void setOperation(String op) {
        operation = op;
    }

    @SuppressWarnings("unused")
    public static void main(String[] args) {
        MatrixCalculator calculator = new MatrixCalculator();
    }

    @SuppressWarnings("unused")
    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();
        System.out.println("Current Action: " + s);

        if (s.equals("Go")) {
            if (!(operation == null) && !operation.equals("")) {
                label.setText("Operation: ");
                //noinspection EnhancedSwitchMigration
                switch (operation) {
                    case "add", "subtract": {
                        boolean temp = false;
                        if (SubFrameAddSubtract.getIsShowing() != null) {
                            for (boolean bool : SubFrameAddSubtract.getIsShowing()) {
                                if (bool) {
                                    temp = true;
                                    break;
                                }
                            }
                        }
                        if (!temp) {
                            SubFrameAddSubtract sF1 = new SubFrameAddSubtract(operation);
                        }
                        break;
                    }
                    case "scale": {
                        SubFrameScale sF1 = new SubFrameScale();
                        break;
                    }
                }
            } else {
                JOptionPane.showMessageDialog(new JFrame(), "No Operation Selected","Error", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            operation = s;
            label.setText("Operation: " + operation);
        }

    }
}
