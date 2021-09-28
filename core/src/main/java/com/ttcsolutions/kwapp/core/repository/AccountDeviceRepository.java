package com.ttcsolutions.kwapp.core.repository;

import java.util.Optional;

import com.ttcsolutions.kwapp.core.model.filter.AccountDeviceFilter;
import com.ttcsolutions.kwapp.core.model.response.AccountDeviceInfoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ttcsolutions.kwapp.core.model.entity.AccountDevice;

@Repository
public interface AccountDeviceRepository extends JpaRepository<AccountDevice, Long>,
		InsertUpdateRepository<AccountDevice>, JpaSpecificationExecutor<AccountDevice> {

	boolean existsById(Long id);

	boolean existsByDeviceCode(String deviceCode);

	boolean existsByDeviceCodeAndAccountId(String deviceCode, Long accountId);

	Optional<AccountDevice> findByAccountId(Long accountIdid);

	Optional<AccountDevice> findByDeviceId(Long deviceId);

	@Query("SELECT NEW com.ttcsolutions.kwapp.core.model.response.AccountDeviceInfoResponse(a.id, a.email, a.avatar, ad.deviceId," +
			" ad.deviceCode, ad.deviceName, ad.relationship, ad.status, ad.icon, ad.createdAt, ad.updatedAt)" +
			" FROM AccountDevice ad JOIN Account a ON ad.accountId = a.id" +
			" WHERE ((COALESCE(?#{#filter.deviceId}) IS NULL) OR (ad.deviceId = ?#{#filter.deviceId}))" +
			" AND ((COALESCE(?#{#filter.deviceCode}) IS NULL) OR (ad.deviceCode = ?#{#filter.deviceCode}))" +
			" AND ((COALESCE(?#{#filter.accountId}) IS NULL) OR (ad.accountId = ?#{#filter.accountId}))" +
			" AND ((COALESCE(?#{#filter.status}) IS NULL) OR (ad.status = ?#{#filter.status}))")
	Page<AccountDeviceInfoResponse> getAccountDeviceInfos(AccountDeviceFilter filter, Pageable pageable);
}
