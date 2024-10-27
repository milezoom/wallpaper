package io.milezoom.downloaderbing;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


public class App {
    public static String getImageURL() throws IOException {
        String imageURL = "";
        Document doc = Jsoup.connect("https://bing.com").get();
        Elements links = doc.select("a[href]");
        for (org.jsoup.nodes.Element link : links) {
            if (link.attr("href").contains("/th?")) {
                imageURL = "https://bing.com" + link.attr("href");
            }
        }
        return imageURL;
    }

    public static void downloadImage(String downloadUrl, String destinationPath) throws MalformedURLException, IOException {
        URL url = URI.create(downloadUrl).toURL();
        ReadableByteChannel rbc = Channels.newChannel(url.openStream());
        FileOutputStream fos = new FileOutputStream(destinationPath);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
    }

    public static void main(String[] args) throws Exception {
        String imageDir = System.getProperty("user.home");
        imageDir += "/Pictures";

        Path imagePath = Path.of(imageDir);
        if (Files.notExists(imagePath)) {
            throw new Exception("Picture directory in user home not exists");
        }

        imageDir += "/wallpaper-desktop.jpg";
        String imageUrl = getImageURL();
        downloadImage(imageUrl, imageDir);
    }
}
