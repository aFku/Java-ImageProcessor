package org.rcbg.afku.ImageAdjusterApp.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProcessedImageRepository extends JpaRepository<ProcessedImage, Integer> {
    public List<ProcessedImage> getProcessedImagesByRawImage_OwnerUuid(String ownerUuid);
    public Optional<ProcessedImage> getProcessedImageByProcessedFilename(String processedFilename);
}
