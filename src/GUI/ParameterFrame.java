package GUI;

import Engine.EngineConstants;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * @author Rafał Jagielski
 */
public class ParameterFrame extends JPanel {

    private JTextField[] fields;

    // Create a form with the specified labels, tooltips, and sizes.
    public ParameterFrame(String[] labels, char[] mnemonics, int[] widths, String[] tips) {
        super(new BorderLayout());
        JPanel labelPanel = new JPanel(new GridLayout(labels.length, 1));
        JPanel fieldPanel = new JPanel(new GridLayout(labels.length, 1));
        add(labelPanel, BorderLayout.WEST);
        add(fieldPanel, BorderLayout.CENTER);
        fields = new JTextField[labels.length];

        for (int i = 0; i < labels.length; i += 1) {
            fields[i] = new JTextField();
            if (i < tips.length)
                fields[i].setToolTipText(tips[i]);
            if (i < widths.length)
                fields[i].setColumns(widths[i]);

            JLabel lab = new JLabel(labels[i], JLabel.RIGHT);
            lab.setLabelFor(fields[i]);
            if (i < mnemonics.length)
                lab.setDisplayedMnemonic(mnemonics[i]);

            labelPanel.add(lab);
            JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
            p.add(fields[i]);
            fieldPanel.add(p);
        }

        fields[0].setText(Double.toString(EngineConstants.c1Attribute));
        fields[1].setText(Double.toString(EngineConstants.c2Attribute));
        fields[2].setText(Integer.toString(EngineConstants.mAttribute));
        fields[3].setText(Double.toString(EngineConstants.sigmaMinimum));
        fields[4].setText(Double.toString(EngineConstants.exampleStartingSigma));
        fields[5].setText(Integer.toString(EngineConstants.noOfGenes));
    }

    public String getText(int i) {
        return (fields[i].getText());
    }

    public static void main(String[] args) {
        String[] labels = { "c1Attribute", "c2Attribute", "mAttribute", "sigmaMinimum", "startingSigma", "noOfGenes" };
        char[] mnemonics = {};
        int[] widths = { 10, 10, 10, 10, 10, 10 };
        String[] descs = { "Mnożnik sigma przy wzroscie.", "Mnożnik sigma przy spadku", "Liczba iteracji, po których sigma jest uaktualniana", "Graniczna wartość parametru sigma", "Początkowa wartość parametru sigma", "liczba osobnikó w populacji" };

        final ParameterFrame form = new ParameterFrame(labels, mnemonics, widths, descs);

        final JFrame f = new JFrame("Text Form Example");
        JButton submit = new JButton("Accept");
        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                Double c1Attribute;
                Double c2Attribute;
                Integer mAttribute;
                Double sigmaMinimum;
                Double exampleStartingSigma;
                Integer noOfGenes;

                try
                {
                    c1Attribute = Double.parseDouble(form.getText(0));
                    c2Attribute = Double.parseDouble(form.getText(1));
                    mAttribute = Integer.parseInt(form.getText(2));
                    sigmaMinimum = Double.parseDouble(form.getText(3));
                    exampleStartingSigma = Double.parseDouble(form.getText(4));
                    noOfGenes = Integer.parseInt(form.getText(5));
                }
                catch (NumberFormatException nfe)
                {
                    JOptionPane.showMessageDialog(null, "Malformed numbers format", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                EngineConstants.c1Attribute = c1Attribute;
                EngineConstants.c2Attribute = c2Attribute;
                EngineConstants.mAttribute = mAttribute;
                EngineConstants.sigmaMinimum = sigmaMinimum;
                EngineConstants.exampleStartingSigma = exampleStartingSigma;
                EngineConstants.noOfGenes = noOfGenes;


                f.dispose();
            }
        });

        f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        f.getContentPane().add(form, BorderLayout.NORTH);
        JPanel p = new JPanel();
        p.add(submit);
        f.getContentPane().add(p, BorderLayout.SOUTH);
        f.pack();
        f.setVisible(true);
    }
}
