/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jsm;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author MOISES
 */
public class UrlInfo {

    public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36";

    public static void main(String[] args) {
        OGObject obj = new UrlInfo().processUrl("http://osamigosdopresidentelula.blogspot.com.br/2017/03/paulo-preto-vai-detonar-o-ninho-tucano.html");
        System.out.println(obj);
    }

   

    public OGObject processUrl(String url) {
        if(url.isEmpty())return  null;
        Document doc = documentByUrl(url);
        if (doc == null) {
            return null;
        }
        String baseUri = getBasicUrlFromDocument(doc);
        String imagemAlternativa = getAlternativeImageFromDocument(doc);
        String tituleAlternativo = getDefaultTitle(doc);
                
        OGObject og = processDocument(doc);
        Elements meta = doc.select("meta");
        // PEGAR DADOS BASEADO EM TAGS META

        og.setOgVideoType(meta.select("[property=og:type]").attr("content").trim());
        og.setOgSiteName(meta.select("[property=og:site_name]").attr("content").trim());
        og.setOgVideo(meta.select("[property=og:video]").attr("content").trim());
        og.setOgAudioType(meta.select("[property=og:audio:type]").attr("content").trim());
        og.setOgImage(meta.select("[property=og:image]").attr("content").trim());
        og.setOgVideoWidh(meta.select("[property=og:video:width]").attr("content").trim());
        og.setOgTitle(meta.select("[property=og:title]").attr("content").trim());
        og.setOgAudio(meta.select("[property=og:audio]").attr("content").trim());
        og.setOgType(meta.select("[property=og:type]").attr("content").trim());
        og.setOgUrl(meta.select("[property=og:url]").attr("content").trim());
        og.setOgImageWidh(meta.select("[property=og:image:width]").attr("content").trim());
        og.setOgDescription(meta.select("[property=og:description]").attr("content").trim());
        og.setOgLocale(meta.select("[property=og:locale]").attr("content").trim());
        og.setOgVideoHeight(meta.select("[property=og:video:height]").attr("content").trim());
        og.setOgImageHeight(meta.select("[property=og:image:height]").attr("content").trim());
        og.setOgDeterminer(meta.select("[property=og:determiner]").attr("content").trim());
        og.setOgLocaleAuternate(meta.select("[property=og:locale:alternate]").attr("content").trim());
        og.setOgUriDefault(getBasicUrlFromDocument(doc));
        // PEGAR DADOS BASEADO EM TAGS ITEMPROP        
        if (og.getOgImage().isEmpty()) {
            og.setOgImage(doc.select("[itemprop=image]").attr("src"));
        }
        if (og.getOgDescription().isEmpty()) {
            og.setOgDescription(doc.select("[itemprop=description]").attr("content"));
        }
        if (og.getOgDescription().isEmpty()) {
            og.setOgDescription(doc.select("[name=description]").attr("content"));
        }
        if (og.getOgUrl().isEmpty()) {
            og.setOgUrl(doc.select("[itemprop=url]").attr("src"));
        }

        if (og.getOgTitle().isEmpty()) {
            og.setOgTitle(doc.select("[itemprop=title]").text());
        }

        if (og.getOgUrl().isEmpty()) {
            og.setOgUrl(baseUri);
        }

        if (og.getOgTitle().isEmpty()) {
            og.setOgTitle(tituleAlternativo);
        }

        if (og.getOgDescription().isEmpty()) {
            Elements title = doc.select("title");
            if (title.size() > 0) {
                og.setOgTitle(title.get(0).text());
            }
        }

        if (og.getOgImage().isEmpty()) {
            og.setOgImage(imagemAlternativa);
        }

        
        return og;
    }

    private void analisarOg(OGObject og, Document doc) {

    }

    private OGObject processDocument(Document doc) {
        OGObject og = new OGObject();

        return og;
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public Document documentByUrl(String url) {
        Document doc = null;
        try {
            doc = Jsoup.connect(url)
                    .ignoreContentType(true)
                    .timeout(100000)
                    .userAgent(USER_AGENT)
                    .get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return doc;
    }

    private String getBasicUrlFromDocument(Document doc) {
        String base = doc.baseUri();       
        base = base.substring(base.indexOf("//") + 2, base.length());        
        base = base.substring(0, base.indexOf("/"));      

        return base.toUpperCase();

    }

    private String getDefaultTitle(Document document) {
        String title = document.title();        
        return title;
    }

    private String getAlternativeImageFromDocument(Document document) {
        String imagemSelecionada = "";
        Elements imagens = document.getElementsByTag("img");
          
        for (Element e : imagens) {
            
            if (!e.attr("src").isEmpty() || !e.attr("srcset").isEmpty() ) {
                
                if (!e.attr("src").contains(".gif") && !e.attr("src").contains("base64")) {
                    int height = 0;//= Integer.parseInt(e.attr("height"));
                    int widh = 0;
                    int proporcao=0;
                    try {
                        height = Integer.parseInt(e.attr("height"));
                        widh = Integer.parseInt(e.attr("width"));
                        proporcao = widh / height;
                    } catch (Exception ex) {
                    }

                                        
                    if (proporcao >=1 && proporcao <=2 && widh >=300) {
                        imagemSelecionada = e.attr("src");
                        System.out.println("Image x " + imagemSelecionada);
                    }

                    break;
                }

            }

        }

        if (imagemSelecionada.length() > 0) {
            if (imagemSelecionada.startsWith("/") || !imagemSelecionada.startsWith("http")) {
                String baseUrl = getBasicUrlFromDocument(document).toLowerCase();
                if (!baseUrl.endsWith("/") && !imagemSelecionada.startsWith("/")) {
                    imagemSelecionada = baseUrl + "/" + imagemSelecionada;
                } else {
                    imagemSelecionada = baseUrl.concat(imagemSelecionada);
                }

            }
        }
        return imagemSelecionada;
    }

}
