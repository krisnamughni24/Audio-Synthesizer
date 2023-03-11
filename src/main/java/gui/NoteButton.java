package gui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;

import gui.listeners.NoteButtonClickListener;
import musical.Note;

public class NoteButton extends JButton
{
	private static final long serialVersionUID = 1L;

	private Note note;
	private boolean isPushed;
	private List<NoteButtonClickListener> clickListeners = new ArrayList<>();

	public NoteButton(Note note)
	{
		this.note = note;
		this.setText(note.toString());
		this.addActionListener(e -> triggerNoteButtonClickListeners());

		setupGraphics(note);
	}

	private void setupGraphics(Note note)
	{
		setOpaque(true);
		if (note.isSharpended())
		{
			setBackground(Color.BLACK);
			return;
		}
	}

	public boolean isPushed()
	{
		return isPushed;
	}

	public void setPushed(boolean isPushed)
	{
		this.isPushed = isPushed;
		if (isPushed)
		{
			setForeground(Color.red);
			return;
		}
		setForeground(Color.black);
	}

	public Note getNote()
	{
		return note;
	}

	public void addNoteButtonClickListener(NoteButtonClickListener clickListener)
	{
		clickListeners.add(clickListener);
	}

	private void triggerNoteButtonClickListeners()
	{
		for (NoteButtonClickListener listener : clickListeners)
		{
			listener.noteButtonWasClicked(this);
		}
	}
}
