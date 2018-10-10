package com.project.project1.Controller;

import com.project.project1.Entity.URLShortenerDTO;
import com.project.project1.Service.URLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@Component
@EnableAutoConfiguration
public class URLResource {
    @Autowired
    private URLService urlService;

    @GetMapping(value = "/shorten")
    public String saveURL(@ModelAttribute("originalURL") String originalURL, ModelMap model) {
        URLShortenerDTO temp = urlService.saveUrl(originalURL);
        model.addAttribute("tinyURL", temp.getShortenedURL());
        return "form";
    }

    @GetMapping("/reverse/{shortenedURL}")
    public RedirectView getURL(@PathVariable String shortenedURL) {
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("http://" + urlService.getURL(shortenedURL).getOriginalURL());

        return redirectView;
    }


    @GetMapping("/form")
    public String greeting() {
        return "form";
    }

}
