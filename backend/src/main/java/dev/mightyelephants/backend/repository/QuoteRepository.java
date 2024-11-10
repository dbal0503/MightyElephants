package dev.mightyelephants.backend.repository;

import dev.mightyelephants.backend.model.Quote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuoteRepository extends JpaRepository<Quote, Long> {
}