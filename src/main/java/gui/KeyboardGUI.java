package gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import controlling.Player;
import gui.listeners.NoteButtonClickListener;
import musical.Note;

public class KeyboardGUI extends JPanel
{

	private static final long serialVersionUID = 1L;

	private static final int BUTTON_WIDTH = 50;
	private static final int BUTTON_HEIGHT_FULLTONE = 200;
	private static final int BUTTON_HEIGHT_SEMITONE = 150;

	private Player player;
	private List<NoteButton> noteButtons = new ArrayList<>();
	private KeyboardKeyBinder keyBinder = new KeyboardKeyBinder(this);

	private boolean configuredKeyListeners;

	public KeyboardGUI(Player keyboard)
	{
		super(new FlowLayout());
		this.player = keyboard;
		super.setBorder(new BevelBorder(BevelBorder.RAISED));

		setupKeySupportCheckBox();
		setupKeyboardButtons();
	}

	public void pushNoteButton(Note note)
	{
		Optional<NoteButton> optionalNoteButton = getNoteButton(note);
		if (optionalNoteButton.isPresent())
		{
			NoteButton noteButton = optionalNoteButton.get();
			if (noteButton.isPushed())
			{
				return;
			}
			noteButton.setPushed(true);
		}

		player.noteOn(note);
	}

	public void releaseNoteButton(Note note)
	{
		Optional<NoteButton> optionalNoteButton = getNoteButton(note);
		if (optionalNoteButton.isPresent())
		{
			optionalNoteButton.get().setPushed(false);
		}

		player.noteOff(note);
	}

	private void setupKeySupportCheckBox()
	{
		JCheckBox activateKeysCheckBox = new JCheckBox("Activate Computer Keyboard");
		activateKeysCheckBox.addItemListener(e -> {
			int stateChange = e.getStateChange();
			if (stateChange == ItemEvent.SELECTED)
			{
				configureKeyListeners();
				return;
			}
			removeKeyListeners();
		});

		super.add(activateKeysCheckBox);
	}

	private void configureKeyListeners()
	{
		if (configuredKeyListeners)
		{
			return;
		}

		configuredKeyListeners = true;
		for (NoteButton noteButton : noteButtons)
		{
			keyBinder.configureKeyBindings(noteButton);
		}
	}

	private void removeKeyListeners()
	{
		for (NoteButton noteButton : noteButtons)
		{
			keyBinder.removeKeyBindings(noteButton);
		}
		configuredKeyListeners = false;
	}

	private Optional<NoteButton> getNoteButton(Note note)
	{
		Predicate<? super NoteButton> predicate = noteButton -> noteButton.getNote().equals(note);
		Stream<NoteButton> filteredNoteButtonStream = noteButtons.stream().filter(predicate);

		return filteredNoteButtonStream.findFirst();
	}

	private void setupKeyboardButtons()
	{
		addNewButtons();

		assignClickListeners();
	}

	private void addNewButtons()
	{
		for (Note note : Note.getNoteList())
		{
			NoteButton noteButton = createNewButton(note);
			super.add(noteButton, new GridBagConstraints());
			noteButtons.add(noteButton);
		}
	}

	private NoteButton createNewButton(Note note)
	{
		NoteButton noteButton = new NoteButton(note);

		int buttonHeight = note.isSharpended() ? BUTTON_HEIGHT_SEMITONE : BUTTON_HEIGHT_FULLTONE;

		noteButton.setPreferredSize(new Dimension(BUTTON_WIDTH, buttonHeight));

		return noteButton;
	}

	private void assignClickListeners()
	{
		NoteButtonClickListener listener = noteButton -> {
			if (!configuredKeyListeners)
			{
				configureKeyListeners();
			}

			if (!noteButton.isPushed())
			{

				player.noteOn(noteButton.getNote());
				noteButton.setPushed(true);
				return;
			}

			player.noteOff(noteButton.getNote());
			noteButton.setPushed(false);
		};

		noteButtons.forEach(btn -> btn.addNoteButtonClickListener(listener));
	}
}
