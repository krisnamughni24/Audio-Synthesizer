package gui.panels;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import gui.VariableSlider;

public abstract class ConfigurationPanel extends JPanel
{
	private static final long serialVersionUID = 1L;

	private List<ChangeListener> listeners = new ArrayList<>();

	public ConfigurationPanel(int rows, int cols)
	{
		super(new GridLayout(rows, cols));
		super.setBorder(new BevelBorder(BevelBorder.RAISED));
	}

	public void addChangeListener(ChangeListener changeListener)
	{
		listeners.add(changeListener);
	}

	protected void triggerChangeListeners()
	{
		listeners.forEach(listener -> listener.stateChanged(new ChangeEvent(this)));
	}

	protected VariableSlider getNewSlider(String name, int min, int max, double value)
	{
		return new VariableSlider(name, min, max, (int) value);
	}

	protected VariableSlider getNewSlider(String name, double min, double max, double value)
	{
		return new VariableSlider(name, min, max, (int) value);
	}

	public abstract Object getConfig();

	protected abstract void applyConfiguration();

	protected abstract void updateConfiguration();
}
