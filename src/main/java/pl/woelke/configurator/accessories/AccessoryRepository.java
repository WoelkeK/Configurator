package pl.woelke.configurator.accessories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AccessoryRepository extends JpaRepository<Accessory, Long> {

    @Query("SELECT a FROM Accessory a WHERE a.id IN :ids")
    List<Accessory> findByIds(@Param("ids") List<Long> ids);

}
