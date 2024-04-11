package org.rcbg.afku.ImageAdjusterApp.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RawImageRepository extends JpaRepository<RawImage, Integer>{
    Optional<RawImage> getRawImageByFilename(String filename);
}
