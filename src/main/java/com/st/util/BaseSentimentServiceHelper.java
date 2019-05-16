package com.st.util;

import com.st.thrift.BaseSentimentService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.cxf.annotations.DataBinding;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TTransport;

@Data
public class BaseSentimentServiceHelper {

    /** 词云服务地址 */
    String host;

    /** 词云服务端口 */
    int port;

    /**
     * 获取分词信息和词云正负向
     * @param text 博文信息的字符串
     * @return
     */
    public String getSentiInfo(String text) {

        TTransport transport = null;
        try {

            transport = ThriftHelper.getTransport(host, port,
                ThriftHelper.BINARY);
            TProtocol protocol = ThriftHelper.getProtocol(transport, ThriftHelper.BINARY);
            BaseSentimentService.Iface client = new BaseSentimentService.Client(protocol);

            String result = client.getSentimentInfo(text);

            return result;

        } catch (Exception e) {
            System.out.println("getSentimentInfo failed");
            e.printStackTrace();
        } finally {
            if (null != transport) {
                transport.close();
            }
        }

        return "";

    }

}
