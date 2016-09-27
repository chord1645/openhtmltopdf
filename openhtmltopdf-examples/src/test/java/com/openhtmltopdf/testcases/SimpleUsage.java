package com.openhtmltopdf.testcases;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class SimpleUsage {
    public static void main(String[] args) {
        new SimpleUsage().exportToPdfBox(
//                "http://hmp.qa.cim120.cn/user/diagnose/healthguidance/pdf/template?taskId=1096&userId=1903847",
//                "http://fed.dev.cim120.cn/static/src/temp/hmp/hmp-2.4.5/pdf_template.html",
                "http://localhost:8080/test/pdf/template",
//                "http://hmp.dev.cim120.cn/home",
//                "http://bbs.nga.cn/thread.php?fid=459",
                "D:/Work/tmp/tmp.pdf");
    }

    public void exportToPdfBox(String url, String out) {
        OutputStream os = null;
        Map<String, String> map = new HashMap<String, String>() {
            {
                put("D:\\Work\\workspace\\hmp\\src\\main\\webapp\\WEB-INF\\config\\msyh.ttf", "Microsoft YaHei");
                put("D:\\Work\\tmp\\songti.TTF", "宋体");
            }
        };
        try {
            os = new FileOutputStream(out);
            new PdfRendererBuilder().fontMap(map)
                    .withUri(url).toStream(os).run();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                os.close();
            } catch (IOException e) {
            }
        }
    }
}