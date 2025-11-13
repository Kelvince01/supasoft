package com.supasoft.partnerservice.repository;

import com.supasoft.common.enums.Status;
import com.supasoft.partnerservice.entity.SupplierItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for SupplierItem entity
 */
@Repository
public interface SupplierItemRepository extends JpaRepository<SupplierItem, Long> {
    
    Optional<SupplierItem> findBySupplierIdAndItemId(Long supplierId, Long itemId);
    
    List<SupplierItem> findByItemId(Long itemId);
    
    List<SupplierItem> findBySupplierIdAndStatus(Long supplierId, Status status);
    
    @Query("SELECT si FROM SupplierItem si WHERE si.itemId = :itemId AND si.status = :status")
    List<SupplierItem> findSuppliersByItem(@Param("itemId") Long itemId, @Param("status") Status status);
    
    @Query("SELECT si FROM SupplierItem si WHERE si.supplier.id = :supplierId AND si.status = :status")
    List<SupplierItem> findItemsBySupplier(@Param("supplierId") Long supplierId, @Param("status") Status status);
    
    @Query("SELECT si FROM SupplierItem si WHERE si.itemId = :itemId AND si.isPreferred = true AND si.status = :status")
    Optional<SupplierItem> findPreferredSupplierForItem(@Param("itemId") Long itemId, @Param("status") Status status);
    
    @Query("SELECT si FROM SupplierItem si WHERE si.itemId = :itemId AND si.status = :status ORDER BY si.costPrice ASC")
    List<SupplierItem> findBestPriceForItem(@Param("itemId") Long itemId, @Param("status") Status status);
    
    @Query("SELECT si FROM SupplierItem si WHERE si.itemId = :itemId AND si.status = :status ORDER BY si.leadTimeDays ASC")
    List<SupplierItem> findFastestSupplierForItem(@Param("itemId") Long itemId, @Param("status") Status status);
    
    @Query("SELECT COUNT(si) FROM SupplierItem si WHERE si.supplier.id = :supplierId AND si.status = :status")
    Long countItemsBySupplier(@Param("supplierId") Long supplierId, @Param("status") Status status);
}

