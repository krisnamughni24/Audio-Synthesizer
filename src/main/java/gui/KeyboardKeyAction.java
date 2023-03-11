package gui;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import musical.Note;

public class KeyboardKeyAction extends AbstractAction
{

	private static final long serialVersionUID = 1L;

	private KeyboardGUI keyboardGUI;
	private Note note;
	private boolean release;

	public KeyboardKeyAction(KeyboardGUI keyboardGUI, Note note, boolean release)
	{
		this.keyboardGUI = keyboardGUI;
		this.note = note;
		this.release = release;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (this.release)
		{
			this.keyboardGUI.releaseNoteButton(this.note);
			return;
		}

		this.keyboardGUI.pushNoteButton(this.note);
	}

	public boolean isConfiguredForButtonRelease()
	{
		return release;
	}
}
