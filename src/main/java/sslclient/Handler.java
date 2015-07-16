package sslclient;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static sslclient.Convert.toHex;

public class Handler extends SimpleChannelUpstreamHandler {

	private static Logger LOG = LoggerFactory.getLogger(Main.class);

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
		super.messageReceived(ctx, e);
		ChannelBuffer buff = (ChannelBuffer)e.getMessage();
		String hex = toHex(buff.array(), buff.readableBytes());
		System.out.print(hex);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
		LOG.error("Exception in SSL handler", e.getCause());
		System.exit(1);
	}

	@Override
	public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		super.channelClosed(ctx, e);
		LOG.info("SSL connection closed");
		System.exit(0);
	}
}
