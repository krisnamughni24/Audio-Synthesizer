package gui;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;

public class VariableSlider extends JPanel
{
	private static final long serialVersionUID = 1L;
	private JSlider slider;
	private JLabel valueLabel;
	private JLabel descriptionLabel;

	private int resolution = 1;

	public VariableSlider(String name, int min, int max, int value)
	{
		super(new BorderLayout());
		slider = new JSlider(min, max, value);
		valueLabel = new JLabel(value + "");
		descriptionLabel = new JLabel(name);

		slider.addChangeListener(e -> valueLabel.setText(slider.getValue() + ""));
		valueLabel.setLabelFor(slider);
		descriptionLabel.setLabelFor(slider);

		super.add(slider, BorderLayout.EAST);
		super.add(descriptionLabel, BorderLayout.NORTH);
		super.add(valueLabel, BorderLayout.WEST);
	}

	public VariableSlider(String name, double min, double max, int value)
	{
		super(new BorderLayout());
		resolution = getNumberOfDecimalPlaces(max) * 10;
		slider = new JSlider((int) min * resolution, (int) max * resolution, value * resolution);
		valueLabel = new JLabel(value + "");
		descriptionLabel = new JLabel(name);

		slider.addChangeListener(e -> valueLabel.setText(slider.getValue() + ""));
		valueLabel.setLabelFor(slider);
		descriptionLabel.setLabelFor(slider);

		super.add(slider, BorderLayout.EAST);
		super.add(descriptionLabel, BorderLayout.NORTH);
		super.add(valueLabel, BorderLayout.WEST);
	}

	private int getNumberOfDecimalPlaces(double max)
	{
		String doubleAsString = Double.toString(max);
		int indexOfDecimalPoint = doubleAsString.indexOf(".");
		return doubleAsString.length() - (indexOfDecimalPoint + 1);
	}

	public void addChangeListener(ChangeListener changeListener)
	{
		slider.addChangeListener(changeListener);
	}

	public double getValue()
	{
		return (double)slider.getValue() / resolution;
	}
}
