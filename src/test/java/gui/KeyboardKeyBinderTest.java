package gui;

import static org.mockito.Mockito.mock;

import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import controlling.Player;
import musical.Note;

public class KeyboardKeyBinderTest
{

	private KeyboardGUI keyboardGUIMock;
	private KeyboardKeyBinder keyBinder;

	@Before
	public void setupTest()
	{
		keyboardGUIMock = new KeyboardGUI(mock(Player.class));
		keyBinder = new KeyboardKeyBinder(keyboardGUIMock);
	}

	@Test
	public void testConfigureKeyBindings()
	{
		NoteButton exampleButton = buildExampleButton();

		keyBinder.configureKeyBindings(exampleButton);

		testSuccessfulKeyBinding(getInputMap(exampleButton), getActionMap(exampleButton));
		testCorrectPushReleaseBinding(getInputMap(exampleButton), getActionMap(exampleButton));
	}

	@Test
	public void testRemoveKeyBindings()
	{
		NoteButton exampleButton = buildExampleButton();

		keyBinder.configureKeyBindings(exampleButton);
		testSuccessfulKeyBinding(getInputMap(exampleButton), getActionMap(exampleButton));

		keyBinder.removeKeyBindings(exampleButton);
		testSuccessfulKeyBindingRemoval(getInputMap(exampleButton), getActionMap(exampleButton));
	}

	private ActionMap getActionMap(NoteButton exampleButton)
	{
		return exampleButton.getActionMap();
	}

	private NoteButton buildExampleButton()
	{
		Note exampleNote = new Note("C4");
		NoteButton noteButton = new NoteButton(exampleNote);
		return noteButton;
	}

	private InputMap getInputMap(NoteButton noteButton)
	{
		int inputMapCondition = JComponent.WHEN_IN_FOCUSED_WINDOW;
		InputMap inputMap = noteButton.getInputMap(inputMapCondition);
		return inputMap;
	}

	private void testSuccessfulKeyBinding(InputMap inputMap, ActionMap actionMap)
	{
		Assert.assertEquals(2, inputMap.size());
		Assert.assertEquals(2, actionMap.size());
	}

	private void testSuccessfulKeyBindingRemoval(InputMap inputMap, ActionMap actionMap)
	{
		Assert.assertEquals(0, inputMap.size());
		Assert.assertEquals(0, actionMap.size());
	}

	private void testCorrectPushReleaseBinding(InputMap inputMap, ActionMap actionMap)
	{
		for (KeyStroke keyStroke : inputMap.allKeys())
		{
			Object actionMapKey = inputMap.get(keyStroke);
			KeyboardKeyAction action = (KeyboardKeyAction) actionMap.get(actionMapKey);

			if (keyStroke.isOnKeyRelease())
			{
				Assert.assertTrue(action.isConfiguredForButtonRelease());
				return;
			}
			Assert.assertFalse(action.isConfiguredForButtonRelease());
		}
	}

}
