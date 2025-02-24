package pl.woelke.configurator.printer;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface PrinterRepository extends CrudRepository<Printer, Long>, JpaSpecificationExecutor<Printer> {
}
