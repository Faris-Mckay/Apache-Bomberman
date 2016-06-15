package com.apache;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.apache.game.GameService;
import com.apache.net.BombermanChannelInitializer;
import com.apache.net.BombermanContext;
import com.apache.net.NetworkConstants;
import com.apache.net.msg.MessageRepository;
import com.apache.util.Utility;
import com.apache.util.parser.MessageRepositoryParser;
import com.google.common.collect.ImmutableSet;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.ResourceLeakDetector;

/**
 * Initializes the individual modules to launch {@link Bomberman}.
 *
 * @author Juan Ortiz <http://github.org/TheRealJP>
 */
public final class Server {

	/**
	 * The {@link ExecutorService} that will execute startup tasks.
	 */
	private final ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(),
			new ThreadFactoryBuilder().setNameFormat("BombermanInitializationThread").build());

	/**
	 * The {@link BombermanContext} that this {@code Server} will be managed with.
	 */
	private final BombermanContext context = new BombermanContext();

	/**
	 * The repository containing data for incoming messages.
	 */
	private final MessageRepository messageRepository = new MessageRepository();

	/**
	 * F package-private constructor to discourage external instantiation
	 * outside of the {@code com.apache} package.
	 */
	Server() {
	}

	/**
	 * Creates {@link Bomberman} by initializing all of the individual modules.
	 *
	 * @throws Exception
	 *             If any exceptions are thrown during initialization.
	 */
	public void init() throws Exception {
		Utility.log("Bomberman is being initialized...");

		initAsyncTasks();
		initGame();
		service.shutdown();
		service.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
		context.getService().awaitRunning();
		bind();

		Utility.log("Bomberman is now online on port {" + NetworkConstants.PORT + "}!");
	}

	/**
	 * Initializes the Netty implementation. Will block indefinitely until the
	 * {@link ServerBootstrap} is bound.
	 *
	 * @throws Exception
	 *             If any exceptions are thrown while binding.
	 */
	private void bind() throws Exception {
		ServerBootstrap bootstrap = new ServerBootstrap();
		EventLoopGroup loopGroup = new NioEventLoopGroup();

		ResourceLeakDetector.setLevel(NetworkConstants.RESOURCE_LEAK_DETECTION);

		bootstrap.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
		bootstrap.childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
		bootstrap.group(loopGroup);
		bootstrap.channel(NioServerSocketChannel.class);
		bootstrap.childHandler(new BombermanChannelInitializer(context, messageRepository));
		bootstrap.bind(NetworkConstants.PORT).syncUninterruptibly();

		ImmutableSet<Integer> preferred = NetworkConstants.PREFERRED_PORTS;
		if (!preferred.contains(NetworkConstants.PORT)) {
			Utility.log("The preferred ports for Bomberman servers are {" + "}.");
		}
	}

	/**
	 * Initializes the {@link GameService} asynchronously, does not wait for it
	 * to enter a {@code RUNNING} state.
	 *
	 * @throws Exception
	 *             If any exceptions are thrown during initialization of the
	 *             {@code GameService}.
	 */
	private void initGame() throws Exception {
		context.getService().startAsync();
	}

	/**
	 * Executes all startup tasks asynchronously in the background using
	 * {@link ExecutorService}.
	 *
	 * @throws Exception
	 *             If any exceptions are thrown while executing startup tasks.
	 */
	private void initAsyncTasks() throws Exception {
		service.execute(new MessageRepositoryParser(messageRepository));
	}
}
