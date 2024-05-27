package nuts.lib.document.data_access;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

abstract class JpaEntityDocument {

    @Repository
    interface JpaSampleRepository extends JpaRepository<JpaRepositoryDocument.JpaSampleEntity, Long> {
    }

}
