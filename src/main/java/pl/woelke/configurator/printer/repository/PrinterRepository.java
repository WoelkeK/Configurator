package pl.woelke.configurator.printer.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import pl.woelke.configurator.printer.model.Printer;

public interface PrinterRepository extends CrudRepository<Printer, Long>, JpaSpecificationExecutor<Printer> {
}
