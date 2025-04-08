package com.skyrocket.repository;

import com.skyrocket.model.articles.Notebook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotebookRepository extends JpaRepository<Notebook, Integer> {
}
