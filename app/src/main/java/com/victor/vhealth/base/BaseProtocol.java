package com.victor.vhealth.base;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseStream;
import com.lidroid.xutils.http.client.HttpRequest;
import com.victor.vhealth.global.Constant;
import com.victor.vhealth.util.FileUtils;
import com.victor.vhealth.util.IOUtils;
import com.victor.vhealth.util.LogUtils;
import com.victor.vhealth.util.MD5Utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

/** 网络请求的基类
 * Created by Victor on 2016/7/6.
 */
public abstract class BaseProtocol<T> {

    public T loadData(int page) throws IOException, HttpException {
        String result;
        result = getDataFromLocal(page);
        if (result == null) {
            result = getDataFromNet(page);
        }
        result = getDataFromNet(page);
        T t = parserJsonData(result);
        return t;
    }

    private String getDataFromLocal(int page) {
        File cacheFile = getCacheFile(page);
        if (cacheFile.exists()) {
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader(cacheFile));
                // 获取缓存保存时间
                long savedTime = Long.parseLong(reader.readLine());
                // 判断缓存是否过期
                if (System.currentTimeMillis() - savedTime <= Constant.CACHE_LIMITED_TIME) {
                    // 读取缓存内容
                    String result = reader.readLine();
                    LogUtils.sf("读取本地缓存文件" + cacheFile.getAbsolutePath());
                    return result;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                IOUtils.close(reader);
            }
        }
        return null;
    }

    private File getCacheFile(int page) {
        String dir = FileUtils.getDir("json");
        String name = getUrlKey();
        Map<String, String> extraParmas = getExtraParmas();
        if (extraParmas != null) {
            // 详情页的情况下 改变命名方法
            for (Map.Entry<String, String> info:
                    extraParmas.entrySet()){
                String value = info.getValue();
                // url + 参数值
                name += value;
            }
        }
        name = MD5Utils.md5Encode(name);
        File file = new File(dir, name);
        return file;
    }

    protected abstract String getUrlKey();

    private String getDataFromNet(int page) throws HttpException, IOException {
        HttpUtils httpUtils = new HttpUtils();
        String url = Constant.URL.BASE + getUrlKey();
        RequestParams params = new RequestParams();
        if (page != 0) {
            params.addQueryStringParameter("page", page + "");
        }
        Map<String, String> paramsMap = getExtraParmas();
        if (paramsMap != null) {
            for (Map.Entry<String, String> info :
                    paramsMap.entrySet()) {
                String key = info.getKey();
                String value = info.getValue();
                params.addQueryStringParameter(key, value);
            }
        }
        LogUtils.sf("url" + url);
        ResponseStream responseStream = httpUtils.sendSync(HttpRequest.HttpMethod.GET, url, params);
        String result = responseStream.readString();
        // 保存网络数据到本地
        if (result != null) {
            BufferedWriter writer = null;
            try {
                File cacheFile = getCacheFile(page);
                writer = new BufferedWriter(new FileWriter(cacheFile));
                writer.write(System.currentTimeMillis() + "");
                writer.write("\r\n");
                writer.write(result);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                IOUtils.close(writer);
            }
        }
        return result;

    }

    protected abstract Map<String,String> getExtraParmas();

    protected abstract T parserJsonData(String result);
}
