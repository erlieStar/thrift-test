package com.st;

import com.st.thrift.Content;
import com.st.util.BaseSentimentServiceHelper;
import com.st.util.JsonUtil;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: lilimin
 * @Date: 2019/5/15 17:00
 */
public class TestThriftByJmeter extends AbstractJavaSamplerClient {

    private BaseSentimentServiceHelper baseSentimentServiceHelper;

    /**
     * 初始化方法，每个线程都会执行一次
     * @param context
     */
    @Override
    public void setupTest(JavaSamplerContext context) {
        String ip = context.getParameter("ip");
        String port = context.getParameter("port");
        baseSentimentServiceHelper = new BaseSentimentServiceHelper();
        baseSentimentServiceHelper.setHost(ip);
        baseSentimentServiceHelper.setPort(Integer.valueOf(port));
        super.setupTest(context);
    }

    /**
     * 设置参数
     * @return
     */
    @Override
    public Arguments getDefaultParameters() {
        Arguments arguments = new Arguments();
        arguments.addArgument("ip", "mt011");
        arguments.addArgument("port", "60000");
        arguments.addArgument("total", "100");
        return arguments;
    }

    /**
     * 运行测试方法
     * @param javaSamplerContext
     * @return
     */
    @Override
    public SampleResult runTest(JavaSamplerContext javaSamplerContext) {
        SampleResult sampleResult = new SampleResult();
        sampleResult.setDataEncoding("utf-8");
        sampleResult.sampleStart();
        int total = Integer.valueOf(javaSamplerContext.getParameter("total"));

        Map<String, Object> map = new HashMap<>();
        List<Content> contentList = new ArrayList<>();
        for (int i = 0; i < total; i++) {
            Content content = new Content();
            content.setMid(String.valueOf(i));
            content.setText("情感确实曼妙。有时机遇恰巧会眷顾了爱情。在擦肩而过的人群中谁能与你并肩同行；谁能理会同你一道上船、驶往爱的彼岸。在滚滚红尘中，只有俩厢情愿，情投意合，才能算是一见钟情，顺理成章。");
            contentList.add(content);
        }
        map.put("data", contentList);
        long begin = System.currentTimeMillis();
        String result = baseSentimentServiceHelper.getSentiInfo(JsonUtil.obj2String(map));
        long cost = System.currentTimeMillis() - begin;
        System.out.println("cost " + cost);
        if (result.equals("")) {
            sampleResult.setSuccessful(false);
            sampleResult.setDataType(SampleResult.TEXT);
        } else {
            sampleResult.setSuccessful(true);
            sampleResult.setDataType(SampleResult.TEXT);
        }
        sampleResult.sampleEnd();
        return sampleResult;
    }


    /**
     * 完成测试
     * @param context
     */
    @Override
    public void teardownTest(JavaSamplerContext context) {
        super.teardownTest(context);
    }

    /**
     * 这个是本地跑用的，执行测试类的时候，并不执行这个方法
     * @param args
     */
    public static void main(String[] args) {
        Arguments arguments = new Arguments();
        arguments.addArgument("ip", "mt011");
        arguments.addArgument("port", "60000");
        arguments.addArgument("total", "100");
        JavaSamplerContext context = new JavaSamplerContext(arguments);
        TestThriftByJmeter jmeter = new TestThriftByJmeter();
        jmeter.setupTest(context);
        jmeter.runTest(context);
        jmeter.teardownTest(context);
    }

}
