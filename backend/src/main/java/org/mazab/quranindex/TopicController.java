package org.mazab.quranindex;

import org.mazab.quranindex.pojos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TopicController {
    @Autowired
    private TopicService service;

    @RequestMapping(method = RequestMethod.GET, value = {"/", "/api", "/healthy", "/api/healthy"})
    public String healthcheck() {
        return "running!";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/api/topics")
    public ShortTopic[] getRootTopics() {
        return service.getRootTopics();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/api/topics/{id}")
    public LongTopic getTopicById(@PathVariable String id) {
        return service.getTopicById(id);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/api/entries/{parent}/{surah}/{ayah}")
    public EntryWithParent getEntryByID(@PathVariable String parent, @PathVariable String surah, @PathVariable String ayah) {
        return service.getEntry(parent, surah, ayah);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/api/entries/{surah}/{ayah}")
    public LongEntry getEntry(@PathVariable int surah, @PathVariable int ayah) {
        return service.getEntry(surah, ayah);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/api/topics/search/{lang}")
    public SearchResults searchTopics(@PathVariable String lang, @RequestParam(name = "value") String searchString) {
        return service.searchTopicsNames(searchString, lang.toLowerCase());
    }
}
