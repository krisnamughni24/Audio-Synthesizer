package gui.panels;

import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.io.File;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import configuration.Preset;
import gui.SynthesizerGUI;
import utils.FileHelper;
import utils.MarshallHelper;

public class PresetPanel extends JPanel
{
	private static final long serialVersionUID = -6660971192667561820L;

	private SynthesizerGUI synthGUI;
	private Preset currentPreset;

	private JLabel presetLabel;

	public PresetPanel(SynthesizerGUI synthGUI, Preset preset)
	{
		super(new FlowLayout(FlowLayout.CENTER));
		super.setBorder(new BevelBorder(BevelBorder.RAISED));

		this.synthGUI = synthGUI;

		initializePanel();
		setCurrentPreset(preset);
	}

	public Preset getCurrentPreset()
	{
		return currentPreset;
	}

	public void setCurrentPreset(Preset preset)
	{
		this.currentPreset = preset;
		presetLabel.setText(preset.getName());
	}

	private void initializePanel()
	{
		presetLabel = new JLabel();
		JLabel label = new JLabel("Preset:");
		JButton saveButton = new JButton("Save");
		JButton loadButton = new JButton("Load");

		saveButton.addActionListener(e -> savePreset());
		loadButton.addActionListener(e -> loadPreset());

		this.add(label);
		this.add(presetLabel);
		this.add(saveButton);
		this.add(loadButton);
	}

	private void loadPreset()
	{
		Optional<File> optionalFile = fileDialogLoad();

		if (optionalFile.isPresent())
		{
			Optional<Object> optionalPreset = readPreset(optionalFile.get());
			if (optionalPreset.isPresent() && optionalPreset.get() instanceof Preset preset)
			{
				synthGUI.applyPreset(preset);
				this.currentPreset = preset;
			}
			return;
		}

		showMessage("Could not load preset!");
	}

	private void savePreset()
	{
		Optional<File> optionalFile = fileDialogSave();
		if (optionalFile.isPresent())
		{
			Preset preset = synthGUI.buildPreset();
			preset.setName(showPresetNameInput());
			writePreset(optionalFile.get(), preset);

			showMessage("Preset successfully saved!");
			setCurrentPreset(preset);
			return;
		}

		showMessage("Could not save Preset!");
	}

	private void showMessage(String message)
	{
		JOptionPane.showMessageDialog(synthGUI, message);
	}

	private String showPresetNameInput()
	{
		return JOptionPane.showInputDialog(synthGUI, "Name for new preset:");
	}

	private Optional<File> fileDialogSave()
	{
		return fileDialog(FileDialog.SAVE);
	}

	private Optional<File> fileDialogLoad()
	{
		return fileDialog(FileDialog.LOAD);
	}

	private Optional<File> fileDialog(int mode)
	{
		FileDialog fd = new FileDialog(synthGUI, "Choose a file", mode);
		fd.setDirectory("./");
		fd.setFile("preset.xml");
		fd.setVisible(true);
		String fileName = fd.getFile();
		String directory = fd.getDirectory();

		if (fileName == null)
		{
			return Optional.empty();
		}
		return Optional.of(new File(directory + fileName));
	}

	private void writePreset(File file, Preset preset)
	{
		MarshallHelper marshaller = new MarshallHelper(Preset.class);
		FileHelper.writeFile(file, marshaller.marshall(preset));
	}

	private Optional<Object> readPreset(File file)
	{
		String fileContent = FileHelper.readFile(file);
		return new MarshallHelper(Preset.class).unmarshall(fileContent);
	}
}
