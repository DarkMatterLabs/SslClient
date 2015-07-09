package sslclient;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class Main {

	private static Logger LOG = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) {
		ClientBootstrap bootstrap = new ClientBootstrap(
				new NioClientSocketChannelFactory(
					Executors.newCachedThreadPool(),
					Executors.newCachedThreadPool()));

		try {
			Handler handler = new Handler();
			bootstrap.setPipelineFactory(new PipelineFactory());
			InetSocketAddress address = Config.getAddressProperty("address");
			LOG.info("Connecting to " + address);
			ChannelFuture future = bootstrap.connect(address);
			// Wait until the connection attempt succeeds or fails.
			Channel channel = future.awaitUninterruptibly().getChannel();
			if (!future.isSuccess()) {
				bootstrap.releaseExternalResources();
				throw new RuntimeException(future.getCause());
			}
			LOG.info("Connected");

			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String line;
			while((line = br.readLine()) != null) {
				LOG.info("Sending: " + line);
				byte[] data = Convert.fromHex(line);
				ChannelFuture write = channel.write(ChannelBuffers.wrappedBuffer(data));
				write.awaitUninterruptibly();
				LOG.info("Sent");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.exit(1);
		}
	}
}
