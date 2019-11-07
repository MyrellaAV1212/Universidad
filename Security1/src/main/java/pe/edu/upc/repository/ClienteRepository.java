package pe.edu.upc.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.edu.upc.entity.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer>{
	
	@Query("from Cliente c where c.dniCliente like %:dniCliente%")
	List<Cliente> findByDniCliente(String dniCliente);

	@Query("from Cliente c where c.datosCliente like %:datosCliente%")
	List<Cliente> findByDatosCliente(String datosCliente);

	@Query("from Cliente c where c.emailCliente like %:emailCliente%")
	List<Cliente> findByEmailCliente(String emailCliente);

	List<Cliente> findByFechaNacimientoCliente(Date fechaNacimientoCliente);


}
