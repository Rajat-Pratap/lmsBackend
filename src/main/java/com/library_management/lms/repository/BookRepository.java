package com.library_management.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.library_management.lms.model.Books;

@Repository
public interface BookRepository extends JpaRepository<Books, Integer> {

}
