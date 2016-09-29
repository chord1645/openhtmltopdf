package com.openhtmltopdf.testcases;

import com.openhtmltopdf.css.parser.FSRGBColor;
import com.openhtmltopdf.pdfboxout.PdfBoxFSFont;
import com.openhtmltopdf.pdfboxout.PdfBoxOutputDevice;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import com.openhtmltopdf.pdfboxout.event.PageEventListener;
import com.openhtmltopdf.render.PageBox;
import com.openhtmltopdf.render.RenderingContext;

import java.awt.*;
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
    class PaintLine implements PageEventListener{

        @Override
        public void beforeFinish(RenderingContext c, PageBox page) {
            ///////////////////////////
            Rectangle content = page.getPrintClippingBounds(c);
            //TODO 每页画一条线
            double a1 = content.getX();
            double a2 = content.getY();
            double a3 = content.getWidth();
            double a4 = content.getHeight();
            BasicStroke bs_1 = new BasicStroke(25, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL);
            PdfBoxOutputDevice outputDevice = (PdfBoxOutputDevice) c.getOutputDevice();
            outputDevice.setStroke(bs_1);
            outputDevice.setColor(new FSRGBColor(0, 0, 0));
            outputDevice.drawLine((int) content.getX(), (int) content.getY(),
                    (int) (page.getOuterPageWidth() - content.getX()), (int) content.getY());
            outputDevice.drawLine((int) content.getX(), (int) (content.getHeight() + content.getX()),
                    (int) (page.getOuterPageWidth() - content.getX()), (int) (content.getHeight() + content.getX()));
            PdfBoxFSFont font1 = (PdfBoxFSFont) c.getFont(outputDevice.getFontSpecification());
            outputDevice.setFont(font1);
            outputDevice.drawStringFast("测试", (float) content.getX(), (float) content.getX() - 150, null, font1.getFontDescription().get(0), 280);
        }
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
            new PdfRendererBuilder()
                    .fontMap(map)
//                    .listener(new PaintLine())
                    .withUri(url).toStream(os)
                    .run();

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