package cz.ptw.databasebrowser.view.rest.repository;

import cz.ptw.databasebrowser.model.Connection;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Spring data rest repository for Connection entity
 */
@RepositoryRestResource(collectionResourceRel = "connections", path = "connections")
public interface ConnectionRestRepository extends PagingAndSortingRepository<Connection, Integer> {
}
