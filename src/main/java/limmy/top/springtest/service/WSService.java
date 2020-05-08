package limmy.top.springtest.service;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import limmy.top.springtest.type.WSServerInitialzer;
import org.springframework.stereotype.Service;

/**
 * @author Limmy
 * @date 2020/5/4 11:37 上午
 */
@Service("WSService")
public class WSService {
    public void init() {
        EventLoopGroup main = new NioEventLoopGroup();
        EventLoopGroup son = new NioEventLoopGroup();
        try {
            ServerBootstrap server = new ServerBootstrap();
            server.group(main, son)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new WSServerInitialzer());
            ChannelFuture future = server.bind(8088).sync();
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            main.shutdownGracefully();
            son.shutdownGracefully();
        }
    }
}
