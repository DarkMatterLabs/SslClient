package sslclient;


import org.jboss.netty.channel.*;
import org.jboss.netty.handler.ssl.*;
import org.jboss.netty.handler.ssl.util.InsecureTrustManagerFactory;

import javax.net.ssl.SSLEngine;
import java.io.File;

import static sslclient.Config.getFileProperty;

public class PipelineFactory implements ChannelPipelineFactory {

	public ChannelPipeline getPipeline() throws Exception {
        ChannelPipeline pipeline = Channels.pipeline();

		File privateKey = getFileProperty("privateKey");
		File publicKey = getFileProperty("publicKey");
		SslContext clientContext =
				new JdkSslClientContext(null,
						publicKey, privateKey, null,
						null, InsecureTrustManagerFactory.INSTANCE,
						null, null,
						0, 0);

        SSLEngine engine = clientContext.newEngine();
        engine.setUseClientMode(true);

        pipeline.addLast("ssl", new SslHandler(engine));
        pipeline.addLast("handler", new Handler());
        return pipeline;
    }

}
