package org.diabblog.domain;
import org.springframework.roo.addon.layers.repository.jpa.RooJpaRepository;

@RooJpaRepository(domainType = Entry.class)
public interface EntryRepository {
}
