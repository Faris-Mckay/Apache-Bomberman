package com.apache.game;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

import com.apache.engine.Task;
import com.apache.engine.task.impl.CleanupTask;
import com.apache.engine.task.impl.SessionLoginTask;
import com.apache.game.entity.Player;
import com.apache.util.BlockingExecutorService;
import com.apache.util.EntityList;

public class Lobby {

	private static final Lobby lobby = new Lobby();

	private BlockingExecutorService backgroundLoader = new BlockingExecutorService(Executors.newSingleThreadExecutor());

	private GameEngine engine;

	private int lobbyId, port;

	private EntityList<Player> players = new EntityList<Player>(30);

	public Lobby() {
		backgroundLoader.submit(new Callable<Object>() {
			@Override
			public Object call() throws Exception {
				// load npcs
				System.out.println("Loading NPCs?");
				return null;
			}
		});
	}

	public BlockingExecutorService getBackgroundLoader() {
		return backgroundLoader;
	}

	public void init(GameEngine engine) throws Exception {
		this.engine = engine;

		this.registerGlobalEvents();
	}

	private void registerGlobalEvents() {
		submit(new CleanupTask());
	}

	public void submit(Task task) {
		this.engine.pushTask(task);
	}

	public GameEngine getEngine() {
		return engine;
	}

	public void load(final Player player) {
		engine.submitWork(new Runnable() {
			@Override
			public void run() {
				player.getChannel().pipeline().context("handler").write(player);
				engine.pushTask(new SessionLoginTask(player));
			}
		});
	}

	public EntityList<Player> getPlayers() {
		return players;
	}

	public void setLobbyId(int lobbyId) {
		this.lobbyId = lobbyId;
	}

	public int getLobbyId() {
		return lobbyId;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getPort() {
		return port;
	}

	public static Lobby getLobby() {
		return lobby;
	}
}
