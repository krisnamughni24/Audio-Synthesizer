package gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import configuration.EnvelopeConfiguration;
import configuration.FilterConfiguration;
import configuration.Preset;
import controlling.Controller;
import controlling.Player;
import gui.panels.EnvelopeConfigurationPanel;
import gui.panels.FilterConfigurationPanel;
import gui.panels.OscillatorSelectionPanel;
import gui.panels.PresetPanel;
import utils.DefaultConstants;

public class SynthesizerGUI extends JFrame
{
	private static final long serialVersionUID = 1L;

	private Controller controller;

	private FilterConfigurationPanel filterPanel;
	private EnvelopeConfigurationPanel envelopePanel;
	private KeyboardGUI keyboard;
	private JPanel mainPanel;
	private OscillatorSelectionPanel selectionPanel;
	private PresetPanel presetPanel;

	public SynthesizerGUI(Controller controller)
	{
		super("Java Audio Synthesizer");
		this.controller = controller;
		setupGUI();
	}

	public Preset buildPreset()
	{
		return new Preset(filterPanel.getConfig(), envelopePanel.getConfig());
	}

	public void applyPreset(Preset preset)
	{
		controller.applyPreset(preset);
	}

	private void setupGUI()
	{
		setDefaultLookAndFeelDecorated(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		initializeFilterPanel();
		initializeEnvelopePanel();
		initializeSelectionPanel();
		initializePresetPanel();
		initializeKeyboard();

		setupMainPanel();

		this.add(mainPanel);

		this.pack();
		this.setVisible(true);
	}

	private void setupMainPanel()
	{
		mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(filterPanel, BorderLayout.EAST);
		mainPanel.add(envelopePanel, BorderLayout.WEST);
		mainPanel.add(keyboard, BorderLayout.SOUTH);
		mainPanel.add(selectionPanel, BorderLayout.NORTH);
		mainPanel.add(presetPanel, BorderLayout.CENTER);
	}

	private void initializeKeyboard()
	{
		Player player = controller.getPlayer();
		keyboard = new KeyboardGUI(player);
	}

	private void initializeEnvelopePanel()
	{
		EnvelopeConfiguration envConfig = DefaultConstants.getEnvelopeConfig();
		envelopePanel = new EnvelopeConfigurationPanel(envConfig);
		envelopePanel.addChangeListener(
				e -> controller.applyEnvelopeConfiguration(envelopePanel.getConfig()));
	}

	private void initializeFilterPanel()
	{
		FilterConfiguration filterConfig = DefaultConstants.getFilterConfig();
		filterPanel = new FilterConfigurationPanel(filterConfig);
		filterPanel.addChangeListener(
				e -> controller.applyFilterConfiguration(filterPanel.getConfig()));
	}

	private void initializeSelectionPanel()
	{
		selectionPanel = new OscillatorSelectionPanel();
		selectionPanel.addOscillatorSelectionListener(type -> {
			controller.setOscillatorType(type);
			this.applyPreset(this.buildPreset());
		});
	}

	private void initializePresetPanel()
	{
		presetPanel = new PresetPanel(this, DefaultConstants.getPreset());
	}

	public static void main(String[] args)
	{
		Controller controller = new Controller();
		new SynthesizerGUI(controller);
	}
}
