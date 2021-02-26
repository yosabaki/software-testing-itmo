package me.artem.ustinov.backend

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource

@RepositoryRestResource
interface BookRepository : JpaRepository<Book, Long> {}