package me.yogeshwar.hibernate.shoppingcart.controllers;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import me.yogeshwar.hibernate.shoppingcart.models.Customer;

@RestController("customer")
@RequestMapping("/customer")
@Transactional
public class CustomerController {
	@Autowired
	SessionFactory sessionFactory;

	@GetMapping("/")
	public List<Customer> getCustomers() {
		return (List<Customer>) sessionFactory.getCurrentSession().createQuery("from Customer").list();
	}

	@PostMapping("/")
	public ResponseEntity<Object> saveCustomer(@RequestBody Customer customer) {
		try {
			Session session = sessionFactory.getCurrentSession();
			session.save(customer);
			return ResponseEntity.status(HttpStatus.CREATED).build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@PutMapping("/")
	public ResponseEntity<Object> updateCustomer(@RequestBody Customer customer) {
		try {
			Session session = sessionFactory.getCurrentSession();
			session.update(customer);
			return ResponseEntity.status(HttpStatus.ACCEPTED).build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteCustomer(@PathVariable("id") int id) {
		try {
			Session session = sessionFactory.getCurrentSession();
			Customer customer = session.get(Customer.class, id);
			if (customer == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
			session.delete(customer);
			return ResponseEntity.status(HttpStatus.ACCEPTED).build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
}
