package com.project.project1.Service;

import com.project.project1.Entity.URLShortener;
import com.project.project1.Entity.URLShortenerDTO;
import com.project.project1.Repository.URLRepository;
import com.project.project1.Utils.Base62;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Component
public class URLService {


    @Autowired
    private URLRepository urlRepository;

//    private final String domain;
//
//    @Autowired
//    public URLService(@Value("${domain.shortener}") String domain) {
//        this.domain = domain;
//    }

    /**
     * Reverse the original URL from the shortened URL
     */
    public URLShortenerDTO getURL(String shortenURL) {
        URLShortenerDTO dto = new URLShortenerDTO();
        if (validateURL(shortenURL)) {

            // Resolve a shortened URL to the initial ID.
            long id = Base62.toBase10(shortenURL);
            id = id / 10000000;
            // Now find your database-record with the ID found
            Optional<URLShortener> urlShortener = urlRepository.findById(id);

            if (urlShortener.isPresent()) {

                URLShortener url = urlShortener.get();
                dto.setId(url.getId().toString());
                dto.setShortenedURL(shortenURL);
                dto.setOriginalURL(url.getOriginalURL());

            }
        }
        return dto;
    }

    /**
     * Save an original URL to database and then
     * generate a shortened URL.
     */
    public URLShortenerDTO saveUrl(String originalURL) {
        URLShortener url = new URLShortener();
        if (validateURL(originalURL)) {
            originalURL = sanitizeURL(originalURL);
            // Quickly check the originalURL is already entered in our system.
            Optional<URLShortener> exitURL = urlRepository.findByOriginalURL(originalURL);

            if (exitURL.isPresent()) {
                // Retrieved from the system.
                url = exitURL.get();

            } else {
                // Otherwise, save a new original URL
                url.setId(urlRepository.getIdWithNextUniqueId());
                url.setOriginalURL(originalURL);
                url = urlRepository.save(url);
            }
        }

        return generateURLShorterner(url);
    }

    private String sanitizeURL(String url) {
        if (url.substring(0, 7).equals("http://"))
            url = url.substring(7);

        if (url.substring(0, 8).equals("https://"))
            url = url.substring(8);

        if (url.charAt(url.length() - 1) == '/')
            url = url.substring(0, url.length() - 1);
        return url;
    }

    private URLShortenerDTO generateURLShorterner(URLShortener url) {

        URLShortenerDTO dto = new URLShortenerDTO();
        dto.setId(url.getId().toString());
        dto.setOriginalURL(url.getOriginalURL());


        // Generate shortenedURL via base62 encode.
        String shortenedURL = "localhost:8080/reverse" + "/" + Base62.toBase62(url.getId().intValue());
        dto.setShortenedURL(shortenedURL);
        return dto;
    }

    private boolean validateURL(String originalURL) {
        return true;
    }
}
