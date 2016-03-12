package org.fog.gui.dialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.UIManager;

import org.fog.gui.core.Graph;
import org.fog.gui.core.Sensor;
import org.fog.gui.core.SpringUtilities;
import org.fog.gui.core.VmNode;
import org.fog.gui.core.Node;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class AddSensor extends JDialog {
	private static final long serialVersionUID = -511667786177319577L;
	
	private final Graph graph;
	
	private JTextField sensorName;
	private JComboBox parentDevice;
	private JComboBox distribution;
	private JTextField uniformLowerBound;
	private JTextField uniformUpperBound;
	private JTextField deterministicValue;
	private JTextField normalMean;
	private JTextField normalStdDev;
	

	/**
	 * Constructor.
	 * 
	 * @param frame the parent frame
	 */
	public AddSensor(final Graph graph, final JFrame frame) {
		this.graph = graph;
		
		setLayout(new BorderLayout());

		add(createInputPanelArea(), BorderLayout.CENTER);
		add(createButtonPanel(), BorderLayout.PAGE_END);
		// show dialog
		setTitle("Add Sensor");
		setModal(true);
		setPreferredSize(new Dimension(350, 400));
		setResizable(false);
		pack();
		setLocationRelativeTo(frame);
		setVisible(true);

	}

	private JPanel createButtonPanel() {

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
		
		JButton okBtn = new JButton("Ok");
		JButton cancelBtn = new JButton("Cancel");
		
		cancelBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
            	setVisible(false);
            }
        });

		okBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean catchedError = false;
				if (sensorName.getText() == null || sensorName.getText().length() < 1) {
					prompt("Please type Sensor name", "Error");
				} else if (distribution.getSelectedIndex() < 0) {
					prompt("Please select Emission time distribution", "Error");
				} else {
					double normalMean_ = -1;
					double normalStdDev_ = -1;
					double uniformLow_ = -1;
					double uniformUp_ = -1;
					double deterministicVal_ = -1;
					
					String dist = (String)distribution.getSelectedItem();
					if(dist.equals("Normal")){
						try {
							normalMean_ = Double.parseDouble(normalMean.getText());
							normalStdDev_ = Double.parseDouble(normalStdDev.getText());
						} catch (NumberFormatException e1) {
							catchedError = true;
							prompt("Input should be numerical character", "Error");
						}
						if(!catchedError){
							Sensor sensor = new Sensor(sensorName.getText().toString(), (String)distribution.getSelectedItem(),
											normalMean_, normalStdDev_, uniformLow_, uniformUp_, deterministicVal_);
							graph.addNode(sensor);
							setVisible(false);
						}
					} else if(dist.equals("Uniform")){
						try {
							uniformLow_ = Double.parseDouble(uniformLowerBound.getText());
							uniformUp_ = Double.parseDouble(uniformUpperBound.getText());
						} catch (NumberFormatException e1) {
							catchedError = true;
							prompt("Input should be numerical character", "Error");
						}
						if(!catchedError){
							Sensor sensor = new Sensor(sensorName.getText().toString(), (String)distribution.getSelectedItem(),
									normalMean_, normalStdDev_, uniformLow_, uniformUp_, deterministicVal_);
							graph.addNode(sensor);
							setVisible(false);
						}
					} else if(dist.equals("Deterministic")){
						try {
							deterministicVal_ = Double.parseDouble(deterministicValue.getText());
						} catch (NumberFormatException e1) {
							catchedError = true;
							prompt("Input should be numerical character", "Error");
						}
						if(!catchedError){
							Sensor sensor = new Sensor(sensorName.getText().toString(), (String)distribution.getSelectedItem(),
									normalMean_, normalStdDev_, uniformLow_, uniformUp_, deterministicVal_);
							graph.addNode(sensor);
							setVisible(false);
						}
					}
					
					
					
					
					
				}
			}
		});

		buttonPanel.add(Box.createHorizontalGlue());
		buttonPanel.add(okBtn);
		buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
		buttonPanel.add(cancelBtn);
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

		return buttonPanel;
	}

	private JPanel createInputPanelArea() {
	    String[] distributionType = {"vm"};
 
        //Create and populate the panel.
        JPanel springPanel = new JPanel(new SpringLayout());
        springPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		
		JLabel lName = new JLabel("Name: ");
		springPanel.add(lName);
		sensorName = new JTextField();
		lName.setLabelFor(sensorName);
		springPanel.add(sensorName);
		
		JLabel distLabel = new JLabel("Distribution Type: ", JLabel.TRAILING);
		springPanel.add(distLabel);	
		distribution = new JComboBox(distributionType);
		distLabel.setLabelFor(distribution);
		distribution.setSelectedIndex(-1);
		distribution.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				
			}
		});
		springPanel.add(distribution);		
		
		JLabel lSize = new JLabel("Size: ");
		springPanel.add(lSize);	
		tfSize = new JTextField();
		lSize.setLabelFor(tfSize);
		springPanel.add(tfSize);		
		
		JLabel lPes = new JLabel("Pes: ");
		springPanel.add(lPes);	
		tfPes = new JTextField();
		lPes.setLabelFor(tfPes);
		springPanel.add(tfPes);	
		
		JLabel lMips = new JLabel("Mips: ");
		springPanel.add(lMips);	
		tfMips = new JTextField();
		lMips.setLabelFor(tfMips);
		springPanel.add(tfMips);		
		
		JLabel lRam = new JLabel("Ram: ");
		springPanel.add(lRam);
		tfRam = new JTextField();
		lRam.setLabelFor(tfRam);
		springPanel.add(tfRam);
				
       //Lay out the panel.
        SpringUtilities.makeCompactGrid(springPanel,
                                        6, 2,        //rows, columns
                                        6, 6,        //initX, initY
                                        6, 6);       //xPad, yPad
		return springPanel;
	}
	
    public static void setUIFont (javax.swing.plaf.FontUIResource f){
        java.util.Enumeration keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
          Object key = keys.nextElement();
          Object value = UIManager.get (key);
          if (value != null && value instanceof javax.swing.plaf.FontUIResource)
            UIManager.put (key, f);
          }
    }
    
	private void prompt(String msg, String type){
		JOptionPane.showMessageDialog(AddSensor.this, msg, type, JOptionPane.ERROR_MESSAGE);
	}
}
