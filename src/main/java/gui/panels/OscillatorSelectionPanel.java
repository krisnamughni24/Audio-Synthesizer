package gui.panels;

import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import gui.listeners.OscillatorTypeSelectionListener;
import synthesis.OscillatorType;

public class OscillatorSelectionPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	private JComboBox<OscillatorType> comboBox;
	private List<OscillatorTypeSelectionListener> selectionListeners = new ArrayList<>();

	public OscillatorSelectionPanel()
	{
		super(new FlowLayout(FlowLayout.CENTER));
		super.setBorder(new BevelBorder(BevelBorder.RAISED));

		initializePanel();
	}

	private void initializePanel()
	{
		JLabel label = new JLabel("Select your voice:");

		comboBox = new JComboBox<>(OscillatorType.values());
		comboBox.setSelectedIndex(0);
		comboBox.addActionListener(e -> triggerSelectionListeners());

		this.add(label);
		this.add(comboBox);
	}

	public void addOscillatorSelectionListener(OscillatorTypeSelectionListener listener)
	{
		selectionListeners.add(listener);
	}

	private void triggerSelectionListeners()
	{
		for (OscillatorTypeSelectionListener selectionListener : selectionListeners)
		{
			selectionListener.oscillatorHasBeenSelected(getSelectedVoice());
		}
	}

	private OscillatorType getSelectedVoice()
	{
		return (OscillatorType) comboBox.getSelectedItem();
	}
}
