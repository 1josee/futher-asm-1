package com.consoleApp;

import java.util.List;

/**
 * @author <Tran Nhat Tien - s3919657>
 */
public interface RentalManager {
    void addRentalAgreement(RentalAgreement rentalAgreement);
    void updateRentalAgreement(String agreementId, RentalAgreement updatedRentalAgreement);
    void deleteRentalAgreement(String agreementId);
    List<RentalAgreement> getAllRentalAgreements();
}
