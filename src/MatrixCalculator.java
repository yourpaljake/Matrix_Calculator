import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MatrixCalculator extends JFrame implements ActionListener {
    static private JFrame frame;
    static private JMenuBar mb;
    static private JMenu elementary;
    static private JMenuItem add, subtract;
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

        add.addActionListener(this);
        add.setActionCommand("add");
        subtract.addActionListener(this);
        subtract.setActionCommand("subtract");

        elementary.add(add);
        elementary.add(subtract);

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
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static String getOperation() {
        return operation;
    }

    public static void setOperation(String op) {
        operation = op;
    }

    public static void main(String[] args) {
        MatrixCalculator calculator = new MatrixCalculator();
    }

    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();
        System.out.println("Current Action: " + s);

        if (s.equals("Go")) {
            if (!(operation == null) && !operation.equals("")) {
                switch (operation) {
                    case "add", "subtract": {
                        SubFrameAddSubtract sF1 = new SubFrameAddSubtract(operation);
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
