/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jsm;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author MOISES
 */
public class MetaTagAnalize {

    public static void main(String[] args) {
        new MetaTagAnalize().teste();
    }

    private void teste() {
        String url ="https://twitter.com/AndresWrites/status/836736292624850944";
        try {
            Document doc = Jsoup.connect(url).get();
            Elements meta = doc.select("meta");
            //System.out.println(meta);
            System.out.println("######################################");
            
            System.out.println("Title: "+meta.select("[property=og:title]").attr("content").trim());
            System.out.println("Url: "+meta.select("[property=og:url]").attr("content").trim());
            System.out.println("Type: "+meta.select("[property=og:type]").attr("content").trim());
            System.out.println("Description: "+meta.select("[property=og:description]").attr("content").trim());
            System.out.println("Image: "+meta.select("[property=og:image]").attr("content").trim());
            System.out.println("Locale: "+meta.select("[property=og:locale]").attr("content").trim());
            System.out.println("Site Name: "+meta.select("[property=og:site_name]").attr("content").trim());
            System.out.println("Markup URL"+meta.select("[property=op:markup_url]").attr("content").trim());
            System.out.println("Video: "+meta.select("[property=og:video]").attr("content").trim());
            System.out.println("Name: "+meta.select("[property=og:name]").attr("content").trim());
            
            
            System.out.println("Canonical: "+meta.select("[link=rel]").attr("content").trim());
            System.out.println("Title Site: "+doc.getElementsByTag("head").get(0).getElementsByTag("title").text());
            System.out.println("Autor: "+meta.select("[property=article:autor]").attr("content").trim());
            String homeUrl = url;
            homeUrl = homeUrl.substring(homeUrl.indexOf("//")+2, homeUrl.length());
            try {
                 homeUrl = homeUrl.substring(0, homeUrl.indexOf("/"));
            } catch (Exception e) {
            }
           
            System.out.println(homeUrl);
            System.out.println("Default Url");
            
            
            Elements image = doc.select("img");
            for(int i = 0;i < image.size();i++){
                System.out.println(image.get(i).attr("src"));  
                if(image.get(i).attr("src").contains(".png")){
                  //System.out.println(image.get(i).attr("src"));  
                }
                
            }
            String i = image.attr("abs:src");
            System.out.println(i);
            
            
            System.out.println("Imagem logo"+doc.getElementById("logo"));
            
            
            /*String text = null;
            Elements metaOgTitle = doc.select("meta[property=og:title]");
            if (metaOgTitle != null) {
                text = metaOgTitle.attr("content");
            } else {
                text = doc.title().trim();
            }

            String imageUrl = null;
            Elements metaOgImage = doc.select("meta[property=og:image]");
            if (metaOgImage != null) {
                imageUrl = metaOgImage.attr("content");
            }
            
            System.out.println(imageUrl);

            System.out.println(text);*/

        } catch (IOException ex) {
            Logger.getLogger(MetaTagAnalize.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public class Example {

        private String title;
        private String description;
        private String language;

        public Example() {
        }

        // setters and getters go here
        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        @Override
        public String toString() {
            return "Example{" + "title=" + title + ", description=" + description + ", language=" + language + '}';
        }

    }
}
