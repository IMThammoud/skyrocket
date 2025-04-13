package com.skyrocket.repository;

import com.skyrocket.model.articles.Notebook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NotebookRepository extends JpaRepository<Notebook, Integer> {
    int countByShelve_Id(UUID shelveId);
}
