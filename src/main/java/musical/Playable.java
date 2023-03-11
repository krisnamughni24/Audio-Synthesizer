package musical;

import java.util.List;

import controlling.Player;

public interface Playable
{
	public void play(Player player);

	public List<Note> getNotes();
}
