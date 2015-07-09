package sslclient;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.*;

import static sslclient.Convert.toHex;

public class Handler extends SimpleChannelUpstreamHandler {
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
		super.messageReceived(ctx, e);
		ChannelBuffer buff = (ChannelBuffer)e.getMessage();
		String hex = toHex(buff.array(), buff.readableBytes());
		System.out.print(hex);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
		System.err.println("Exception cause in SSL connection");
		e.getCause().printStackTrace();
		System.exit(1);
	}

	@Override
	public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		super.channelClosed(ctx, e);
		System.err.println("SSL connection closed");
		System.exit(0);
	}
}
