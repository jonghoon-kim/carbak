package com.chabak.services;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.chabak.vo.Blog;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

//@Service("blogService")
public class BlogService {

    private static String clientID = "ZASiEiikXllOHewfCz5D"; //api 사용 신청시 제공되는 아이디
    private static String clientSecret = "ICb4IEklgs"; //패스워드

    public List<Blog> searchBlog(String keyword, int display, int start) {
        URL url;
        List<Blog> list = null;
        try {
            url = new URL("https://openapi.naver.com/v1/search/blog.xml?query=" + URLEncoder.encode(keyword, "UTF-8")
                    + (display != 0 ? "&display=" + display : "") + (start != 0 ? "&start=" + start : ""));
            URLConnection urlConn;

            //url 연결
            urlConn = url.openConnection();
            urlConn.setRequestProperty("X-naver-Client-Id", clientID);
            urlConn.setRequestProperty("X-naver-Client-Secret", clientSecret);

            //파싱 - 팩토리 만들고 팩토리로 파서 생성 (풀 파서 사용)
            XmlPullParserFactory factory;

            //factory = XmlPullParserFactory.newInstance();
            factory = new org.xmlpull.mxp1.MXParserFactory();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput((new InputStreamReader(urlConn.getInputStream())));


            int eventType = parser.getEventType();
            Blog b = null;
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.END_DOCUMENT: // 문서의 끝
                        break;
                    case XmlPullParser.START_DOCUMENT:
                        list = new ArrayList<Blog>();
                        break;
                    case XmlPullParser.START_TAG: {
                        String tag = parser.getName();
                        switch (tag) {
                            case "item":
                                b = new Blog();
                                break;
                            case "title":
                                if (b != null)
                                    b.setTitle(parser.nextText());
                                break;
                            case "link":
                                if (b != null)
                                    b.setLink(parser.nextText());
                                break;
                            case "bloggername":
                                if (b != null)
                                    b.setBloggername(parser.nextText());
                                break;
                            case "bloggerlink":
                                if (b != null)
                                    b.setBloggerlink(parser.nextText());
                                break;
                            case "description":
                                if (b != null)
                                    b.setDescription(parser.nextText());
                                break;
                            case "total":
                                if (b != null)
                                    b.setTotal(parser.nextText());
                                break;
                            case "postdate":
                                if (b != null)
                                    b.setPostdate(parser.nextText());
                                break;
                        }
                        break;
                    }

                    case XmlPullParser.END_TAG: {
                        String tag = parser.getName();
                        if (tag.equals("item")) {
                            list.add(b);
                            b = null;
                        }

                    }

                }
                eventType = parser.next();
            }

        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return list;
    }

}