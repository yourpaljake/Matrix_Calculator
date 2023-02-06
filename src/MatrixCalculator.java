import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

class WindowClose implements WindowListener {
    public void windowOpened(WindowEvent e) {}

    public void windowClosing(WindowEvent e) {}

    public void windowClosed(WindowEvent e) {
        MatrixCalculator.getFrame().setVisible(true);
    }

    public void windowIconified(WindowEvent e) {}

    public void windowDeiconified(WindowEvent e) {}

    public void windowActivated(WindowEvent e) {}

    public void windowDeactivated(WindowEvent e) {}
}

public class MatrixCalculator extends JFrame implements ActionListener {
    static private JFrame frame;
    static private JMenuBar mb;
    static private JMenu elementary;
    static private JMenuItem add, subtract, scale;
    static private JButton go;
    static private JLabel label;

    static private String operation;

    public MatrixCalculator() {
        frame = new JFrame("Matrix Calculator");
        frame.setLayout(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();

        label = new JLabel("Nothing Selected");

        mb = new JMenuBar();

        elementary = new JMenu("Elementary Operations");

        add = new JMenuItem("Add (A+B)");
        subtract = new JMenuItem("Subtract (A-B)");
        scale = new JMenuItem("Scale (cA)");

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

        go = new JButton("Go");
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

    public static String getOperation() {
        return operation;
    }

    public static void setOperation(String op) {
        operation = op;
    }

    public static JFrame getFrame() {
        return frame;
    }

    public static void main(String[] args) {
        MatrixCalculator calculator = new MatrixCalculator();
    }

    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();
        System.out.println("Current Action: " + s);

        if (s.equals("Go")) {
            if (!(operation == null) && !operation.equals("")) {
                label.setText("Operation: ");
                switch (operation) {
                    case "add", "subtract": {
                        SubFrameAddSubtract sF1 = new SubFrameAddSubtract(operation);
                        frame.setVisible(false);
                        break;
                    }
                    case "scale": {
                        SubFrameScale sF1 = new SubFrameScale();
                        frame.setVisible(false);
                        break;
                    }
                }
            }
        } else {
            operation = s;
            label.setText("Operation: " + operation);
        }

    }
}
