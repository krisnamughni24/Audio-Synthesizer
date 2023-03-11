package gui;

import java.util.Map;

import javax.swing.JComponent;
import javax.swing.KeyStroke;

import musical.NoteName;
import utils.DefaultConstants;

public class KeyboardKeyBinder
{
	private KeyboardGUI keyboardGUI;
	private Map<NoteName, String> keyBindings;
	private static final int INPUT_MAP_CONDITION = JComponent.WHEN_IN_FOCUSED_WINDOW;

	public KeyboardKeyBinder(KeyboardGUI keyboardGUI, Map<NoteName, String> keyBindingMap)
	{
		this.keyboardGUI = keyboardGUI;
		this.keyBindings = keyBindingMap;
	}

	public KeyboardKeyBinder(KeyboardGUI keyboardGUI)
	{
		this(keyboardGUI, DefaultConstants.getKeyBindings());
	}

	public NoteButton configureKeyBindings(NoteButton noteButton)
	{
		String key = getKey(noteButton);

		configurePush(noteButton, key);
		configureRelease(noteButton, key);
		return noteButton;
	}

	public void removeKeyBindings(NoteButton noteButton)
	{
		noteButton.getInputMap(INPUT_MAP_CONDITION).clear();
		noteButton.getActionMap().clear();
	}

	private String getKey(NoteButton noteButton)
	{
		return keyBindings.get(noteButton.getNote().getNoteName());
	}

	private NoteButton configureRelease(NoteButton noteButton, String key)
	{
		return configureKeyBinding(noteButton, key, true);
	}

	private NoteButton configurePush(NoteButton noteButton, String key)
	{
		return configureKeyBinding(noteButton, key, false);
	}

	private NoteButton configureKeyBinding(NoteButton noteButton, String key, boolean release)
	{
		KeyStroke keyStrokeRelease = getKeyStroke(key, release);
		String actionName = getActionName(key, release);
		KeyboardKeyAction action = getAction(noteButton, release);

		bindActionToKeyStroke(noteButton, keyStrokeRelease, actionName, action);

		return noteButton;
	}

	private void bindActionToKeyStroke(NoteButton noteButton, KeyStroke keyStroke,
			String actionName, KeyboardKeyAction action)
	{
		noteButton.getInputMap(INPUT_MAP_CONDITION).put(keyStroke, actionName);
		noteButton.getActionMap().put(actionName, action);
	}

	private KeyboardKeyAction getAction(NoteButton noteButton, boolean release)
	{
		return new KeyboardKeyAction(keyboardGUI, noteButton.getNote(), release);
	}

	private String getActionName(String key, boolean release)
	{
		String unformattedName = release ? "release_%s" : "push_%s";
		return String.format(unformattedName, key);
	}

	private KeyStroke getKeyStroke(String key, boolean release)
	{
		String unformattedKeyString = release ? "released %s" : "%s";
		String keyStrokeString = String.format(unformattedKeyString, key);
		KeyStroke keyStroke = KeyStroke.getKeyStroke(keyStrokeString);
		if (keyStroke == null)
		{
			throw new IllegalArgumentException("Could not parse argument to key stroke!");
		}
		return keyStroke;
	}
}
