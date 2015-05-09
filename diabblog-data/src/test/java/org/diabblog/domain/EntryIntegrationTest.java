package org.diabblog.domain;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.roo.addon.test.RooIntegrationTest;

@Configurable
@EnableSpringConfigured
@RooIntegrationTest(entity = Entry.class)
public class EntryIntegrationTest {
	
	@Autowired
	EntryRepository entryRepository;

    @Test
    public void testMarkerMethod() {
    }
}
