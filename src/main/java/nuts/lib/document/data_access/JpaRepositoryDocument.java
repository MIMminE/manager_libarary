package nuts.lib.document.data_access;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

abstract class JpaRepositoryDocument {

    @Entity
    static class JpaSampleEntity{

        @Id @GeneratedValue(strategy = GenerationType.AUTO)
        Long id;

    }
}
