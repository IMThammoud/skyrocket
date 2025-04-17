package com.skyrocket.repository;

import com.skyrocket.model.Shelve;
import com.skyrocket.model.articles.electronics.Notebook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NotebookRepository extends JpaRepository<Notebook, Integer> {
    int countByShelve_Id(UUID shelveId);

    List<Notebook> findByShelve(Shelve shelve);
}
