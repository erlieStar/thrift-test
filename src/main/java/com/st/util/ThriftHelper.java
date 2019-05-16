package com.st.util;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThriftHelper {


    public static final int maxWorkThreads = 50;
    public static final int maxReadBufferBytes = 1024 * 1024 * 100;

    public static final int BINARY = 1;
    public static final int COMPACT = 2;

    public static TProtocol getProtocol(TTransport transport, int type) {

        TProtocol protocol = null;
        try {

            // 传输层
            // TSocket —— 使用阻塞式 I/O 进行传输，是最常见的模式
            // TFramedTransport —— 使用非阻塞方式，按块的大小进行传输，类似于 Java 中的 NIO
            // TNonblockingTransport —— 使用非阻塞方式，用于构建异步客户端

            // 传输协议
            // TBinaryProtocol —— 二进制编码格式进行数据传输
            // TCompactProtocol —— 高效率的、密集的二进制编码格式进行数据传输
            // TJSONProtocol —— 使用 JSON 的数据编码协议进行数据传输
            // TSimpleJSONProtocol —— 只提供 JSON 只写的协议，适用于通过脚本语言解析

            if (BINARY == type) {

                transport.open();

                protocol = new TBinaryProtocol(transport);
            } else if (COMPACT == type) {

                transport.open();

                protocol = new TCompactProtocol(transport);

            }

        } catch (Exception e) {
            System.out.println("failed connect to thrift server");
        }

        return protocol;

    }

    public static TTransport getTransport(String host, int port, int type) {
        int socketTimeout = 60000;
        int connectTimeout = 60000;

        TTransport transport = null;
        try {

            // 传输层
            // TSocket —— 使用阻塞式 I/O 进行传输，是最常见的模式
            // TFramedTransport —— 使用非阻塞方式，按块的大小进行传输，类似于 Java 中的 NIO
            // TNonblockingTransport —— 使用非阻塞方式，用于构建异步客户端

            // 传输协议
            // TBinaryProtocol —— 二进制编码格式进行数据传输
            // TCompactProtocol —— 高效率的、密集的二进制编码格式进行数据传输
            // TJSONProtocol —— 使用 JSON 的数据编码协议进行数据传输
            // TSimpleJSONProtocol —— 只提供 JSON 只写的协议，适用于通过脚本语言解析

            if (BINARY == type) {
                transport = new TSocket(host, port, socketTimeout, connectTimeout);

            } else if (COMPACT == type) {

                transport = new TFramedTransport.Factory()
                    .getTransport(new TSocket(host, port, socketTimeout, connectTimeout));

            }

        } catch (Exception e) {
            System.out.println("failed connect to thrift server ");
            throw new RuntimeException("can not connect to host " + host + ":" + port);
        } finally {

            // if (null != transport) {
            // transport.close();
            // }

        }

        return transport;

    }

}
