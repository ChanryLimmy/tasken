package limmy.top.springtest.type;

import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import limmy.top.springtest.service.PublisherService;
import limmy.top.springtest.service.SettingService;


/**
 * @author Limmy
 * @date 2020/5/4 11:51 上午
 */
public class ObserverHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    private PublisherService publisherService = new PublisherService();

    private SettingService settingService = new SettingService();

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {
        String content = textWebSocketFrame.text();
        System.out.println("接收到:" + content);
        /*
        1 获取 WSsetting
        2 name1,name2,name3 订阅 publisher
         */
        String[] codes = content.split(" ");
        switch (codes[0]) {
            case "1":
                // 退订 publisher
                if (codes.length < 2)
                    break;
                String[] names_ = codes[1].split(" ");
                for (String name : names_) {
                    Publisher publisher = publisherService.getPublisher(name);
                    if (publisher != null) {
                        publisher.removeObserver(channelHandlerContext.channel());
                        channelHandlerContext.channel().writeAndFlush(new TextWebSocketFrame("1$limmy$" + publisher.getName()));
                    }
                }
                break;
            case "2":
                // 订阅 publisher
                if (codes.length < 2)
                    break;
                String[] names = codes[1].split(" ");
                for (String name : names) {
                    Publisher publisher = publisherService.getPublisher(name);
                    if (publisher != null) {
                        publisher.addObserver(channelHandlerContext.channel());
                        channelHandlerContext.channel().writeAndFlush(new TextWebSocketFrame("5$limmy$" + publisher.getName()));
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("连接:" + ctx.channel().remoteAddress());
        super.handlerAdded(ctx);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("移除:" + ctx.channel().remoteAddress());
        super.handlerRemoved(ctx);
    }
}
