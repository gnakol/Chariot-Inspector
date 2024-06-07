package fr_scapartois_auto.chariot_inspector.webservices;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface Webservices<T> {

    Page<T> all(Pageable pageable);

    T add(T e);

    T update(Long id, T e);

    void remove(Long id);

    Optional<T> getById(Long id);


}
