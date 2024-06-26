package mandepaulo.wallet.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import mandepaulo.wallet.exceptions.BeneficiaryException;
import mandepaulo.wallet.exceptions.CustomerException;
import mandepaulo.wallet.exceptions.WalletException;
import mandepaulo.wallet.models.Beneficiary;
import mandepaulo.wallet.models.dto.BeneficiaryDTO;
import mandepaulo.wallet.service.BeneficiaryService;

@RestController
@Controller

public class BeneficiaryController {

    @RequestMapping("/beneficiarios")
    public String index() {
        return "Beneficiaries/index";
    }

    @Autowired
    BeneficiaryService beneficiaryService;

    /*--------------------------------------------   Add Beneficiary Mapping -------------------------------------------------*/
    @PostMapping("/beneficiarios-add")
    public ResponseEntity<Beneficiary> addBeneneficiaryMapping(@RequestBody Beneficiary beneficiary,
            @RequestParam String key) throws BeneficiaryException, WalletException, CustomerException {
        System.out.println(beneficiary);
        return new ResponseEntity<Beneficiary>(beneficiaryService.addBeneficiary(beneficiary, key),
                HttpStatus.ACCEPTED);

    }

    /*--------------------------------------------   View Beneficiary - walletId -------------------------------------------------*/
    @GetMapping("/view/walletId")
    public ResponseEntity<Beneficiary> getBeneneficiaryByWalletIdMapping(@RequestParam Integer walletId,
            @RequestParam String key) throws BeneficiaryException, CustomerException {

        return new ResponseEntity<Beneficiary>((Beneficiary) beneficiaryService.findAllByWallet(walletId),
                HttpStatus.FOUND);

    }

    /*--------------------------------------------   View Beneficiary - Name -------------------------------------------------*/
    @GetMapping("/view/name")
    public ResponseEntity<Beneficiary> getBeneneficiaryByNameMapping(@RequestParam String name,
            @RequestParam String key) throws BeneficiaryException, CustomerException {

        return new ResponseEntity<Beneficiary>(beneficiaryService.viewBeneficiary(name, key), HttpStatus.FOUND);

    }

    /*--------------------------------------------   View All Beneficiary Mapping -------------------------------------------------*/
    @GetMapping("/beneficiarios-viewall")
    public ResponseEntity<List<Beneficiary>> getAllBeneneficiaryMapping(@RequestParam String key)
            throws BeneficiaryException, CustomerException {

        return new ResponseEntity<List<Beneficiary>>(beneficiaryService.viewAllBeneficiary(key), HttpStatus.FOUND);

    }

    /*--------------------------------------------   Delete Beneficiary Mapping -------------------------------------------------*/
    @DeleteMapping("/delete")
    public ResponseEntity<Beneficiary> deleteBeneneficiaryMapping(@Valid @RequestBody BeneficiaryDTO beneficiary,
            @RequestParam String key) throws BeneficiaryException, CustomerException {

        return new ResponseEntity<Beneficiary>(beneficiaryService.deleteBeneficiary(key, beneficiary), HttpStatus.OK);

    }
}
